package com.example.demo.src.restaurant;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.restaurant.model.GetFNRestaurantRes;
import com.example.demo.src.restaurant.model.GetResKindMenuRes;
import com.example.demo.src.restaurant.model.GetResKindRes;
import com.example.demo.src.restaurant.model.GetRestaurantRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public BaseResponse<List<GetRestaurantRes>> getRestaurantListByCategoryId(@RequestParam Integer categoryId, @RequestParam(required = false) Double longitude, @RequestParam(required = false) Double latitude, @RequestParam(required = false) String sortBy){
        try{
            if(categoryId == null){ // 12번 API 골라먹는 맛집
                List<GetRestaurantRes> getRestaurantResList = restaurantProvider.getRestaurantList(longitude, latitude, sortBy);
                return new BaseResponse<>(getRestaurantResList);
            }
            // 10번 카테고리별 가게 리스트
            List<GetRestaurantRes> getRestaurantResList = restaurantProvider.getRestaurantListByCategoryId(categoryId, longitude, latitude, sortBy);
            return new BaseResponse<>(getRestaurantResList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 13. 가게 메인 화면 정보 API
     * [GET] /:restaurantId??longitude={longitude}&latitude={latitude}
     * @return BaseResponse<GetRestaurantRes>
     *
     */
    @ResponseBody
    @GetMapping("/{restaurantId}")
    public BaseResponse<GetRestaurantRes> getRestaurantById(@PathVariable Integer restaurantId, @RequestParam(required = false) Double longitude, @RequestParam(required = false) Double latitude){
        try{
            GetRestaurantRes getRestaurantRes = restaurantProvider.getRestaurantById(restaurantId, longitude, latitude);
            return new BaseResponse<>(getRestaurantRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 14. 가게 메인 화면 메뉴 종류 API
     * [GET] /:restaurantId/kinds
     * @return BaseResponse<List<GetResKindRes>>
     *
     */
    @ResponseBody
    @GetMapping("/{restaurantId}/kinds")
    public BaseResponse<List<GetResKindRes>> getResKindList(@PathVariable Integer restaurantId){
        try{
            List<GetResKindRes> getResKindResList = restaurantProvider.getResKindList(restaurantId);
            return new BaseResponse<>(getResKindResList);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 15. 가게 메인 화면 종류별 메뉴
     * [GET] /:restaurantId/menus
     * @return BaseResponse<List<GetResKindMenuRes>
     *
     */
    @ResponseBody
    @GetMapping("/{restaurantId}/menus")
    public BaseResponse<List<GetResKindMenuRes>> getResKindMenuList(@PathVariable Integer restaurantId){
        try {
            List<GetResKindMenuRes> getResKindMenuList = restaurantProvider.getResKindMenuList(restaurantId);
            return new BaseResponse<>(getResKindMenuList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
