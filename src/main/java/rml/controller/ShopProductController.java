package rml.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.*;
import rml.service.ShopProductService;
import rml.util.MD5;
import rml.util.ReturnJson;

import java.util.Date;
import java.util.UUID;

/**
 * Created by edward-echo on 2016/4/19.
 */

@Controller
@RequestMapping("/Shop")
public class ShopProductController {

    @Autowired
    private ShopProductService shopProductService;


    @RequestMapping(value="/Product",method = RequestMethod.POST)
    @ResponseBody
    public Object getGoods(@RequestBody  ShopProduct shopProduct) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(61000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(shopProduct.getName())||StringUtils.isEmpty(shopProduct.getIcon())||StringUtils.isEmpty(shopProduct.getDesc())||StringUtils.isEmpty(shopProduct.getShopId())){
            returnJson.setErrorCode(61001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(shopProduct.getRandomKey()+"at^&*ta");
        if(!result1.equals(shopProduct.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + shopProduct.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            shopProduct.setUuid(UUID.randomUUID().toString());
            shopProduct.setCreateTime(new Date());
            shopProductService.insert(shopProduct);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(61004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }

    @RequestMapping(value="/Products",method = RequestMethod.GET)
    @ResponseBody
    public Object getGoods11(ShopProduct shopProduct) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(61000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(shopProduct.getShopId())){
            returnJson.setErrorCode(61001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(shopProduct.getRandomKey()+"at^&*ta");
        if(!result1.equals(shopProduct.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + shopProduct.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try{
            returnJson.setReturnObject(shopProductService.getShopProducts(shopProduct.getShopId()));
        }catch (Exception e){
            e.printStackTrace();
            returnJson.setErrorCode(61002);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        return returnJson;
    }

    @RequestMapping(value="/Product",method = RequestMethod.PUT)
    @ResponseBody
    public Object modifyProduct(@RequestBody ShopProduct shopProduct) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(62000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(shopProduct.getUuid())||StringUtils.isEmpty(shopProduct.getName())||StringUtils.isEmpty(shopProduct.getIcon())||StringUtils.isEmpty(shopProduct.getDesc())){
            returnJson.setErrorCode(62001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(shopProduct.getRandomKey()+"at^&*ta");
        if(!result1.equals(shopProduct.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + shopProduct.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try{
            shopProductService.updateProduct(shopProduct);
        }catch (Exception e){
            e.printStackTrace();
            returnJson.setErrorCode(62002);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        return returnJson;
    }

    @RequestMapping(value="/Product/Delete",method = RequestMethod.POST)
    @ResponseBody
    public Object deleteProduct(@RequestBody ShopProduct shopProduct) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(63000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(shopProduct.getUuid())){
            returnJson.setErrorCode(63001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(shopProduct.getRandomKey()+"at^&*ta");
        if(!result1.equals(shopProduct.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + shopProduct.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try{
            shopProductService.deleteProduct(shopProduct.getUuid());
        }catch (Exception e){
            e.printStackTrace();
            returnJson.setErrorCode(63002);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        return returnJson;
    }
}
