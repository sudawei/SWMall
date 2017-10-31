package com.swmall.controller.protal;

import com.swmall.common.Const;
import com.swmall.common.ResponseStatusCode;
import com.swmall.common.ServerResponse;
import com.swmall.pojo.User;
import com.swmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author Administrator
 * @data 2017/10/16/016.
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @PostMapping("login")
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username, password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 用户退出
     * @param session
     * @return
     */
    @PostMapping("logout")
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("register")
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }

    /**
     * 检查用户名或者邮箱是否存在
     * @param str
     * @param type
     * @return
     */
    @PostMapping("check_valid")
    public ServerResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }

    /**
     * 获取密码提示问题
     * @param username
     * @return
     */
    @PostMapping("forget_get_question")
    public ServerResponse<String> forgetGetQuestion(HttpSession session,String username){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMsg("用户未登录");
        }
        if(!StringUtils.equals(user.getUsername(),username)){
            return ServerResponse.createByErrorMsg("无法获取他人的密码提示问题");
        }
        return iUserService.selectQuestion(username);
    }

    /**
     * 验证密码提示问题的答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @PostMapping("forget_check_answer")
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
       return iUserService.checkAnswer(username,question,answer);
    }

    /**
     * 忘记密码时的重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @PostMapping("forget_reset_password")
    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
    }

    /**
     * 重置密码
     * @param session
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @PostMapping("reset_password")
    public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMsg("用户未登录");
        }
        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }

    /**
     * 更新用户信息
     * @param session
     * @param user
     * @return
     */
    @PostMapping("update_infomation")
    public ServerResponse<User> updateInformation(HttpSession session,User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMsg("用户未登录");
        }
        //从session中的当前用户信息中获取id，防止横向越权
        user.setId(currentUser.getId());

        ServerResponse<User> response = iUserService.update_information(user);
        if(response.isSuccess()){
            response.getData().setUsername(currentUser.getUsername());
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 获取当前用户的信息
     * @param session
     * @return
     */
    @PostMapping("get_user_info")
    public ServerResponse<User> getInformation(HttpSession session){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMsg(ResponseStatusCode.NEED_LOGIN.getCode(),"用户未登录，请先登录");
        }
        return iUserService.get_information(currentUser.getId());
    }









}
