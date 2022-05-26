package com.example.demo.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;

@RestController
@RequestMapping("/app/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;




    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }


    /**
     * 1. 회원가입 API
     * [POST] /users/signUp
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("/sign-up")
    public BaseResponse<PostSignUpInRes> createUser(@RequestBody PostSignUpReq postSignUpReq) {

        //이메일
        if(postSignUpReq.getEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        if(!isRegexEmail(postSignUpReq.getEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }

        //핸드폰 번호
        if(postSignUpReq.getPhone() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PHONE);
        }
        if(!isRegexPhone(postSignUpReq.getPhone())){
            return new BaseResponse<>(POST_USERS_INVALID_PHONE);
        }

        //비밀번호
        if(postSignUpReq.getPassword() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }
        if (!isRegexPassword(postSignUpReq.getPassword())) {
            return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
        }


        try{
            PostSignUpInRes postSignUpInRes = userService.createUser(postSignUpReq);
            return new BaseResponse<>(postSignUpInRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 2. 이메일 유저 조회
     * [GET] /users?email={email}
     * @return BaseResponse<GetPhoneUserRes>
     */
    /**
     * 3. 핸드폰번호 유저 조회
     * [GET] /users?phone={phone}
     * @return BaseResponse<GetPhoneUserRes>
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse checkUserByPhone(@RequestParam(required = false) String phone, @RequestParam(required = false) String email){
        try {
            if(email != null){
                GetEmailUserRes getEmailUserRes = new GetEmailUserRes(userProvider.checkEmail(email) == 1? true : false);
                return new BaseResponse<>(getEmailUserRes);
            }

            if(phone != null){
                if(userProvider.checkPhone(phone) == 0) {
                    return new BaseResponse(new GetPhoneUserRes(false, null));
                }
                GetPhoneUserRes getPhoneUserRes = userProvider.getUserByPhone(phone);
                return new BaseResponse<>(getPhoneUserRes);
            }

            // 쿼리스트링이 아무것도 없을 때... 전체 유저를 조회해야할 듯. 다음은 수정이 필요합니다.
            System.out.println("users - 쿼리 스트링이 없습니다.");
            return new BaseResponse<>(REQUEST_ERROR);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 4. 로그인 API
     * [POST] /users/sign-in
     * @return BaseResponse<PostUserRes>
     */
    @ResponseBody
    @PostMapping("/sign-in")
    public BaseResponse<PostSignUpInRes> signIn(@RequestBody PostSignInReq postSignInReq){
        // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
        // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
        //이메일
        if(postSignInReq.getEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        if(!isRegexEmail(postSignInReq.getEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }

        try{
            PostSignUpInRes postLoginRes = userService.signIn(postSignInReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 5. jwt 자동 로그인 API
     * [POST] /users/sign-in/Jwt
     * @return BaseResponse<PostSignInJwtRes>
     */
    @ResponseBody
    @GetMapping("/sign-in/jwt")
    public BaseResponse<GetSignInJwtRes> signInByJwt(){
        //로그인 값 - jwt validation은 interceptor에서 처리
        try{
            GetSignInJwtRes getSignInJwtRes = userService.signInByJwt();
            return new BaseResponse<>(getSignInJwtRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 6. 현재 유저 대표 주소 조회 API
     * [GET] /users/:userId/addresses?isSelected=true
     * @return BaseResponse<GetUserAddresseRes>
     *     주소 전체 조회는 jwt가 있어야한다.
     *
     * 22. 유저 주소 전체 조회 API - 보류
     * [GET] /users/:userId/addresses
     * @return BaseResponse<List<GetUserAddressInfoRes>>
     */
    @ResponseBody
    @GetMapping("/{userId}/addresses")
    public BaseResponse getUserAddress(@PathVariable int userId, @RequestParam(required = false) Boolean isSelected){

        try{
            int userIdByJwt = jwtService.getUserId();
            if(userId != userIdByJwt){
                return new BaseResponse(INVALID_USER_JWT);
            }

            if(isSelected != null){
                GetUserAddressRes getUserAddressRes = userProvider.getUserAddress(userId, isSelected);
                return new BaseResponse(getUserAddressRes);
            }

            return new BaseResponse(null);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
