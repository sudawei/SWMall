package com.swmall.controller.backend;

import com.swmall.common.Const;
import com.swmall.common.ResponseStatusCode;
import com.swmall.common.ServerResponse;
import com.swmall.pojo.Product;
import com.swmall.pojo.User;
import com.swmall.service.IProductService;
import com.swmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author : suwei
 * @description : 商品控制器
 * @date : 2017\11\11 0011 14:52
 */
@RestController
@RequestMapping("/manage/product")
public class ProductManageController {


    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    /**
     * 更新或者添加商品的操作
     * @param session session
     * @param product 商品
     * @return 分页信息
     */
    @PostMapping("save")
    public ServerResponse productSave(HttpSession session, Product product){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMsg(ResponseStatusCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检查一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //具体操作
            return iProductService.saveOrupdateProduct(product);
        }else {
            return ServerResponse.createByErrorMsg("用户权限不足");
        }
    }

    /**
     * 设置商品的上下架
     * @param session  session
     * @param productId 商品id
     * @param productStatus 商品的状态
     * @return ServerResponse
     */
    @GetMapping("/{productId}/{productStatus}/set_sale_status")
    public ServerResponse setSaleStatus(HttpSession session, @PathVariable("productId") Integer productId ,@PathVariable("productStatus") Integer productStatus){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMsg(ResponseStatusCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检查一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //具体操作
            return iProductService.setSaleStatus(productId,productStatus);
        }else {
            return ServerResponse.createByErrorMsg("用户权限不足");
        }
    }

    /**
     * 获取商品的详情
     * @param session  session
     * @param productId 商品id
     * @return ServerResponse
     */
    @GetMapping("/{productId}/detail")
    public ServerResponse getDetail(HttpSession session, @PathVariable("productId") Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMsg(ResponseStatusCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检查一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //具体操作
            return iProductService.manageProductDetail(productId);
        }else {
            return ServerResponse.createByErrorMsg("用户权限不足");
        }
    }

    /**
     * 获取商品列表页
     * @param session session
     * @param pageNum 当前页码
     * @param pageSize 页面大小
     * @return
     */
    @GetMapping("list")
    public ServerResponse getList(HttpSession session,@RequestParam(value= "pageNum",defaultValue = "1") int pageNum,
                                  @RequestParam(value= "pageSize",defaultValue = "10")  int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMsg(ResponseStatusCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检查一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //具体操作
            return iProductService.getProductList(pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMsg("用户权限不足");
        }
    }


    /**
     * 根据productName或者productId搜索商品
     * @param session session
     * @param productName 商品名称
     * @param productId 商品id
     * @param pageNum 当前页码
     * @param pageSize 页面大小
     * @return ServerResponse
     */
    @PostMapping("search")
    public ServerResponse productSearch(HttpSession session,String productName,Integer productId,@RequestParam(value= "pageNum",defaultValue = "1") int pageNum,
                                        @RequestParam(value= "pageSize",defaultValue = "10")  int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMsg(ResponseStatusCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检查一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //具体操作
            return iProductService.searchProduct(productName,productId,pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMsg("用户权限不足");
        }
    }




}
