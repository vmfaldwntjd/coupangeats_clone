package com.example.demo.src.coupon;

import com.example.demo.config.BaseException;
import com.example.demo.src.coupon.model.PostCouponReq;
import com.example.demo.src.coupon.model.PostCouponRes;
import com.example.demo.src.review.model.PatchReviewReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.MODIFY_FAIL_REVIEW;

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

    //쿠폰 사용 관련 메소드
    public void useCoupon(int couponId, int userId) throws BaseException {
        try {
            int result = couponDao.useCoupon(couponId, userId); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_REVIEW);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
