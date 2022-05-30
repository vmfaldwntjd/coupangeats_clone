package com.example.demo.src.cart;

import com.example.demo.config.BaseException;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class CartProvider {

    private final CartDao cartDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CartProvider(CartDao cartDao, JwtService jwtService){
        this.cartDao = cartDao;
        this.jwtService = jwtService;
    }

    public int getResIdByUserId(int userId) throws BaseException{
        try{
            return cartDao.getResIdByUserId(userId);
        } catch(Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int getCartIdByUserId(int userId) throws BaseException{
        try {
            return cartDao.getCartIdByUserId(userId);
        } catch(Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
