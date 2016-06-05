package rml.controller;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.AutoPopulatingList;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.*;
import rml.service.*;
import rml.util.DesUtil;
import rml.util.MD5;
import rml.util.ReturnJson;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by edward-echo on 2015/12/20.
 */

@Controller
@RequestMapping("/Order")
public class OrderController {

    @Autowired
    private CargoService cargoService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private GoodService goodService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private FundingService fundingService;

    @Autowired
    private FundingCommentService fundingCommentService;

    @Autowired
    private UserAddressService userAddressService;


    @RequestMapping(value="/Order",method = RequestMethod.POST)
    @ResponseBody
    public Object addUser(@RequestBody Activity activity) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(9000);
        returnJson.setReturnMessage("调用成功"+activity.toString());
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(activity.getUuid())||StringUtils.isEmpty(activity.getUserId())){

            returnJson.setErrorCode(9001);
            returnJson.setReturnMessage("传入参数为空" + activity.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            activity.setEndTime(new Date());
            Activity activity1 = activityService.getActivityByUUID(activity.getUuid());

            if (activity1==null){
                returnJson.setErrorCode(9002);
                returnJson.setReturnMessage("活动不存在或者活动已经过期"+activity.toString());
                returnJson.setServerStatus(1);
                return returnJson;
            }

            User user = userService.selectByPrimaryKey(activity.getUserId());
            if (user==null){
                returnJson.setErrorCode(9003);
                returnJson.setReturnMessage("userId不存在"+activity.toString());
                returnJson.setServerStatus(1);
                return returnJson;
            }
           if(orderService.getUserOrder(user.getUuid())!=null){
               returnJson.setErrorCode(9004);
               returnJson.setReturnMessage("您已经参与了众筹活动，请勿重复参与"+activity.toString());
               returnJson.setServerStatus(1);
               return returnJson;
           }
           String goodIds = activity1.getGoodIds();
           Order order = new Order();
            order.setUuid(UUID.randomUUID().toString());
            order.setTotalMoney(activity.getGoodsTotalMoney());
            order.setCreateTime(new Date());
            order.setCargoIds(goodIds.replaceAll("'", ""));
            order.setLimitedDays(activity1.getLimitedTime());
            order.setUserId(activity.getUserId());
            order.setType(2);
            order.setStatus(3);
            order.setActureMoney(0);
            order.setActivityId(activity.getUuid());
            order.setlDesc(activity1.getDesc());
            orderService.insert(order);
            Funding funding = new Funding();
            funding.setName(user.getNickName()+"的众筹");
            funding.setActivityId(activity1.getUuid());
            funding.setStartDate(new Date());
            funding.setLimitedDays(activity1.getLimitedTime());
            funding.setlDesc(activity1.getDesc());
            funding.setRaisedMoney(0);
            funding.setTotalMoney(activity1.getGoodsTotalMoney());
            funding.setOrderId(order.getUuid());
            funding.setUserId(user.getUuid());
            funding.setStatus(1);
            funding.setUuid(UUID.randomUUID().toString());
            fundingService.insert(funding);
            returnJson.setReturnObject(order);
            returnJson.setReturnValue(order.getUuid());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(9005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }

        return returnJson;
    }


    @RequestMapping(value="/Order/Status",method = RequestMethod.POST)
    @ResponseBody
    public Object addU21ser(@RequestBody Order order) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(9000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);

        if(order.getStatus()==0||order.getUuid()==null){
            returnJson.setErrorCode(9001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(order.getRandomKey()+"at^&*ta");
        if(!result1.equals(order.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            orderService.updateOrderStatus(order);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(9005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }


    @RequestMapping(value="/Order/Return",method = RequestMethod.POST)
    @ResponseBody
    public Object subStatus(@RequestBody Order order) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(11000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);

        if(order.getUuid()==null){
            returnJson.setErrorCode(11001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(order.getRandomKey()+"at^&*ta");
        if(!result1.equals(order.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            order.setSubStatus(1);
            orderService.updateSubStatus(order);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(11005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }



    @RequestMapping(value="/Order/User",method = RequestMethod.POST)
    @ResponseBody
    public Object addU111ser(@RequestBody Order order) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(9000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);


        if(order.getCargoIds()==null&&order.getUserId()==null){
            returnJson.setErrorCode(9001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(order.getRandomKey()+"at^&*ta");
        if(!result1.equals(order.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            User user = userService.selectByPrimaryKey(order.getUserId());
            if (user==null){
                returnJson.setErrorCode(9003);
                returnJson.setReturnMessage("userId不存在"+order.toString());
                returnJson.setServerStatus(1);
                return returnJson;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(9005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }


    @RequestMapping(value="/Orders/Status",method = RequestMethod.GET)
    @ResponseBody
    public Object addU15511ser(Order order) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(9000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);

        if(order.getUserId()==null||order.getStatus()==0){
            returnJson.setErrorCode(9001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(order.getRandomKey()+"at^&*ta");
        if(!result1.equals(order.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            logger.info("userId传的值为:"+order.getUserId());
            User user = userService.selectByPrimaryKey(order.getUserId());
            if (user==null){
                returnJson.setErrorCode(9003);
                returnJson.setReturnMessage("userId不存在"+order.toString());
                returnJson.setServerStatus(1);
                return returnJson;
            }
        List<Order> orders = orderService.getUserOrderStatus(order);
        List<Order> list = new ArrayList<Order>();
        for(Order order1:orders){
            int totalPrice = 0;
            List<Cargo> cargos = new ArrayList<Cargo>();
            String cargoIds = order1.getCargoIds();
            String[] value = cargoIds.split(",");
            for(String cargoId:value){
                Cargo cargo = cargoService.getCargo(cargoId);
                PropertyValue propertyValue = propertyValueService.getPropertyValue(cargo.getPropertyId());
                int price = propertyValue.getPrice();
                propertyValue.setPrice(propertyValue.getPrice());
                Product product = productService.getProduct(propertyValue.getProductId());
                cargo.setProduct(product);
                cargo.setPropertyValue(propertyValue);
                product.setImgsMain(product.getImgsMain().split(",")[0]);
                product.setImgs(null);
                totalPrice = totalPrice + price * cargo.getGoodCount();
                cargos.add(cargo);
            }
            order1.setCargos(cargos);
            order1.setCargoIds(order.getCargoIds());
            order1.setTotalMoney(totalPrice);
            order1.setActureMoney(totalPrice);
            order1.setCreateTime(new Date());
            order1.setDeliveryFee(0);
            list.add(order1);
            }
            returnJson.setReturnObject(list);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(9005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }

        return returnJson;
    }

    @RequestMapping(value="/Show",method = RequestMethod.GET)
    public Object User(Order order) throws Exception{

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(39000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);

        int totalPrice = 0;
        if(StringUtils.isEmpty(order.getUserId())||StringUtils.isEmpty(order.getCargoIds())){

            returnJson.setErrorCode(39001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(order.getRandomKey()+"at^&*ta");
        if(!result1.equals(order.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            User user = userService.selectByPrimaryKey(order.getUserId());
            if(user==null){
                returnJson.setErrorCode(39002);
                returnJson.setReturnMessage("userId不存在");
                returnJson.setServerStatus(1);
                return returnJson;
            }
            UserAddress defaultAddress = userAddressService.getDefaultAddress(user.getUuid());
            if(defaultAddress!=null){
                user.setUserAddress(defaultAddress);
            }else {
                List<UserAddress> userAddress = userAddressService.getUserAddress(user.getUuid());

                if (userAddress.size() > 0) {
                    user.setUserAddress(userAddress.get(0));
                }
            }
            String[] cargos = order.getCargoIds().split(",");
            List<Good> goods = new ArrayList<Good>();
            List<Cargo> cargos1 = new ArrayList<Cargo>();
            for(String cargo:cargos){
                Cargo cargo1 = cargoService.getCargo(cargo);
                if(cargo1==null){
                    returnJson.setErrorCode(39003);
                    returnJson.setReturnMessage("cargoId不存在");
                    returnJson.setServerStatus(1);
                    return returnJson;
                }
                String goodId = "";
                Good good = goodService.getGoodByUUID(goodId);
                good.setCount(cargo1.getGoodCount());
                totalPrice = totalPrice+good.getPrice()*cargo1.getGoodCount();
                goods.add(good);
                cargo1.setGood(good);
                cargos1.add(cargo1);
            }
            Order order1 = new Order();
            order1.setUser(user);
            order1.setCargos(cargos1);
            order1.setTotalMoney(totalPrice);
            returnJson.setReturnObject(order1);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(39004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        Object jsonObject = JSONObject.toJSON(returnJson);
        return DesUtil.encrypt(jsonObject.toString(),"123456");
    }


    @Autowired
    PropertyValueService propertyValueService;


    @Autowired
    ProductService productService;

    @RequestMapping(value="/Good",method = RequestMethod.GET)
    @ResponseBody
    public Object UserPost(Order order)throws Exception {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(40000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);

        int totalPrice = 0;
        if (StringUtils.isEmpty(order.getUserId()) || StringUtils.isEmpty(order.getCargoIds())) {

            returnJson.setErrorCode(40001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(order.getRandomKey()+"at^&*ta");
        if(!result1.equals(order.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            User user = userService.selectByPrimaryKey(order.getUserId());
            if (user == null) {
                returnJson.setErrorCode(40002);
                returnJson.setReturnMessage("userId不存在");
                returnJson.setServerStatus(1);
                return returnJson;
            }
            Order order1 = new Order();
            UserAddress userAddress = userAddressService.getDefaultAddress(order.getUserId());
            order1.setUserAddress(userAddress);
            if(userAddress!=null) {
                order1.setUserAddressId(userAddress.getUuid());
            }else {
                List<UserAddress> addresses = userAddressService.getUserAddress(order.getUserId());
                if (addresses.size() > 0) {
                    order1.setUserAddress(addresses.get(0));
                    order1.setUserAddressId(addresses.get(0).getUuid());
                }
            }
            order1.setUserId(order.getUserId());
            String[] cargos = order.getCargoIds().split(",");
            List<Cargo> cargos1 = new ArrayList<Cargo>();
            for (String cargo : cargos) {
                Cargo cargo1 = cargoService.getCargo(cargo);
                if (cargo1 == null) {
                    returnJson.setErrorCode(40004);
                    returnJson.setReturnMessage("cargoId不存在");
                    returnJson.setServerStatus(1);
                    return returnJson;
                }
                PropertyValue propertyValue = propertyValueService.getPropertyValue(cargo1.getPropertyId());
                int price = propertyValue.getPrice();
                propertyValue.setPrice(propertyValue.getPrice());
                Product product = productService.getProduct(propertyValue.getProductId());
                cargo1.setProduct(product);
                cargo1.setPropertyValue(propertyValue);
                product.setImgsMain(product.getImgsMain().split(",")[0]);
                product.setImgs(null);
                totalPrice = totalPrice + price * cargo1.getGoodCount();
                cargos1.add(cargo1);
            }
            int deliveryFee = 0;
            if(totalPrice<15800){
                deliveryFee = 1200;
                order1.setDeliveryFee(1200);
            }else if(totalPrice>=15800){
                order1.setDeliveryFee(0);
            }
            order1.setCargos(cargos1);
            order1.setCargoIds(order.getCargoIds());
            order1.setTotalMoney(totalPrice+deliveryFee);
            order1.setActureMoney(totalPrice+deliveryFee);
            order1.setCreateTime(new Date());
            order1.setUuid(createOrderNo(formatDate()));
            order1.setStatus(2);
            order1.setType(1);
            orderService.insert(order1);
            totalPrice = totalPrice+deliveryFee;
            if(user.getOnlineMoney()>=totalPrice){
                order1.setIsMoneyPay(true);
            }
            order1.setUserMoneyTotal(user.getOnlineMoney());
            returnJson.setReturnObject(order1);
        } catch (Exception ex) {
            ex.printStackTrace();
            returnJson.setErrorCode(40005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }


    @RequestMapping(value="/Order/Detail",method = RequestMethod.GET)
    @ResponseBody
    public Object UserPost1(Order order)throws Exception {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(40000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);

        int totalPrice = 0;
        if (StringUtils.isEmpty(order.getUuid())) {

            returnJson.setErrorCode(40001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(order.getRandomKey()+"at^&*ta");
        if(!result1.equals(order.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            Order order1 = orderService.getOrder(order.getUuid());
            List<Product> products = new ArrayList<Product>();
            UserAddress userAddress = userAddressService.getAddressById(order1.getUserAddressId());
            order1.setUserAddress(userAddress);
            order1.setUserId(order1.getUserId());
            String[] cargos = order1.getCargoIds().split(",");
            List<Cargo> cargoList = new ArrayList<Cargo>();
            for (String cargo : cargos) {
                Cargo cargo1 = cargoService.getCargo(cargo);
                PropertyValue propertyValue = propertyValueService.getPropertyValue(cargo1.getPropertyId());
                int price = propertyValue.getPrice();
                propertyValue.setPrice(propertyValue.getPrice());
                Product product = productService.getProduct(propertyValue.getProductId());
                cargo1.setProduct(product);
                cargo1.setPropertyValue(propertyValue);
                product.setImgsMain(product.getImgsMain().split(",")[0]);
                product.setImgs(null);
                products.add(product);
                totalPrice = totalPrice + price * cargo1.getGoodCount();
                cargoList.add(cargo1);
            }
            order1.setCargos(cargoList);
            order1.setTotalMoney(totalPrice);
            order1.setActureMoney(totalPrice);
            order1.setCreateTime(new Date());
            UserAddress userAddress1= userAddressService.getDefaultAddress(order1.getUserId());
            order1.setUserAddress(userAddress1);
            if(userAddress1!=null) {
                order1.setUserAddressId(userAddress1.getUuid());
            }else {
                List<UserAddress> addresses = userAddressService.getUserAddress(order1.getUserId());
                if (addresses.size() > 0) {
                    order1.setUserAddress(addresses.get(0));
                    order1.setUserAddressId(addresses.get(0).getUuid());
                }
            }
            if(totalPrice<15800){
                order1.setDeliveryFee(1200);
            }else{
                order1.setDeliveryFee(0);
            }
            order1.setProducts(products);
            returnJson.setReturnObject(order1);
        } catch (Exception ex) {
            ex.printStackTrace();
            returnJson.setErrorCode(40005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }

    @RequestMapping(value="/Good/Confirm",method = RequestMethod.POST)
    @ResponseBody
    public Object UserPos1t(@RequestBody Order order)throws Exception {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(40000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);

        if (order.getPayType()==0||StringUtils.isEmpty(order.getUuid()) || StringUtils.isEmpty(order.getUserAddressId())) {

            returnJson.setErrorCode(40001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(order.getRandomKey()+"at^&*ta");
        if(!result1.equals(order.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            Order order1 = orderService.getOrder(order.getUuid());
            UserAddress userAddress = userAddressService.getAddressById(order.getUserAddressId());
            order.setStatus(2);
            orderService.updateAddress(order);
            order1.setUserAddress(userAddress);
            order1.setComment(order.getComment());
            order1.setPayType(order.getPayType());
            String tempValue = order1.getCargoIds();
            String[] cargoIds = tempValue.split(",");
            for(String cargoId:cargoIds){
                    cargoService.deleteCargo(cargoId);
            }
            returnJson.setReturnObject(order1);
        } catch (Exception ex) {
            ex.printStackTrace();
            returnJson.setErrorCode(40005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }


    @RequestMapping(value="/Funding",method = RequestMethod.GET)
    @ResponseBody
    public Object addUser(String uuid) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(14000);
        returnJson.setReturnMessage("调用成功"+uuid.toString());
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(uuid)){

            returnJson.setErrorCode(14001);
            returnJson.setReturnMessage("传入参数为空" + uuid.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        Funding funding = null;
        try {
            funding = fundingService.getUserFunding(uuid);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(14004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(funding);
        return returnJson;
    }


    @RequestMapping(value="/Order",method = RequestMethod.POST)
    @ResponseBody
    public Object deleteOrder(@RequestBody Order order) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(13000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(order.getUuid())){

            returnJson.setErrorCode(13001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            orderService.delete(order.getUuid());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(13004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }



    @RequestMapping(value="/Order",method = RequestMethod.PUT)
    @ResponseBody
    public Object getOrder(@RequestBody Activity activity) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(13000);
        returnJson.setReturnMessage("调用成功"+activity.getUuid());
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(activity.getUuid())){

            returnJson.setErrorCode(13001);
            returnJson.setReturnMessage("传入参数为空" + activity.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            fundingService.updateOrderReal(activity.getUuid());
            orderService.updateOrderReal(activity.getUuid());
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(13004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }


    @RequestMapping(value="/Raise",method=RequestMethod.POST)
    @ResponseBody
    public Object raise(@RequestBody Order order) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(13000);
        returnJson.setReturnMessage("调用成功"+order.toString());
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(order.getName())||StringUtils.isEmpty(order.getPid())||StringUtils.isEmpty(order.getUserId())){

            returnJson.setErrorCode(13001);
            returnJson.setReturnMessage("传入参数为空" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(order.getRandomKey()+"at^&*ta");
        if(!result1.equals(order.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            Order order1 = orderService.getOrderById(order.getPid());
            Funding funding = fundingService.getUserFunding(order.getUserId());

            FundingComment fundingComment = new FundingComment();
            fundingComment.setUuid(UUID.randomUUID().toString());
            fundingComment.setCreateTime(new Date());
            fundingComment.setContent(order.getComment());
            fundingComment.setUserId(order.getUserId());
            fundingComment.setOwnerId(order1.getUserId());
            fundingComment.setUserName(order.getName());
            fundingComment.setUserName(order.getName());
            fundingCommentService.insertComment(fundingComment);
            order.setUuid(UUID.randomUUID().toString());
            order.setCreateTime(new Date());
            orderService.insertUserPay(order);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(13004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(order);
        returnJson.setReturnValue(order.getUuid());
        return returnJson;
    }

    @RequestMapping(value="/Orders/Backend",method = RequestMethod.POST)
    @ResponseBody
    public Object getUsers(@RequestBody Order order1) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<Order> results = null;
        try {
            results =  orderService.getOrders(order1);
            for(Order order:results) {
                StringBuffer tmpName = new StringBuffer();
                List<String> values = new ArrayList<String>();
                List<Product> products = new ArrayList<Product>();
                UserAddress userAddress = userAddressService.getAddressById(order.getUserAddressId());
                order.setUserAddress(userAddress);
                if(userAddress!=null) {
                    order.setDeliveryAddress(userAddress.getName() + "/" + userAddress.getProvince() + " " + userAddress.getCity() + " " + userAddress.getArea() + " " + userAddress.getAddress()+"/"+userAddress.getMobile());
                }
                if(order.getCargoIds()!=null) {
                    String[] cargos = order.getCargoIds().split(",");
                    for (String cargo : cargos) {
                        Cargo cargo1 = cargoService.getCargo(cargo);
                        if (cargo1 != null) {
                            PropertyValue propertyValue = propertyValueService.getPropertyValue(cargo1.getPropertyId());
                            if(propertyValue==null){
                                continue;
                            }
                            int price = propertyValue.getPrice();
                            propertyValue.setPrice(propertyValue.getPrice());
                            Product product = productService.getProduct(propertyValue.getProductId());
                            if(product!=null) {
                                tmpName.append(product.getName());
                                tmpName.append("/"+cargo1.getGoodCount());
                            }else{
                                break;
                            }
                        }
                    }
                }
                order.setProductName(tmpName.toString());
                User user = userService.selectByPrimaryKey(order.getUserId());
                if(user!=null) {
                    order.setNickName(user.getNickName());
                }else{
                    order.setNickName("");
                }
                if(StringUtils.isEmpty(order.getComment())){
                    order.setComment("该订单没有买家留言");
                }
            }
            return results;
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
    }



    @RequestMapping(value="/Orders/Backend/OnlineMoney",method = RequestMethod.POST)
    @ResponseBody
    public Object getUsers33(@RequestBody Order order1) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<Order> results = null;
        int totalMoney = 0;
        try {
            if(order1.getMobile()!=null&&!StringUtils.isEmpty(order1.getMobile())&&!StringUtils.isEmpty(order1.getStartDate())&&!StringUtils.isEmpty(order1.getEndDate())){
                String startDate = order1.getStartDate().replaceAll("%20"," ");
                String endDate = order1.getEndDate().replaceAll("%20"," ");
                order1.setStartDate(startDate);
                order1.setEndDate(endDate);
                results = orderService.searchMobileDate(order1);
                for(Order order:results) {
                    totalMoney = totalMoney+order.getActureMoney();
                    User user = userService.selectByPrimaryKey(order.getUserId());
                    if(user!=null) {
                        order.setNickName(user.getNickName());
                        order.setMobile(user.getMobile());
                    }else{
                        order.setNickName("");
                        order.setMobile("");
                    }
                }
            }else if(order1.getMobile()!=null&&!StringUtils.isEmpty(order1.getMobile())){
                results = orderService.searchMobile(order1);
                for(Order order:results) {
                    totalMoney = totalMoney+order.getActureMoney();
                    User user = userService.selectByPrimaryKey(order.getUserId());
                    if(user!=null) {
                        order.setNickName(user.getNickName());
                        order.setMobile(user.getMobile());
                    }else{
                        order.setNickName("");
                        order.setMobile("");
                    }
                }
            }else{
                String startDate = order1.getStartDate().replaceAll("%20"," ");
                String endDate = order1.getEndDate().replaceAll("%20"," ");
                order1.setStartDate(startDate);
                order1.setEndDate(endDate);
                results =  orderService.getOnlineMoneyOrders(order1);
                for(Order order:results) {
                    totalMoney = totalMoney+order.getActureMoney();
                    User user = userService.selectByPrimaryKey(order.getUserId());
                    if(user!=null) {
                        order.setNickName(user.getNickName());
                        order.setMobile(user.getMobile());
                    }else{
                        order.setNickName("");
                        order.setMobile("");
                    }
                }
                totalMoney = orderService.getOnlineMoneyTotal(order1).getActureMoney();
            }
            returnJson.setReturnValue(new Integer(totalMoney).toString());
            returnJson.setReturnObject(results);
            return returnJson;
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
    }


    @RequestMapping(value="/Orders/Backend/OnlineMoney/Total",method = RequestMethod.POST)
    @ResponseBody
    public Object getUsers4433(@RequestBody Order order1) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<Order> results = null;
        try {
            int totalMoney = 0;
            String startDate = order1.getStartDate().replaceAll("%20"," ");
            String endDate = order1.getEndDate().replaceAll("%20"," ");
            order1.setStartDate(startDate);
            order1.setEndDate(endDate);
            results =  orderService.getOnlineMoneyOrdersTotal(order1);
            return results.size();
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
    }



    @RequestMapping(value="/Orders/Backend/Count",method = RequestMethod.GET)
    @ResponseBody
    public Object getUser11s() {
        return new Integer(orderService.getOrdersTotal().size()).toString();

    }




    @RequestMapping(value="/Raise/Update",method = RequestMethod.POST)
    @ResponseBody
    public Object raiseUpdate(@RequestBody Order order) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(14000);
        returnJson.setReturnMessage("调用成功" + order.toString());
        returnJson.setServerStatus(0);
        logger.info("进入回调支付了");

        if(StringUtils.isEmpty(order.getSeriesNo())||StringUtils.isEmpty(order.getUuid())||StringUtils.isEmpty(order.getActureMoney())){
            logger.info("回调支付传入参数为空");
            returnJson.setErrorCode(14001);
            returnJson.setReturnMessage("传入参数为空" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(order.getRandomKey()+"at^&*ta");
        if(!result1.equals(order.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            logger.info("传入支付金额为"+order.getActureMoney());
            Order order1 = orderService.getOrderByIdTemp1(order.getUuid());
            order1.setPaidTime(new Date());
            order1.setSeriesNo(order.getSeriesNo());
            order1.setActureMoney(order.getActureMoney());
            logger.info("订单金额为" + order1.getActureMoney());
            if(order1.getStatus()!=2){
                returnJson.setErrorCode(14003);
                returnJson.setReturnMessage("该订单已经支付过了" + order.toString());
                returnJson.setServerStatus(1);
                return  returnJson;
            }
            if(order1.getActureMoney()!=order.getActureMoney()){
                returnJson.setErrorCode(14002);
                returnJson.setReturnMessage("支付金额错误" + order.toString());
                returnJson.setServerStatus(1);
                return  returnJson;
            }
            order.setType(order1.getType());
            order.setStatus(3);
            order.setPaidTime(new Date());
            order.setUserAddressId(order1.getUserAddressId());
            orderService.updateOrderMoney(order);
            User user = userService.selectByPrimaryKey(order1.getUserId());
            user.setLevel(2);
            if(order1.getType()==2){
                user.setOfflineMoney(order.getActureMoney());
                user.setOnlineMoney(order.getActureMoney());
                userService.updateMoney(user);
                order1.setStatus(5);
                orderService.updateOrderMoney(order1);
            }else if(order1.getType()==3){
                userService.updateUserLevel(user);
                user.setOfflineMoney(1000);
                user.setOnlineMoney(1000);
                userService.updateOfflineMoney(user);
                userService.updateOnlineMoney(user);
            }else if(order1.getType()==5){
                user.setOfflineMoney(0);
                userService.updateOfflineMoney(user);
                Shop shop = shopService.getShopById(order1.getShopId());
                List<Order> orders = orderService.getOrdersPid(order.getUuid());
                int shopRate = 100-shop.getReturnRate();
                for(Order order3:orders){
                    if(order3.getType()==6) {
                        int money = order3.getActureMoney()*shopRate/100;
                        shop.setOfflineMoney(order3.getActureMoney()*shopRate/100);
                        shopService.updateMoney(shop);
                        order3.setSeriesNo(order.getSeriesNo());
                        order3.setUserAddressId(order3.getUserAddressId());
                        order3.setPid(order.getUuid());
                        order3.setUpdateTime(new Date());
                        order3.setType(order3.getType());
                        order3.setStatus(3);
                        order3.setPaidTime(new Date());
                        order3.setActureMoney(money);
                        order3.setTotalMoney(money);
                        orderService.updateOrderMoneyNew(order3);
                        continue;
                    }
                    if(order3.getType()==7) {
                        int money = order3.getActureMoney()*shopRate/100;
                        User user3 = userService.selectByPrimaryKey(order3.getUserId());
                        user3.setOnlineMoney(order3.getActureMoney()+user3.getOnlineMoney());
                        userService.updateOnlineMoney(user3);
                        order3.setSeriesNo(order.getSeriesNo());
                        order3.setActureMoney(money);
                        order3.setTotalMoney(money);
                        order3.setUserAddressId(order1.getUserAddressId());
                        order3.setPid(order1.getUuid());
                        order3.setUpdateTime(new Date());
                        order3.setType(order3.getType());
                        order3.setStatus(3);
                        order3.setPaidTime(new Date());
                        orderService.updateOrderMoneyNew(order3);
                    }
                    order3.setSeriesNo(order.getSeriesNo());
                    order3.setUserAddressId(order1.getUserAddressId());
                    order3.setPid(order1.getUuid());
                    order3.setUpdateTime(new Date());
                    order3.setType(order3.getType());
                    order3.setStatus(3);
                    order3.setPaidTime(new Date());
                    orderService.updateOrderMoneyNew(order3);
                }
            }
            logger.info("回调操作成功了");

        }catch(Exception ex){
            ex.printStackTrace();
            logger.info("回调支付服务器错误" + ex.getStackTrace());
            returnJson.setErrorCode(14004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }

    @RequestMapping(value="/Address",method = RequestMethod.POST)
    @ResponseBody
    public Object address(@RequestBody Order order) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(17000);
        returnJson.setReturnMessage("调用成功" + order.toString());
        returnJson.setServerStatus(0);


    if(StringUtils.isEmpty(order.getUuid())||StringUtils.isEmpty(order.getAddressId())||StringUtils.isEmpty(order.getComment())){
            returnJson.setErrorCode(17001);
            returnJson.setReturnMessage("传入参数为空" + order.toString());
            returnJson.setServerStatus(1);
        return returnJson;
    }
        String result1 = MD5.GetMD5Code(order.getRandomKey()+"at^&*ta");
        if(!result1.equals(order.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            Order order1 = orderService.getOrderByIdTemp(order.getUuid());
            UserAddress userAddress = userAddressService.getAddressById(order.getAddressId());
            order1.setUserAddress(userAddress);
            order1.setComment(order.getComment());
            orderService.updateAddress(order1);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(17004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }



    @RequestMapping(value="/User/Charge",method = RequestMethod.POST)
    @ResponseBody
    public Object charge(@RequestBody Order order) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(17000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(order.getUserId())){
            returnJson.setErrorCode(17001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(order.getRandomKey()+"at^&*ta");
        if(!result1.equals(order.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        logger.info("他它用户充值的金额为"+order.getActureMoney());
        try {
            User user = userService.selectByPrimaryKey(order.getUserId());
            if(user.getLevel()!=2){
                returnJson.setErrorCode(17002);
                returnJson.setReturnMessage("普通会员不能充值");
                returnJson.setServerStatus(1);
                return returnJson;
            }
            if(user==null){
                returnJson.setErrorCode(17003);
                returnJson.setReturnMessage("userId不存在");
                returnJson.setServerStatus(1);
                return returnJson;
            }
            order.setCreateTime(new Date());
            order.setUuid(createOrderNo(formatDate()));
            order.setUserId(user.getUuid());
            order.setTotalMoney(order.getActureMoney());
            order.setType(2);
            order.setStatus(2);
            orderService.insertNormalUser(order);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(17004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(order);
        return returnJson;
    }

    @RequestMapping(value="/User/Upgrade",method = RequestMethod.GET)
    @ResponseBody
    public Object normalUserOrder(Order order) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(17000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(order.getUserId())){
            returnJson.setErrorCode(17001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(order.getRandomKey()+"at^&*ta");
        if(!result1.equals(order.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
           User user = userService.selectByPrimaryKey(order.getUserId());
            if(user==null){
                returnJson.setErrorCode(17002);
                returnJson.setReturnMessage("userId不存在");
                returnJson.setServerStatus(1);
                return returnJson;
            }
            if(user.getLevel()==2){
                returnJson.setErrorCode(17003);
                returnJson.setReturnMessage("用户已经是vip用户了");
                returnJson.setServerStatus(1);
                return returnJson;
            }
            order.setCreateTime(new Date());
            order.setUuid(createOrderNo(formatDate()));
            order.setActureMoney(1000);
            order.setTotalMoney(order.getActureMoney());
            order.setUserId(user.getUuid());
            order.setType(3);
            orderService.insertNormalUser(order);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(17004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(order);
        return returnJson;
    }


    @RequestMapping(value="/Orders/Shop",method = RequestMethod.GET)
    @ResponseBody
    public Object getShopOrders(Order order) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(19000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(order.getShopId())||StringUtils.isEmpty(order.getStartDate())){
            returnJson.setErrorCode(19001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(order.getRandomKey()+"at^&*ta");
        if(!result1.equals(order.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            String originDate = order.getStartDate();
            String dateString = order.getStartDate()+" 00:00:00";
            order.setStartDate(dateString);
            dateString = originDate+" 23:59:59";
            order.setEndDate(dateString);
            List<Order> orders = orderService.getBizOrderDaily(order);
            for(Order order1:orders){
                int payBack = 0;
                List<Order> childOrders = orderService.getOrdersPid(order1.getPid());
                for(Order order2:childOrders){
                    if(order2.getType()==6){
                        payBack = order2.getActureMoney();
                        break;
                    }
                }
                order1.setShopPayBack(payBack);
            }
            returnJson.setReturnObject(orders);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(19004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }
    Logger logger  =  Logger.getLogger(OrderController.class);
    @RequestMapping(value="/Alipay/CallBack",method = RequestMethod.POST)
    @ResponseBody
    public Object finishOrderAli(String notify_time,String notify_type,String notify_id,String sign_type,String sign,String out_trade_no,String subject,
                                 String payment_type,String trade_no,String trade_status,String seller_id,String seller_email,String buyer_id,
                                 String buyer_email,String total_fee,String quantity,String price,String body,String gmt_create,String gmt_payment,
                                 String is_total_fee_adjust,String use_coupon,String discount,String refund_status,String gmt_refund) {
            logger.info("支付宝回调了");
            Order order = new Order();
           logger.info("支付的金额为:"+total_fee);
           logger.info("支付的状态为:"+trade_status);
           logger.info("支付宝流水号为:"+trade_no);
       int fee =  new Double(new Double(total_fee)*100).intValue();
            order.setActureMoney(fee);
        Order order1 = orderService.getOrder(out_trade_no);
        if(trade_status.equals("TRADE_SUCCESS")||trade_status.equals("TRADE_FINISHED")){
                String userId = order1.getUserId();
                order.setStatus(3);
            }else{
                order.setStatus(2);
                return "fail";
            }
        if(order1.getStatus()!=2){
            logger.info("该订单已经支付过了");
            return "success";
        }
        Order order2 = orderService.getOrder(out_trade_no);

            order.setSeriesNo(trade_no);
           order.setUserAddressId(order2.getUserAddressId());
            order.setUuid(out_trade_no);
            order.setUpdateTime(new Date());
            order.setType(order2.getType());
            order.setStatus(3);
           order.setPaidTime(new Date());
        order.setActureMoney(order2.getActureMoney());
        orderService.updateOrderMoney(order);
        String userId = order1.getUserId();
        User user2 = new User();
        user2.setLevel(2);
        user2.setUuid(userId);
        if(order2.getType()==3){
            user2.setOfflineMoney(1000);
            user2.setOnlineMoney(1000);
            userService.updateOfflineMoney(user2);
            userService.updateOnlineMoney(user2);
            userService.updateUserLevel(user2);
        }else if(order2.getType()==2){
            user2.setOfflineMoney(fee);
            user2.setOnlineMoney(fee);
            userService.updateMoney(user2);
        }else if(order2.getType()==8){
            User user = userService.selectByPrimaryKey(order2.getUserId());
            user.setOfflineMoney(0);
            userService.updateOfflineMoney(user);
            Shop shop = shopService.getShopById(order2.getShopId());
            List<Order> orders = orderService.getOrdersPid(out_trade_no);
            int shopRate = 100-shop.getReturnRate();
            for(Order order3:orders){
                if(order3.getType()==6) {
                    int money = order3.getActureMoney()*shopRate/100;
                    shop.setOfflineMoney(order3.getActureMoney()*shopRate/100);
                    shopService.updateMoney(shop);
                    order3.setSeriesNo(trade_no);
                    order3.setUserAddressId(order2.getUserAddressId());
                    order3.setPid(out_trade_no);
                    order3.setUpdateTime(new Date());
                    order3.setType(order3.getType());
                    order3.setStatus(3);
                    order3.setPaidTime(new Date());
                    order3.setActureMoney(money);
                    order3.setTotalMoney(money);
                    orderService.updateOrderMoneyNew(order3);
                    continue;
                }
                if(order3.getType()==7) {
                    int money = order3.getActureMoney()*shopRate/100;
                    User user3 = userService.selectByPrimaryKey(order3.getUserId());
                    user3.setOnlineMoney(order3.getActureMoney()+user3.getOnlineMoney());
                    userService.updateOnlineMoney(user3);
                    order3.setSeriesNo(trade_no);
                    order3.setActureMoney(money);
                    order3.setTotalMoney(money);
                    order3.setUserAddressId(order2.getUserAddressId());
                    order3.setPid(out_trade_no);
                    order3.setUpdateTime(new Date());
                    order3.setType(order3.getType());
                    order3.setStatus(3);
                    order3.setPaidTime(new Date());
                    orderService.updateOrderMoneyNew(order3);
                }
                order3.setSeriesNo(trade_no);
                order3.setUserAddressId(order2.getUserAddressId());
                order3.setPid(out_trade_no);
                order3.setUpdateTime(new Date());
                order3.setType(order3.getType());
                order3.setStatus(3);
                order3.setPaidTime(new Date());
                orderService.updateOrderMoneyNew(order3);
            }
        }
        logger.info("支付宝回调了成功了");
            return "success";
    }


    @Autowired
    private ShopService shopService;

    public  String formatDate(){
        java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String s = format1.format(new Date());
        return s;
    }

    public String createOrderNo(String s){
        s = s+UUID.randomUUID().toString().substring(0,4);
        s = s+UUID.randomUUID().toString().substring(0,2);
        return  s.replaceAll("-","").replaceAll(" ","").replaceAll(":","");
    }

    @RequestMapping(value="/Pay/Tata",method = RequestMethod.POST)
    @ResponseBody
    public Object minusOnlineMoney(@RequestBody Order order) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(17000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(order.getUuid())){
            returnJson.setErrorCode(17001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(order.getRandomKey()+"at^&*ta");
        if(!result1.equals(order.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            Order order1 = orderService.getOrder(order.getUuid());
            if(order1.getStatus()!=2){
                returnJson.setErrorCode(17004);
                returnJson.setReturnMessage("请勿重复支付订单");
                returnJson.setServerStatus(1);
                return returnJson;
            }
            if(order1==null){
                returnJson.setErrorCode(17002);
                returnJson.setReturnMessage("order uuid不存在");
                returnJson.setServerStatus(1);
                return returnJson;
            }
            User user = userService.selectByPrimaryKey(order1.getUserId());
            if(user.getOnlineMoney()<order1.getActureMoney()){
                returnJson.setErrorCode(17003);
                returnJson.setReturnMessage("用户线上币不够");
                returnJson.setServerStatus(1);
                return returnJson;
            }
            user.setOnlineMoney(order1.getActureMoney());
            userService.minusOnlineMonet(user);
            order1.setStatus(3);
            order1.setPaidTime(new Date());
            order1.setSeriesNo("99999999");
            orderService.updateOrderMoney(order1);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(17005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }


    @RequestMapping(value="/Shop/Report",method = RequestMethod.GET)
    @ResponseBody
    public Object shopReport(Order order) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(18000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(order.getShopId())){
            returnJson.setErrorCode(18001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(order.getRandomKey()+"at^&*ta");
        if(!result1.equals(order.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            Order order1 = new Order();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(new Date());
            dateString = dateString+" 00:00:00";
            order.setStartDate(dateString);
            order.setEndDate(new Date().toLocaleString());
            int orderDaily = 0;
            int orderMonth = 0;
            int orderWeek = 0;
            int orderTotal = 0;
            List<Integer> weekDayMoneyValues = new ArrayList<Integer>();
            try {
                orderDaily = orderService.getShopReport(order);
            }catch (Exception e){

            }
                Calendar cal = Calendar.getInstance();
                Calendar calendar = Calendar.getInstance(Locale.CHINA);
                calendar.setTime(new Date());// 设置当前日期
                int day = calendar.getMinimum(Calendar.DAY_OF_MONTH);// 取得当前月的最小日期(天)
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.HOUR,0);
                calendar.set(Calendar.AM_PM,0);
                calendar.set(Calendar.MINUTE,0);
                Date date = calendar.getTime();
                order.setStartDate(date.toLocaleString());
                try {
                    orderMonth = orderService.getShopReport(order);
                }catch (Exception e){
                }
                calendar.setTime(new Date());// 设置当前日期
                int weekDay = calendar.getMinimum(Calendar.DAY_OF_WEEK);// 取得当前月的最小日期(天)
                calendar.set(Calendar.DAY_OF_WEEK, day);
                calendar.set(Calendar.HOUR,0);
                calendar.set(Calendar.AM_PM,0);
                calendar.set(Calendar.MINUTE,0);
                date = calendar.getTime();
                order.setStartDate(date.toLocaleString());
                try {
                    orderWeek = orderService.getShopReport(order);
                }catch (Exception e){
                }
                int YEAR = calendar.getMinimum(Calendar.YEAR);// 取得当前月的最小日期(天)
                calendar.set(Calendar.YEAR,YEAR);
                calendar.set(Calendar.DAY_OF_WEEK, day);
                calendar.set(Calendar.HOUR,0);
                calendar.set(Calendar.AM_PM,0);
                calendar.set(Calendar.MINUTE,0);
                date = calendar.getTime();
                order.setStartDate(date.toLocaleString());
                try {
                    orderTotal = orderService.getShopReport(order);
                }catch (Exception e){

                }
                int innerDay = calendar.getMinimum(Calendar.DAY_OF_WEEK);// 取得当前月的最小日期(天)
                for(int i=0;i<7;i++) {
                    Calendar calendar1 = Calendar.getInstance(Locale.CHINA);
                    int year = calendar1.get(Calendar.YEAR);
                    calendar1.set(Calendar.YEAR,year);
                    calendar1.set(Calendar.DAY_OF_WEEK, innerDay++);
                    calendar1.set(Calendar.HOUR, 0);
                    calendar1.set(Calendar.AM_PM, 0);
                    calendar1.set(Calendar.MINUTE, 0);
                    date = calendar1.getTime();
                    order.setStartDate(date.toLocaleString());
                    calendar1.set(Calendar.HOUR, 12);
                    calendar1.set(Calendar.AM_PM, 1);
                    calendar1.set(Calendar.MINUTE, 0);
                    order.setEndDate(calendar1.getTime().toLocaleString());
                    try {
                        weekDayMoneyValues.add(orderService.getShopReport(order));
                    }catch (Exception e){
                        weekDayMoneyValues.add(0);
                    }
                }
            Shop shop = shopService.getShopById(order.getShopId());
            ShopAccount shopAccount = new ShopAccount();
            shopAccount.setWeekAmount(orderWeek);
            shopAccount.setDaliyAmount(orderDaily);
            shopAccount.setMonthAmount(orderMonth);
            shopAccount.setTotalAmount(orderTotal);
            shopAccount.setAccountAmount(orderTotal);
            shopAccount.setWeekValues(weekDayMoneyValues);
            returnJson.setReturnObject(shopAccount);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(18004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }
    public static void main(String[]args){
      System.err.println(MD5.GetMD5Code("66739a"));
    }
}
