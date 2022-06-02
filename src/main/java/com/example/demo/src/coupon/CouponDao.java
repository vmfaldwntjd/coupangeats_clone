package com.example.demo.src.coupon;

import com.example.demo.src.coupon.model.GetCouponRes;
import com.example.demo.src.coupon.model.PostCouponReq;
import com.example.demo.src.review.model.GetReviewRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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

    //쿠폰 조회 관련 메소드
    public List<GetCouponRes> getUserCoupon(int userId) {
        String getUserCouponQuery = "select user_id, coupon.coupon_id, content from coupon\n" +
                "inner join coupon_image on coupon.coupon_id = coupon_image.coupon_id\n" +
                "where user_id = ?;";
        int userParams = userId;
        return this.jdbcTemplate.query(getUserCouponQuery,
                (rs, rowNum) -> new GetCouponRes(
                        rs.getInt("user_id"),
                        rs.getInt("coupon_id"),
                        rs.getString("content")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                userParams); // 해당 닉네임을 갖는 모든 User 정보를 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }
}
