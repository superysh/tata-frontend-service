package rml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.Terminal;
import rml.model.User;
import rml.model.UserMoney;
import rml.service.TerminalService;
import rml.util.MD5;
import rml.util.ReturnJson;

/**
 * Created by edward-echo on 2016/3/25.
 */
@Controller
@RequestMapping("/Terminal")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;

    @RequestMapping(value="/Update",method = RequestMethod.GET)
    @ResponseBody
    public Object getMoney(Terminal terminalMain) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(28000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        String result1 = MD5.GetMD5Code(terminalMain.getRandomKey()+"at^&*ta");
        if(!result1.equals(terminalMain.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + terminalMain.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            Terminal terminal = terminalService.getVersion(terminalMain.getVersion());
            if(terminal==null){
                returnJson.setErrorCode(28001);
                returnJson.setReturnMessage("版本号不存在");
                returnJson.setServerStatus(0);
            }
            int increment = terminal.getIncrement();
            Terminal terminal1 = terminalService.getMaxVersion();
            int updateVersion = terminal1.getForceUpdateIncrement();

            if(increment<=updateVersion){
                terminal1.setNeedUpdate(true);
            }else{
                terminal1.setNeedUpdate(false);
            }
            if(increment==terminal1.getIncrement()){
                returnJson.setErrorCode(28003);
                returnJson.setReturnMessage("已经是最新版本");
                returnJson.setServerStatus(0);
                return returnJson ;
            }
            returnJson.setReturnObject(terminal1);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(28002);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(2);
        }
        return returnJson ;
    }
}
