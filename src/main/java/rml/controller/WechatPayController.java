package rml.controller;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import rml.model.Order;
import rml.model.WechatPayResult;
import rml.service.OrderService;
import rml.service.WechatPayResultService;
import rml.util.GetWxOrderno;
import rml.util.RequestHandler;
import rml.util.ReturnJson;
import rml.util.Sha1Util;
import rml.util.TenpayUtil;

/**  
 * @author yeshenghong
 * @version ：2016-6-9 上午11:46:48  
 */  
@Controller("wechatPayController")  
@RequestMapping("/wechatpay")  
public class WechatPayController {  
	@Autowired
	private OrderService orderService;
	@Autowired
	private WechatPayResultService wechatPayResultService;
	
    Logger logger  =   Logger.getLogger(this.getClass());

	//商户相关资料   
	public static final String appid = "wxf78e3f615d875251";//在微信开发平台登记的app应用  
	public static final String appsecret = "a1bd2a33d99f64be4638fcb6e21e304f";  
	public static final String partner = "1336678701";//商户号  
	public static final String partnerkey ="hTc8o0xZpy2v48q9XL1l6wq2rEc5x3m0";//不是商户登录密码，是商户在微信平台设置的32位长度的api秘钥，  
	public static final String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";  
	public static final String notify_url = "http://fw.tata168.com/tata/wechatpay/notify.html";//异步通知地址  

	@RequestMapping(value = "/topay")   
	@ResponseBody 
	public Object topay(HttpServletRequest request,HttpServletResponse response) throws Exception{ 
		ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(7000);
        returnJson.setReturnMessage("调用成功支付");
	    returnJson.setServerStatus(0);
	    request.setCharacterEncoding("UTF-8");  
	    response.setCharacterEncoding("UTF-8");  
	    response.setContentType("text/html;charset=UTF-8");  
	    String orderId= request.getParameter("orderId");  
	    JSONObject retMsgJson=new JSONObject();  
	    if(orderId==null){  
	        returnJson.setErrorCode(7001);
            returnJson.setReturnMessage("传入参数为空");
            returnJson.setServerStatus(1);
            return returnJson;
	    }  
	    Order order = orderService.getOrder(orderId);
	    if(order==null){  
	        returnJson.setErrorCode(7001);
            returnJson.setReturnMessage("查询不到对应的订单");
            returnJson.setServerStatus(1);
            return returnJson;
	    }
	    String money = order.getActureMoney()+"";//获取订单金额  
	    //金额转化为分为单位  
	    float sessionmoney = Float.parseFloat(money);  
	    String finalmoney = String.format("%.2f", sessionmoney);  
	    finalmoney = finalmoney.replace(".", "");  
	    String currTime = TenpayUtil.getCurrTime();  
	    //8位日期  
	    String strTime = currTime.substring(8, currTime.length());  
	    //四位随机数  
	    String strRandom = TenpayUtil.buildRandom(4) + "";  
	    //10位序列号,可以自行调整。  
	    String strReq = strTime + strRandom;          
	    //商户号  
	    String mch_id = partner;  
	    //子商户号  非必输  
	    //String sub_mch_id="";  
	    //设备号   非必输  
	    String device_info="";  
	    //随机数   
	    String nonce_str = strReq;  
	    String body = order.getProductName()==null?"未知明细":order.getProductName();  
	    //附加数据  
	    String attach = order.getUserId();  
	    //商户订单号  
	    String out_trade_no = orderId;//订单编号
	    int intMoney = Integer.parseInt(finalmoney);              
	    //总金额以分为单位，不带小数点  
	    String total_fee = String.valueOf(intMoney);  
	    //订单生成的机器 IP  
	    String spbill_create_ip = request.getRemoteAddr();  
	    String trade_type = "APP";//app支付必须填写为APP  
	    //对以下字段进行签名  
	    SortedMap<String, String> packageParams = new TreeMap<String, String>();  
	    packageParams.put("appid", appid);    
	    packageParams.put("attach", attach);   
	    packageParams.put("body", body);    
	    packageParams.put("mch_id", mch_id);      
	    packageParams.put("nonce_str", nonce_str);    
	    packageParams.put("notify_url", notify_url);    
	    packageParams.put("out_trade_no", out_trade_no);      
	    packageParams.put("spbill_create_ip", spbill_create_ip);   
	    packageParams.put("total_fee", total_fee);  
	    packageParams.put("trade_type", trade_type);    
	    RequestHandler reqHandler = new RequestHandler(request, response);  
	    reqHandler.init(appid, appsecret, partnerkey);        
	    String sign = reqHandler.createSign(packageParams);//获取签名  
	    String xml="<xml>"+  
	            "<appid>"+appid+"</appid>"+  
	            "<attach>"+attach+"</attach>"+  
	            "<body><![CDATA["+body+"]]></body>"+  
	            "<mch_id>"+mch_id+"</mch_id>"+  
	            "<nonce_str>"+nonce_str+"</nonce_str>"+  
	            "<notify_url>"+notify_url+"</notify_url>"+  
	            "<out_trade_no>"+out_trade_no+"</out_trade_no>"+  
	            "<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+  
	            "<total_fee>"+total_fee+"</total_fee>"+  
	            "<trade_type>"+trade_type+"</trade_type>"+  
	            "<sign>"+sign+"</sign>"+  
	            "</xml>"; 
	    String allParameters = "";
		try {
			allParameters =  reqHandler.genPackage(packageParams);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJson.setErrorCode(9005);
            returnJson.setReturnMessage("生成签名异常");
            returnJson.setServerStatus(2);
            return returnJson;
		}
	    String prepay_id="";  
	    try {  
	        prepay_id = GetWxOrderno.getPayNo(createOrderURL, xml);  
	        if(prepay_id.equals("")){  
	        	returnJson.setErrorCode(1004);
	            returnJson.setReturnMessage("获取预支付ID异常");
	            returnJson.setServerStatus(2);
		        return returnJson;  
	         }  
	    } catch (Exception e1) {  
	        // TODO Auto-generated catch block  
	        e1.printStackTrace();  
	        returnJson.setErrorCode(9005);
            returnJson.setReturnMessage("服务器异常");
            returnJson.setServerStatus(2);
            return returnJson;
	     }  
        //获取到prepayid后对以下字段进行签名最终发送给app  
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();  
	    String timestamp = Sha1Util.getTimeStamp();  
	    finalpackage.put("appid", appid);    
	    finalpackage.put("timestamp", timestamp);    
	    finalpackage.put("noncestr", nonce_str);    
	    finalpackage.put("partnerid", partner);   
	    finalpackage.put("package", "Sign=WXPay");                
	    finalpackage.put("prepayid", prepay_id);    
	    String finalsign = reqHandler.createSign(finalpackage);  
        retMsgJson.put("msg", "ok");  
        retMsgJson.put("appid", appid);  
        retMsgJson.put("timestamp", timestamp);  
        retMsgJson.put("noncestr", nonce_str);  
        retMsgJson.put("partnerid", partner);  
        retMsgJson.put("prepayid", prepay_id);  
        retMsgJson.put("package", "Sign=WXPay");  
        retMsgJson.put("sign", finalsign);  
        returnJson.setReturnObject(retMsgJson);
        returnJson.setReturnValue(retMsgJson.toString());
        return returnJson;
	}  
	
	//微信异步通知  
	@RequestMapping(value = "/notify")   
	@ResponseBody 
	public void notify(HttpServletRequest request,HttpServletResponse response){  
	    try
	    {
	    	request.setCharacterEncoding("UTF-8");  
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/html;charset=UTF-8");  
		    response.setHeader("Access-Control-Allow-Origin", "*");  
		    InputStream in=request.getInputStream();  
		    ByteArrayOutputStream out = new ByteArrayOutputStream();  
		    byte[] buffer =new byte[1024];  
		    int len=0;  
		    while((len=in.read(buffer))!=-1){  
		        out.write(buffer, 0, len);  
		    }  
		    out.close();  
		    in.close();  
		    String msgxml=new String(out.toByteArray(),"utf-8");//xml数据  
	//	    msgxml =  "<xml><appid><![CDATA[wx2421b1c4370ec43b]]></appid>" +
	//				  "<attach><![CDATA[支付测试]]></attach>" +
	//				  "<bank_type><![CDATA[CFT]]></bank_type>" +
	//				  "<fee_type><![CDATA[CNY]]></fee_type>" +
	//				  "<is_subscribe><![CDATA[Y]]></is_subscribe>" +
	//				  "<mch_id><![CDATA[10000100]]></mch_id>" +
	//				  "<nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>" +
	//				  "<openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>" +
	//				  "<out_trade_no><![CDATA[20160512010920936137]]></out_trade_no>" +
	//				  "<result_code><![CDATA[SUCCESS]]></result_code>" +
	//				  "<return_code><![CDATA[SUCCESS]]></return_code>" +
	//				  "<sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>" +
	//				  "<sub_mch_id><![CDATA[10000100]]></sub_mch_id>" +
	//				  "<time_end><![CDATA[20140903131540]]></time_end>" +
	//				  "<total_fee>1</total_fee>" +
	//				  "<trade_type><![CDATA[JSAPI]]></trade_type>" +
	//				  "<transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>" +
	//				  "</xml>";
		    logger.info(msgxml);
		    Map map =  GetWxOrderno.doXMLParse(msgxml);  
		    String result_code=(String) map.get("result_code");  
		    String out_trade_no  = (String) map.get("out_trade_no");  
		    String total_fee  = (String) map.get("total_fee");  
		    String sign  = (String) map.get("sign");  
		    Double amount=new Double(total_fee)/100;//获取订单金额  
		    String attach= (String) map.get("attach");  
		    String sn = out_trade_no;//获取订单编号  
		    Order order = orderService.getOrder(sn);  
		    if(result_code.equals("SUCCESS")&&order!=null){  
		       //验证签名  
		        float sessionmoney = Float.parseFloat(order.getActureMoney()+"");  
		        String finalmoney = String.format("%.2f", sessionmoney);  
		        finalmoney = finalmoney.replace(".", "");  
		        int intMoney = Integer.parseInt(finalmoney);              
		        //总金额以分为单位，不带小数点  
		        String order_total_fee = String.valueOf(intMoney);  
		        String fee_type  = (String) map.get("fee_type");  
		        String bank_type  = (String) map.get("bank_type");  
		        String cash_fee  = (String) map.get("cash_fee");  
		        String is_subscribe  = (String) map.get("is_subscribe");  
		        String nonce_str  = (String) map.get("nonce_str");  
		        String openid  = (String) map.get("openid");  
		        String return_code  = (String) map.get("return_code");  
		        String sub_mch_id  = (String) map.get("sub_mch_id");  
		        String time_end  = (String) map.get("time_end");  
		        String trade_type  = (String) map.get("trade_type");  
		        String transaction_id  = (String) map.get("transaction_id");  
	            //需要对以下字段进行签名  
		        SortedMap<String, String> packageParams = new TreeMap<String, String>();  
		        packageParams.put("appid", appid);    
		        packageParams.put("attach", order.getUserId().toString()); //用自己系统的数据：会员id  
		        packageParams.put("bank_type", bank_type);    
		        packageParams.put("cash_fee", cash_fee);    
		        packageParams.put("fee_type", fee_type);      
		        packageParams.put("is_subscribe", is_subscribe);    
		        packageParams.put("mch_id", partner);    
		        packageParams.put("nonce_str", nonce_str);        
		        packageParams.put("openid", openid);   
		        packageParams.put("out_trade_no", out_trade_no);  
		        packageParams.put("result_code", result_code);    
		        packageParams.put("return_code", return_code);        
		        packageParams.put("sub_mch_id", sub_mch_id);   
		        packageParams.put("time_end", time_end);  
		        packageParams.put("total_fee", order_total_fee);    //用自己系统的数据：订单金额  
		        packageParams.put("trade_type", trade_type);   
		        packageParams.put("transaction_id", transaction_id);  
		        WechatPayResult wechatPayResult = new WechatPayResult();
		        wechatPayResult.setAppid(appid);
		        wechatPayResult.setAttach(attach);
		        wechatPayResult.setBankType(bank_type);
		        wechatPayResult.setCashFee(cash_fee==null?null:Integer.parseInt(cash_fee));
		        wechatPayResult.setCashFeeType((String)map.get("cash_fee_type"));
		        wechatPayResult.setCouponCount(map.get("cash_fee_type")==null?null:(Integer)map.get("cash_fee_type"));
		        wechatPayResult.setCouponFee(null);
		        wechatPayResult.setCouponFeeSingle(null);
		        wechatPayResult.setCouponId(null);
		        wechatPayResult.setCreateTime(new Date());
		        wechatPayResult.setDeviceInfo(null);
		        wechatPayResult.setErrCode(null);
		        wechatPayResult.setErrCodeDes(null);
		        wechatPayResult.setFeeType(null);
		        wechatPayResult.setMchId(partner);
		        wechatPayResult.setNonceStr(nonce_str);
		        wechatPayResult.setOpenid(openid);
		        wechatPayResult.setOutTradeNo(out_trade_no);
		        wechatPayResult.setResultCode(result_code);
		        wechatPayResult.setSign(sign);
		        wechatPayResult.setSubscribe(is_subscribe.equals("Y")?true:false);
		        wechatPayResult.setTimeEnd(time_end);
		        wechatPayResult.setTotalFee(order_total_fee);
		        wechatPayResult.setTradeType(trade_type);
		        wechatPayResult.setTransactionId(transaction_id);
		        wechatPayResultService.insert(wechatPayResult);
		        logger.info("将回调信息存入数据库！");
		        RequestHandler reqHandler = new RequestHandler(request, response);  
		        reqHandler.init(appid, appsecret, partnerkey);        
		        String endsign = reqHandler.createSign(packageParams);  
		        if(sign.equals(endsign)){//验证签名是否正确  官方签名工具地址：https://pay.weixin.qq.com/wiki/tools/signverify/   
		            if(return_code.equals("订单没有支付")){  
		                try{  
			                //处理自己的业务逻辑  
			                response.getWriter().write(setXml("SUCCESS", "OK"));    //告诉微信已经收到通知了  
		                }catch(Exception e){  
		                    System.out.println("微信支付异步通知异常");  
		                    logger.info("微信支付异步通知异常：" + e.getMessage());
		                }  
		            }else if(return_code.equals("订单支付了")){  
		                response.getWriter().write(setXml("SUCCESS", "OK"));    //告诉微信已经收到通知了  
		            }  
		      
		        }  
		    }  
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    	logger.info("执行回调方法异常:" + e.getMessage());
	    }
    }  
	
    public static String setXml(String return_code,String return_msg){  
        return "<xml><return_code><![CDATA["+return_code+"]]></return_code><return_msg><![CDATA["+return_msg+"]]></return_msg></xml>";  
	}  
}  