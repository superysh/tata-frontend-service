package rml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.SearchLog;
import rml.model.User;
import rml.service.SearchLogService;
import rml.util.ReturnJson;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by edward-echo on 2016/4/13.
 */
@Controller
@RequestMapping("/Search")
public class SearchLogController {


    @Autowired
    private SearchLogService searchLogService;

    @RequestMapping(value="/Log",method = RequestMethod.POST)
    @ResponseBody
    public Object search(@RequestBody SearchLog searchLog) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(35000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<User> results = null;
        try {
            searchLog.setUuid(UUID.randomUUID().toString());
            searchLog.setCreateTime(new Date());
            searchLogService.insertLog(searchLog);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(35003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(results);
        return returnJson;
    }

    @RequestMapping(value="/Logs",method = RequestMethod.GET)
    @ResponseBody
    public Object searchLog(SearchLog searchLog) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(36000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        try {
            returnJson.setReturnObject(searchLogService.getLogs(searchLog));
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(36003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return returnJson;
    }
}
