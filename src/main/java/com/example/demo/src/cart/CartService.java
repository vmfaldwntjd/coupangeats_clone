package com.example.demo.src.cart;

import com.example.demo.config.BaseException;
import com.example.demo.src.cart.model.OptionKindInfo;
import com.example.demo.src.cart.model.PostCartReq;
import com.example.demo.src.cart.model.PostCartRes;
import com.example.demo.src.user.UserProvider;
import com.example.demo.utils.JwtService;
import io.jsonwebtoken.Jwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class CartService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CartDao cartDao;
    private final CartProvider cartProvider;
    private final JwtService jwtService;
    private final UserProvider userProvider;

    @Autowired
    public CartService(CartDao cartDao, CartProvider cartProvider, JwtService jwtService, UserProvider userProvider){
        this.cartDao = cartDao;
        this.cartProvider = cartProvider;
        this.jwtService = jwtService;
        this.userProvider = userProvider;
    }

    @Transactional(rollbackOn = Exception.class)
    public PostCartRes createCart(PostCartReq postCartReq) throws BaseException{
        // 필수 옵션을 선택하지 않은경우 카트를 비운 후 insert가 반려된다.

        // TODO : cart validation 처리
        int userId = postCartReq.getUserId();
        int restaurantId = postCartReq.getRestaurantId();

        int cartId = cartProvider.getCartIdByUserId(userId);
        int resIdByUserId = cartProvider.getResIdByUserId(userId);

        System.out.println("cartId : "+cartId +"  resId : "+resIdByUserId);

        // 카트가 존재하면서 res id가 다르다면 Request error
        if(cartId > 0 && restaurantId != resIdByUserId){
            throw new BaseException(POST_CARTS_ANOTHER_RESTAURANT_ID_EXISTS);
        }

        // 필수 선택 옵션을 선택하지 않았다면 Request error
        for(OptionKindInfo oki : postCartReq.getOptionKindInfoList()){
            if(oki.getIsEssential() && oki.getOptionInfoList().size() == 0){
                throw new BaseException(POST_CARTS_EMPTY_ESSENTIAL_OPTION);
            }
        }

        System.out.println("분기 test");

        int totalPrice = 0;
        try {
            // 카트가 없다면 카트 생성
            if(cartId == -1) {
                // 선택된 주소 정보를 기입합니다.
                Integer userAddressId = userProvider.getUserAddressId(postCartReq.getUserId());
                if(userAddressId == -1) userAddressId = null;
                cartId = cartDao.createCart(postCartReq, userAddressId);
            }

            System.out.println("카트 생성까지 완료");

            totalPrice = cartDao.createCartMenu(cartId, postCartReq);
            int t = cartDao.setTotalPrice(cartId, totalPrice);

            if(t != 1){
                throw new BaseException(DATABASE_ERROR);
            }

            return new PostCartRes(userId, cartId, totalPrice);
        } catch(Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
