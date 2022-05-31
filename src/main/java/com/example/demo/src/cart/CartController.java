package com.example.demo.src.cart;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.cart.model.GetTotalPriceRes;
import com.example.demo.src.cart.model.PostCartReq;
import com.example.demo.src.cart.model.PostCartRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/carts")
public class CartController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final CartProvider cartProvider;
    @Autowired
    private final CartService cartService;

    public CartController(JwtService jwtService, CartProvider cartProvider, CartService cartService){
        this.jwtService = jwtService;
        this.cartProvider = cartProvider;
        this.cartService = cartService;
    }

    /**
     * 18. 카트 담기 요청 API
     * [POST]
     * @return BaseResponse<PostCartRes>
     *
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostCartRes> createCart(@RequestBody PostCartReq postCartReq){
        try{
            PostCartRes postCartRes = cartService.createCart(postCartReq);
            return new BaseResponse<>(postCartRes);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 49. 카트 총 금액 조회 API
     * [POST]
     * @return BaseResponse<GetCartRes>
     *
     */
    @ResponseBody
    @GetMapping("/{cartId}/totalPrice")
    public BaseResponse<GetTotalPriceRes> getTotalPrice(@PathVariable Integer cartId){
        try{
            return new BaseResponse<>(new GetTotalPriceRes(cartProvider.getTotalPrice(cartId)));
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
//     * 17. 카트 화면 조회 API
//     * [GET]
//     * @retrun BaseResponse<GetCart
//     * */
//    @ResponseBody
//    @GetMapping("")
}
