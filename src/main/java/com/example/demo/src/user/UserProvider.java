package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }


    public int checkEmail(String email) throws BaseException{
        try{
            return userDao.checkEmail(email);
        } catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkPhone(String phone) throws BaseException{
        try{
            return userDao.checkPhone(phone);
        } catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetPhoneUserRes getUserByPhone(String phone) throws BaseException {
        try {
            GetPhoneUserRes getUserRes = userDao.getUserEmailByPhone(phone);
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public GetUserAddressRes getUserAddress(int userId, Boolean isSelected) throws BaseException{
        try {
            GetUserAddressRes getUserAddressRes = userDao.getUserAddress(userId, isSelected);
            return getUserAddressRes;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int getUserAddressId(int userId) throws BaseException {
        try {
            return userDao.getUserAddressId(userId);
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //core 추가
    public List<GetOrderRes> getOrdersByDeliveryStatus(int user_id, int delivery_status) throws BaseException {
        try {
            List<GetOrderRes> getOrderRes = userDao.getOrdersByDeliveryStatus(user_id, delivery_status);
            return getOrderRes;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //core 추가
    public List<GetReceiptRes> getReceipts(int userId, String orderId) throws BaseException {
        try {
            List<GetReceiptRes> getReceiptRes = userDao.getReceipts(userId, orderId);
            return getReceiptRes;
        } catch (Exception exception) {
            System.out.println(exception); //오류 내용 확인용
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //core추가
    public GetUserAddressDetailRes getUserAddressDetail(int userId, int userAddressId) throws BaseException {
        try {
            GetUserAddressDetailRes getUserAddressDetailRes = userDao.getUserAddressDetail(userId, userAddressId);
            return getUserAddressDetailRes;
        } catch (Exception exception) {
            System.out.println(exception); //오류 내용 확인용
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
