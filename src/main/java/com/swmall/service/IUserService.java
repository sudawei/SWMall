package com.swmall.service;

import com.swmall.common.ServerResponse;
import com.swmall.pojo.User;

/**
 * @author suwei
 * @date 2017/10/16/016.
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str,String type);

    ServerResponse selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username,String question,String answer);

    ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);

    ServerResponse<User> update_information(User user);

    ServerResponse<User> get_information(Integer userId);
}
