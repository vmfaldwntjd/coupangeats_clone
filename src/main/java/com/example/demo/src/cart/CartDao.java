package com.example.demo.src.cart;

import com.example.demo.src.cart.model.OptionInfo;
import com.example.demo.src.cart.model.OptionKindInfo;
import com.example.demo.src.cart.model.PostCartReq;
import com.example.demo.src.cart.model.PostCartRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.example.demo.src.cart.query.CartQuery.createCartQuery;

@Repository
public class CartDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int getResIdByUserId(int userId) {
        try {
            String checkCartByUserIdQuery = "select restaurant_id from cart where user_id = ?";
            return this.jdbcTemplate.queryForObject(checkCartByUserIdQuery,
                    int.class,
                    userId);
        } catch (IncorrectResultSetColumnCountException exception){ //userId에 대해 여러개 조회
            return -1;
        } catch (EmptyResultDataAccessException exception){ // userId에 대해 결과 없음
            return -1;
        }
    }

    public int getCartIdByUserId(int userId){
        try {
            String checkCartByUserIdQuery = "select cart_id from cart where user_id = ?";
            return this.jdbcTemplate.queryForObject(checkCartByUserIdQuery,
                    int.class,
                    userId);
        } catch (IncorrectResultSetColumnCountException exception){//userId에 대해 여러개 조회
            return -1;
        } catch (EmptyResultDataAccessException exception){ // userId에 대해 결과 없음
            return -1;
        }
    }


    public int createCart(PostCartReq postCartReq, Integer userAddressId){

        Object[] creatCartParams = new Object[]{postCartReq.getUserId(), userAddressId, postCartReq.getRestaurantId()};
        this.jdbcTemplate.update(createCartQuery, creatCartParams);

        // 마지막으로 update/insert된 테이블의 PK값.
        return this.jdbcTemplate.queryForObject("select last_insert_id()", int.class);
    }

    public int createCartMenu(int cartId, PostCartReq postCartReq){
        int sum = postCartReq.getMenuPrice(); // 현재 들어온 메뉴에 대한모든 값을 저장하고 반환합니다.
        // 매개변수값이 너무 많아서 확인차 이곳에 쿼리 작성.
        String createCartMenuQuery = "INSERT INTO cart_menu (cart_id, menu_id, price, " +
                "count, menu_name, parent_menu_id)\n" +
                "VALUES(?, ?, ?, ?, ?, ?);";
        Object[] createCartMenuParam = new Object[]{cartId, postCartReq.getMenuId(), postCartReq.getMenuPrice(),
        postCartReq.getMenuCount(), postCartReq.getMenuName(), postCartReq.getMenuId()};
        this.jdbcTemplate.update(createCartMenuQuery, createCartMenuParam);

        List<OptionKindInfo> optionKindInfoList = postCartReq.getOptionKindInfoList();
        for(OptionKindInfo oki : optionKindInfoList) {
            List<OptionInfo> optionInfoList = oki.getOptionInfoList();
            for(OptionInfo oi : optionInfoList){
                sum += oi.getOptionPrice();
                // 옵션은 수가 단 하나
                createCartMenuParam = new Object[]{cartId, oi.getOptionId(), oi.getOptionPrice(),
                        1, oi.getOptionName(), postCartReq.getMenuId()};
                this.jdbcTemplate.update(createCartMenuQuery, createCartMenuParam);
            }
        }

       return sum*postCartReq.getMenuCount();
    }

    public int setTotalPrice(int cartId, int totalPrice){
        return this.jdbcTemplate.update("UPDATE cart SET total_price = ? WHERE cart_id = ?", totalPrice, cartId);
    }
}
