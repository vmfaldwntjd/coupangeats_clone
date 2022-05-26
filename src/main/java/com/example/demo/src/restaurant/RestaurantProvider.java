package com.example.demo.src.restaurant;

import com.example.demo.config.BaseException;
import com.example.demo.src.restaurant.model.GetFNRestaurantRes;
import com.example.demo.src.restaurant.model.GetRestaurantRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

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
        try {
            List<GetFNRestaurantRes> getRestaurantList = restaurantDao.getFNRestaurantList(26, longitude, latitude);
            return getRestaurantList;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetFNRestaurantRes> getNewRestaurantList(Double longitude, Double latitude) throws BaseException{
        try {
            List<GetFNRestaurantRes> getRestaurantList = restaurantDao.getFNRestaurantList(2, longitude, latitude);
            return getRestaurantList;
        } catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetRestaurantRes> getRestaurantListByCategoryId(int categoryId, Double longitude, Double latitude, String sortBy, String orderBy) throws BaseException{
        try {
            List<GetRestaurantRes> getRestaurantList = restaurantDao.getRestaurantListByCategoryId(categoryId, longitude, latitude, sortBy, orderBy);
            return getRestaurantList;
        } catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetRestaurantRes> getRestaurantList(Double longitude, Double latitude, String sortBy, String orderBy) throws BaseException{
        try {
            List<GetRestaurantRes> getRestaurantList = restaurantDao.getRestaurantList(longitude, latitude, sortBy, orderBy);
            return getRestaurantList;
        } catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
