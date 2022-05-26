package com.example.demo.src.restaurant;

import com.example.demo.src.restaurant.model.GetFNRestaurantRes;
import com.example.demo.src.restaurant.model.GetRestaurantRes;
import com.example.demo.src.restaurant.query.RestaurantQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.example.demo.src.restaurant.query.RestaurantQuery.*;
import static com.example.demo.src.restaurant.query.RestaurantQuery.getRestaurantListQuery;
import static com.example.demo.src.restaurant.query.RestaurantQuery.getFNRestaurantListQuery;

@Repository
public class RestaurantDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetFNRestaurantRes> getFNRestaurantList(int categoryId, Double longitude, Double latitude){
        // 기본값 설정. 둘 중 하나라도 null이면 기본값 설정.
        if(longitude == null || latitude == null) {
            latitude = 37.4638409;
            longitude = 126.9526383;
        }
        String getFNRestaurantListQuery = RestaurantQuery.getFNRestaurantListQuery;
        Object[] getRestaurantListParams = new Object[]{longitude, latitude, categoryId};
        return this.jdbcTemplate.query(getFNRestaurantListQuery,
                (rs, rowNum) -> new GetFNRestaurantRes(
                        rs.getInt("restaurant_id"),
                        rs.getString("res_name"),
                        rs.getString("res_image_url"),
                        rs.getInt("delivery_time"),
                        rs.getDouble("star_point"),
                        rs.getInt("review_count"),
                        rs.getDouble("distance"),
                        rs.getInt("min_delivery_fee")
                ), getRestaurantListParams);
    }



    // 카테고리별 가게 리스트
    public List<GetRestaurantRes> getRestaurantListByCategoryId(int categoryId, Double longitude, Double latitude, String sortBy, String orderBy){


        // 기본값 설정. 둘 중 하나라도 null이면 기본값 설정.
        if(longitude == null || latitude == null) {
            latitude = 37.4638409;
            longitude = 126.9526383;
        }
        // 기본값 설정. 둘 중 하나라도 null이면 기본값 설정.
        if(sortBy == null || orderBy == null) {
            sortBy = "star_point";
            orderBy = "DESC";
        }

        // String의 특수성에 의해 orderBy 옵션 적용은 String 이어붙이기로 구현.
        String getRestaurantListByCategoryIdQuery = getRestaurantListByCategoryIdQuery(sortBy, orderBy);

        Object[] getRestaurantListParams = new Object[]{longitude, latitude, categoryId, 100};
        return this.jdbcTemplate.query(getRestaurantListByCategoryIdQuery,
                (rs, rowNum) -> new GetRestaurantRes(
                        rs.getInt("restaurant_id"),
                        rs.getString("res_name"),
                        rs.getString("res_image_url"),
                        rs.getInt("is_cheetah"),
                        rs.getInt("delivery_time"),
                        rs.getDouble("star_point"),
                        rs.getInt("review_count"),
                        rs.getDouble("distance"),
                        rs.getInt("min_delivery_fee")
                ), getRestaurantListParams);
    }

    // (기본) 골라먹는 맛집
    public List<GetRestaurantRes> getRestaurantList(Double longitude, Double latitude, String sortBy, String orderBy){
        if(longitude == null) longitude = 126.9526383;
        if(latitude == null) latitude = 37.4638409;
        if(sortBy == null) sortBy = "star_point";
        if(orderBy == null) orderBy = "DESC";

        String getRestaurantListQuery = getRestaurantListQuery(sortBy, orderBy);
        Object[] getRestaurantListParams = new Object[]{longitude, latitude, 45};
        return this.jdbcTemplate.query(getRestaurantListQuery,
                (rs, rowNum) -> new GetRestaurantRes(
                        rs.getInt("restaurant_id"),
                        rs.getString("res_name"),
                        rs.getString("res_image_url"),
                        rs.getInt("is_cheetah"),
                        rs.getInt("delivery_time"),
                        rs.getDouble("star_point"),
                        rs.getInt("review_count"),
                        rs.getDouble("distance"),
                        rs.getInt("min_delivery_fee")
                ), getRestaurantListParams);
    }
}
