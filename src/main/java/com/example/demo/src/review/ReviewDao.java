package com.example.demo.src.review;

import com.example.demo.src.payment.model.GetUserPaymentRes;
import com.example.demo.src.payment.model.PostUserPaymentReq;
import com.example.demo.src.review.model.GetReviewRes;
import com.example.demo.src.review.model.PatchReviewReq;
import com.example.demo.src.review.model.PostReviewReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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

    //리뷰 삭제 메소드
    public boolean deleteReview(int reviewId, int userId) {
        String deleteReviewQuery = "DELETE FROM coupangeats.review WHERE review_id = ? AND user_id = ?";
        Object[] args = new Object[] {reviewId, userId};

        return jdbcTemplate.update(deleteReviewQuery, args) == 1;
    }

    //유저가 작성한 리뷰 하나 조회 메소드
    public GetReviewRes getUserReview(int reviewId, int userId) {
        String getUserReviewQuery = "select review_id, user_id, restaurant_name, star_point, content, menu_name from review\n" +
                "inner join restaurant r on review.restaurant_id = r.restaurant_id\n" +
                "where review_id = ? and review.user_id = ?;";
        Object[] args = new Object[] {reviewId, userId};;
        return this.jdbcTemplate.queryForObject(getUserReviewQuery,
                (rs, rowNum) -> new GetReviewRes(
                        rs.getInt("review_id"),
                        rs.getInt("user_id"),
                        rs.getString("restaurant_name"),
                        rs.getInt("star_point"),
                        rs.getString("content"),
                        rs.getString("menu_name")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                args); // 해당 닉네임을 갖는 모든 User 정보를 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }
}
