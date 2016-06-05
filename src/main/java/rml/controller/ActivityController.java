package rml.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.Activity;
import rml.model.CrowdFunding;
import rml.model.Good;
import rml.model.User;
import rml.service.ActivityService;
import rml.service.CrowdFundingService;
import rml.service.GoodService;
import rml.util.MD5;
import rml.util.ReturnJson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by edward-echo on 2015/12/20.
 */

@Controller
@RequestMapping("/Activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @Autowired
    GoodService goodService;

    @Autowired
    CrowdFundingService crowdFundingService;

    @RequestMapping(value="/Goods",method = RequestMethod.GET)
    @ResponseBody
    public Object getGoods() {

        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(8000);
        returnJson.setReturnMessage("调用成功");

        String name = "2015他它众筹活动";
        Activity  activity = activityService.getActivityByName(name);
        List<Good> goods = null;
        try {
            String uuids = activity.getGoodIds();
            goods = goodService.getActivityGoods(uuids);
            System.err.println(goods.size());
            activity.setList(goods);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(8003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        returnJson.setReturnObject(activity);
        return returnJson;
    }


    @RequestMapping(value="/Activities",method = RequestMethod.GET)
    @ResponseBody
    public Object get() {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(10000);
        returnJson.setReturnMessage("调用成功");
        HashMap<String,List<List<String>>> map = new HashMap<String, List<List<String>>>();
        List<List<String>> temps = new ArrayList<List<String>>();
        try {
         List<Activity> activities =   activityService.getActivities();
         for(Activity activity:activities){
             List<String> values = new ArrayList<String>();
             values.add(activity.getName());
             SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             String dateString = formatter.format(activity.getStartTime());
             values.add(dateString);
             dateString = formatter.format(activity.getEndTime());
             values.add(dateString);
             values.add(new Float(activity.getGoodsTotalMoney()).toString());
             values.add(new Float(activity.getLevelThreeMoney()).toString());
             values.add(new Float(activity.getLevelTwoThreeMoney()).toString());
             values.add(new Float(activity.getLevelOneFxRate()).toString());
             values.add(new Float(activity.getLevelTwoFxRate()).toString());
             values.add(new Float(activity.getLevelThreeFxRate()).toString());
             values.add(new Float(activity.getLevelTwoMoney()).toString());
             values.add(new Integer(activity.getTotalJoinedPeople()).toString());
             values.add(new Float(activity.getTotalIncomeMoney()).toString());
             values.add("<input type='button' value='添加一级分销商' onclick="+"add('"+activity.getUuid()+"','"+activity.getName().replaceAll(" ","")+"')"+ " />");
             temps.add(values);
         }
          map.put("data", temps);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(9003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return map;
    }

    @RequestMapping(value="/Funding/Level/One",method = RequestMethod.GET)
    @ResponseBody
    public Object getLevelOne() {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(9000);
        returnJson.setReturnMessage("调用成功");
        HashMap<String,List<List<String>>> map = new HashMap<String, List<List<String>>>();
        List<List<String>> temps = new ArrayList<List<String>>();
        try {
            List<CrowdFunding> crowdFundings = crowdFundingService.getLevelOne();
            for(CrowdFunding crowdFunding:crowdFundings){
                List<String> values = new ArrayList<String>();
                values.add(crowdFunding.getName());
                Activity activity = activityService.getActivityByUUID(crowdFunding.getActivityId());
                values.add(activity.getName());
                if(crowdFunding.getLevel()==1){
                    values.add("一级经销商");
                }
                if(crowdFunding.getLevel()==2){
                    values.add("二级经销商");
                }
                if(crowdFunding.getLevel()==3){
                    values.add("三级经销商");
                }
                values.add(new Float(crowdFunding.getRasiedMoney()).toString());
                values.add(new Float(crowdFunding.getTotalRasiedPeople()).toString());
                values.add(new Float(crowdFunding.getMoneyRate()).toString());
                if(crowdFunding.getType()==1){
                    values.add("按比率结算");
                }
                if(crowdFunding.getType()==2){
                    values.add("按金额结算");
                }
                values.add(new Float(crowdFunding.getMoneySingle()).toString());
                values.add("<input type='button' value='添加二级分销商' onclick="+"add('"+activity.getUuid()+"','"+activity.getName().replaceAll(" ","")+"'"+ ",'"+crowdFunding.getUserId()+"')  />");
                temps.add(values);
            }
            map.put("data",temps);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(11003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return map;
    }


    @RequestMapping(value="/Funding/Level/Two",method = RequestMethod.GET)
    @ResponseBody
    public Object getLevelTwo() {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(33000);
        returnJson.setReturnMessage("调用成功");
        HashMap<String,List<List<String>>> map = new HashMap<String, List<List<String>>>();
        List<List<String>> temps = new ArrayList<List<String>>();
        try {
            List<CrowdFunding> crowdFundings = crowdFundingService.getLevelTwo();
            for(CrowdFunding crowdFunding:crowdFundings){
                List<String> values = new ArrayList<String>();
                values.add(crowdFunding.getName());
                Activity activity = activityService.getActivityByUUID(crowdFunding.getActivityId());
                values.add(activity.getName());
                if(crowdFunding.getLevel()==1){
                    values.add("一级经销商");
                }
                if(crowdFunding.getLevel()==2){
                    values.add("二级经销商");
                }
                if(crowdFunding.getLevel()==3){
                    values.add("三级经销商");
                }
                values.add(new Float(crowdFunding.getRasiedMoney()).toString());
                values.add(new Float(crowdFunding.getTotalRasiedPeople()).toString());
                values.add(new Float(crowdFunding.getMoneyRate()).toString());
                if(crowdFunding.getType()==1){
                    values.add("按比率结算");
                }
                if(crowdFunding.getType()==2){
                    values.add("按金额结算");
                }
                values.add(new Float(crowdFunding.getMoneySingle()).toString());
                values.add("<input type='button' value='添加三级分销商' onclick="+"add('"+crowdFunding.getUserId()+"','"+crowdFunding.getName().replaceAll(" ", "")+"')"+ " />");
                temps.add(values);
            }
            map.put("data",temps);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(11003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return map;
    }

    @RequestMapping(value="/Funding/Level/Three",method = RequestMethod.GET)
    @ResponseBody
    public Object getLevelThree() {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(33000);
        returnJson.setReturnMessage("调用成功");
        HashMap<String,List<List<String>>> map = new HashMap<String, List<List<String>>>();
        List<List<String>> temps = new ArrayList<List<String>>();
        try {
            List<CrowdFunding> crowdFundings = crowdFundingService.getLevelThree();
            for(CrowdFunding crowdFunding:crowdFundings){
                List<String> values = new ArrayList<String>();
                values.add(crowdFunding.getName());
                Activity activity = activityService.getActivityByUUID(crowdFunding.getActivityId());
                values.add(activity.getName());
                if(crowdFunding.getLevel()==1){
                    values.add("一级经销商");
                }
                if(crowdFunding.getLevel()==2){
                    values.add("二级经销商");
                }
                if(crowdFunding.getLevel()==3){
                    values.add("三级经销商");
                }
                values.add(new Float(crowdFunding.getRasiedMoney()).toString());
                values.add(new Float(crowdFunding.getTotalRasiedPeople()).toString());
                values.add(new Float(crowdFunding.getMoneyRate()).toString());
                if(crowdFunding.getType()==1){
                    values.add("按比率结算");
                }
                if(crowdFunding.getType()==2){
                    values.add("按金额结算");
                }
                values.add(new Float(crowdFunding.getMoneySingle()).toString());
                temps.add(values);
            }
            map.put("data",temps);
        }catch(Exception ex){
            ex.printStackTrace();
            returnJson.setErrorCode(11003);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
        }
        return map;
    }

    public static void main(String[]args){
        System.err.print(MD5.GetMD5Code("T4PKeE4san"+"123456"));
    }
}
