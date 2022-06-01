package com.example.demo.src.payment;


import com.example.demo.config.BaseException;
import com.example.demo.src.payment.model.GetUserPaymentRes;
import com.example.demo.src.user.model.GetPhoneUserRes;
import com.example.demo.src.user.model.GetUserFavoriteRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class PaymentProvider {
    private final PaymentDao paymentDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PaymentProvider(PaymentDao paymentDao, JwtService jwtService) {
        this.paymentDao = paymentDao;
        this.jwtService = jwtService;
    }

    //결제 정보 관리 화면 메소드
    public List<GetUserPaymentRes> getUserPayment(int userId) throws BaseException {
        try {
            List<GetUserPaymentRes> getUserPaymentRes = paymentDao.getUserPayment(userId);
            return getUserPaymentRes;
        } catch (Exception exception) {
            System.out.println(exception); //오류 내용 확인용
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
