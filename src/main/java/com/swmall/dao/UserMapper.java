package com.swmall.dao;

import com.swmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 查询用户是否存在
     * @param username
     * @return 用户的数量
     */
    int checkUsername(String username);

    /**
     * 查询邮箱是否存在
     * @param email
     * @return
     */
    int checkEmail(String email);

    /**
     * 查询对应密码是否存在
     * @param password
     * @param userId
     * @return
     */
    int checkPaswsword(@Param("password") String password,@Param("userId") int userId);

    User selectLogin(@Param("username") String username, @Param("password") String password);

    /**
     * 查询密码提示问题
     * @param username
     * @return
     */
    String selectQuestion(String username);

    /**
     * 检验密码提示问题的答案是否正确
     * @param username
     * @param question
     * @param answer
     * @return
     */
    int checkAnswer(@Param("username")String username,@Param("question")String question,@Param("answer")String answer);

    int updatePasswordByUsername(@Param("username")String username,@Param("passwordNew") String passwordNew);

    int checkEmailByUserid(@Param("email")String email,@Param("userId") int userId);
}