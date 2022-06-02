package com.example.demo.src.coupon;

import com.example.demo.src.coupon.model.PostCouponReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CouponDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    //쿠폰 생성 관련 메소드
    public int createCoupon(int userId, PostCouponReq postCouponReq){
        String createCouponQuery = "INSERT INTO coupangeats.coupon (coupon_num, user_id) VALUES (?, ?);";
        Object[] createCouponParams = new Object[]{postCouponReq.getCouponNum(), userId};
        this.jdbcTemplate.update(createCouponQuery, createCouponParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }
}
