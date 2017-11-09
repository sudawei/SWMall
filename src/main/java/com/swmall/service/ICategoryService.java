package com.swmall.service;

import com.swmall.common.ServerResponse;
import com.swmall.pojo.Category;

import java.util.List;

/**
 * @author : suwei
 * @description :
 * @date : 2017\11\9 0009 14:35
 */
public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId ,String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    ServerResponse selectCategoryAndChildrenById( Integer categoryId );
}
