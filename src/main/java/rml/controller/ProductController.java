package rml.controller;

import com.lowagie.text.Image;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import rml.dao.ProductBrandMapper;
import rml.model.*;
import rml.service.*;
import rml.servlet.FileUploadServlet;
import rml.util.*;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by edward-echo on 2016/1/23.
 */
@Controller
@RequestMapping("/Product")
public class ProductController  {

    @Autowired
    private ProductBrandService productBrandService;


    @RequestMapping(value="/Brands",method = RequestMethod.GET)
      @ResponseBody
      public Object getUsers(String moduleId) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<List<String>> temps = new ArrayList<List<String>>();
        List<ProductBrand> results = null;
        HashMap<String,List<List<String>>> map = new HashMap<String, List<List<String>>>();
        try {
            results = productBrandService.getProductBrand(moduleId);
            for(ProductBrand user:results){
                List<String> values = new ArrayList<String>();
                values.add(user.getName());
                values.add(new Integer(user.getOrder()).toString());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(user.getCreateTime());
                values.add(dateString);
                if(user.getIsTop()==0){
                    values.add("否");
                }else{
                    values.add("是");
                }
                values.add("<button id='modify' onclick=modify('"+user.getUuid()+"')>修改记录</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button id='modify' onclick=deleteBrand('"+user.getUuid()+"')>删除记录</button>");

                temps.add(values);

            }
            map.put("data",temps);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(temps);
        return map;
    }



    @Autowired
    PropertyValueService propertyValueService;


    @Autowired
    private ProductBrandLinkService productBrandLinkService;

    @RequestMapping(value="/Product",method = RequestMethod.POST)
    @ResponseBody
    public Object addUser(@RequestBody Product product) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(1000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        try {
            product.setUuid(UUID.randomUUID().toString());
            for(int i=0;i<product.getPropertyId().size();i++){
                PropertyValue propertyValue = new PropertyValue();
                propertyValue.setPropertyId(product.getPropertyId().get(i));
                propertyValue.setProductId(product.getUuid());
                propertyValue.setModuleId(product.getModuleId());
                propertyValue.setPropertyKey(product.getPropertyKey().get(i));
                propertyValue.setPropertyValue(product.getPropertyValue().get(i));
                propertyValue.setPrice(100);
                propertyValue.setRecordStatus(1);
                propertyValue.setType(1);
                propertyValue.setCreateTime(new Date());
                propertyValue.setUuid(UUID.randomUUID().toString());
                propertyValueService.insert(propertyValue);
                PropertyValue propertyValue1 = new PropertyValue();
                propertyValue1.setType(2);
                propertyValue1.setBrandId(product.getBrandId());
                propertyValue1.setProductId(product.getUuid());
                propertyValue1.setPropertyId(product.getPropertyKey().get(i));
                propertyValue1.setModuleId(product.getModuleId());
                propertyValue1.setPropertyKey(product.getPropertyValue().get(i));
                propertyValue1.setPropertyValue(product.getPropertyValue().get(i));
                propertyValue1.setRecordStatus(1);
                propertyValue1.setType(2);
                propertyValue1.setCreateTime(new Date());
                propertyValue1.setUuid(UUID.randomUUID().toString());
                propertyValueService.insert(propertyValue1);
            }
            String [] prices = product.getBackPrice().split(",");
            String[] productBrandIds = product.getBrandIds().split(",");
            for(int i=0;i<productBrandIds.length;i++){
                ProductBrandLink  link = new ProductBrandLink();
                link.setCreateTime(new Date());
                link.setBrandId(productBrandIds[i]);
                link.setProductId(product.getUuid());
                link.setUuid(UUID.randomUUID().toString());
                productBrandLinkService.insertBrand(link);
            }
            String[] imgs_main = product.getImgsMain().split(",");
            String main_files = "";
            for (String img : imgs_main) {
                if (!org.springframework.util.StringUtils.isEmpty(img) || img == null) {
                    try {
                        String fileName = FileUtil.transferImg1(img, System.getProperty("java.io.tmpdir") + "/tmp0");
                        String filePath = FileUploadServlet.fileUpload(new File(fileName));
                        if (main_files != "") {
                            main_files = main_files + "," + filePath;
                        } else {
                            main_files = main_files + filePath;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            product.setMainImgs(main_files);
            imgs_main = product.getImgs().split(",");
            main_files = "";
            for (String img : imgs_main) {
                if (!org.springframework.util.StringUtils.isEmpty(img) || img == null) {
                    try {
                        String fileName = FileUtil.transferImg7(img, System.getProperty("java.io.tmpdir") + "/tmp0");
                        String filePath = FileUploadServlet.fileUpload(new File(fileName));
                        if (main_files != "") {
                            main_files = main_files + "," + filePath;
                        } else {
                            main_files = main_files + filePath;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            imgs_main = product.getImgsMain().split(",");
            main_files = "";
            for (String img : imgs_main) {
                if (!org.springframework.util.StringUtils.isEmpty(img) || img == null) {
                    try {
                        String fileName = FileUtil.transferImg7(img, System.getProperty("java.io.tmpdir") + "/tmp0");
                        String filePath = FileUploadServlet.fileUpload(new File(fileName));
                        if (main_files != "") {
                            main_files = main_files + "," + filePath;
                        } else {
                            main_files = main_files + filePath;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            product.setMainImgsBig(main_files);
            product.setCreateTime(new Date());
            productService.insert(product);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(1004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }


    @Autowired
    private ProductCategoryLinkService productCategoryLinkService;

//    @RequestMapping(value="/Product/Suit",method = RequestMethod.POST)
//    @ResponseBody
//    public Object addUser22(@RequestBody Product product) {
//
//        ReturnJson returnJson = new ReturnJson();
//        returnJson.setErrorCode(2000);
//        returnJson.setReturnMessage("调用成功");
//        returnJson.setServerStatus(0);
//        try {
//            product.setUuid(UUID.randomUUID().toString());
//            String[] propertyIds = product.getPropertyId().split(",");
//            String[] propertyKeys = product.getPropertyKey().split(",");
//            String[] propertyValues = product.getPropertyValue().split(",");
//            String [] prices = product.getBackPrice().split(",");
//            String[] productBrandIds = product.getBrandIds().split(",");
//            for(int i=0;i<productBrandIds.length;i++){
//                ProductBrandLink  link = new ProductBrandLink();
//                link.setCreateTime(new Date());
//                link.setBrandId(productBrandIds[i]);
//                link.setProductId(product.getUuid());
//                link.setUuid(UUID.randomUUID().toString());
//                productBrandLinkService.insertBrand(link);
//            }
//            String[] categoryIds = product.getCategoryIds().split(",");
//            for(int i=0;i<categoryIds.length;i++){
//                ProductCategoryLink link = new ProductCategoryLink();
//                link.setUuid(UUID.randomUUID().toString());
//                link.setCategoryId(categoryIds[i]);
//                link.setProductId(product.getUuid());
//                link.setCreateTime(new Date());
//                productCategoryLinkService.insertCategory(link);
//            }
//            for(int i=0;i<propertyIds.length;i++){
//                PropertyValue propertyValue = new PropertyValue();
//                propertyValue.setPropertyId(propertyIds[i]);
//                propertyValue.setProductId(product.getUuid());
//                propertyValue.setModuleId(product.getModuleId());
//                propertyValue.setPropertyKey(propertyKeys[i]);
//                propertyValue.setPropertyValue(propertyValues[i]);
//                propertyValue.setPrice(new Double(new Double(prices[i])*100).intValue());
//                propertyValue.setRecordStatus(1);
//                propertyValue.setType(1);
//                propertyValue.setCreateTime(new Date());
//                propertyValue.setUuid(UUID.randomUUID().toString());
//                propertyValueService.insert(propertyValue);
//                PropertyValue propertyValue1 = new PropertyValue();
//                propertyValue1.setType(2);
//                propertyValue1.setBrandId(product.getBrandId());
//                propertyValue1.setProductId(product.getUuid());
//                propertyValue1.setPropertyId(propertyIds[i]);
//                propertyValue1.setModuleId(product.getModuleId());
//                propertyValue1.setPropertyKey(propertyKeys[i]);
//                propertyValue1.setPropertyValue(propertyValues[i]);
//                propertyValue1.setRecordStatus(1);
//                propertyValue1.setType(2);
//                propertyValue1.setCreateTime(new Date());
//                propertyValue1.setUuid(UUID.randomUUID().toString());
//                propertyValueService.insert(propertyValue1);
//            }
//            String[] imgs_main = product.getImgsMain().split(",");
//            String main_files = "";
//            for (String img : imgs_main) {
//                if (!org.springframework.util.StringUtils.isEmpty(img) || img == null) {
//                    try {
//                        String fileName = FileUtil.transferImg1(img, System.getProperty("java.io.tmpdir") + "/tmp0/");
//                        String filePath = FileUploadServlet.fileUpload(new File(fileName));
//                        if (main_files != "") {
//                            main_files = main_files + "," + filePath;
//                        } else {
//                            main_files = main_files + filePath;
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            product.setMainImgs(main_files);
//            imgs_main = product.getImgs().split(",");
//            main_files = "";
//            for (String img : imgs_main) {
//                if (!org.springframework.util.StringUtils.isEmpty(img) || img == null) {
//                    try {
//                        String fileName = FileUtil.transferImg7(img, System.getProperty("java.io.tmpdir") + "/tmp0");
//                        String filePath = FileUploadServlet.fileUpload(new File(fileName));
//                        if (main_files != "") {
//                            main_files = main_files + "," + filePath;
//                        } else {
//                            main_files = main_files + filePath;
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            product.setDetailImgs(main_files);
//            imgs_main = product.getImgsMain().split(",");
//            main_files = "";
//            for (String img : imgs_main) {
//                if (!org.springframework.util.StringUtils.isEmpty(img) || img == null) {
//                    try {
//                        String fileName = FileUtil.transferImg7(img, System.getProperty("java.io.tmpdir") + "/tmp0");
//                        String filePath = FileUploadServlet.fileUpload(new File(fileName));
//                        if (main_files != "") {
//                            main_files = main_files + "," + filePath;
//                        } else {
//                            main_files = main_files + filePath;
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            product.setMainImgsBig(main_files);
//            product.setCreateTime(new Date());
//            product.setCreateTime(new Date());
//            productService.insert(product);
//        }catch(Exception ex){
//            ex.printStackTrace();
//            returnJson.setErrorCode(1004);
//            returnJson.setReturnMessage("服务器异常");
//            returnJson.setServerStatus(2);
//            return returnJson;
//        }
//        return returnJson;
//    }



    @RequestMapping(value="/Brands/Product/Add",method = RequestMethod.GET)
    @ResponseBody
    public Object getUsers2(String moduleId) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<List<String>> temps = new ArrayList<List<String>>();
        List<ProductBrand> results = null;
        HashMap<String,List<List<String>>> map = new HashMap<String, List<List<String>>>();
        try {
            results = productBrandService.getProductBrand(moduleId);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return results;
    }


    @RequestMapping(value="/Delete",method = RequestMethod.GET)
    @ResponseBody
    public Object getUsers222(String uuid) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        try {
            productService.deleteProduct(uuid);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }


    @Autowired
    PropertyService propertyService;

    @RequestMapping(value="/Properties",method = RequestMethod.GET)
    @ResponseBody
    public Object getUsers3(String moduleId) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<Property> results = null;
        try {
            results = propertyService.getProperties(moduleId);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return results;
    }


//    @RequestMapping(value="/Product/Update",method = RequestMethod.POST)
//    @ResponseBody
//    public Object getUsers443(@RequestBody Product product) {
//
//        ReturnJson returnJson = new ReturnJson();
//        returnJson.setErrorCode(2000);
//        returnJson.setReturnMessage("调用成功");
//        returnJson.setServerStatus(0);
//        try {
//            ProductCategoryLink link1 = new ProductCategoryLink();
//            link1.setProductId(product.getUuid());
//            productCategoryLinkService.deleteProductCategory(link1);
//            propertyValueService.delete(product.getUuid());
//            productBrandLinkService.deleteBrand(product.getUuid());
//            String[] productBrandIds = product.getBrandIds().split(",");
//            for(int i=0;i<productBrandIds.length;i++){
//                ProductBrandLink  link = new ProductBrandLink();
//                link.setCreateTime(new Date());
//                link.setBrandId(productBrandIds[i]);
//                link.setProductId(product.getUuid());
//                link.setUuid(UUID.randomUUID().toString());
//                productBrandLinkService.insertBrand(link);
//            }
//
//            String[] categoryIds = product.getCategoryIds().split(",");
//            for(int i=0;i<categoryIds.length;i++){
//                ProductCategoryLink link = new ProductCategoryLink();
//                link.setUuid(UUID.randomUUID().toString());
//                link.setCategoryId(categoryIds[i]);
//                link.setProductId(product.getUuid());
//                link.setCreateTime(new Date());
//                productCategoryLinkService.insertCategory(link);
//            }
//            String[] propertyIds = product.getPropertyId().split(",");
//            String[] propertyKeys = product.getPropertyKey().split(",");
//            String[] propertyValues = product.getPropertyValue().split(",");
//            String [] prices = product.getBackPrice().split(",");
//            for(int i=0;i<propertyIds.length;i++){
//                PropertyValue propertyValue = new PropertyValue();
//                propertyValue.setPropertyId(propertyIds[i]);
//                propertyValue.setProductId(product.getUuid());
//                propertyValue.setModuleId(product.getModuleId());
//                propertyValue.setPropertyKey(propertyKeys[i]);
//                propertyValue.setPropertyValue(propertyValues[i]);
//                propertyValue.setPrice(new Double(new Double(prices[i])*100).intValue());
//                propertyValue.setRecordStatus(1);
//                propertyValue.setType(1);
//                propertyValue.setCreateTime(new Date());
//                propertyValue.setUuid(UUID.randomUUID().toString());
//                propertyValueService.insert(propertyValue);
//                PropertyValue propertyValue1 = new PropertyValue();
//                propertyValue1.setType(2);
//                propertyValue1.setBrandId(product.getBrandId());
//                propertyValue1.setProductId(product.getUuid());
//                propertyValue1.setPropertyId(propertyIds[i]);
//                propertyValue1.setModuleId(product.getModuleId());
//                propertyValue1.setPropertyKey(propertyKeys[i]);
//                propertyValue1.setPropertyValue(propertyValues[i]);
//                propertyValue1.setRecordStatus(1);
//                propertyValue1.setType(2);
//                propertyValue1.setCreateTime(new Date());
//                propertyValue1.setUuid(UUID.randomUUID().toString());
//                propertyValueService.insert(propertyValue1);
//            }
//            String[] imgs_main = product.getImgsMain().split(",");
//            String main_files = "";
//            for (String img : imgs_main) {
//                if (!org.springframework.util.StringUtils.isEmpty(img) || img == null) {
//                    try {
//                        String fileName = FileUtil.transferImg1(img, System.getProperty("java.io.tmpdir") + "/tmp0/");
//                        String filePath = FileUploadServlet.fileUpload(new File(fileName));
//                        if (main_files != "") {
//                            main_files = main_files + "," + filePath;
//                        } else {
//                            main_files = main_files + filePath;
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            product.setMainImgs(main_files);
//            imgs_main = product.getImgs().split(",");
//            main_files = "";
//            for (String img : imgs_main) {
//                if (!org.springframework.util.StringUtils.isEmpty(img) || img == null) {
//                    try {
//                        String fileName = FileUtil.transferImg7(img, System.getProperty("java.io.tmpdir") + "/tmp0/" );
//                        String filePath = FileUploadServlet.fileUpload(new File(fileName));
//                        if (main_files != "") {
//                            main_files = main_files + "," + filePath;
//                        } else {
//                            main_files = main_files + filePath;
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            product.setDetailImgs(main_files);
//            product.setCreateTime(new Date());
//            product.setDetailImgs(main_files);
//            imgs_main = product.getImgsMain().split(",");
//            main_files = "";
//            for (String img : imgs_main) {
//                if (!org.springframework.util.StringUtils.isEmpty(img) || img == null) {
//                    try {
//                        String fileName = FileUtil.transferImg7(img, System.getProperty("java.io.tmpdir") + "/tmp0");
//                        String filePath = FileUploadServlet.fileUpload(new File(fileName));
//                        if (main_files != "") {
//                            main_files = main_files + "," + filePath;
//                        } else {
//                            main_files = main_files + filePath;
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            product.setMainImgsBig(main_files);
//            productService.update(product);
//        }catch(Exception ex){
//            ex.printStackTrace();
//            returnJson.setErrorCode(2003);
//            returnJson.setReturnMessage("服务器异常");
//            returnJson.setServerStatus(2);
//            return returnJson;
//        }
//        return returnJson;
//    }

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value="/Categories/ROOT",method = RequestMethod.GET)
    @ResponseBody
    public Object getUsers11(String moduleId) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<ProductCategory> results = null;

        List<Map<String,String>> returnValue = new ArrayList<Map<String, String>>();
        try {
            results = productCategoryService.getCategoriesRoot(moduleId);
            for(ProductCategory user:results){
                HashMap<String,String> values = new HashMap<String, String>();
                   values.put("uuid",user.getUuid());
                    values.put("name",user.getName());
                    values.put("order",new Integer(user.getOrder()).toString());
                    values.put("createTime",new Long(user.getCreateTime().getTime()).toString());
                if(user.getPid()!=null){
                    values.put("_parentId",user.getPid());
                }else{
                    values.put("_parentId","");
                }
                values.put("function","<button id='modify' onclick=modify('"+user.getUuid()+"','"+user.getName()+"','"+user.getOrder()+"')>修改记录</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button id='modify' onclick=deleteBrand('"+user.getUuid()+"')>删除记录</button>");
                returnValue.add(values);
            }
            TreeTable treeTable = new TreeTable();
            treeTable.setTotal(returnValue.size());
            treeTable.setRows(returnValue);
            return treeTable;
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
    }

    @RequestMapping(value="/Categories",method = RequestMethod.GET)
    @ResponseBody
    public Object getUsers12221(String moduleId) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<ProductCategory> results = null;

        List<Map<String,String>> returnValue = new ArrayList<Map<String, String>>();
        try {
            results = productCategoryService.getProductCategories(moduleId);
            for(ProductCategory user:results){
                HashMap<String,String> values = new HashMap<String, String>();
                values.put("uuid",user.getUuid());
                values.put("name",user.getName());
                values.put("order",new Integer(user.getOrder()).toString());
                values.put("createTime",new Long(user.getCreateTime().getTime()).toString());
                if(user.getPid()!=null){
                    values.put("_parentId",user.getPid());
                }else{
                    values.put("_parentId","");
                }
                values.put("function","<button id='modify' onclick=modify('"+user.getUuid()+"','"+user.getName()+"','"+user.getOrder()+"')>修改记录</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button id='modify' onclick=deleteBrand('"+user.getUuid()+"')>删除记录</button>");
                returnValue.add(values);
            }
            TreeTable treeTable = new TreeTable();
            treeTable.setTotal(returnValue.size());
            treeTable.setRows(returnValue);
            return treeTable;
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
    }

    @RequestMapping(value="/Categories/All",method = RequestMethod.GET)
    @ResponseBody
    public Object getUsers1All(String moduleId) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<ProductCategory> results = null;

        try {
            results = productCategoryService.getCategoriesRoot(moduleId);
            for(ProductCategory user:results) {
                List<ProductCategory> child= productCategoryService.getProductPid(user.getUuid());
                user.setChild(child);
            }
            return  results;
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
    }





    @Autowired
    private ProductService productService;

    @RequestMapping(value="/Products",method = RequestMethod.GET)
    @ResponseBody
    public Object products(Product product) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<ProductCategory> results = null;

        if(StringUtils.isEmpty(product.getModuleId())||product.getPageSize()==0&&product.getPageNo()==0){
            returnJson.setErrorCode(2000);
            returnJson.setReturnMessage("调用成功");
            returnJson.setServerStatus(0);
        }
        try {
            List<Product> products = productService.getProducts(product);
            for(Product product1:products){
                List<PropertyValue> values = propertyValueService.getProductProperty(product1.getUuid());
                for(PropertyValue propertyValue:values){
                    DecimalFormat    df   = new DecimalFormat("######0");
                    propertyValue.setVipPrice(df.format(propertyValue.getPrice()));
                }
                product1.setPropertyValues(values);
            }
            return  products;
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
    }



    @RequestMapping(value="/Products/Front",method = RequestMethod.GET)
    @ResponseBody
    public Object productsFront(Product product) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<ProductCategory> results = null;

        if(product.getPageNo()==0||product.getPageSize()==0){
            returnJson.setErrorCode(2000);
            returnJson.setReturnMessage("调用成功");
            returnJson.setServerStatus(0);
        }
        String result = MD5.GetMD5Code(product.getRandomKey()+"at^&*ta");
        if(!result.equals(product.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + product.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            product.setModuleId("9");
            List<Product> products = productService.getProductFront(product);
            for(Product product1:products){
                List<PropertyValue> values = propertyValueService.getProductProperty(product1.getUuid());
                for(PropertyValue propertyValue:values){
                    DecimalFormat    df   = new DecimalFormat("######0");
                    propertyValue.setVipPrice(df.format(propertyValue.getPrice()*100));
                }
                product1.setPropertyValues(values);
                if(product1.getMainImgs()!=null) {
                    product1.setImgsMain(product1.getMainImgs());
                }
                product1.setImgs(null);
                product1.setDetailImgs(null);
                product1.setMainImgs(null);
            }
            int count = productService.getFrontAll(product.getModuleId());
            returnJson.setReturnValue(new Integer(count).toString());
            returnJson.setReturnObject(products);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    @RequestMapping(value="/Product/Front",method = RequestMethod.GET)
    @ResponseBody
    public Object productFront(Product product) {
        Jedis jedis = JedisConn.getRedis();
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<ProductCategory> results = null;

        if(StringUtils.isEmpty(product.getUuid())){
            returnJson.setErrorCode(2000);
            returnJson.setReturnMessage("调用成功");
            returnJson.setServerStatus(0);
        }

        String result1 = MD5.GetMD5Code(product.getRandomKey()+"at^&*ta");
        if(!result1.equals(product.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + product.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
           Product product1 = productService.getProduct(product.getUuid());
            List<PropertyValue> values = propertyValueService.getProductProperty(product1.getUuid());
            for(PropertyValue propertyValue:values){
                DecimalFormat    df   = new DecimalFormat("######0");
                propertyValue.setVipPrice(df.format(propertyValue.getPrice()));
            }
            if(!StringUtils.isEmpty(product1.getMainImgsBig())) {


                product1.setImgsMain(product1.getMainImgsBig());
                String tmpValue = product1.getImgsMain();
                String [] imgsMain = tmpValue.split(",");
                List<Size> imgsMianSizes = new ArrayList<Size>();
        try{
            for(int i=0;i<imgsMain.length;i++) {
                Image img = null;
                Size size = new Size();
                if (jedis.get(imgsMain[i] + "," + "width") != null) {
                    size.setWidth(jedis.get(imgsMain[i] + "," + "width"));
                } else {
                    if (img == null) {
                        img = Image.getInstance(new URL(imgsMain[i]));
                    }
                    size.setWidth(new Float(img.width()).toString());
                    jedis.append(imgsMain[i] + "," + "width", new Float(img.width()).toString());
                }
                if (jedis.get(imgsMain[i] + "," + "height") != null) {
                    size.setHeight(jedis.get(imgsMain[i] + "," + "height"));
                } else {
                    if (img == null) {
                        img = Image.getInstance(new URL(imgsMain[i]));
                    }
                    size.setHeight(new Float(img.height()).toString());
                    jedis.append(imgsMain[i] + "," + "height", new Float(img.height()).toString());
                }
                imgsMianSizes.add(size);
            }
            }catch (Exception e){
                returnJson.setErrorCode(2006);
            }
            product1.setImgsMainSizes(imgsMianSizes);
            }
        if(!StringUtils.isEmpty(product1.getDetailImgs())) {
            try {
                product1.setImgs(product1.getDetailImgs());
                List<Size> imgs = new ArrayList<Size>();
                String tmpValue = product1.getImgs();
                String[] imgsValue = tmpValue.split(",");
                for (int i = 0; i < imgsValue.length; i++) {
                    Image img = null;
                    Size size = new Size();
                    if (jedis.get(imgsValue[i] + "," + "width") != null) {
                        size.setWidth(jedis.get(imgsValue[i] + "," + "width"));
                    } else {
                        if (img == null) {
                            img = Image.getInstance(new URL(imgsValue[i]));
                        }
                        size.setWidth(new Float(img.width()).toString());
                        jedis.append(imgsValue[i] + "," + "width", new Float(img.width()).toString());
                    }
                    if (jedis.get(imgsValue[i] + "," + "height") != null) {
                        size.setHeight(jedis.get(imgsValue[i] + "," + "height"));
                    } else {
                        if (img == null) {
                            img = Image.getInstance(new URL(imgsValue[i]));
                        }
                        size.setHeight(new Float(img.height()).toString());
                        jedis.append(imgsValue[i] + "," + "height", new Float(img.height()).toString());
                    }
                    imgs.add(size);
                }
                product1.setImgsSizes(imgs);
            }catch (Exception e){
                e.printStackTrace();
            }
            }
            ProductCollection value = new ProductCollection();
            value.setProductId(product.getUuid());
            value.setUserId(product.getUserId());
            ProductCollection productCollection = productCollectionService.getRepeatCollection(value);
            if(productCollection!=null){
                product1.setCollected(true);
            }
            product1.setPropertyValues(values);
            logger.info("collected的值为:"+product1.isCollected());
            product1.setMainImgs(null);
            product1.setMainImgsBig(null);
            product1.setDetailImgs(null);
            returnJson.setReturnObject(product1);
            return returnJson;
    }


    @Autowired
    private ProductCollectionService productCollectionService;

    @RequestMapping(value="/Products/All",method = RequestMethod.GET)
    @ResponseBody
    public Object productsAll(Product product) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);

        try {
            int count = productService.getProductsAll(product);
            return  count;
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
    }

    @RequestMapping(value="/Category",method = RequestMethod.POST)
    @ResponseBody
    public Object createCategory(@RequestBody ProductCategory productCategory) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        try {
            productCategory.setUuid(UUID.randomUUID().toString());
            productCategory.setCreateTime(new Date());
            productCategoryService.insertCategory(productCategory);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }

    @RequestMapping(value="/Categories/Children",method = RequestMethod.GET)
    @ResponseBody
    public Object getUsers33(String uuid) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<List<String>> temps = new ArrayList<List<String>>();
        List<ProductCategory> results = null;
        HashMap<String,List<List<String>>> map = new HashMap<String, List<List<String>>>();
        try {
            results = productCategoryService.getProductPid(uuid);
            for(ProductCategory user:results){
                int i=0;
                List<String> values = new ArrayList<String>();
                List<ProductCategory> pumps= productCategoryService.getProductPid(user.getUuid());
                values.add(user.getUuid());
                if(pumps.size()>0){
                    values.add("<img src='images/f01.png' onclick=addChild('"+user.getUuid()+"')></img>&nbsp;&nbsp;"+user.getName());
                }else{
                    values.add(user.getName());
                }
                values.add(new Integer(user.getOrder()).toString());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(user.getCreateTime());
                values.add(dateString);
                values.add("<button id='modify' onclick=modify('"+user.getUuid()+"'%'"+user.getName()+"'&'"+user.getOrder()+"')>修改记录</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button id='modify' onclick=deleteBrand('"+user.getUuid()+"')>删除记录</button>");
                temps.add(values);

            }
            map.put("data", temps);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(temps);
        return map;
    }


    @RequestMapping(value="/Category/Products",method = RequestMethod.GET)
    @ResponseBody
    public Object gssetUsers33(Product product1) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        try {
            String result1 = MD5.GetMD5Code(product1.getRandomKey()+"at^&*ta");
            if(!result1.equals(product1.getSecretKey())){
                returnJson.setErrorCode(99999);
                returnJson.setReturnMessage("密钥无效" + product1.toString());
                returnJson.setServerStatus(1);
                return returnJson;
            }
            String categroyId = product1.getCategoryId();
            List<Product> result = new ArrayList<Product>();
            if(categroyId.indexOf("99999")!=-1){
                String[] tmp = categroyId.split(",");
                categroyId = tmp[0];
                    ProductCategory category = productCategoryService.getCategoryUUID(categroyId);
                    List<ProductCategory> categories = productCategoryService.getProductPid(category.getPid());
                    for(ProductCategory category1:categories){
                        List<Product> products = productService.getProductCategory(category1.getUuid());
                        for(Product product:products) {
                                List<PropertyValue> values = propertyValueService.getProductProperty(product.getUuid());
                                product.setPropertyValues(values);
                                result.add(product);
                            }
                        }
                returnJson.setReturnObject(result);
                return returnJson;
                }
            List<Product> products = productService.getProductCategory(categroyId);
            for(Product product:products){
                List<PropertyValue> values = propertyValueService.getProductProperty(product.getUuid());
                product.setPropertyValues(values);
                product.setImgsMain(product.getMainImgs());
                product.setImgs(null);
            }
            returnJson.setReturnObject(products);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }

    @RequestMapping(value="/Categories/Children/Front",method = RequestMethod.GET)
    @ResponseBody
    public Object g11etUsers33(String uuid) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<List<String>> temps = new ArrayList<List<String>>();
        List<ProductCategory> results = null;
        HashMap<String,List<List<String>>> map = new HashMap<String, List<List<String>>>();
        try {
            results = productCategoryService.getProductPid(uuid);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(results);
        return returnJson;
    }

    @RequestMapping(value="/Brand/Modify",method = RequestMethod.POST)
    @ResponseBody
    public Object getUsers1(@RequestBody ProductBrand productBrand) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(28000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(productBrand.getUuid())||StringUtils.isEmpty(productBrand.getName())||productBrand.getOrder()==0){
            returnJson.setErrorCode(28001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            productBrandService.updateBrand(productBrand);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(28002);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(1);
        }
        return returnJson ;
    }

    @Autowired
    BannerService bannerService;


    @RequestMapping(value="/Brands/Front",method = RequestMethod.GET)
    @ResponseBody
    public Object getUsers222(Product product1) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(28000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        String result1 = MD5.GetMD5Code(product1.getRandomKey()+"at^&*ta");
        if(!result1.equals(product1.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + product1.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            List<Banner> banners = bannerService.getPageBanners("brands");
            BrandsContainer brandsContainer = new BrandsContainer();
            brandsContainer.setBanners(banners);
            List<ProductBrand> brands = productBrandService.getBrandsAll();
            brandsContainer.setBanners(banners);
            brandsContainer.setBrands(brands);
            returnJson.setReturnObject(brandsContainer);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(28002);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(1);
        }
        return returnJson ;
    }



    @RequestMapping(value="/Brand/Delete",method = RequestMethod.POST)
    @ResponseBody
    public Object getUsers31(@RequestBody ProductBrand productBrand) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(28000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(productBrand.getUuid())){
            returnJson.setErrorCode(28001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            productBrandService.deleteBrand(productBrand.getUuid());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(28002);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(1);
        }
        return returnJson ;
    }

    @RequestMapping(value="/Brand",method = RequestMethod.GET)
    @ResponseBody
    public Object getUser122s21(String uuid) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(28000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);

        try {
            returnJson.setReturnObject(productBrandService.getProductBrandUUID(uuid));
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(28002);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(1);
        }
        return returnJson ;
    }



    @RequestMapping(value="/Brand",method = RequestMethod.POST)
    @ResponseBody
    public Object getUsers21(@RequestBody ProductBrand productBrand) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(28000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(productBrand.getOrder()==0||StringUtils.isEmpty(productBrand.getName())||StringUtils.isEmpty(productBrand.getModuleId())||StringUtils.isEmpty(productBrand.getModuleName())){
            returnJson.setErrorCode(28001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            productBrand.setUuid(UUID.randomUUID().toString());
            productBrand.setCreateTime(new Date());
            productBrandService.insertBrand(productBrand);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(28002);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(1);
        }
        return returnJson ;
    }

    @RequestMapping(value="/Search/Backend",method = RequestMethod.POST)
    @ResponseBody
    public Object getUsers221(@RequestBody Product product) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(39000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(product.getName())||product.getPageNo()==0||product.getPageSize()==0){
            returnJson.setErrorCode(39001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            List<Product> products = productService.queryProductSearch(product);
            List<Product> productsTotal = productService.queryProductSearchTotal(product);
            for(Product product1:products){
                List<PropertyValue> values = propertyValueService.getProductProperty(product1.getUuid());
                for(PropertyValue propertyValue:values){
                    DecimalFormat    df   = new DecimalFormat("######0");
                    propertyValue.setVipPrice(df.format(propertyValue.getPrice()));
                }
                product1.setPropertyValues(values);
            }
            return products;
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(39002);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(1);
            return returnJson ;
        }
    }


    @RequestMapping(value="/Search",method = RequestMethod.POST)
    @ResponseBody
    public Object getUsers224(@RequestBody Product product) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(39000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(product.getName())||product.getPageNo()==0||product.getPageSize()==0){
            returnJson.setErrorCode(39001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            List<Product> products = productService.queryProductSearch(product);
            List<Product> productsTotal = productService.queryProductSearchTotal(product);
            for(Product product1:products){
                List<PropertyValue> values = propertyValueService.getProductProperty(product1.getUuid());
                for(PropertyValue propertyValue:values){
                    DecimalFormat    df   = new DecimalFormat("######0");
                    propertyValue.setVipPrice(df.format(propertyValue.getPrice()));
                }
                product1.setPropertyValues(values);
            }
            returnJson.setReturnValue(new Integer(productsTotal.size()).toString());
            returnJson.setReturnObject(products);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(39002);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(1);
            return returnJson ;
        }
        return returnJson;
    }


    @RequestMapping(value="/Search/Brand/Backend",method = RequestMethod.POST)
    @ResponseBody
    public Object getUsers445(@RequestBody Product product) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(39000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(product.getName())||product.getPageNo()==0||product.getPageSize()==0){
            returnJson.setErrorCode(39001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            List<Product> products = productService.searchBrand(product);
            for(Product product1:products){
                List<PropertyValue> values = propertyValueService.getProductProperty(product1.getUuid());
                for(PropertyValue propertyValue:values){
                    DecimalFormat    df   = new DecimalFormat("######0");
                    propertyValue.setVipPrice(df.format(propertyValue.getPrice()));
                }
                product1.setPropertyValues(values);
            }
            return products;
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(39002);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(1);
            return returnJson ;
        }
    }





    public static void main(String[]args){
        System.err.println(MD5.GetMD5Code("ta1234at"));
    }

}
