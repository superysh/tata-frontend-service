package rml.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.FundingComment;
import rml.service.FundingCommentService;
import rml.util.ReturnJson;

import java.util.Date;
import java.util.UUID;

/**
 * Created by edward-echo on 2015/12/27.
 */
@Controller
@RequestMapping("/Funding")
public class FundingCommentController {

    @Autowired
    private FundingCommentService fundingCommentService;

    @RequestMapping(value="/Comment",method = RequestMethod.POST)
    @ResponseBody
    public Object addUser(@RequestBody FundingComment fundingComment) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(7000);
        returnJson.setReturnMessage("调用成功"+fundingComment.toString());
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(fundingComment.getUserId())||StringUtils.isEmpty(fundingComment.getContent())||StringUtils.isEmpty(fundingComment.getFundingId())){

            returnJson.setErrorCode(7001);
            returnJson.setReturnMessage("传入参数为空" + fundingComment.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            fundingComment.setUuid(UUID.randomUUID().toString());
            fundingComment.setCreateTime(new Date());
            fundingCommentService.insertComment(fundingComment);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(1004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(fundingComment);
        returnJson.setReturnValue(fundingComment.getUuid());
        return returnJson;
    }


}
