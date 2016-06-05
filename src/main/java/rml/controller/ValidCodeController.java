package rml.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.ValidCode;
import rml.service.ValidCodeService;
import rml.util.*;

/**
 * Created by Administrator on 2015/9/13.
 */

@Controller
@RequestMapping("/ValidCode")
public class ValidCodeController {

    @Autowired
    ValidCodeService validCodeService;

    private String smsContent = "【他它网】感谢支持他它，您的短信验证码为:";


    @RequestMapping(value="/ValidCode",method = RequestMethod.POST)
    @ResponseBody
    public Object sentValidCode(@RequestBody ValidCode code){
        String validCode = RandomCode.getRandomCode(4);
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(3000);
        returnJson.setReturnMessage("调用成功" + code.toString());
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(code.getMobile())||StringUtils.isEmpty(code.getChannel())){
            returnJson.setErrorCode(3001);
            returnJson.setReturnMessage("传入参数为空" + code.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(code.getRandomKey()+"at^&*ta");
        if(!result1.equals(code.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + code.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        sendSms(code.getMobile(),validCode);
        code.setValidCode(validCode);
        try {
            validCodeService.insert(code);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(3002);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        returnJson.setReturnValue(validCode);
        return returnJson;
    }

    private void sendSms(String phoneNo,String validCode){
        // TODO Auto-generated method stub
        String username = "vipcaige";//短信宝帐户名
        String pass =  MD5.GetMD5Code(("caige168"));//短信宝帐户密码，md5加密后的字符串
        String phone = phoneNo;//电话号码
        try {
            String content = java.net.URLEncoder.encode(smsContent+" "+validCode+""+",在20分钟内有效.","UTF-8");//发送内容
            HttpSend send = new HttpSend("http://www.smsbao.com/sms?u="+username+"&p="+pass+"&m="+phone+"&c="+content);
            send.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
