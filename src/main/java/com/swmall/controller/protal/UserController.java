package com.swmall.controller.protal;

import com.swmall.dao.UserMapper;
import com.swmall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/10/16/016.
 */
//证明是controller层并且返回json
@RestController
public class UserController {
    //依赖注入
    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "cs")
    public User cs() {
        //调用dao层
        User user = userMapper.selectByPrimaryKey(1);
        return user;
    }

}