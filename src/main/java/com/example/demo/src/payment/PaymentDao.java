package com.example.demo.src.payment;

import com.example.demo.src.payment.model.GetUserPaymentRes;
import com.example.demo.src.payment.model.PostUserPaymentReq;
import com.example.demo.src.user.model.GetUserFavoriteRes;
import com.example.demo.src.user.model.PostUserAddressReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PaymentDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //결제 관리 화면 출력
    public List<GetUserPaymentRes> getUserPayment(int userId) {
        String getUserFavoriteQuery = "select card_id, card_name, card_num, user_id from card\n" +
                "where user_id = ?;\n";
        int getUserPaymentParams = userId;
        return this.jdbcTemplate.query(getUserFavoriteQuery,
                (rs, rowNum) -> new GetUserPaymentRes(
                        rs.getInt("card_id"),
                        rs.getString("card_name"),
                        rs.getString("card_num"),
                        rs.getInt("user_id")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getUserPaymentParams); // 해당 닉네임을 갖는 모든 User 정보를 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    //결제 관리에서 카드 추가 메소드
    public int createUserPayment(int userId, PostUserPaymentReq postUserPaymentReq){
        String createUserPaymentQuery = "INSERT INTO coupangeats.card (card_num, user_id, valid_thru_month, valid_thru_year, cvc, password, card_name) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Object[] createUserPaymentParams = new Object[]{postUserPaymentReq.getCardNum(), userId, postUserPaymentReq.getValidThruMonth(), postUserPaymentReq.getValidThruYear(),
                postUserPaymentReq.getCvc(),postUserPaymentReq.getPassword(), postUserPaymentReq.getCardName()};
        this.jdbcTemplate.update(createUserPaymentQuery, createUserPaymentParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    //결제 관리에서 카드 삭제 메소드
    public boolean deleteUserPayment(int userId, int cardId) {
        String deleteUserPaymentQuery = "delete from card where user_id = ? and card_id = ?;";
        Object[] args = new Object[] {userId, cardId};

        return jdbcTemplate.update(deleteUserPaymentQuery, args) == 1;
    }

    //카드 번호 중복 여부 체크
    public int checkCardNum(String cardNum){
        String checkCardNumQuery = "select exists(select card_num from card where card_num = ?)";
        String checkCardNumParams = cardNum;
        return this.jdbcTemplate.queryForObject(checkCardNumQuery,
                int.class,
                checkCardNumParams);
    }
}
