package rml.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.Terminal;
import rml.model.UserCouponTicket;
import rml.service.TerminalService;
import rml.service.UserCouponTicketService;
import rml.util.MD5;
import rml.util.ReturnJson;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by edward-echo on 2016/5/18.
 */
@Controller
@RequestMapping("/User/Coupon")
public class UserCouponTicketController {


    private static final Logger logger = LoggerFactory.getLogger(UserCouponTicketController.class);

    @Autowired
    private UserCouponTicketService userCouponTicketService;

    @RequestMapping(value="/Coupon",method = RequestMethod.POST)
    @ResponseBody
    public Object getMoney(@RequestBody UserCouponTicket userCouponTicket) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(28000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        List<UserCouponTicket> tickets = new ArrayList<UserCouponTicket>();
        UserCouponTicket ticket = userCouponTicketService.getMaxBatchNo();
        int batchNo = 0;
        if(ticket!=null){
            batchNo  = ticket.getBatchNo();
        }
        ++batchNo;
        boolean flag = false;
        while(flag==false) {
            int leak = 0;
            for (int i = 0; i < userCouponTicket.getNum(); i++) {
                try {
                    insert(userCouponTicket, batchNo);
                    tickets.add(insert(userCouponTicket, batchNo));
                } catch (Exception ex) {
                    logger.info(ex.getMessage());
                    ex.getStackTrace();
                    ++leak;
                    continue;

                }
            }
            userCouponTicket.setNum(leak);
            if(leak==0){
                flag = true;
            }
            System.err.println(leak);
        }
        returnJson.setReturnObject(tickets);
        return returnJson ;
    }
    public  UserCouponTicket insert(UserCouponTicket userCouponTicket,int batchNo){
        UserCouponTicket ticket1 = new UserCouponTicket();
        Date date = new Date();
        String localDate = date.toLocaleString();
        SimpleDateFormat myFmt1 = new SimpleDateFormat("yy-MM-dd");
        localDate = myFmt1.format(date);
        String[] values = localDate.split("-");
        ticket1.setYear(values[0]);
        ticket1.setMonth(values[1]);
        String no = ticket1.getYear();
        no = no + ticket1.getMonth();
        no = no + values[2];
        String uuid = UUID.randomUUID().toString();
        no = no + randomString(6);
        no = no.substring(0, no.length() - 1);
        no = no + randomStringWord(1);
        ticket1.setCreateTime(new Date());
        ticket1.setNo(no);
        ticket1.setUuid(uuid);
        ticket1.setRecordStatus(1);
        ticket1.setBatchNo(batchNo);
        ticket1.setShopName(userCouponTicket.getShopName());
        ticket1.setTicketValue(userCouponTicket.getTicketValue());
        userCouponTicketService.insert(ticket1);
        return ticket1;
    }
    public static final String randomString(int length) {
         Random randGen = null;
        char[] numbersAndLetters = null;
        if (length < 1) {
            return null;
        }
        if (randGen == null) {
            randGen = new Random();
            numbersAndLetters = ("abcdefghijkmnpqrstuvwxyz0123456789").toCharArray();
        }
        char [] randBuffer = new char[length];
        for (int i=0; i<randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(33)];
        }
        return new String(randBuffer);
    }



    public static final String randomStringWord(int length) {
        Random randGen = null;
        char[] numbersAndLetters = null;
        if (length < 1) {
            return null;
        }
        if (randGen == null) {
            randGen = new Random();
            numbersAndLetters = ("abcdefghijkmnpqrstuvwxyz").toCharArray();
        }
        char [] randBuffer = new char[length];
        for (int i=0; i<randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(23)];
        }
        return new String(randBuffer);
    }
    @RequestMapping(value="/Ticket",method = RequestMethod.GET)
    @ResponseBody
    public Object getTicket(UserCouponTicket ticket) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(28000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        String result1 = MD5.GetMD5Code(ticket.getRandomKey()+"at^&*ta");
        if(!result1.equals(ticket.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + ticket.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        try {
            UserCouponTicket ticket1 = userCouponTicketService.getTicket(ticket);
            if (ticket1 == null) {
                returnJson.setErrorCode(28001);
                returnJson.setReturnMessage("条形码编号不存在");
                returnJson.setServerStatus(1);
                return returnJson;
            }
            if (ticket1.getRecordStatus() != 1) {
                returnJson.setErrorCode(28002);
                returnJson.setReturnMessage("条形码编号无效");
                returnJson.setServerStatus(1);
                returnJson.setReturnObject(ticket1);
                return returnJson;
            } else {
                returnJson.setErrorCode(28003);
                returnJson.setReturnMessage("条形码编号有效");
                returnJson.setServerStatus(1);
                returnJson.setReturnObject(ticket1);
                return returnJson;
            }
        }catch (Exception e){
            returnJson.setErrorCode(28004);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(1);
            return returnJson;
        }
    }

    @RequestMapping(value="/Ticket/Disable",method = RequestMethod.POST)
    @ResponseBody
    public Object ticketDisable(@RequestBody UserCouponTicket ticket) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(28000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        String result1 = MD5.GetMD5Code(ticket.getRandomKey() + "at^&*ta");
        if(!result1.equals(ticket.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + ticket.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        UserCouponTicket ticket1 = userCouponTicketService.getTicket(ticket);
        if(ticket1==null){
            returnJson.setErrorCode(28001);
            returnJson.setReturnMessage("条形码编号不存在");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        userCouponTicketService.disable(ticket1);
        return  returnJson;
    }


    @RequestMapping(value="/Coupons",method = RequestMethod.GET)
    @ResponseBody
    public Object get11(@RequestBody UserCouponTicket userCouponTicket) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(28000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        UserCouponTicket ticket = userCouponTicketService.getMaxBatchNo();
        List<UserCouponTicket> tickets = userCouponTicketService.getTicketBatch(ticket);
        returnJson.setReturnObject(tickets);
        return returnJson ;
    }

    public static void main(String[]args){
        System.err.print(randomString(6));

    }

    @RequestMapping(value="/Tickets",method = RequestMethod.GET)
    @ResponseBody
    public Object getTickets(UserCouponTicket ticket) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(28000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        try {
           List<UserCouponTicket>  tickets = userCouponTicketService.getTickets(ticket);
           returnJson.setReturnObject(tickets);
           return returnJson;
        }catch (Exception e){
            returnJson.setErrorCode(28004);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(1);
            return returnJson;
        }
    }

    @RequestMapping(value="/Tickets/Total",method = RequestMethod.GET)
    @ResponseBody
    public Object getTicketsTotal() {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(28000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        try {
            List<UserCouponTicket>  tickets = userCouponTicketService.getTicketsTotal();
            return tickets.size();
        }catch (Exception e){
            returnJson.setErrorCode(28004);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(1);
            return returnJson;
        }
    }

    @RequestMapping(value="/ShopName",method = RequestMethod.POST)
    @ResponseBody
    public Object changeShopName(@RequestBody UserCouponTicket ticket) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(28000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        try {
            userCouponTicketService.updateShopName(ticket);
        }catch (Exception e){
            returnJson.setErrorCode(28004);
            returnJson.setReturnMessage("服务器错误");
            returnJson.setServerStatus(1);
            return returnJson;
        }
        return  returnJson;
    }


}
