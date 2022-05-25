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
     * jwt 자동 로그인 API
     * [POST] /users/sign-in/Jwt
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/sign-in/jwt")
    public BaseResponse<PostSignInJwtRes> signInByJwt(){
        //로그인 값 - jwt validation은 interceptor에서 처리
        try{
            PostSignInJwtRes postSignInJwtRes = userService.signInByJwt();
            return new BaseResponse<>(postSignInJwtRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

//
//    /**
//     * 유저정보변경 API
//     * [PATCH] /users/:userIdx
//     * @return BaseResponse<String>
//     */
//    @ResponseBody
//    @PatchMapping("/{userIdx}")
//    public BaseResponse<String> modifyUserName(@PathVariable("userIdx") int userIdx, @RequestBody User user){
//        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
//            //같다면 유저네임 변경
//            PatchUserReq patchUserReq = new PatchUserReq(userIdx,user.getUserName());
//            userService.modifyUserName(patchUserReq);
//
//            String result = "";
//        return new BaseResponse<>(result);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }


}
