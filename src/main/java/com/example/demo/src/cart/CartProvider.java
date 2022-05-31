package com.example.demo.src.cart;

import com.example.demo.config.BaseException;
import com.example.demo.src.cart.model.ResOrderMenuInfo;
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

    public int getTotalPrice(int cartId) throws BaseException {
        try {
            return cartDao.getTotalPrice(cartId);
        } catch(Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // cart_menu에 있는 메뉴 정보를 불러온다.
//    public ResOrderMenuInfo getOrderMenuInfo(int cartId) throws BaseException {
//        try{
//            return cartDao.getOrderMenuInfo(cartId);
//        } catch(Exception exception){
//            System.out.println(exception);
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
    public String getOptionInfoString(int cartId, int menuId, int menuOrder) throws BaseException {
        try {
            return cartDao.getOptionInfoString(cartId, menuId,  menuOrder);
        } catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
