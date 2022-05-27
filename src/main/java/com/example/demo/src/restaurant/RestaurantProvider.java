package com.example.demo.src.restaurant;

import com.example.demo.config.BaseException;
import com.example.demo.src.restaurant.model.GetFNRestaurantRes;
import com.example.demo.src.restaurant.model.GetResKindMenuRes;
import com.example.demo.src.restaurant.model.GetResKindRes;
import com.example.demo.src.restaurant.model.GetRestaurantRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.RESTAURANTS_INVALID_SORT_BY;

@Service
public class RestaurantProvider {
    private final RestaurantDao restaurantDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RestaurantProvider(RestaurantDao restaurantDao, JwtService jwtService){
        this.restaurantDao = restaurantDao;
        this.jwtService = jwtService;
    }

    public List<GetFNRestaurantRes> getFranchiseRestaurantList(Double longitude, Double latitude)throws BaseException {
        // 기본값 설정. 둘 중 하나라도 null이면 기본값 설정.
        if(longitude == null || latitude == null) {
            latitude = 37.4638409;
            longitude = 126.9526383;
        }
        try {
            List<GetFNRestaurantRes> getRestaurantList = restaurantDao.getFNRestaurantList(26, longitude, latitude);
            return getRestaurantList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetFNRestaurantRes> getNewRestaurantList(Double longitude, Double latitude) throws BaseException{
        // 기본값 설정. 둘 중 하나라도 null이면 기본값 설정.
        if(longitude == null || latitude == null) {
            latitude = 37.4638409;
            longitude = 126.9526383;
        }
        try {
            List<GetFNRestaurantRes> getRestaurantList = restaurantDao.getFNRestaurantList(2, longitude, latitude);
            return getRestaurantList;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 카테고리별 가게 리스트
    public List<GetRestaurantRes> getRestaurantListByCategoryId(int categoryId, Double longitude, Double latitude, String sortBy) throws BaseException{
        // 기본값 설정. 둘 중 하나라도 null이면 기본값 설정.
        if(longitude == null || latitude == null) {
            latitude = 37.4638409;
            longitude = 126.9526383;
        }
        String orderBy;
        // 기본값 설정.
        if(sortBy == null){
            sortBy = "star_point";
            orderBy = "DESC";
        } else if(sortBy.contentEquals("distance")){
            orderBy = "ASC";
        } else if(sortBy.contentEquals("created_at")){
            orderBy = "DESC";
        } else if(sortBy.contentEquals("star_point")){
            orderBy = "DESC";
        } else {
            throw new BaseException(RESTAURANTS_INVALID_SORT_BY);
        }
        try {
            List<GetRestaurantRes> getRestaurantList = restaurantDao.getRestaurantListByCategoryId(categoryId, longitude, latitude, sortBy, orderBy);
            return getRestaurantList;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 골라먹는 맛집 리스트
    public List<GetRestaurantRes> getRestaurantList(Double longitude, Double latitude, String sortBy) throws BaseException{
        // 기본값 설정. 둘 중 하나라도 null이면 기본값 설정.
        if(longitude == null || latitude == null) {
            latitude = 37.4638409;
            longitude = 126.9526383;
        }
        String orderBy;
        // 기본값 설정.
        if(sortBy == null){
            sortBy = "star_point";
            orderBy = "DESC";
        } else if(sortBy.contentEquals("distance")){
            orderBy = "ASC";
        } else if(sortBy.contentEquals("created_at")){
            orderBy = "DESC";
        } else if(sortBy.contentEquals("star_point")){
            orderBy = "DESC";
        } else {
            throw new BaseException(RESTAURANTS_INVALID_SORT_BY);
        }

        try {
            List<GetRestaurantRes> getRestaurantList = restaurantDao.getRestaurantList(longitude, latitude, sortBy, orderBy);
            return getRestaurantList;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 각 가게별 메인화면
    public GetRestaurantRes getRestaurantById(int restaurantId, Double longitude, Double latitude) throws BaseException {
        // 기본값 설정. 둘 중 하나라도 null이면 기본값 설정.
        if(longitude == null || latitude == null) {
            latitude = 37.4638409;
            longitude = 126.9526383;
        }
        try {
            GetRestaurantRes getRestaurantRes = restaurantDao.getRestaurantById(restaurantId, longitude, latitude);
            return getRestaurantRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 각 가게 메인화면 메뉴 리스트
    public List<GetResKindRes> getResKindList(int restaurantId) throws BaseException {
        try {
            List<GetResKindRes> getResKindResList = restaurantDao.getResKindList(restaurantId);
            return getResKindResList;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 각 가게 메인화면 종류 별 메뉴 리스트
    public List<GetResKindMenuRes> getResKindMenuList(int restaurantId) throws  BaseException{
        try{
            List<GetResKindMenuRes> getResKindMenuResList = restaurantDao.getResKindMenuList(restaurantId);
            return getResKindMenuResList;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
