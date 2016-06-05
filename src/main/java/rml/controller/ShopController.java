package rml.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.*;
import rml.service.LocationService;
import rml.service.OrderService;
import rml.service.ShopService;
import rml.service.ValidCodeService;
import rml.util.MD5;
import rml.util.ReturnJson;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by edward-echo on 2016/3/28.
 */
@Controller
@RequestMapping("/Shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ValidCodeService validCodeService;

    @Autowired
    private LocationService locationService;

    @RequestMapping(value="/Shop",method = RequestMethod.POST)
    @ResponseBody
    public Object addUser(@RequestBody Shop shop) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(1000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(shop.getPhone())||StringUtils.isEmpty(shop.getValidCode())||StringUtils.isEmpty(shop.getPassword())||StringUtils.isEmpty(shop.getMobile())||StringUtils.isEmpty(shop.getIdCardHand())||StringUtils.isEmpty(shop.getIdCardFront())||StringUtils.isEmpty(shop.getIdCardBack())||StringUtils.isEmpty(shop.getCityId())||StringUtils.isEmpty(shop.getBizLicensePath())||StringUtils.isEmpty(shop.getAreaId())||StringUtils.isEmpty(shop.getName())||StringUtils.isEmpty(shop.getAddress())||StringUtils.isEmpty(shop.getValidCode())){

            returnJson.setErrorCode(1001);
            returnJson.setReturnMessage("传入参数为空" + shop.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(shop.getRandomKey()+"at^&*ta");
        if(!result1.equals(shop.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + shop.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        ValidCode code = new ValidCode();
        code.setValidCode(shop.getValidCode());
        code.setMobile(shop.getMobile());


        int flag = validCodeService.checkValidCode(code);
        if(flag==1){
            returnJson.setErrorCode(1002);
            returnJson.setReturnMessage("验证码错误，请重新输入" + code.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        Shop shop1 = shopService.getByMobile(shop.getMobile());
        if(shop1!=null){
            returnJson.setErrorCode(1003);
            returnJson.setReturnMessage("该手机号已存在，请勿重复注册用户" + code.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            City city = locationService.getCity(shop.getCityId());
            Province province = locationService.getProvince(shop.getProvinceId());
            Area area = locationService.getArea(shop.getAreaId());

            shop.setAddress(city.getCityName()+" "+province.getProvinceName()+" "+area.getAreaName()+" "+shop.getAddress());
            shop.setCreateTime(new Date());
            shop.setUuid(UUID.randomUUID().toString());
            shop.setStatus(1);
            shop.setPassword(MD5.GetMD5Code(shop.getPassword()));
            shopService.insert(shop);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(1004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(shop);
        return returnJson;
    }

    @Autowired
    private OrderService orderService;

    @RequestMapping(value="/Shop",method = RequestMethod.GET)
    @ResponseBody
    public Object shopLogin(Shop shop) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(1000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(shop.getMobile())||StringUtils.isEmpty(shop.getPassword())){

            returnJson.setErrorCode(1001);
            returnJson.setReturnMessage("传入参数为空" + shop.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(shop.getRandomKey()+"at^&*ta");
        if(!result1.equals(shop.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + shop.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            shop.setPassword(MD5.GetMD5Code(shop.getPassword()));
            Shop shop1 = shopService.getShop(shop);
            if(shop1==null){
                returnJson.setErrorCode(1002);
                returnJson.setReturnMessage("商户不存在或密码错误");
                returnJson.setServerStatus(1);
                return returnJson;
            }
            if(shop1.getStatus()==1){
                returnJson.setErrorCode(1003);
                returnJson.setReturnMessage("商户审核中,请等待");
                returnJson.setServerStatus(1);
                return returnJson;
            }
            if(shop1.getStatus()==3){
                returnJson.setErrorCode(1004);
                returnJson.setReturnMessage("商户审核失败");
                returnJson.setServerStatus(1);
                return returnJson;
            }
            Date today = new Date();
            Order order = new Order();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(new Date());
            dateString = dateString+" 00:00:00";
            order.setStartDate(dateString);
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
            dateString = formatter1.format(new Date());
            order.setEndDate(dateString);
            order.setShopId(shop1.getUuid());
            int orderDaily = 0;
            try {
                orderDaily = orderService.getShopReport(order);
            }catch (Exception e){

            }
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            int YEAR = calendar.getMinimum(Calendar.YEAR);// 取得当前月的最小日期(天)
            int day = calendar.getMinimum(Calendar.DAY_OF_WEEK);// 取得当前月的最小日期(天)
            calendar.set(Calendar.YEAR,YEAR);
            calendar.set(Calendar.DAY_OF_WEEK, day);
            calendar.set(Calendar.HOUR,0);
            calendar.set(Calendar.AM_PM,0);
            calendar.set(Calendar.MINUTE,0);
            Date date = calendar.getTime();
            int orderTotal = 0;
            order.setStartDate(date.toLocaleString());
            try {
                orderTotal = orderService.getShopReport(order);
            }catch (Exception e){

            }
            shop1.setMoneyDaily(orderDaily);
            shop1.setMoneyTotal(orderTotal);
            returnJson.setReturnObject(shop1);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(1005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }

    @RequestMapping(value="/Shop/ChangePwd",method = RequestMethod.POST)
    @ResponseBody
    public Object shopPwd(@RequestBody Shop shop) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(1000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(shop.getMobile())||StringUtils.isEmpty(shop.getPassword())||StringUtils.isEmpty(shop.getValidCode())){

            returnJson.setErrorCode(1001);
            returnJson.setReturnMessage("传入参数为空" + shop.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(shop.getRandomKey()+"at^&*ta");
        if(!result1.equals(shop.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + shop.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            ValidCode code = new ValidCode();
            code.setValidCode(shop.getValidCode());
            code.setMobile(shop.getMobile());

            int flag = validCodeService.checkValidCode(code);
            if(flag==1){
                returnJson.setErrorCode(1002);
                returnJson.setReturnMessage("验证码错误，请重新输入" + code.toString());
                returnJson.setServerStatus(1);
                return returnJson;
            }
            shop.setPassword(MD5.GetMD5Code(shop.getPassword()));
            shopService.updatePassword(shop);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(1004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }

    @RequestMapping(value="/Shops",method = RequestMethod.GET)
    @ResponseBody
    public Object getUsers(Shop shop) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<List<String>> temps = new ArrayList<List<String>>();
        List<Shop> results = null;
        try {
            results = shopService.getShops(shop);
            for(Shop user:results) {
                if(user.getStatus()==1){
                    user.setStatusShow("审核中");
                }

                if(user.getStatus()==2){
                    user.setStatusShow("审核通过");
                }

                if(user.getStatus()==3){
                    user.setStatusShow("审核失败");
                }
                if(user.getType()==1){
                    user.setTypeShow("普通线下商家");
                }else  if(user.getType()==2){
                    user.setTypeShow("vip旗舰店");
                }else if(user.getType()==0){
                    user.setTypeShow("未知");
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(20303);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(results);
        return returnJson;
    }

    @RequestMapping(value="/Shops/Count",method = RequestMethod.GET)
    @ResponseBody
    public Object getUserss() {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(20001);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<Shop> results = null;
        try {
            return shopService.getShopTotal();
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(20313);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
    }

    @RequestMapping(value="/Shop/Status",method = RequestMethod.POST)
    @ResponseBody
    public Object modifyStatus(@RequestBody Shop shop) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(21001);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<Shop> results = null;

        try {
           shopService.updateStatus(shop);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(21003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }


    @RequestMapping(value="/Shop/Type",method = RequestMethod.POST)
    @ResponseBody
    public Object modifyType(@RequestBody Shop shop) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(22001);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<Shop> results = null;
        try {
            shopService.updateType(shop);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(22003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }
    public static  void main(String[]args){
        String originPath = "http://file.weiqu168.com/group1/M00/00/34/i8QzQVayAReAWMViAABkMhSNT9U19..jpg";
        String destPath = "http://tp.tata168.com";
        int index = originPath.indexOf("/group1");
        destPath = destPath + originPath.substring(index,originPath.length());
        System.err.print(destPath);

    }
}
