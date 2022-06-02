package com.example.demo.src.coupon;

import com.example.demo.config.BaseException;
import com.example.demo.src.coupon.model.PostCouponReq;
import com.example.demo.src.coupon.model.PostCouponRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class CouponService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CouponDao couponDao;
    private final CouponProvider couponProvider;
    private final JwtService jwtService;


    @Autowired
    public CouponService(CouponDao couponDao, CouponProvider couponProvider, JwtService jwtService) {
        this.couponDao = couponDao;
        this.couponProvider = couponProvider;
        this.jwtService = jwtService;
    }

    //쿠폰 생성 관련 메소드
    public PostCouponRes createCoupon(int userId, PostCouponReq postCouponReq) throws BaseException {
        try {
            int couponId = couponDao.createCoupon(userId, postCouponReq);
            return new PostCouponRes(couponId);

        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
