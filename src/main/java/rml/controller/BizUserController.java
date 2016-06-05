package rml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.BizUser;
import rml.model.ProductBrand;
import rml.service.BizUserService;
import rml.service.ProductBrandService;
import rml.util.MD5;
import rml.util.ReturnJson;

/**
 * Created by edward-echo on 2016/4/6.
 */
@Controller
@RequestMapping("/Biz/User")
public class BizUserController {

    @Autowired
    BizUserService bizUserService;

    @RequestMapping(value="/User",method = RequestMethod.GET)
    @ResponseBody
    public Object addUser(BizUser user) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(7000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(user.getMobile())||StringUtils.isEmpty(user.getPassword())){

            returnJson.setErrorCode(7001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(user.getRandomKey() + "at^&*ta");
        if(!result1.equals(user.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + user.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        user.setPassword(MD5.GetMD5Code(user.getPassword()));
        BizUser bizUser = bizUserService.getUser(user);
        if(bizUser==null){
            returnJson.setErrorCode(7002);
            returnJson.setReturnMessage("用户名或密码无效");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        returnJson.setReturnObject(user);
        return returnJson;
    }

    public static void main(String[]args){
        System.err.println(MD5.GetMD5Code("6643xsf"));
    }
}
