package rml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.*;
import rml.service.LocationService;
import rml.util.MD5;
import rml.util.ReturnJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/3 0003.
 */

@Controller
@RequestMapping("/Location")
public class LocationController {

    @Autowired
    LocationService locationService;

    @RequestMapping(value="/Provinces",method = RequestMethod.GET)
    @ResponseBody
    public Object getProvinces(User user){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(4000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<Province> result = null;
        String result1 = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
        if(!result1.equals(user.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + user.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            result  = locationService.getProvinces();
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(4002);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(result);
        return returnJson;
    }


    @RequestMapping(value="/All",method = RequestMethod.GET)
    @ResponseBody
    public Object geAll(User user){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(4000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<Province> result = null;
        List<ProvinceCity> values = new ArrayList<ProvinceCity>();
        String result1 = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
        if(!result1.equals(user.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + user.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            result  = locationService.getProvinces();
            for(Province province:result){
                ProvinceCity provinceCity = new ProvinceCity();
                provinceCity.setProvinceId(province.getProvinceId());
                provinceCity.setProvinceName(province.getProvinceName());
                List<City> cities  = locationService.getCities(province.getProvinceId());
                List<CityArea> cityAreas = new ArrayList<CityArea>();
                for(City city:cities){
                    CityArea cityArea = new CityArea();
                    cityArea.setCityId(city.getCityId());
                    cityArea.setCityName(city.getCityName());
                    List<Area> areas  = locationService.getAreas(city.getCityId());
                    cityArea.setAreas(areas);
                    cityAreas.add(cityArea);
                }
                provinceCity.setCityAreaList(cityAreas);
                values.add(provinceCity);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(4002);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        return values;
    }



    @RequestMapping(value="/Cities",method = RequestMethod.GET)
    @ResponseBody
    public Object getCities(Province province){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(5000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<City> result = null;
        String result1 = MD5.GetMD5Code(province.getRandomKey()+"at^&*ta");
        if(!result1.equals(province.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + province.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            result  = locationService.getCities(province.getProvinceId());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(5002);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(result);
        return returnJson;
    }

    @RequestMapping(value="/Areas",method = RequestMethod.GET)
    @ResponseBody
    public Object getAreas(ProvinceCity city){
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(6000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<Area> result = null;
        String result1 = MD5.GetMD5Code(city.getRandomKey()+"at^&*ta");
        if(!result1.equals(city.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + city.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            result  = locationService.getAreas(city.getCityId());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(6002);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnObject(result);
        return returnJson;
    }
}
