package com.example.demo.src.review;

import com.example.demo.src.payment.model.PostUserPaymentReq;
import com.example.demo.src.review.model.PatchReviewReq;
import com.example.demo.src.review.model.PostReviewReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ReviewDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //리뷰 생성 관련 메소드
    public int createReview(int userId, int restaurantId, PostReviewReq postReviewReq){
        String createReviewQuery = "INSERT INTO coupangeats.review (restaurant_id, user_id, star_point, menu_name, content) VALUES (?, ?, ?, ?, ?);";
        Object[] createReviewParams = new Object[]{restaurantId, userId, postReviewReq.getStarPoint(), postReviewReq.getMenu_name(),
                postReviewReq.getContent()};
        this.jdbcTemplate.update(createReviewQuery, createReviewParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    //리뷰 수정 메소드
    public int patchReview(int reviewId, int userId, PatchReviewReq patchReviewReq) {
        String patchReviewQuery = "UPDATE coupangeats.review t SET t.star_point = ?, t.content = ? WHERE t.review_id = ? AND t.user_id = ?;"; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] patchReviewParams = new Object[]{patchReviewReq.getStarPoint(), patchReviewReq.getContent(), reviewId, userId}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(patchReviewQuery, patchReviewParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }
}
