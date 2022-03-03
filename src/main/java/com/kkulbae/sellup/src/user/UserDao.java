package com.kkulbae.sellup.src.user;


import com.kkulbae.sellup.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select * from users";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nickname"))
                );
    }

    public List<GetUserRes> getUsersByEmail(String email){
        String getUsersByEmailQuery = "select * from users where email =?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nickname")),
                getUsersByEmailParams);
    }

    public GetUserRes getUser(int userIdx){
        String getUserQuery = "select * from users where userIdx = ?";
        int getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nickname")),
                getUserParams);
    }
    

    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into users(email, password, nickname) VALUES (?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getEmail(), postUserReq.getPassword(), postUserReq.getNickname()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from users where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int checkNickName(String nickname){
        String checkNickNameQuery = "select exists(select nickname from users where nickname = ?)";
        String checkNickNameParams = nickname;
        return this.jdbcTemplate.queryForObject(checkNickNameQuery,
                int.class,
                checkNickNameParams);

    }

    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update UserInfo set userName = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select userIdx,email,password,nickname from users where email = ?";
        String getPwdParams = postLoginReq.getEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nickname")
                ),
                getPwdParams
                );

    }

    public int checkValidUser(int userIdx){
        String checkValidUserQuery = "select exists(select userIdx from users where userIdx = ? and isDeleted = 'Y')";
        int checkValidUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkValidUserQuery,
                int.class,
                checkValidUserParams);

    }

}
