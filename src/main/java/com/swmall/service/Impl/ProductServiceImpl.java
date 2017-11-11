package com.swmall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.swmall.common.ResponseStatusCode;
import com.swmall.common.ServerResponse;
import com.swmall.dao.CategoryMapper;
import com.swmall.dao.ProductMapper;
import com.swmall.pojo.Category;
import com.swmall.pojo.Product;
import com.swmall.service.IProductService;
import com.swmall.util.DateTimeUtil;
import com.swmall.util.PropertiesUtil;
import com.swmall.vo.ProductDetailVo;
import com.swmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author : suwei
 * @description :
 * @date : 2017\11\11 0011 14:58
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 保存或者修改商品
     * @param product 商品
     * @return ServerResponse
     */
    @Override
    public ServerResponse saveOrupdateProduct(Product product){
        if(product != null){
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] split = product.getSubImages().split(",");
                if(split.length > 0){
                    product.setMainImage(split[0]);
                }
            }
            if(product.getId() != null){
                int rowCount = productMapper.updateByPrimaryKey(product);
                if(rowCount > 0){
                    return ServerResponse.createBySuccessMsg("更新商品成功");
                }
                return ServerResponse.createByErrorMsg("更新商品失败");
            }else {
                int rowCount =  productMapper.insert(product);
                if(rowCount > 0){
                    return ServerResponse.createBySuccessMsg("新增商品成功");
                }
                return ServerResponse.createByErrorMsg("新增商品失败");
            }
        }
        return ServerResponse.createByErrorMsg("保存或者修改商品参数错误");
    }


    /**
     * 设置商品的上下架
     * @param productId 商品id
     * @param productStatus 商品状态
     * @return ServerResponse
     */
    @Override
    public ServerResponse<String> setSaleStatus(Integer productId ,Integer productStatus){
        if(productId == null || productStatus == null){
            return ServerResponse.createByErrorCodeMsg(ResponseStatusCode.ILLEGAL_ARGUMENT.getCode(),ResponseStatusCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(productStatus);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if(rowCount > 0){
            return ServerResponse.createBySuccessMsg("设置商品状态成功");
        }
        return ServerResponse.createByErrorMsg("设置商品状态失败");
    }

    /**
     * 获取商品详情
     * @param productId 商品id
     * @return ServerResponse
     */
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId){
        if(productId == null){
            return ServerResponse.createByErrorCodeMsg(ResponseStatusCode.ILLEGAL_ARGUMENT.getCode(),ResponseStatusCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createByErrorMsg("商品已下架或者删除");
        }
        //将Product组装成ProductDetailVo
        ProductDetailVo productDetailVo = this.assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }


    /**
     * 将Product组装成ProductDetailVo
     * @param product product
     * @return ProductDetailVo
     */
    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();

        productDetailVo.setId(product.getId());
        productDetailVo.setName(product.getName());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setPrice(product.getPrice());

        //imageHost
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.image.host","http://60.205.176.176:9000/"));
        //parentCategoryId
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            //缺省设置为根节点
            productDetailVo.setParentCategoryId(0);
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        //createtime,updatetime
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));

        return productDetailVo;
    }

    /**
     * 获取商品列表页
     * @param pageNum 当前页码
     * @param pageSize 页面大小
     * @return ServerResponse
     */
    @Override
    public ServerResponse<PageInfo> getProductList(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectList();
        List<ProductListVo> productListVoList = Lists.newArrayList();

        for(Product productItem : productList){
            ProductListVo productListVo = this.assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 将Product组装成ProductListVo
     * @param product 商品
     * @return ProductListVo
     */
    private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setName(product.getName());
        productListVo.setPrice(product.getPrice());
        productListVo.setStatus(product.getStatus());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.image.host","http://60.205.176.176:9000/"));
        return productListVo;
    }

    /**
     * 根据productName或者productId搜索商品
     * @param productName 商品名称
     * @param productId 商品id
     * @param pageNum 当前页码
     * @param pageSize 页面大小
     * @return ServerResponse<PageInfo>
     */
    public ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(productName)){
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        //根据productName模糊查询
        List<Product> productList = productMapper.selectByProductNameAndProductId(productName, productId);
        List<ProductListVo> productListVoList = Lists.newArrayList();

        for(Product productItem : productList){
            ProductListVo productListVo = this.assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);

    }





}
