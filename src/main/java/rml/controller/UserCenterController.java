package rml.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.Comment;
import rml.model.User;
import rml.service.CommentService;
import rml.service.UserService;
import rml.servlet.FileUploadServlet;
import rml.util.MD5;
import rml.util.ReturnJson;

import java.io.File;
import java.util.Hashtable;
import java.util.UUID;

/**
 * Created by edward-echo on 2016/2/27.
 */

@Controller
@RequestMapping("/UserCenter")
public class UserCenterController {


    @Autowired
    private UserService userService;


    @RequestMapping(value="/Account",method = RequestMethod.GET)
    @ResponseBody
    public Object getAccount(User user) {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(2000);
        returnJson.setReturnMessage("调用成功" + user.toString());
        returnJson.setServerStatus(0);


        if(StringUtils.isEmpty(user.getUuid())){

            returnJson.setErrorCode(2001);
            returnJson.setReturnMessage("传入参数为空" + user.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try{
            returnJson.setReturnObject(userService.getAccount(user.getUuid()));
            return returnJson;
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(2003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
    }



    @Autowired
    CommentService commentService;

    @RequestMapping(value="/Comment",method = RequestMethod.POST)
    @ResponseBody
    public Object Comment(@RequestBody Comment comment) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(62000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(comment.getContent())||StringUtils.isEmpty(comment.getUserId())){
            returnJson.setErrorCode(62002);
            returnJson.setReturnMessage("传入参数为空" + comment.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String result1 = MD5.GetMD5Code(comment.getRandomKey()+"at^&*ta");
        if(!result1.equals(comment.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + comment.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            commentService.createComment(comment);
        } catch (Exception ex) {
            ex.printStackTrace();
            returnJson.setErrorCode(62004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }


    @RequestMapping(value="/Dimension/Code",method = RequestMethod.GET)
    @ResponseBody
    public Object DimensionCode(String userId) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(63000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        if(StringUtils.isEmpty(userId)){
            returnJson.setErrorCode(62002);
            returnJson.setReturnMessage("传入参数为空" + userId.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            int width = 200;
            int height = 200;
            String format = "png";
            Hashtable hints= new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(userId, BarcodeFormat.QR_CODE, width, height,hints);
            File outputFile = new File(System.getProperty("java.io.tmpdir")+"/" + UUID.randomUUID()+".png");
            outputFile.createNewFile();
            MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
            return FileUploadServlet.fileUpload(outputFile);
        } catch (Exception ex) {
            ex.printStackTrace();
            returnJson.setErrorCode(63004);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
        }
        return returnJson;
    }
}
