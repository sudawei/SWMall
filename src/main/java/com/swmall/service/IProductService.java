package com.swmall.service;

import com.github.pagehelper.PageInfo;
import com.swmall.common.ServerResponse;
import com.swmall.pojo.Product;
import com.swmall.vo.ProductDetailVo;

/**
 * @author : suwei
 * @description :
 * @date : 2017\11\11 0011 14:57
 */
public interface IProductService {
    ServerResponse saveOrupdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId ,Integer productStatus);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize);

}
