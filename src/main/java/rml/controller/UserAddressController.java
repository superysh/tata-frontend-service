package rml.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.UserAddress;
import rml.service.LocationService;
import rml.service.UserAddressService;
import rml.util.MD5;
import rml.util.ReturnJson;
import sun.swing.StringUIClientPropertyKey;

import java.util.List;
import java.util.UUID;

/**
 * Created by edward-echo on 2015/12/22.
 */
@Controller
@RequestMapping("/User")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private LocationService locationService;

    @RequestMapping(value="/Address",method = RequestMethod.POST)
    @ResponseBody
    public Object addUser(@RequestBody UserAddress userAddress) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(8000);
        returnJson.setReturnMessage("调用成功"+userAddress.toString());
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(userAddress.getMobile())||StringUtils.isEmpty(userAddress.getName())||StringUtils.isEmpty(userAddress.getUserId())||StringUtils.isEmpty(userAddress.getProvinceId())||StringUtils.isEmpty(userAddress.getCityId())||StringUtils.isEmpty(userAddress.getAddress())||StringUtils.isEmpty(userAddress.getAreaId())){

            returnJson.setErrorCode(8001);
            returnJson.setReturnMessage("传入参数为空" + userAddress.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(userAddress.getRandomKey()+"at^&*ta");
        if(!result1.equals(userAddress.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + userAddress.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            if(userAddress.getMobile().length()!=11){
                returnJson.setErrorCode(8005);
                returnJson.setReturnMessage("手机格式错误" + userAddress.toString());
                returnJson.setServerStatus(1);
                return returnJson;
            }
            userAddress.setStatus(1);
            userAddress.setIsDefault(0);
            String province = locationService.getProvince(userAddress.getProvinceId()).getProvinceName();
            String city = locationService.getCity(userAddress.getCityId()).getCityName();
            String area = locationService.getArea(userAddress.getAreaId()).getAreaName();
            userAddress.setCity(city);
            userAddress.setProvince(province);
            userAddress.setArea(area);
            userAddress.setName(userAddress.getName());
            userAddress.setAddress(province+city+area+userAddress.getAddress());
            userAddressService.insert(userAddress);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(8004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(userAddress);
        returnJson.setReturnValue(userAddress.getUuid());
        return returnJson;
    }


    @RequestMapping(value="/Address",method = RequestMethod.PUT)
    @ResponseBody
    public Object setDefaultAddress(@RequestBody UserAddress userAddress) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(10000);
        returnJson.setReturnMessage("调用成功"+userAddress.toString());
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(userAddress.getUserId())|| StringUtils.isEmpty(userAddress.getUuid())){
            returnJson.setErrorCode(10001);
            returnJson.setReturnMessage("传入参数为空" + userAddress.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(userAddress.getRandomKey()+"at^&*ta");
        if(!result1.equals(userAddress.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + userAddress.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            userAddressService.setDefalutZero(userAddress.getUserId());
            userAddressService.setDefalutUnique(userAddress.getUuid());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(10003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(userAddress);
        returnJson.setReturnValue(userAddress.getUuid());
        return returnJson;
    }


    @RequestMapping(value="/Address/Delete",method = RequestMethod.GET)
    @ResponseBody
    public Object delete(UserAddress userAddressMain) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(10000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(userAddressMain.getUuid())){
            returnJson.setErrorCode(10001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(userAddressMain.getRandomKey()+"at^&*ta");
        if(!result1.equals(userAddressMain.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + userAddressMain.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
           userAddressService.deleteAddress(userAddressMain.getUuid());

        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(10002);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }



    @RequestMapping(value="/Addresses",method = RequestMethod.GET)
    @ResponseBody
    public Object getAddresses(UserAddress userAddress) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(11000);
        returnJson.setReturnMessage("调用成功"+userAddress.toString());
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(userAddress.getUserId())){
            returnJson.setErrorCode(11001);
            returnJson.setReturnMessage("传入参数为空" + userAddress.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(userAddress.getRandomKey()+"at^&*ta");
        if(!result1.equals(userAddress.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + userAddress.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        List<UserAddress> results = null;
        try {
            results =userAddressService.getUserAddress(userAddress.getUserId());

        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(10002);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(results);
        return returnJson;
    }


    @RequestMapping(value="/Address/Modify",method = RequestMethod.POST)
    @ResponseBody
    public Object modify(@RequestBody UserAddress userAddress) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(8000);
        returnJson.setReturnMessage("调用成功"+userAddress.toString());
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(userAddress.getMobile())||StringUtils.isEmpty(userAddress.getName())||StringUtils.isEmpty(userAddress.getUuid())||StringUtils.isEmpty(userAddress.getProvinceId())||StringUtils.isEmpty(userAddress.getCityId())||StringUtils.isEmpty(userAddress.getAddress())||StringUtils.isEmpty(userAddress.getAreaId())){

            returnJson.setErrorCode(8001);
            returnJson.setReturnMessage("传入参数为空" + userAddress.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(userAddress.getRandomKey()+"at^&*ta");
        if(!result1.equals(userAddress.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + userAddress.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            UserAddress userAddress1 = userAddressService.getAddressById(userAddress.getUuid());
            String province = locationService.getProvince(userAddress.getProvinceId()).getProvinceName();
            String city = locationService.getCity(userAddress.getCityId()).getCityName();
            String area = locationService.getArea(userAddress.getAreaId()).getAreaName();
            userAddress1.setCity(city);
            userAddress1.setProvince(province);
            userAddress1.setArea(area);
            userAddress1.setAddress(province+city+area+userAddress.getAddress());
            userAddress1.setMobile(userAddress.getMobile());
            userAddress1.setName(userAddress.getName());
            userAddress1.setProvinceId(userAddress.getProvinceId());
            userAddress1.setCityId(userAddress.getCityId());
            userAddress1.setAreaId(userAddress.getAreaId());
            userAddressService.update(userAddress1);
            returnJson.setReturnObject(userAddress1);
            returnJson.setReturnValue(userAddress1.getUuid());

        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(8004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }
}
