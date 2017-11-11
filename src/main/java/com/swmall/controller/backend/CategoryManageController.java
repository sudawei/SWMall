package com.swmall.controller.backend;

import com.swmall.common.Const;
import com.swmall.common.ResponseStatusCode;
import com.swmall.common.ServerResponse;
import com.swmall.pojo.User;
import com.swmall.service.ICategoryService;
import com.swmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author : suwei
 * @description :
 * @date : 2017\11\9 0009 14:21
 */
@RestController
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;


    /**
     * 添加分类
     * @param session session
     * @param categoryName 分类名称
     * @param parentId 父类名称，默认0
     * @return ServerResponse
     */
    @GetMapping("add_category")
    public ServerResponse addCategory(HttpSession session,String categoryName,@RequestParam(value = "parentId",defaultValue = "0") Integer parentId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMsg(ResponseStatusCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检查一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //添加分类的具体操作
            return iCategoryService.addCategory(categoryName, parentId);
        }else {
            return ServerResponse.createByErrorMsg("用户权限不足");
        }
    }


    /**
     * 设置分类名称
     * @param session session
     * @param categoryId 分类id
     * @param categoryName 分类名称
     * @return ServerResponse
     */
    @GetMapping("set_category_name")
    public ServerResponse setCategoryName(HttpSession session,Integer categoryId ,String categoryName){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMsg(ResponseStatusCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检查一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //设置分类名称的具体操作
            return iCategoryService.updateCategoryName(categoryId,categoryName);
        }else {
            return ServerResponse.createByErrorMsg("用户权限不足");
        }
    }


    /**
     * 查找对应id的子分类
     * @param session session
     * @param categoryId 分类id
     * @return ServerResponse
     */
    @GetMapping("get_category")
    public ServerResponse getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId ){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMsg(ResponseStatusCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检查一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //设置分类名称的具体操作
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }else {
            return ServerResponse.createByErrorMsg("用户权限不足");
        }
    }

    /**
     * 查找对应id的所有子分类
     * @param session session
     * @param categoryId 分类id
     * @return ServerResponse
     */
    @GetMapping("get_deep_category")
    public ServerResponse getChildrenDeepCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId ){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMsg(ResponseStatusCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检查一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //查找对应id的所有子分类的具体操作
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        }else {
            return ServerResponse.createByErrorMsg("用户权限不足");
        }
    }


}
