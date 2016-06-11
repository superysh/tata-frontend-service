package rml.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**  
 * @author yeshenghong
 * @version ：2016-6-9 上午11:46:48  
 */  
@Controller("wechatPayTestController")  
public class WxPayTestController {  
	@RequestMapping("/wxpaytest")
	public String wxpaytest(HttpServletRequest request,HttpServletResponse response) {
		return "/wxpaytest";
	}
	
}  