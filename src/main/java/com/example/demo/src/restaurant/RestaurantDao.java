package com.example.demo.src.restaurant;

import com.example.demo.src.restaurant.model.*;
import com.example.demo.src.restaurant.query.RestaurantQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.example.demo.src.restaurant.query.RestaurantQuery.*;
import static com.example.demo.src.restaurant.query.RestaurantQuery.getRestaurantListQuery;

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

        // String의 특수성에 의해 orderBy 옵션 적용은 String 이어붙이기로 구현.
        String getRestaurantListByCategoryIdQuery = getRestaurantListByCategoryIdQuery(sortBy, orderBy);

        Object[] getRestaurantListParams = new Object[]{longitude, latitude, categoryId, 100};
        List<GetRestaurantRes> getRestaurantResList = this.jdbcTemplate.query(getRestaurantListByCategoryIdQuery,
                (rs, rowNum) -> new GetRestaurantRes(
                        rs.getInt("restaurant_id"),
                        rs.getString("res_name"),
                        null,
                        rs.getInt("is_cheetah"),
                        rs.getInt("delivery_time"),
                        rs.getDouble("star_point"),
                        rs.getInt("review_count"),
                        rs.getDouble("distance"),
                        rs.getInt("min_delivery_fee")
                ), getRestaurantListParams);

        for(GetRestaurantRes getRestaurantRes : getRestaurantResList){
            int restaurantId = getRestaurantRes.getRestaurantId();
            List<String> resImageUrlList = this.jdbcTemplate.query(getResImageUrlByIdQuery,
                    (rs, rowNum) -> new String(
                            rs.getString("res_image_url")
                    ), restaurantId);
            getRestaurantRes.setResImageUrlList(resImageUrlList);
        }
        return getRestaurantResList;
    }

    // (기본) 골라먹는 맛집
    public List<GetRestaurantRes> getRestaurantList(Double longitude, Double latitude, String sortBy, String orderBy){

        String getRestaurantListQuery = getRestaurantListQuery(sortBy, orderBy);
        Object[] getRestaurantListParams = new Object[]{longitude, latitude, 45};
        List<GetRestaurantRes> getRestaurantResList = this.jdbcTemplate.query(getRestaurantListQuery,
                (rs, rowNum) -> new GetRestaurantRes(
                        rs.getInt("restaurant_id"),
                        rs.getString("res_name"),
                        null,
                        rs.getInt("is_cheetah"),
                        rs.getInt("delivery_time"),
                        rs.getDouble("star_point"),
                        rs.getInt("review_count"),
                        rs.getDouble("distance"),
                        rs.getInt("min_delivery_fee")
                ), getRestaurantListParams);

        for(GetRestaurantRes getRestaurantRes : getRestaurantResList){
            int restaurantId = getRestaurantRes.getRestaurantId();
            List<String> resImageUrlList = this.jdbcTemplate.query(getResImageUrlByIdQuery,
                    (rs, rowNum) -> new String(
                            rs.getString("res_image_url")
                    ), restaurantId);
            getRestaurantRes.setResImageUrlList(resImageUrlList);
        }
        return getRestaurantResList;
    }

    public GetRestaurantRes getRestaurantById(int restaurantId, Double longitude, Double latitude){
        String getRestaurantByIdQuery = RestaurantQuery.getRestaurantByIdQuery;
        Object[] getRestaurantByIdParams = new Object[]{longitude, latitude, restaurantId};
        GetRestaurantRes getRestaurantRes = this.jdbcTemplate.queryForObject(getRestaurantByIdQuery,
                (rs, rowNum) -> new GetRestaurantRes(
                        rs.getInt("restaurant_id"),
                        rs.getString("res_name"),
                        null,
                        rs.getInt("is_cheetah"),
                        rs.getInt("delivery_time"),
                        rs.getDouble("star_point"),
                        rs.getInt("review_count"),
                        rs.getDouble("distance"),
                        rs.getInt("min_delivery_fee")
                ), getRestaurantByIdParams);

        List<String> resImageUrlList = this.jdbcTemplate.query(getResImageUrlByIdQuery,
                (rs, rowNum) -> new String(
                        rs.getString("res_image_url")
                ), restaurantId);
        getRestaurantRes.setResImageUrlList(resImageUrlList);

        return getRestaurantRes;
    }

    public List<GetResKindRes> getResKindList(int restaurantId){
        return this.jdbcTemplate.query(getResKindQuery,
                (rs, rowNum) -> new GetResKindRes(
                        rs.getInt("kind_id"),
                        rs.getString("kind_name")
                ), restaurantId);
    }

    public List<GetResKindMenuRes> getResKindMenuList(int restaurantId){
        return this.jdbcTemplate.query(getResKindMenuQuery,
                (rs, rowNum) -> new GetResKindMenuRes(
                        rs.getInt("kind_id"),
                        rs.getString("kind_name"),
                        rs.getInt("menu_id"),
                        rs.getString("menu_name"),
                        rs.getInt("menu_price"),
                        rs.getString("menu_image_url"),
                        rs.getString("menu_description")
                ), restaurantId);
    }

    public GetResMenuRes getResMenuList(int restaurantId, int menuId){
        Object[] getResMenuListParam = new Object[]{restaurantId, menuId};
        GetResMenuRes getResMenuList = this.jdbcTemplate.queryForObject(getResMenuQuery,
                (rs, rowNum) -> new GetResMenuRes(
                        rs.getInt("menu_id"),
                        rs.getString("menu_name"),
                        rs.getInt("menu_price"),
                        null,
                        rs.getString("menu_description")
                ), getResMenuListParam);

        List<String> menuImageUrlList = this.jdbcTemplate.query(getResMenuImageUrlListQuery,
                (rs, rowNum) -> rs.getString("menu_image_url"),
                getResMenuListParam);

        getResMenuList.setMenuImageUrlList(menuImageUrlList);

        return getResMenuList;
    }


    public List<GetResMenuOptionRes> getResMenuOption(int restaurantId, int menuId){
        List<GetResMenuOptionRes> getResMenuOption = this.jdbcTemplate.query(getResMenuKindQuery,
                (rs, rowNum) -> new GetResMenuOptionRes(
                        rs.getInt("option_kind_id"),
                        rs.getString("option_kind_name"),
                        rs.getInt("is_essential") == 1 ? true : false,
                        null
                ), menuId);

        for(int i = 0; i < getResMenuOption.size(); i++){
            GetResMenuOptionRes temp = getResMenuOption.get(i);
            int kindId = temp.getOptionKindId();
            Object[] getResOptionParam = new Object[]{restaurantId, kindId};
            List<OptionInfo> getOptionInfo = this.jdbcTemplate.query(getResMenuOptionQuery,
                    (rs, rowNum) -> new OptionInfo(
                            rs.getInt("option_id"),
                            rs.getString("option_name"),
                            rs.getInt("option_price")
                    ), getResOptionParam);
            temp.setOptionInfoList(getOptionInfo);
        }

        return getResMenuOption;
    }
}
