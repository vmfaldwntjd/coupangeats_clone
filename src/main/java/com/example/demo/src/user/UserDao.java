package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

//    public List<GetUserRes> getUsers(){
//        String getUsersQuery = "select * from UserInfo";
//        return this.jdbcTemplate.query(getUsersQuery,
//                (rs,rowNum) -> new GetUserRes(
//                        rs.getInt("userIdx"),
//                        rs.getString("userName"),
//                        rs.getString("ID"),
//                        rs.getString("Email"),
//                        rs.getString("password"))
//                );
//    }
//
//    public List<GetUserRes> getUsersByEmail(String email){
//        String getUsersByEmailQuery = "select * from UserInfo where email =?";
//        String getUsersByEmailParams = email;
//        return this.jdbcTemplate.query(getUsersByEmailQuery,
//                (rs, rowNum) -> new GetUserRes(
//                        rs.getInt("userIdx"),
//                        rs.getString("userName"),
//                        rs.getString("ID"),
//                        rs.getString("Email"),
//                        rs.getString("password")),
//                getUsersByEmailParams);
//    }
//
//    public GetUserRes getUser(int userIdx){
//        String getUserQuery = "select * from UserInfo where userIdx = ?";
//        int getUserParams = userIdx;
//        return this.jdbcTemplate.queryForObject(getUserQuery,
//                (rs, rowNum) -> new GetUserRes(
//                        rs.getInt("userIdx"),
//                        rs.getString("userName"),
//                        rs.getString("ID"),
//                        rs.getString("Email"),
//                        rs.getString("password")),
//                getUserParams);
//    }

    public GetPhoneUserRes getUserEmailByPhone(String phone){
        String getUserEmailByPhoneQuery = "select email from user where phone = ?";
        return this.jdbcTemplate.queryForObject(getUserEmailByPhoneQuery,
                (rs, rowNum) -> new GetPhoneUserRes(
                        true,
                        rs.getString("email")),
                phone);
    }

    

    public int createUser(PostSignUpReq postSignUpReq){
        String createUserQuery = "insert into user (email, name, phone, password) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postSignUpReq.getEmail(), postSignUpReq.getName(), postSignUpReq.getPhone(), postSignUpReq.getPassword()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int setTokens(int userId, String jwt, String refreshJwt){
        String setUserTokensQuery = "update user set access_token = ?, refresh_token = ? where user_id = ?";
        Object[] setUserTokensParams = new Object[]{jwt, refreshJwt, userId};
        return this.jdbcTemplate.update(setUserTokensQuery, setUserTokensParams);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from user where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }

    public int checkPhone(String phone){
        String checkPhoneQuery = "select exists(select phone from user where phone = ?)";
        String checkPhoneParams = phone;
        return this.jdbcTemplate.queryForObject(checkPhoneQuery,
                int.class,
                checkPhoneParams);
    }

    public String getUserAccessToken(int userId){
        String getUserAccessTokenQuery = "select access_token from user where user_id = ?";
        int getUserAccessTokenParam = userId;
        return this.jdbcTemplate.queryForObject(getUserAccessTokenQuery,
                String.class,
                getUserAccessTokenParam);
    }
    public String getUserRefreshToken(int userId){
        String getUserRefreshTokenQuery = "select refresh_token from user where user_id = ?";
        int getUserRefreshTokenParam = userId;
        return this.jdbcTemplate.queryForObject(getUserRefreshTokenQuery,
                String.class,
                getUserRefreshTokenParam);
    }


    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update UserInfo set userName = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public User getUser(PostSignInReq postSignInReq){
        String getUserQuery = "select user_id, name, email, password, status, phone from user where email = ?";
        String getUserParam = postSignInReq.getEmail();

        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("status"),
                        rs.getString("phone")
                ),
                getUserParam
                );
    }

    public int checkId(int userId){
        String checkIdQuery = "select exists(select user_id from user where user_id = ?)";
        int checkIdParams = userId;
        return this.jdbcTemplate.queryForObject(checkIdQuery,
                int.class,
                checkIdParams);
    }

    public GetUserAddressRes getUserAddress(int userId, Boolean isSelected){
        String getUserAddressQuery = "select user_id, address_name from user_address where user_id = ? AND is_selected = ?";

        Object[] getUserAddressParams = new Object[]{userId, isSelected};
        return this.jdbcTemplate.queryForObject(getUserAddressQuery,
                (rs, rowNum) -> new GetUserAddressRes(
                        rs.getInt("user_id"),
                        rs.getString("address_name")
                ),
                getUserAddressParams);
    }
}
