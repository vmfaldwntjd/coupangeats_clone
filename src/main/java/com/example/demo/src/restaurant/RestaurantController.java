package com.example.demo.src.restaurant;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.restaurant.model.GetFNRestaurantRes;
import com.example.demo.src.restaurant.model.GetRestaurantRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.RESTAURANTS_EMPTY_CATEGORY_ID;

@RestController
@RequestMapping("/app/restaurants")
public class RestaurantController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final RestaurantProvider restaurantProvider;

    public RestaurantController(JwtService jwtService, RestaurantProvider restaurantProvider){
        this.jwtService = jwtService;
        this.restaurantProvider = restaurantProvider;
    }

    @ResponseBody
    @GetMapping("/franchise")
    public BaseResponse<List<GetFNRestaurantRes>> getFranchiseRestaurantList(@RequestParam(required = false) Double longitude, @RequestParam(required = false) Double latitude) {
        try {
            List<GetFNRestaurantRes> getRestaurantResList = restaurantProvider.getFranchiseRestaurantList(longitude, latitude);
            return new BaseResponse<>(getRestaurantResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/new")
    public BaseResponse<List<GetFNRestaurantRes>> getNewRestaurantList (@RequestParam(required = false) Double longitude, @RequestParam(required = false) Double latitude) {
        try {
                List<GetFNRestaurantRes> getRestaurantResList = restaurantProvider.getNewRestaurantList(longitude, latitude);
                return new BaseResponse<>(getRestaurantResList);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetRestaurantRes>> getRestaurantListByCategoryId(@RequestParam Integer categoryId, @RequestParam(required = false) Double longitude, @RequestParam(required = false) Double latitude, @RequestParam(required = false) String sortBy, @RequestParam(required = false) String orderBy){
        try{
            if(categoryId == null){ // 12번 API 골라먹는 맛집
                List<GetRestaurantRes> getRestaurantResList = restaurantProvider.getRestaurantList(longitude, latitude, sortBy, orderBy);
                return new BaseResponse<>(getRestaurantResList);
            }
            // 10번 카테고리별 가게 리스트
            List<GetRestaurantRes> getRestaurantResList = restaurantProvider.getRestaurantListByCategoryId(categoryId, longitude, latitude, sortBy, orderBy);
            return new BaseResponse<>(getRestaurantResList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
