package rml.controller;

import com.alibaba.druid.filter.AutoLoad;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.*;
import rml.service.CargoService;
import rml.service.GoodService;
import rml.service.ProductService;
import rml.service.PropertyValueService;
import rml.util.MD5;
import rml.util.ReturnJson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by edward on 2016/1/5 0005.
 */
@Controller
@RequestMapping("/Cargo")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @Autowired
    private PropertyValueService propertyValueService;

    @RequestMapping(value="/Cargo",method = RequestMethod.POST)
    @ResponseBody
    public Object addUser(@RequestBody Cargo cargo) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(35000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(cargo.getUserId())||StringUtils.isEmpty(cargo.getPropertyId())||cargo.getGoodCount()==0){
            returnJson.setErrorCode(35001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result = MD5.GetMD5Code(cargo.getRandomKey()+"at^&*ta");
        if(!result.equals(cargo.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + cargo.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            PropertyValue propertyValue = propertyValueService.getPropertyValue(cargo.getPropertyId());
            if(propertyValue==null){
                returnJson.setErrorCode(35002);
                returnJson.setReturnMessage("propertyValue 不存在");
                returnJson.setServerStatus(1);
                return returnJson;
            }
            Cargo cargo1 = cargoService.getGood(cargo);
            if(cargo1!=null){
                cargo1.setGoodCount(cargo.getGoodCount()+cargo1.getGoodCount());
                int price = cargo1.getActureMoney();
                price = price+ propertyValue.getPrice()*cargo.getGoodCount();
                cargo1.setActureMoney(price);
                cargo1.setTotalMoney(price);
                cargoService.updateCount(cargo1);
                cargo = cargo1;
            }else{
                int price = propertyValue.getPrice()*cargo.getGoodCount();
                cargo.setActureMoney(price);
                cargo.setTotalMoney(price);
                cargo.setUuid(UUID.randomUUID().toString());
                cargo.setCreateTime(new Date());
                cargoService.insertGood(cargo);
            }

        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(35004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(cargo);
        return returnJson;
    }

    @Autowired
    ProductService productService;

    @RequestMapping(value="/Cargo",method = RequestMethod.GET)
    @ResponseBody
    public Object getGoods(Cargo cargo) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(36000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(cargo.getUserId())){
            returnJson.setErrorCode(36001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result = MD5.GetMD5Code(cargo.getRandomKey()+"at^&*ta");
        if(!result.equals(cargo.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + cargo.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            int totalPrice = 0;
            List<Cargo> list = cargoService.getUserGood(cargo.getUserId());
            for(Cargo cargo1:list){
                PropertyValue propertyValue = propertyValueService.getPropertyValue(cargo1.getPropertyId());
                if(propertyValue==null){
                    continue;
                }
                propertyValue.setPrice(propertyValue.getPrice());
                Product product = productService.getProduct(propertyValue.getProductId());
                cargo1.setPropertyValue(propertyValue);
                cargo1.setProductName(product.getName());
                String img = product.getImgsMain().split(",")[0];
                cargo1.setProductPic(img);
                totalPrice = totalPrice+cargo1.getActureMoney();
            }
            CargoContainer cargoContainer = new CargoContainer();
            cargoContainer.setTotalMoney(totalPrice);
            cargoContainer.setCargos(list);
            returnJson.setReturnObject(cargoContainer);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(36004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }

    @RequestMapping(value="/Cargo",method = RequestMethod.PUT)
    @ResponseBody
    public Object deleteGoods(@RequestBody Cargo cargo) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(37000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(cargo.getPropertyId())||StringUtils.isEmpty(cargo.getUserId())||cargo.getGoodCount()==0){
            returnJson.setErrorCode(37001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result = MD5.GetMD5Code(cargo.getRandomKey()+"at^&*ta");
        if(!result.equals(cargo.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + cargo.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            Cargo cargo1 = cargoService.getGood(cargo);
            if(cargo1.getGoodCount()==cargo.getGoodCount()){
                cargoService.delete(cargo.getPropertyId());
            }
            if(cargo1.getGoodCount()<cargo.getGoodCount()){
                returnJson.setErrorCode(37005);
                returnJson.setReturnMessage("删除商品数量不允许大于现有商品数量");
                returnJson.setServerStatus(1);
                return returnJson;
            }
            PropertyValue propertyValue = propertyValueService.getPropertyValue(cargo.getPropertyId());
            int money = propertyValue.getPrice()*cargo.getGoodCount();
            cargo.setActureMoney(money);
            cargo.setTotalMoney(money);
            cargoService.updateCountMinus(cargo);
            returnJson.setReturnObject(cargo);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(37004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }
    @RequestMapping(value="/Delete",method = RequestMethod.GET)
    @ResponseBody
    public Object deleteCargo(Cargo cargo) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(38000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(cargo.getPropertyId())){
            returnJson.setErrorCode(38001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result = MD5.GetMD5Code(cargo.getRandomKey()+"at^&*ta");
        if(!result.equals(cargo.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + cargo.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            cargoService.delete(cargo.getPropertyId());
            returnJson.setReturnObject(cargo);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(38004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }


    @RequestMapping(value="/Cargo/Merge",method = RequestMethod.GET)
    @ResponseBody
    public Object a11ddUser(Cargo cargo) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(35000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(cargo.getUserId())||StringUtils.isEmpty(cargo.getPropertyId())){
            returnJson.setErrorCode(35001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result = MD5.GetMD5Code(cargo.getRandomKey()+"at^&*ta");
        if(!result.equals(cargo.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + cargo.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        List<Cargo> results = new ArrayList<Cargo>();
        try {
            String[] propertyIds = cargo.getPropertyId().split(",");
            String[] goodCounts = cargo.getGoodCountMerge().split(",");
            System.err.print(propertyIds.length);
            for(int i=0;i<propertyIds.length;i++) {
                PropertyValue propertyValue = propertyValueService.getPropertyValue(propertyIds[i]);
                if (propertyValue == null) {
                    returnJson.setErrorCode(35002);
                    returnJson.setReturnMessage("propertyValue 不存在");
                    returnJson.setServerStatus(1);
                    return returnJson;
                }
                String propertyId = propertyIds[i];
                cargo.setPropertyId(propertyId);
                Cargo cargo1 = cargoService.getGood(cargo);
                if (cargo1 != null) {
                    cargo1.setGoodCount(new Integer(goodCounts[i]) + cargo1.getGoodCount());
                    int price = cargo1.getActureMoney();
                    price = price + propertyValue.getPrice()  * new Integer(goodCounts[i]);
                    cargo1.setActureMoney(price);
                    cargo1.setTotalMoney(price);
                    cargoService.updateCount(cargo1);
                    cargo = cargo1;
                } else {
                    int price = propertyValue.getPrice()  * new Integer(goodCounts[i]);
                    cargo.setActureMoney(price);
                     cargo.setTotalMoney(price);
                    cargo.setGoodCount(new Integer(goodCounts[i]));
                    cargo.setUuid(UUID.randomUUID().toString());
                    cargo.setCreateTime(new Date());
                    cargo.setPropertyId(propertyId);
                    cargoService.insertGood(cargo);
                }
                results.add(cargo);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(35004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(results);
        return returnJson;
    }

    @RequestMapping(value="/Cargo/Num",method = RequestMethod.GET)
    @ResponseBody
    public Object getGoodsNum(Cargo cargo) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(37000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(cargo.getUserId())){
            returnJson.setErrorCode(37001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result = MD5.GetMD5Code(cargo.getRandomKey()+"at^&*ta");
        if(!result.equals(cargo.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + cargo.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            int size = 0;
            List<Cargo> list = cargoService.getUserGood(cargo.getUserId());
            for(Cargo cargo1:list){
                size = size+cargo1.getGoodCount();
            }
            returnJson.setReturnObject(size);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(37004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }

}
