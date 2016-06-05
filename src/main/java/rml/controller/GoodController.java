package rml.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.Good;
import rml.model.User;
import rml.model.ValidCode;
import rml.service.GoodService;
import rml.util.ReturnJson;

/**
 * Created by edward-echo on 2015/12/20.
 */
@Controller
@RequestMapping("/Good")
public class GoodController {

    @Autowired
    private GoodService goodService;

    @RequestMapping(value="/Good",method = RequestMethod.POST)
    @ResponseBody
    public Object addUser(@RequestBody Good good) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(7000);
        returnJson.setReturnMessage("调用成功"+good.toString());
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(good.getCapacity())||StringUtils.isEmpty(good.getName())||good.getPrice()==0){

            returnJson.setErrorCode(7001);
            returnJson.setReturnMessage("传入参数为空" + good.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            goodService.insert(good);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(1004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(good);
        returnJson.setReturnValue(good.getUuid());
        return returnJson;
    }


}
