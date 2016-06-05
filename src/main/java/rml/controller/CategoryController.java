package rml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.*;
import rml.service.BannerService;
import rml.service.ProductCategoryService;
import rml.util.MD5;
import rml.util.ReturnJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edward-echo on 2016/3/14.
 */

@Controller
@RequestMapping("/Category")
public class CategoryController {
    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    BannerService bannerService;

    @RequestMapping(value="/Recommend/Categories",method = RequestMethod.GET)
    @ResponseBody
    public Object getUsers1All(ProductCategory category) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        Categories categories = new Categories();
        String result = MD5.GetMD5Code(category.getRandomKey()+"at^&*ta");
        if(!result.equals(category.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + category.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {

            categories.setHotCategories(productCategoryService.getHotRec());
            categories.setNormalCategories(productCategoryService.getNormalRec());
            List<Banner> banners = bannerService.getPageBanners("categoryList");
            categories.setBanners(banners);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(categories);
        return returnJson;
    }

    @RequestMapping(value="/Categories",method = RequestMethod.GET)
    @ResponseBody
    public Object getUssssers1All(ProductCategory category) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        String result = MD5.GetMD5Code(category.getRandomKey()+"at^&*ta");
        if(!result.equals(category.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + category.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            returnJson.setReturnObject(productCategoryService.getSecRec());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }


    @RequestMapping(value="/Category",method = RequestMethod.GET)
    @ResponseBody
    public Object getUssssers1Assll(ProductCategory category) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        String result = MD5.GetMD5Code(category.getRandomKey()+"at^&*ta");
        if(!result.equals(category.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + category.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            returnJson.setReturnObject(productCategoryService.getCategoryUUID(category.getUuid()));
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }



    @RequestMapping(value="/Categories/Secondary/Jump",method = RequestMethod.GET)
    @ResponseBody
    public Object getUserJumpAll(ProductCategory category) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        String result1 = MD5.GetMD5Code(category.getRandomKey()+"at^&*ta");
        if(!result1.equals(category.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + category.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            CategoryPage page = new CategoryPage();
            List<ProductCategory> result = new ArrayList<ProductCategory>();
            ProductCategory category2 = new ProductCategory();
            category2.setUuid(category.getUuid()+",999999");
            category2.setName("精选");
            category2.setOrder(1);
            result.add(category2);
            ProductCategory category1 = productCategoryService.getCategoryUUID(category.getUuid());
            ProductCategory firstCategory = productCategoryService.getCategoryUUID(category1.getPid());
            List<ProductCategory> categories = productCategoryService.getProductPid(firstCategory.getUuid());
            for(ProductCategory productCategory:categories){
                result.add(productCategory);
            }
            List<Banner> banners = bannerService.getPageBanners(firstCategory.getName());
            for(ProductCategory category3:categories){
                if(category3.getUuid().equals(category1.getUuid())){

                    category3.setIsCurrent(true);
                }
            }
            page.setFirstLevelCategory(firstCategory);

            page.setSecondLevelCategories(result);

            page.setBanners(banners);
            returnJson.setReturnObject(page);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }

    @RequestMapping(value="/Categories/Top/Jump",method = RequestMethod.GET)
    @ResponseBody
    public Object getUser1JumpAll(ProductCategory categoryMain) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        String result1 = MD5.GetMD5Code(categoryMain.getRandomKey()+"at^&*ta");
        if(!result1.equals(categoryMain.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + categoryMain.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            String id = "";
            List<ProductCategory> categories = new ArrayList<ProductCategory>();
            List<ProductCategory> categories2 = new ArrayList<ProductCategory>();
            ProductCategory category1 = new ProductCategory();
            CategoryPage page = new CategoryPage();
            ProductCategory firstCategory = productCategoryService.getCategoryUUID(categoryMain.getUuid());
            List<ProductCategory> categories1 = productCategoryService.getProductPid(firstCategory.getUuid());
            for(ProductCategory category:categories1){
                categories.add(category);
                id= category.getUuid();
            }
            category1.setUuid(id+",999999");
            category1.setName("精选");
            category1.setOrder(1);
            categories2.add(category1);
            for(ProductCategory category:categories1){
                categories2.add(category);
                id= category.getUuid();
            }
            List<Banner> banners = bannerService.getPageBanners(firstCategory.getName());
            page.setFirstLevelCategory(firstCategory);
            page.setSecondLevelCategories(categories2);
            page.setBanners(banners);
            returnJson.setReturnObject(page);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }
}
