package com.example.demo.src.coupon;

import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponProvider {
    private final CouponDao couponDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CouponProvider(CouponDao couponDao, JwtService jwtService) {
        this.couponDao = couponDao;
        this.jwtService = jwtService;
    }
}
