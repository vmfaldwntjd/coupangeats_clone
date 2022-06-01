package com.example.demo.src.payment;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.payment.model.GetUserPaymentRes;
import com.example.demo.src.payment.model.PostUserPaymentReq;
import com.example.demo.src.payment.model.PostUserPaymentRes;
import com.example.demo.src.user.model.GetUserFavoriteRes;
import com.example.demo.src.user.model.PostUserAddressReq;
import com.example.demo.src.user.model.PostUserAddressRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/app/payments")
public class PaymentController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final PaymentProvider paymentProvider;

    @Autowired
    private final PaymentService paymentService;

    public PaymentController(JwtService jwtService, PaymentProvider paymentProvider, PaymentService paymentService){
        this.jwtService = jwtService;
        this.paymentProvider = paymentProvider;
        this.paymentService = paymentService;
    }

    //결제 정보 관리 화면 메소드
    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<List<GetUserPaymentRes>> getUserPayment(@PathVariable("userId") int userId) {
        try {
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt) {
                return new BaseResponse(INVALID_USER_JWT);
            }
            List<GetUserPaymentRes> getUserPaymentRes = paymentProvider.getUserPayment(userId);
            return new BaseResponse<>(getUserPaymentRes);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    //결제 관리에서 카드 추가하기 메소드
    @ResponseBody
    @PostMapping("/{userId}")
    public BaseResponse<PostUserPaymentRes> createUserPayment(@PathVariable("userId") int userId, @RequestBody PostUserPaymentReq postUserPaymentReq) {
        try {
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt) {
                return new BaseResponse(INVALID_USER_JWT);
            }
            PostUserPaymentRes postUserPaymentRes = paymentService.createUserPayment(userId, postUserPaymentReq);
            return new BaseResponse<>(postUserPaymentRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
