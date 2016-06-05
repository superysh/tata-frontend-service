package rml.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.Cargo;
import rml.model.User;
import rml.model.UserType;
import rml.model.UserTypeDesc;
import rml.service.CargoService;
import rml.service.UserService;
import rml.service.UserTypeDescService;
import rml.service.UserTypeService;
import rml.util.ReturnJson;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2015/12/7 0007.
 */

@Controller
@RequestMapping("/User")
public class UserTypeController {

    @Autowired
    UserTypeDescService userTypeDescService;

    @Autowired
    UserTypeService userTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CargoService cargoService;


    @RequestMapping(value="/Types",method = RequestMethod.GET)
    @ResponseBody
    public Object getUser(User user) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(5000);
        returnJson.setReturnMessage("调用成功" + user.toString());
        returnJson.setServerStatus(0);

       List<UserTypeDesc> result = null;
        try {
            result = userTypeDescService.getUserTypes();
            if(result==null){
                returnJson.setErrorCode(5002);
                returnJson.setReturnMessage("用户名或者密码错误");
                returnJson.setServerStatus(1);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(5003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(result);
        return returnJson;
    }


    }
