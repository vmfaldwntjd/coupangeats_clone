package com.example.demo.src.coupon;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.coupon.model.PostCouponReq;
import com.example.demo.src.coupon.model.PostCouponRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/app/coupons")
public class CouponController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final CouponProvider couponProvider;

    @Autowired
    private final CouponService couponService;

    public CouponController(JwtService jwtService, CouponProvider couponProvider, CouponService couponService) {
        this.jwtService = jwtService;
        this.couponProvider = couponProvider;
        this.couponService = couponService;
    }

    //쿠폰 생성 메소드
    @ResponseBody
    @PostMapping("/{userId}")
    public BaseResponse<PostCouponRes> createCoupon(@PathVariable("userId") int userId, @RequestBody PostCouponReq postCouponReq) {
        try {
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt) {
                return new BaseResponse(INVALID_USER_JWT);
            }
            PostCouponRes postCouponRes = couponService.createCoupon(userId, postCouponReq);
            return new BaseResponse<>(postCouponRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
