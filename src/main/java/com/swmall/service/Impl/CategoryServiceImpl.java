package com.swmall.service.Impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.swmall.common.ServerResponse;
import com.swmall.dao.CategoryMapper;
import com.swmall.pojo.Category;
import com.swmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author : suwei
 * @description : 商品分类业务处理
 * @date : 2017\11\9 0009 14:35
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private  static Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加商品分类
     * @param categoryName 分类名称
     * @param parentId 父类名称，默认0
     * @return ServerResponse
     */
    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId){
        if(parentId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMsg("增加品类参数错误");
        }
        Category category = new Category();
        category.setParentId(parentId);
        category.setName(categoryName);
        category.setStatus(true);

        int rowCount = categoryMapper.insert(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccessMsg("增加品类成功");
        }
        return ServerResponse.createByErrorMsg("增加品类失败");
    }


    /**
     * 设置分类名称
     * @param categoryId 分类id
     * @param categoryName 分类名称
     * @return ServerResponse
     */
    @Override
    public ServerResponse updateCategoryName(Integer categoryId ,String categoryName){
        if(categoryId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMsg("更新品类参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccessMsg("设置分类名称成功");
        }
        return ServerResponse.createByErrorMsg("设置分类名称失败");

    }

    /**
     * 查找对应id的子分类
     * @param categoryId 分类id
     * @return ServerResponse
     */
    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId){
        List<Category> categoryList = categoryMapper.selectCategoryChildrenNByParentId(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);

    }

    /**
     * 递归查找对应id的所有子分类的id
     * @param categoryId 分类id
     * @return ServerResponse
     */
    @Override
    public ServerResponse selectCategoryAndChildrenById( Integer categoryId ){
        Set<Category> categorySet = Sets.newHashSet();
        if(categoryId == null){
            return ServerResponse.createByErrorMsg("参数错误");
        }
        findChildCategory(categorySet,categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();
        for(Category categoryItem : categorySet){
            categoryIdList.add(categoryItem.getId());
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    /**
     * 递归算法，算出所有子节点
     * @param categorySet 返回值作为参数
     * @param categoryId id
     * @return Set<Category>
     */
    public Set<Category> findChildCategory(Set<Category> categorySet,Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.selectCategoryChildrenNByParentId(categoryId);
        for(Category categoryItem : categoryList){
           findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }


}
