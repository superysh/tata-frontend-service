package rml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.Product;
import rml.model.ProductCollection;
import rml.model.Property;
import rml.model.PropertyValue;
import rml.service.ProductCollectionService;
import rml.service.ProductService;
import rml.service.PropertyValueService;
import rml.util.MD5;
import rml.util.ReturnJson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by edward-echo on 2016/4/22.
 */

@Controller
@RequestMapping("/Product")
public class ProductCollectionController{


    @Autowired
    private ProductCollectionService productCollectionService;

    @Autowired
    private PropertyValueService propertyValueService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value="/Collection",method = RequestMethod.POST)
    @ResponseBody
    public Object getUserss(@RequestBody  ProductCollection productCollection) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(21000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(productCollection.getProductId())||StringUtils.isEmpty(productCollection.getUserId())){
            returnJson.setErrorCode(21002);
            returnJson.setReturnMessage("参数为空或者参数不正确");
            returnJson.setServerStatus(0);
            return  returnJson;
        }
        String result1 = MD5.GetMD5Code(productCollection.getRandomKey()+"at^&*ta");
        if(!result1.equals(productCollection.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + productCollection.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        ProductCollection productCollection1 = productCollectionService.getRepeatCollection(productCollection);
        if(productCollection1!=null){
            returnJson.setErrorCode(21003);
            returnJson.setReturnMessage("该商品已经在亲的收藏列表了噢");
            returnJson.setServerStatus(0);
            return  returnJson;
        }
        try {
           productCollection.setUuid(UUID.randomUUID().toString());
           productCollection.setCreateTime(new Date());
           productCollectionService.insert(productCollection);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(21004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(productCollection);
        return returnJson;
    }

    @RequestMapping(value="/Collections",method = RequestMethod.GET)
    @ResponseBody
    public Object getUsers11(ProductCollection productCollection) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(22000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(productCollection.getUserId())||productCollection.getPageNo()==0||productCollection.getPageSize()==0){
            returnJson.setErrorCode(22002);
            returnJson.setReturnMessage("参数为空或者参数不正确");
            returnJson.setServerStatus(0);
            return  returnJson;
        }
        String result1 = MD5.GetMD5Code(productCollection.getRandomKey()+"at^&*ta");
        if(!result1.equals(productCollection.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + productCollection.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            List<ProductCollection> collections = productCollectionService.getUserCollection(productCollection);
            List<ProductCollection> collectionsTotal = productCollectionService.getUserCollectionTotal(productCollection);
            List<Product> products = new ArrayList<Product>();
            returnJson.setReturnValue(new Integer(collectionsTotal.size()).toString());
            for(ProductCollection collection:collections){
                try {
                    String productId = collection.getProductId();
                    Product product = productService.getProduct(productId);
                    List<PropertyValue> values = propertyValueService.getProductProperty(productId);
                    product.setPropertyValues(values);
                    String [] tmp = product.getImgsMain().split(",");
                    product.setImgsMain(tmp[0]);
                    product.setImgs(null);
                    products.add(product);
                }catch (Exception e){
                    e.printStackTrace();
                    continue;
                }
            }
            returnJson.setReturnObject(products);
            returnJson.setReturnValue(new Integer(collectionsTotal.size()).toString());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(22003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }

    @RequestMapping(value="/Collection/Delete",method = RequestMethod.POST)
    @ResponseBody
    public Object getUsers66(@RequestBody  ProductCollection productCollection) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(23000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(productCollection.getUserId())||StringUtils.isEmpty(productCollection.getProductId())){
            returnJson.setErrorCode(23002);
            returnJson.setReturnMessage("参数为空或者参数不正确");
            returnJson.setServerStatus(0);
            return  returnJson;
        }
        String result1 = MD5.GetMD5Code(productCollection.getRandomKey()+"at^&*ta");
        if(!result1.equals(productCollection.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + productCollection.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            String tmp = productCollection.getProductId();
            if(tmp.indexOf(",")!=-1){
                String []ids = tmp.split(",");
                for(String id:ids){
                    productCollection.setProductId(id);
                    productCollectionService.delete(productCollection);
                }
            }else {
                productCollectionService.delete(productCollection);
            }

        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(23003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }
}
