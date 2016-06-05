package rml.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.*;
import rml.service.*;
import rml.util.EmojiFilter;
import rml.util.MD5;
import rml.util.ReturnJson;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/User")
public class UserController {

	private static final  Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private CrowdFundingService crowdFundingService;

	@Autowired
	private ValidCodeService validCodeService;

	@Autowired
	private VerifyCodeService verifyCodeService;

	private UserService userService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private OrderService orderService;

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService UserService) {
		this.userService = UserService;
	}


	@RequestMapping(value="/User",method = RequestMethod.POST)
	@ResponseBody
	public Object addUser11(@RequestBody User user) {

		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(1000);
		returnJson.setReturnMessage("调用成功"+user.toString());
		returnJson.setServerStatus(0);


		if(StringUtils.isEmpty(user.getPassword())||StringUtils.isEmpty(user.getMobile())||StringUtils.isEmpty(user.getValidCode())){

			returnJson.setErrorCode(1001);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result1 = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result1.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		ValidCode code = new ValidCode();
		code.setValidCode(user.getValidCode());
		code.setMobile(user.getMobile());


		int flag = validCodeService.checkValidCode(code);
		if(flag==1){
			returnJson.setErrorCode(1002);
			returnJson.setReturnMessage("验证码错误，请重新输入" + code.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		User value = userService.selectByMobile(user.getMobile());
		if(value!=null){
			returnJson.setErrorCode(1003);
			returnJson.setReturnMessage("该手机号已存在，请勿重复注册用户" + code.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			user.setLevel(1);
			userService.insert(user);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(1004);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnObject(user);
		returnJson.setReturnValue(user.getToken());
		return returnJson;
	}




	@RequestMapping(value="/User/New",method = RequestMethod.POST)
	@ResponseBody
	public Object addUser(@RequestBody User user) {

	ReturnJson returnJson = new ReturnJson();
	returnJson.setErrorCode(1000);
	returnJson.setReturnMessage("调用成功"+user.toString());
	returnJson.setServerStatus(0);


	if(StringUtils.isEmpty(user.getPassword())||StringUtils.isEmpty(user.getAccountName())||StringUtils.isEmpty(user.getSessionId())||StringUtils.isEmpty(user.getVerifyCode())){

		returnJson.setErrorCode(1001);
		returnJson.setReturnMessage("传入参数为空" + user.toString());
		returnJson.setServerStatus(1);
		return returnJson;
		}
		String result1 = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result1.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		VerifyCode code = new VerifyCode();
		code.setSessionId(user.getSessionId());
		code.setVerifyCode(user.getVerifyCode());
		code = verifyCodeService.checkVerifyCode(code);
		if(code==null){
			returnJson.setErrorCode(1005);
			returnJson.setReturnMessage("验证码错误，请重新输入");
			returnJson.setServerStatus(1);
			return returnJson;
		}
		if(user.getAccountName().length()<4||user.getAccountName().length()>10){
			returnJson.setErrorCode(1002);
			returnJson.setReturnMessage("您的账号长度有误,请输入长度为4-10位的账号");
			returnJson.setServerStatus(1);
			return returnJson;
		}
		User value = userService.selectByAccount(user.getAccountName());
		if(value!=null){
			returnJson.setErrorCode(1003);
			returnJson.setReturnMessage("该账号已存在，请更换账号后重试");
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			user.setLevel(1);
			userService.insert(user);
			verifyCodeService.deleteVerifyCode(code.getUuid());
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(1004);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnObject(user);
		returnJson.setReturnValue(user.getToken());
		return returnJson;
	}


	@RequestMapping(value="/User/Third",method = RequestMethod.POST)
	@ResponseBody
	public Object addUserThird(@RequestBody User user) {

		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(1000);
		returnJson.setReturnMessage("调用成功"+user.toString());
		returnJson.setServerStatus(0);
		if(StringUtils.isEmpty(user.getNickName())||StringUtils.isEmpty(user.getThirdPartUuid())||user.getChannel()==0||StringUtils.isEmpty(user.getIcon())){

			returnJson.setErrorCode(1001);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result1 = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result1.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String name  = EmojiFilter.filterEmoji(user.getNickName());
		user.setNickName(name);
		logger.info("用户传入thrid part uuid为:" + user.getThirdPartUuid());
		logger.info("用户传入channel为:"+user.getChannel());
		User user1 = null;
		if(user.getChannel()==1){
			user1= userService.selectThirdQQ(user.getThirdPartUuid());
		}
		if(user.getChannel()==2){
			user1= userService.selectThirdWeixin(user.getThirdPartUuid());
		}
		if(user.getChannel()==3){
			user1= userService.selectThirdWeibo(user.getThirdPartUuid());
		}
		if(user1!=null) {
			userService.updateThird(user);
			returnJson.setReturnObject(user1);
			if (user1.getQqId() == null) {
				user1.setQQBind(false);
			} else {
				user1.setQQBind(true);
			}
			if (user1.getWeiboId() == null) {
				user1.setWeiboBind(false);
			} else {
				user1.setWeiboBind(true);
			}
			if (user1.getWeixinId() == null) {
				user1.setWeinxinBind(false);
			} else {
				user1.setWeinxinBind(true);
			}
			returnJson.setReturnValue(user1.getUuid());
			}else {
				user1 = userService.getThird(user.getThirdPartUuid());
			if (user1 != null) {
				userService.updateThird(user1);
				returnJson.setReturnObject(user1);
				return returnJson;
			}else{
				user.setCreateTime(new Date());
				user.setUpdateTime(new Date());
				user.setLevel(1);
				user.setUuid(UUID.randomUUID().toString());
				userService.insertThird(user);
				returnJson.setReturnObject(user);
				returnJson.setReturnValue(user.getUuid());
			}
		}
		return returnJson;
	}


	@RequestMapping(value="/User/Mobile",method = RequestMethod.POST)
	@ResponseBody
	public Object moveMobile(@RequestBody User user) {

		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(26000);
		returnJson.setReturnMessage("调用成功"+user.toString());
		returnJson.setServerStatus(0);


		if(StringUtils.isEmpty(user.getMobile())||StringUtils.isEmpty(user.getUuid())||StringUtils.isEmpty(user.getValidCode())){
			returnJson.setErrorCode(26001);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result1 = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result1.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		ValidCode code = new ValidCode();
		code.setValidCode(user.getValidCode());
		code.setMobile(user.getMobile());


		int flag = validCodeService.checkValidCode(code);
		if(flag==1){
			returnJson.setErrorCode(26005);
			returnJson.setReturnMessage(" 验证码错误，请重新输入" + code.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
        User user1 = userService.selectByMobile(user.getMobile());
		User user2 = userService.selectByPrimaryKey(user.getUuid());
		if(user1!=null){
			returnJson.setErrorCode(26006);
			returnJson.setReturnMessage("该手机号已被其他账号使用，请使用其他手机号" + code.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		user2.setMobile(user.getMobile());
		userService.updateMobile(user2);
		return returnJson;
	}

	@RequestMapping(value="/User",method = RequestMethod.GET)
	@ResponseBody
	public Object getUser(User user) {

		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(2000);
		returnJson.setReturnMessage("调用成功" + user.toString());
		returnJson.setServerStatus(0);


		if(StringUtils.isEmpty(user.getPassword())||StringUtils.isEmpty(user.getMobile())){

			returnJson.setErrorCode(2001);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result1 = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result1.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		User result = null;
        User user1 = userService.selectByMobile(user.getMobile());
        user.setValid(user1.getValid());
		try {
			result = userService.checkPassword(user);
			if(result==null){
				returnJson.setErrorCode(2002);
				returnJson.setReturnMessage("用户名或者密码错误");
				returnJson.setServerStatus(1);
				return returnJson;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(2003);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnObject(result);
		returnJson.setReturnValue(result.getToken());
		return returnJson;
	}

	@RequestMapping(value="/User/New",method = RequestMethod.GET)
	@ResponseBody
	public Object getUser1(User user) {

		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(18000);
		returnJson.setReturnMessage("调用成功" + user.toString());
		returnJson.setServerStatus(0);


		if(StringUtils.isEmpty(user.getPassword())||StringUtils.isEmpty(user.getKeyWord())){

			returnJson.setErrorCode(18001);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result1 = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result1.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		User result = null;
		User user1 = userService.selectByMobile(user.getKeyWord());
		User user2 = userService.selectByAccount(user.getKeyWord());
		if(user1!=null) {
			user.setValid(user1.getValid());
		}
		if(user2!=null){
			user.setValid(user2.getValid());
		}
		try {
			if(user2==null&&user1==null){
				returnJson.setErrorCode(18004);
				returnJson.setReturnMessage("用户名或者密码错误");
				returnJson.setServerStatus(1);
				return returnJson;
			}
			if(user2!=null) {
				user.setAccountName(user2.getAccountName());
				result = userService.checkPasswordAccount(user);
			}
			if(result==null){
				user.setMobile(user1.getMobile());
				result = userService.checkPasswordMobile(user);
			}
			if(result==null){
				returnJson.setErrorCode(18004);
				returnJson.setReturnMessage("用户名或者密码错误");
				returnJson.setServerStatus(1);
				return returnJson;
			}
			if(result.getQqId()==null||StringUtils.isEmpty(result.getQqId())){
				result.setQQBind(false);
			}else{
				result.setQQBind(true);
			}
			if(result.getWeiboId()==null||StringUtils.isEmpty(result.getWeiboId())){
				result.setWeiboBind(false);
			}else{
				result.setWeiboBind(true);
			}
			if(result.getWeixinId()==null||StringUtils.isEmpty(result.getWeixinId())){
				result.setWeinxinBind(false);
			}else{
				result.setWeinxinBind(true);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(18003);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnObject(result);
		return returnJson;
	}


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
		String result = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
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



	@RequestMapping(value="/Search",method = RequestMethod.GET)
	@ResponseBody
	public Object search(User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(31000);
		returnJson.setReturnMessage("调用成功" + user.toString());
		returnJson.setServerStatus(0);
		List<User> results = null;
		try {
			results = userService.selectByNickPhone(user);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(2003);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnObject(results);
		return returnJson;
	}

	@ RequestMapping(value="/User/Up",method = RequestMethod.GET)
	@ResponseBody
	public Object getUserTypes(User user) {

		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(3000);
		returnJson.setReturnMessage("调用成功" + user.toString());
		returnJson.setServerStatus(0);


		if(StringUtils.isEmpty(user.getPassword())||StringUtils.isEmpty(user.getMobile())){

			returnJson.setErrorCode(3001);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result1 = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result1.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		User result = null;
		try {
			result = userService.checkPassword(user);
			if(result==null){
				returnJson.setErrorCode(3002);
				returnJson.setReturnMessage("用户名或者密码错误");
				returnJson.setServerStatus(1);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(3003);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnObject(result);
		returnJson.setReturnValue(result.getToken());
		return returnJson;
	}



	@RequestMapping(value="/Email",method = RequestMethod.PUT)
	@ResponseBody
	public Object updateEmail(User user) {

		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(4000);
		returnJson.setReturnMessage("调用成功" + user.toString());
		returnJson.setServerStatus(0);


		if(StringUtils.isEmpty(user.getEmail())||StringUtils.isEmpty(user.getToken())){

			returnJson.setErrorCode(4001);
			returnJson.setReturnMessage("传入参数为空 " + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result1 = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result1.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		User result = null;
		try {
			result = userService.selectByToken(user.getToken());
			if(result==null){
				returnJson.setErrorCode(4002);
				returnJson.setReturnMessage("该token不存在 "+user.toString());
				returnJson.setServerStatus(1);
				return returnJson;
			}
			if(!isEmail(user.getEmail())){
				returnJson.setErrorCode(4003);
				returnJson.setReturnMessage("邮箱地址非法，请重新输入 "+user.toString());
				returnJson.setServerStatus(1);
				return returnJson;
			}

			userService.updateEmail(user);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(4003);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnValue(user.getToken());
		return returnJson;
	}

	public boolean isEmail(String email){
		if (null==email || "".equals(email)) return false;
//        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
		Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
		Matcher m = p.matcher(email);
		return m.matches();
	}


	@RequestMapping(value="/ValidCode",method = RequestMethod.GET)
	@ResponseBody
	public Object isValid(User user) {

		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(10000);
		returnJson.setReturnMessage("调用成功"+user.toString());
		returnJson.setServerStatus(0);


		if(StringUtils.isEmpty(user.getMobile())||StringUtils.isEmpty(user.getValidCode())){
			returnJson.setErrorCode(10001);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		ValidCode code = new ValidCode();
		code.setValidCode(user.getValidCode());
		code.setMobile(user.getMobile());

		try {
			int flag = validCodeService.checkValidCode(code);
			if (flag == 1) {
				returnJson.setErrorCode(10002);
				returnJson.setReturnMessage("验证码错误，请重新输入" + code.toString());
				returnJson.setServerStatus(1);
				return returnJson;
			}
            User user1 = userService.selectByMobile(user.getMobile());
            returnJson.setReturnObject(user1);
        }catch (Exception e){
			e.printStackTrace();
			returnJson.setErrorCode(10003);
			returnJson.setReturnMessage("服务器错误" + code.toString());
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnValue(user.getToken());
		return returnJson;
	}


	@RequestMapping(value="/Pay/Password",method = RequestMethod.PUT)
	@ResponseBody
	public Object updatePwd(@RequestBody User user) {

		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(11000);
		returnJson.setReturnMessage("调用成功" + user.toString());
		returnJson.setServerStatus(0);


		if(StringUtils.isEmpty(user.getUuid())||StringUtils.isEmpty(user.getUuid())||StringUtils.isEmpty(user.getPayPassword())||StringUtils.isEmpty(user.getValidCode())){
			returnJson.setErrorCode(11001);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			ValidCode code = new ValidCode();
			code.setValidCode(user.getValidCode());
			code.setMobile(user.getMobile());
			int flag = validCodeService.checkValidCode(code);
			if (flag == 1) {
				returnJson.setErrorCode(10002);
				returnJson.setReturnMessage("验证码错误，请重新输入" + code.toString());
				returnJson.setServerStatus(1);
				return returnJson;
			}
			userService.updatePayPass(user);
		}catch (Exception e){
			e.printStackTrace();
			returnJson.setErrorCode(10003);
			returnJson.setReturnMessage("服务器错误" + user.toString());
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnObject(user);
		returnJson.setReturnValue(user.getToken());
		return returnJson;
	}

	@RequestMapping(value="/Password",method = RequestMethod.PUT)
	@ResponseBody
	public Object updatePayPwd(@RequestBody User user) {

		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(12000);
		returnJson.setReturnMessage("调用成功"+user.toString());
		returnJson.setServerStatus(0);


		if(StringUtils.isEmpty(user.getUuid())||StringUtils.isEmpty(user.getPassword())){
			returnJson.setErrorCode(12001);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
            User user1 = userService.selectByPrimaryKey(user.getUuid());
			user.setUuid(user1.getUuid());
			user.setValid(user1.getValid());
            userService.updatePasswordTwo(user);

		}catch (Exception e){
			e.printStackTrace();
			returnJson.setErrorCode(12003);
			returnJson.setReturnMessage("服务器错误" + user.toString());
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnObject(user);
		returnJson.setReturnValue(user.getUuid());
		return returnJson;
	}


	@RequestMapping(value="/Password/Change",method = RequestMethod.PUT)
	@ResponseBody
	public Object updatePayPwd1(@RequestBody User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(12000);
		returnJson.setReturnMessage("调用成功"+user.toString());
		returnJson.setServerStatus(0);


		if(StringUtils.isEmpty(user.getValidCode())||StringUtils.isEmpty(user.getMobile())||StringUtils.isEmpty(user.getPassword())){
			returnJson.setErrorCode(12001);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			ValidCode validCode = new ValidCode();
			validCode.setMobile(user.getMobile());
			validCode.setValidCode(user.getValidCode());
			int flag = validCodeService.checkValidCode(validCode);
			if(flag==1){
				returnJson.setErrorCode(12002);
				returnJson.setReturnMessage("验证码错误，请重新输入");
				returnJson.setServerStatus(1);
				return returnJson;
			}
			User user1 = userService.selectByMobile(user.getMobile());
			if(user1==null){
				returnJson.setErrorCode(12003);
				returnJson.setReturnMessage("手机号不存在");
				returnJson.setServerStatus(1);
				return returnJson;
			}
			user.setUuid(user1.getUuid());
			user.setValid(user1.getValid());
			userService.updatePassword(user);
		}catch (Exception e){
			e.printStackTrace();
			returnJson.setErrorCode(12003);
			returnJson.setReturnMessage("服务器错误" + user.toString());
			returnJson.setServerStatus(2);
			return returnJson;
		}
		return returnJson;
	}


	@RequestMapping(value="/NickName",method = RequestMethod.PUT)
	@ResponseBody
	public Object nickName(@RequestBody User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(12000);
		returnJson.setReturnMessage("调用成功"+user.toString());
		returnJson.setServerStatus(0);


		if(StringUtils.isEmpty(user.getNickName())||StringUtils.isEmpty(user.getUuid())){
			returnJson.setErrorCode(12001);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			userService.updateNickName(user);
		}catch (Exception e){
			e.printStackTrace();
			returnJson.setErrorCode(12003);
			returnJson.setReturnMessage("服务器错误" + user.toString());
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnObject(user);
		return returnJson;
	}


	@RequestMapping(value="/Gender",method = RequestMethod.PUT)
	@ResponseBody
	public Object gender(@RequestBody User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(12000);
		returnJson.setReturnMessage("调用成功"+user.toString());
		returnJson.setServerStatus(0);


		if(user.getSex()==0||StringUtils.isEmpty(user.getUuid())){
			returnJson.setErrorCode(12001);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			userService.updateSex(user);
		}catch (Exception e){
			e.printStackTrace();
			returnJson.setErrorCode(12003);
			returnJson.setReturnMessage("服务器错误" + user.toString());
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnObject(user);
		return returnJson;
	}


	@RequestMapping(value="/Degree",method = RequestMethod.PUT)
	@ResponseBody
	public Object degree(@RequestBody User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(12000);
		returnJson.setReturnMessage("调用成功" + user.toString());
		returnJson.setServerStatus(0);


		if(user.getDegree()==0||StringUtils.isEmpty(user.getUuid())){
			returnJson.setErrorCode(12001);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			userService.updateDegree(user);
		}catch (Exception e){
			e.printStackTrace();
			returnJson.setErrorCode(12003);
			returnJson.setReturnMessage("服务器错误" + user.toString());
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnObject(user);
		return returnJson;
	}

	@RequestMapping(value="/birth",method = RequestMethod.PUT)
	@ResponseBody
	public Object birth(@RequestBody User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(12000);
		returnJson.setReturnMessage("调用成功"+user.toString());
		returnJson.setServerStatus(0);


		if(user.getBirthday()==null||StringUtils.isEmpty(user.getUuid())){
			returnJson.setErrorCode(12001);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			userService.updateBirth(user);
		}catch (Exception e){
			e.printStackTrace();
			returnJson.setErrorCode(12003);
			returnJson.setReturnMessage("服务器错误" + user.toString());
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnObject(user);
		return returnJson;
	}

	@RequestMapping(value="/Password/Change/Web",method = RequestMethod.PUT)
	@ResponseBody
	public Object updatePayPwd2(@RequestBody User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(12000);
		returnJson.setReturnMessage("调用成功" + user.toString());
		returnJson.setServerStatus(0);


		if(StringUtils.isEmpty(user.getOldPassword())||StringUtils.isEmpty(user.getUuid())||StringUtils.isEmpty(user.getPassword())){
			returnJson.setErrorCode(12001);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		if(user.getPassword().length()<6||user.getPassword().length()>20){
			returnJson.setErrorCode(12003);
			returnJson.setReturnMessage("密码格式错误" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			User user1 = userService.selectByPrimaryKey(user.getUuid());
			user1.setPassword(user.getOldPassword());
			user1 = userService.checkPasswordUUID(user1);
			if(user1==null){
				returnJson.setErrorCode(12002);
				returnJson.setReturnMessage("密码错误，请重新输入");
				returnJson.setServerStatus(1);
				return returnJson;
			}
			user1.setPassword(user.getPassword());
			userService.updatePasswordTwo(user1);
		}catch (Exception e){
			e.printStackTrace();
			returnJson.setErrorCode(12004);
			returnJson.setReturnMessage("服务器错误" + user.toString());
			returnJson.setServerStatus(2);
			return returnJson;
		}
		return returnJson;
	}

	@RequestMapping(value="/Users",method = RequestMethod.GET)
	@ResponseBody
	public Object getUsers() {

		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(2000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		List<List<String>> temps = new ArrayList<List<String>>();
		List<User> results = null;
		HashMap<String,List<List<String>>> map = new HashMap<String, List<List<String>>>();
		try {

			results = userService.getUsers();
			for(User user:results){
				List<String> values = new ArrayList<String>();
				if(StringUtils.isEmpty(user.getNickName())||user.getNickName()==null){
					values.add("无昵称");
				}else{
					values.add(user.getNickName());
				}
                if(StringUtils.isEmpty(user.getMobile())){
                    values.add("无手机号");
                }else{
                    values.add(user.getMobile());
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateString = formatter.format(user.getCreateTime());
				values.add(dateString);
				if(user.getLevel()==1){
					values.add("普通会员");
				}else if(user.getLevel()==2){
					values.add("vip会员");
				}
				if(user.getIdentification()==1){
					values.add("会员");
				}else if(user.getIdentification()==2){
					values.add("分销商");
				}
				if(user.getType()==1){
					values.add("手机注册");
				}else if(user.getType()==2){
					values.add("第三方登录");
				}
				if(user.getChannel()==1){
					values.add("QQ");
				}else if(user.getChannel()==2){
					values.add("微信");
				}else if(user.getChannel()==3) {
					values.add("微博");
				}else {
					values.add("手机号");
				}
				temps.add(values);
			}
			map.put("data", temps);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(2003);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnObject(temps);
		return map;
	}

	@RequestMapping(value="/Users/Date",method = RequestMethod.GET)
	@ResponseBody
	public Object getUsersDate() {

		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(2000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		List<List<String>> temps = new ArrayList<List<String>>();
		List<User> results = null;
		HashMap<String,List<List<String>>> map = new HashMap<String, List<List<String>>>();
		try {
			User user1 = new User();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(new Date());
			dateString = dateString+" 00:00:00";
			user1.setStartDate(dateString);
			user1.setEndDate(new Date().toLocaleString());
			results = userService.getUsersDate(user1);
            for(User user:results){
                List<String> values = new ArrayList<String>();
                values.add(user.getNickName());
                values.add(user.getMobile());
                String dateString1 = formatter.format(user.getCreateTime());
                values.add(dateString1);
                if(user.getLevel()==4){
                    values.add("注册会员");
                }else if(user.getLevel()==2){
                    values.add("vip会员");
                }
                if(user.getIdentification()==1){
                    values.add("会员");
                }else if(user.getIdentification()==2){
                    values.add("分销商");
                }
                if(user.getType()==1){
                    values.add("手机注册");
                }else if(user.getType()==2){
                    values.add("第三方登录");
                }
                if(user.getChannel()==1){
                    values.add("QQ");
                }else if(user.getChannel()==2){
                    values.add("微信");
                }else if(user.getChannel()==3) {
                    values.add("微博");
                }else {
                    values.add("手机号");
                }
                temps.add(values);
            }
			map.put("data", temps);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(2003);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnObject(temps);
		return map;
	}


	@RequestMapping(value="/Users/Date/Count",method = RequestMethod.GET)
	@ResponseBody
	public Object getUsersDateCount() {
		try {
			List<User> results = null;
			User user1 = new User();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(new Date());
			dateString = dateString+" 00:00:00";
			user1.setStartDate(dateString);
			user1.setEndDate(new Date().toLocaleString());
			results = userService.getUsersDate(user1);
			return new Integer(results.size());

		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null ;
	}


	@RequestMapping(value="/User/Level",method = RequestMethod.GET)
	@ResponseBody
	public Object updateTpe(User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(28000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		if(StringUtils.isEmpty(user.getUuid())){
			returnJson.setErrorCode(28001);
			returnJson.setReturnMessage("传入参数为空");
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			User user1 = new User();
			user1.setUuid(user.getUuid());
			user1.setLevel(2);
			userService.updateType(user1);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(28002);
			returnJson.setReturnMessage("服务器错误");
			returnJson.setServerStatus(1);
		}
		return returnJson ;
	}

	@RequestMapping(value="/User/Type",method = RequestMethod.PUT)
	@ResponseBody
	public Object updateType(@RequestBody User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(28000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		if(StringUtils.isEmpty(user.getUuid())||user.getLevel()==0){
			returnJson.setErrorCode(28001);
			returnJson.setReturnMessage("传入参数为空");
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			userService.updateUserLevel(user);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(28002);
			returnJson.setReturnMessage("服务器错误");
			returnJson.setServerStatus(2);
		}
		return returnJson ;
	}

	@RequestMapping(value="/User/Icon",method = RequestMethod.PUT)
	@ResponseBody
	public Object updateIcon(@RequestBody User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(28000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		if(StringUtils.isEmpty(user.getUuid())||StringUtils.isEmpty(user.getIcon())){
			returnJson.setErrorCode(28001);
			returnJson.setReturnMessage("传入参数为空");
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			userService.updateIcon(user);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(28002);
			returnJson.setReturnMessage("服务器错误");
			returnJson.setServerStatus(2);
		}
		return returnJson ;
	}

    @Autowired
    private  ShopService shopService;

	@RequestMapping(value="/Scan/Normal",method = RequestMethod.GET)
	@ResponseBody
	public Object scanIcon(User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(28000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		if(StringUtils.isEmpty(user.getShopId())||StringUtils.isEmpty(user.getUuid())||user.getMoney()==0){
			returnJson.setErrorCode(28001);
			returnJson.setReturnMessage("传入参数为空");
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
            Shop shop = shopService.getShopById(user.getShopId());
            if(shop==null){
                returnJson.setErrorCode(28002);
                returnJson.setReturnMessage("shopId不存在");
                returnJson.setServerStatus(1);
                return returnJson;
            }
			User user1 = userService.selectByPrimaryKey(user.getUuid());
            if(user1==null){
                returnJson.setErrorCode(28003);
                returnJson.setReturnMessage("userId不存在");
                returnJson.setServerStatus(1);
                return returnJson;
            }
			int originOfflineMoney = user1.getOfflineMoney();
			if(user.getMoney()>user1.getOfflineMoney()){
				int needPay = user.getMoney()-user1.getOfflineMoney();
				int rate = shop.getReturnRate();
				int payBack = (needPay*rate)/100;
				Order order1 = new Order();
				order1.setCreateTime(new Date());
				order1.setUuid(createOrderNo(formatDate()));
				order1.setActureMoney(payBack);
				order1.setTotalMoney(payBack);
				order1.setShopPayBack(payBack);
				order1.setUserId(user.getUuid());
				order1.setType(5);
				order1.setStatus(2);
				order1.setShopId(shop.getUuid());
				orderService.insertNormalUserNew(order1);
				order1.setNickName(user1.getNickName());
				order1.setOfflineMoney(originOfflineMoney);
				order1.setNeedPay(needPay);
				order1.setMoney(user.getMoney());
				Order order = new Order();
				order.setCreateTime(new Date());
				order.setUuid(createOrderNo(formatDate()));
				order.setActureMoney(user.getOfflineMoney());
				order.setTotalMoney(user.getOfflineMoney());
				order.setUserId(user.getUuid());
				order.setType(4);
				order.setShopId(shop.getUuid());
				order.setPid(order1.getUuid());
				order.setStatus(2);
				orderService.insertNormalUserNew(order);
				order1.setNickName(user1.getNickName());
				order1.setOfflineMoney(originOfflineMoney);
				order1.setNeedPay(needPay);
				order1.setMoney(user.getMoney());
				Order order4 = new Order();
				order4.setCreateTime(new Date());
				order4.setUuid(createOrderNo(formatDate()));
				order4.setActureMoney(needPay);
				order4.setTotalMoney(needPay);
				order4.setUserId(user.getUuid());
				order4.setType(6);
				order4.setShopId(shop.getUuid());
				order4.setPid(order1.getUuid());
				order4.setStatus(2);
				orderService.insertNormalUserNew(order4);
				order1.setNickName(user1.getNickName());
				order1.setOfflineMoney(originOfflineMoney);
				order1.setNeedPay(needPay);
				order1.setMoney(user.getMoney());
				Order order5 = new Order();
				order5.setCreateTime(new Date());
				order5.setUuid(createOrderNo(formatDate()));
				order5.setActureMoney(needPay);
				order5.setTotalMoney(needPay);
				order5.setUserId(user.getUuid());
				order5.setType(7);
				order5.setShopId(shop.getUuid());
				order5.setPid(order1.getUuid());
				order5.setStatus(2);
				orderService.insertNormalUserNew(order5);
				order1.setNickName(user1.getNickName());
				order1.setOfflineMoney(originOfflineMoney);
				order1.setNeedPay(needPay);
				order1.setMoney(user.getMoney());
				Order order6 = new Order();
				order6.setCreateTime(new Date());
				order6.setUuid(createOrderNo(formatDate()));
				order6.setActureMoney(user.getMoney());
				order6.setTotalMoney(user.getMoney());
				order6.setUserId(user.getUuid());
				order6.setType(8);
				order6.setShopId(shop.getUuid());
				order6.setPid(order1.getUuid());
				order6.setStatus(3);
				orderService.insertNormalUserNew(order6);
				returnJson.setErrorCode(28005);
				returnJson.setReturnMessage("用户线下币不够");
				returnJson.setReturnObject(order1);
			}else{
				user1.setOfflineMoney(user1.getOfflineMoney()-user.getMoney());
				userService.updateOfflineMoney(user1);
				Order order = new Order();
				order.setMoney(user.getMoney());
				order.setCreateTime(new Date());
				order.setUuid(createOrderNo(formatDate()));
				order.setActureMoney(user.getMoney());
				order.setTotalMoney(user.getMoney());
				order.setUserId(user.getUuid());
				order.setSeriesNo("199999");
				order.setPaidTime(new Date());
				order.setType(8);
				order.setStatus(3);
				order.setNickName(user1.getNickName());
				order.setShopName(shop.getName());
				order.setShopId(shop.getUuid());
				orderService.insertNormalUserNew(order);
				int shopRate = 100-shop.getReturnRate();
				int payBack = user.getMoney()*shopRate/100;
				shop.setOfflineMoney(user.getMoney()*shopRate/100);
				shopService.updateMoney(shop);
				Order order1 = new Order();
				order1.setMoney(user.getMoney());
				order1.setCreateTime(new Date());
				order1.setUuid(createOrderNo(formatDate()));
				order1.setActureMoney(payBack);
				order1.setTotalMoney(payBack);
				order1.setUserId(user.getUuid());
				order1.setSeriesNo("299999");
				order1.setPaidTime(new Date());
				order1.setType(6);
				order1.setPid(order.getUuid());
				order1.setStatus(3);
				order1.setNickName(user1.getNickName());
				order1.setShopName(shop.getName());
				order1.setShopId(shop.getUuid());
				orderService.insertNormalUserNew(order1);
				shop = shopService.getShopById(shop.getUuid());
				order.setTotalMoney(shop.getOfflineMoney());
				returnJson.setErrorCode(28006);
				returnJson.setReturnMessage("用户线下币足够");
				returnJson.setReturnObject(order);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(28004);
			returnJson.setReturnMessage("服务器错误");
			returnJson.setServerStatus(2);
		}
		return returnJson ;
	}



	@RequestMapping(value="/Scan/Vip",method = RequestMethod.GET)
	@ResponseBody
	public Object scanIconVip(User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(29000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		if(StringUtils.isEmpty(user.getShopId())||StringUtils.isEmpty(user.getUuid())||user.getMoney()==0){
			returnJson.setErrorCode(29001);
			returnJson.setReturnMessage("传入参数为空");
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			Shop shop = shopService.getShopById(user.getShopId());
			if(shop==null){
				returnJson.setErrorCode(29002);
				returnJson.setReturnMessage("shopId不存在");
				returnJson.setServerStatus(1);
				return returnJson;
			}
			String result = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
			if(!result.equals(user.getSecretKey())){
				returnJson.setErrorCode(99999);
				returnJson.setReturnMessage("密钥无效" + user.toString());
				returnJson.setServerStatus(1);
				return returnJson;
			}
			User user1 = userService.selectByPrimaryKey(user.getUuid());
			if(user1==null){
				returnJson.setErrorCode(29003);
				returnJson.setReturnMessage("userId不存在");
				returnJson.setServerStatus(1);
				return returnJson;
			}
			if(user.getMoney()>user1.getOnlineMoney()){
				returnJson.setErrorCode(29005);
				returnJson.setReturnMessage("用户线上币不够，请充值,用户线上币金额为"+new Float(user1.getOnlineMoney())/100+"元,需支付的金额为"+new Float(user.getMoney())/100+"元");
				returnJson.setServerStatus(1);
				return returnJson;
			}else{
				user1.setOnlineMoney(user1.getOnlineMoney()-user.getMoney());
			}
			userService.updateOnlineMoney(user1);
			Order order = new Order();
			order.setMoney(user.getMoney());
			order.setCreateTime(new Date());
			order.setUuid(createOrderNo(formatDate()));
			order.setActureMoney(user.getMoney());
			order.setTotalMoney(user.getMoney());
			order.setUserId(user.getUuid());
			order.setType(8);
			order.setStatus(3);
			order.setShopId(shop.getUuid());
			order.setNickName(user1.getNickName());
			orderService.insertNormalUserNew(order);
			returnJson.setErrorCode(29006);
			returnJson.setReturnObject(order);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(28004);
			returnJson.setReturnMessage("服务器错误");
			returnJson.setServerStatus(2);
		}
		return returnJson ;
	}





	@RequestMapping(value="/User/Money",method = RequestMethod.GET)
	@ResponseBody
	public Object getMoney(User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(28000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		try {
			String result = MD5.GetMD5Code(user.getRandomKey() + "at^&*ta");
			if(!result.equals(user.getSecretKey())){
				returnJson.setErrorCode(99999);
				returnJson.setReturnMessage("密钥无效" + user.toString());
				returnJson.setServerStatus(1);
				return returnJson;
			}
			User user1 = userService.selectByPrimaryKey(user.getUserId());
			UserMoney userMoney = new UserMoney();
			userMoney.setUserId(user1.getUuid());
			userMoney.setOnlineMoney(user1.getOnlineMoney());
			userMoney.setOfflineMoney(user1.getOfflineMoney());
			returnJson.setReturnObject(userMoney);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(28002);
			returnJson.setReturnMessage("服务器错误");
			returnJson.setServerStatus(2);
		}
		return returnJson ;
	}

	@RequestMapping(value="/User/Third/Bind",method = RequestMethod.POST)
	@ResponseBody
	public Object postUser(@RequestBody  User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(29000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		if(StringUtils.isEmpty(user.getIcon())||user.getChannel()==0||StringUtils.isEmpty(user.getThirdPartUuid())||StringUtils.isEmpty(user.getUuid())) {
				returnJson.setErrorCode(29001);
				returnJson.setReturnMessage("传入参数为空");
				returnJson.setServerStatus(1);
				return returnJson;
		}
		try{
			String result = MD5.GetMD5Code(user.getRandomKey() + "at^&*ta");
			if(!result.equals(user.getSecretKey())){
				returnJson.setErrorCode(99999);
				returnJson.setReturnMessage("密钥无效" + user.toString());
				returnJson.setServerStatus(1);
				return returnJson;
			}

			User user1 = null;
			if(user.getChannel()==1){
				user1 = userService.selectThirdQQ(user.getThirdPartUuid());
				if(user1!=null){
					returnJson.setErrorCode(29003);
					returnJson.setReturnMessage("该QQ号已经被绑定过，请重新绑定");
					returnJson.setServerStatus(1);
					return returnJson;
				}
				user.setQqId(user.getThirdPartUuid());
				user.setQqIcon(user.getIcon());
				userService.updateThirdQQ(user);
			}else if(user.getChannel()==2){
				user1 = userService.selectThirdWeixin(user.getThirdPartUuid());
				if(user1!=null){
					returnJson.setErrorCode(29004);
					returnJson.setReturnMessage("该微信帐号已经被绑定过，请重新绑定");
					returnJson.setServerStatus(1);
					return returnJson;
				}
				user.setWeixinId(user.getThirdPartUuid());
				user.setWeixinIcon(user.getIcon());
				userService.updateThirdWeixin(user);
			}else if(user.getChannel()==3){
				user1 = userService.selectThirdWeibo(user.getThirdPartUuid());
				if(user1!=null){
					returnJson.setErrorCode(29005);
					returnJson.setReturnMessage("该微博帐号已经被绑定过，请重新绑定");
					returnJson.setServerStatus(1);
					return returnJson;
				}
				user.setWeiboIcon(user.getIcon());
				user.setWeiboId(user.getThirdPartUuid());
				userService.updateThirdWeibo(user);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(29002);
			returnJson.setReturnMessage("服务器错误");
			returnJson.setServerStatus(2);
		}
		return returnJson ;
	}






	@RequestMapping(value="/Funding/Level/One",method = RequestMethod.POST)
	@ResponseBody
	public Object updateLevel(@RequestBody  User user) {
		ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(29000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		if(user.getType()==0||StringUtils.isEmpty(user.getActivityId())||StringUtils.isEmpty(user.getUserId())){
			returnJson.setErrorCode(29001);
			returnJson.setReturnMessage("传入参数为空");
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			String[] uuids = user.getUserId().split(",");
			for(String uuid:uuids) {
				if(!StringUtils.isEmpty(uuid)) {
					User user1 = userService.selectByPrimaryKey(uuid);
					CrowdFunding crowdFunding = new CrowdFunding();
					Activity activity = activityService.getActivityByUUID(user.getActivityId());
					crowdFunding.setUserId(uuid);
					crowdFunding.setLevel(user.getLevel());
					crowdFunding.setLevel(1);
					crowdFunding.setMoneyRate(activity.getLevelOneFxRate());
					crowdFunding.setMoneySingle(activity.getLevelOneFxMoney());
					crowdFunding.setType(user.getType());
					crowdFunding.setName(user1.getNickName());
					crowdFunding.setRasiedMoney(0);
					crowdFunding.setTotalMoney(0);
					crowdFunding.setActivityId(user.getActivityId());
					crowdFunding.setTotalRasiedPeople(0);
					crowdFunding.setUuid(UUID.randomUUID().toString());
					crowdFunding.setStartDate(new Date());
					crowdFundingService.insert(crowdFunding);
					user.setFundingLevel(1);
					user.setIdentification(2);
					user.setUuid(uuid);
					userService.updateLevel(user);
					userService.updateIden(user);
				}
            }
		}catch(Exception ex){
            ex.printStackTrace();
			returnJson.setErrorCode(29002);
			returnJson.setReturnMessage("服务器错误");
			returnJson.setServerStatus(1);
		}
		return returnJson ;
	}

	@RequestMapping(value="/Funding/Level/Two",method = RequestMethod.POST)
	@ResponseBody
	public Object updateLevelTwo(@RequestBody User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(33000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		if(StringUtils.isEmpty(user.getUuid())||StringUtils.isEmpty(user.getPid())){
			returnJson.setErrorCode(33001);
			returnJson.setReturnMessage("传入参数为空");
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			String[] uuids = user.getUuid().split(",");
			for(String value:uuids) {
				if(!StringUtils.isEmpty(value)) {
					User user1 = userService.selectByPrimaryKey(value);
					User use1 = new User();
					use1.setUuid(value);
					use1.setFundingLevel(2);
					use1.setIdentification(2);
					use1.setPid(user.getPid());
					CrowdFunding crowdFunding1 = new CrowdFunding();
					crowdFunding1.setUserId(user.getPid());
					CrowdFunding crowdFunding2 = crowdFundingService.getUser(crowdFunding1);
					CrowdFunding crowdFunding = new CrowdFunding();
					Activity activity = activityService.getActivityByUUID(crowdFunding2.getActivityId());
					crowdFunding.setUserId(value);
					crowdFunding.setLevel(2);
					crowdFunding.setMoneyRate(activity.getLevelOneFxRate());
					crowdFunding.setMoneySingle(activity.getLevelOneFxMoney());
					crowdFunding.setType(user.getType());
					crowdFunding.setName(user1.getNickName());
					crowdFunding.setRasiedMoney(0);
					crowdFunding.setTotalMoney(0);
					crowdFunding.setActivityId(crowdFunding2.getActivityId());
					crowdFunding.setTotalRasiedPeople(0);
					crowdFunding.setUuid(UUID.randomUUID().toString());
					crowdFunding.setStartDate(new Date());
					crowdFundingService.insert(crowdFunding);
					userService.updateLevel(use1);
					userService.updateIden(use1);
					userService.updateLevelTwo(use1);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(33002);
			returnJson.setReturnMessage("服务器错误");
			returnJson.setServerStatus(1);
		}
		return returnJson ;
	}


	@RequestMapping(value="/Funding/Level/Three",method = RequestMethod.POST)
	@ResponseBody
	public Object updateLevelThree(@RequestBody User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(34000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		if(StringUtils.isEmpty(user.getUuid())||StringUtils.isEmpty(user.getPid())){
			returnJson.setErrorCode(34001);
			returnJson.setReturnMessage("传入参数为空");
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			String[] uuids = user.getUuid().split(",");
			for(String value:uuids) {
				if(!StringUtils.isEmpty(value)) {
					User user1 = userService.selectByPrimaryKey(value);
					User use1 = new User();
					use1.setUuid(value);
					use1.setFundingLevel(2);
					use1.setIdentification(2);
					use1.setPid(user.getPid());
					CrowdFunding crowdFunding1 = new CrowdFunding();
					crowdFunding1.setUserId(user.getPid());
					CrowdFunding crowdFunding2 = crowdFundingService.getUser(crowdFunding1);
					CrowdFunding crowdFunding = new CrowdFunding();
					Activity activity = activityService.getActivityByUUID(crowdFunding2.getActivityId());
					crowdFunding.setUserId(value);
					crowdFunding.setLevel(3);
					crowdFunding.setMoneyRate(activity.getLevelOneFxRate());
					crowdFunding.setMoneySingle(activity.getLevelOneFxMoney());
					crowdFunding.setType(user.getType());
					crowdFunding.setName(user1.getNickName());
					crowdFunding.setRasiedMoney(0);
					crowdFunding.setTotalMoney(0);
					crowdFunding.setActivityId(crowdFunding2.getActivityId());
					crowdFunding.setTotalRasiedPeople(0);
					crowdFunding.setUuid(UUID.randomUUID().toString());
					crowdFunding.setStartDate(new Date());
					crowdFundingService.insert(crowdFunding);
					userService.updateLevel(use1);
					userService.updateIden(use1);
					userService.updateLevelTwo(use1);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(33002);
			returnJson.setReturnMessage("服务器错误");
			returnJson.setServerStatus(1);
		}
		return returnJson ;
	}



	@RequestMapping(value="/Funding/Level/One",method = RequestMethod.GET)
	@ResponseBody
	public Object getLevel() {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(0000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		List<List<String>> temps = new ArrayList<List<String>>();
		List<User> results = null;
		HashMap<String,List<List<String>>> map = new HashMap<String, List<List<String>>>();
		try {
			User user1 = new User();
			user1.setFundingLevel(1);
			results = userService.getUsersFunding(user1);
			for(User user:results){
				List<String> values = new ArrayList<String>();
				values.add(user.getNickName());
				values.add(user.getMobile());
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateString = formatter.format(user.getCreateTime());
				values.add(dateString);
				if(user.getLevel()==1){
					values.add("普通会员");
				}else if(user.getLevel()==2){
					values.add("金卡会员");
				}else if(user.getLevel()==3){
					values.add("vip会员");
				}
				if(user.getIdentification()==1){
					values.add("会员");
				}else if(user.getIdentification()==2){
					values.add("分销商");
				}
				if(user.getType()==1){
					values.add("众筹");
				}else if(user.getType()==2){
					values.add("促销");
				}
				values.add("<input type='button' value='添加下级分销商' onclick="+"add('"+user.getUuid()+"','"+user.getNickName().replaceAll(" ", "") +"')"+ " />");
				values.add("<input type='button' value='查看下级分销商' onclick="+"view('"+user.getUuid()+"')"+ " />");
				temps.add(values);
			}
			map.put("data", temps);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(2003);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnObject(temps);
		return map;
	}


	@RequestMapping(value="/Users/Detail",method = RequestMethod.GET)
	@ResponseBody
	public Object set(String pid) {

		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(2000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		List<List<String>> temps = new ArrayList<List<String>>();
		List<User> results = null;
		HashMap<String,List<List<String>>> map = new HashMap<String, List<List<String>>>();
		try {
			results = userService.getSet(pid);
			for(User user:results){
				List<String> values = new ArrayList<String>();
				values.add(user.getNickName());
				values.add(user.getMobile());
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateString = formatter.format(user.getCreateTime());
				values.add(dateString);
				if(user.getLevel()==1){
					values.add("普通会员");
				}else if(user.getLevel()==2){
					values.add("金卡会员");
				}else if(user.getLevel()==3){
					values.add("vip会员");
				}
				if(user.getIdentification()==1){
					values.add("会员");
				}else if(user.getIdentification()==2){
					values.add("分销商");
				}
				if(user.getType()==1){
					values.add("众筹");
				}else if(user.getType()==2){
					values.add("促销");
				}
				temps.add(values);
			}
			map.put("data",temps);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(2003);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnObject(temps);
		return map;
	}

	public  String formatDate(){
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String s = format1.format(new Date());
		return s;
	}
	public String createOrderNo(String s){
		s = s+UUID.randomUUID().toString().substring(0,4);
		s = s+UUID.randomUUID().toString().substring(0,2);
		return  s.replaceAll("-","").replaceAll(" ","").replaceAll(":","");
	}



	@RequestMapping(value="/Password/Secret",method = RequestMethod.GET)
	@ResponseBody
	public Object updatePwd11(User user) {

		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(12000);
		returnJson.setReturnMessage("调用成功"+user.toString());
		returnJson.setServerStatus(0);



		if(StringUtils.isEmpty(user.getUuid())||StringUtils.isEmpty(user.getPassword())||StringUtils.isEmpty(user.getSecretKey())||StringUtils.isEmpty(user.getRandomKey() )){
			returnJson.setErrorCode(12001);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result = MD5.GetMD5Code(user.getRandomKey()+"at^&*ta");
		if(!result.equals(user.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			User user1 = userService.selectByPrimaryKey(user.getUuid());
			user.setUuid(user1.getUuid());
			user.setValid(user1.getValid());
			userService.updatePassword(user);

		}catch (Exception e){
			e.printStackTrace();
			returnJson.setErrorCode(12003);
			returnJson.setReturnMessage("服务器错误" + user.toString());
			returnJson.setServerStatus(2);
			return returnJson;
		}
		returnJson.setReturnObject(user);
		returnJson.setReturnValue(user.getUuid());
		return returnJson;
	}

	public static void main(String[]args){
		String randomKey = UUID.randomUUID().toString();
		String secretKey = MD5.GetMD5Code(randomKey+"at^&*ta");
		System.err.println(randomKey);
		System.err.println(secretKey);
	}

	@RequestMapping(value="/OfflineMoney/Minus",method = RequestMethod.POST)
	 @ResponseBody
	 public Object minusMoney(@RequestBody User user1) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(12000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		List<User> results = null;
		if(StringUtils.isEmpty(user1.getUuid())){
			returnJson.setErrorCode(12001);
			returnJson.setReturnMessage("传入参数为空");
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result = MD5.GetMD5Code(user1.getRandomKey()+"at^&*ta");
		if(!result.equals(user1.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效");
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			User user = userService.selectByPrimaryKey(user1.getUuid());
			if(user==null){
				returnJson.setErrorCode(12002);
				returnJson.setReturnMessage("用户id不存在");
				returnJson.setServerStatus(1);
				return  returnJson;
			}
			if(user.getOfflineMoney()<1000){
				returnJson.setErrorCode(12003);
				returnJson.setReturnMessage("用户线下币不够,扣除失败");
				returnJson.setServerStatus(1);
				return  returnJson;
			}
			user.setOfflineMoney(1000);
			userService.minusOfflineMoney(user);
			Order order = new Order();
			order.setType(12);
			order.setUuid(createOrderNo(formatDate()));
			order.setUserId(user.getUuid());
			order.setTotalMoney(1000);
			order.setActureMoney(1000);
			order.setStatus(3);
			order.setCreateTime(new Date());
			orderService.insertNormalUserNew(order);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(2003);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
			return returnJson;
		}
		return returnJson;
	}

	@RequestMapping(value="/Key/Unique",method = RequestMethod.GET)
	@ResponseBody
	public Object getUser11(User user1) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(31000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		List<User> results = null;
		if(StringUtils.isEmpty(user1.getUuid())){
			returnJson.setErrorCode(12001);
			returnJson.setReturnMessage("传入参数为空");
			returnJson.setServerStatus(1);
			return returnJson;
		}
		String result = MD5.GetMD5Code(user1.getRandomKey()+"at^&*ta");
		if(!result.equals(user1.getSecretKey())){
			returnJson.setErrorCode(99999);
			returnJson.setReturnMessage("密钥无效");
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			User user = userService.selectByPrimaryKey(user1.getUuid());
			if(user==null){
				returnJson.setErrorCode(31001);
				returnJson.setReturnMessage("用户id不存在");
				returnJson.setServerStatus(1);
				return  returnJson;
			}
			returnJson.setReturnObject(user);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(2003);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
			return returnJson;
		}
		return returnJson;
	}


	@RequestMapping(value="/OnlineMoney/Plus",method = RequestMethod.POST)
	@ResponseBody
	public Object plusOnlineMoney(@RequestBody User user1) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(31000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		List<User> results = null;
		try {
			User user = userService.selectByPrimaryKey(user1.getUuid());
			if(user==null){
				returnJson.setErrorCode(31001);
				returnJson.setReturnMessage("用户id不存在");
				returnJson.setServerStatus(1);
				return  returnJson;
			}
			String result = MD5.GetMD5Code(user1.getRandomKey() + "at^&*ta");
			if(!result.equals(user1.getSecretKey())){
				returnJson.setErrorCode(99999);
				returnJson.setReturnMessage("密钥无效");
				returnJson.setServerStatus(1);
				return returnJson;
			}
			returnJson.setReturnObject(user);
			user.setOnlineMoney(user.getOnlineMoney() + user1.getOnlineMoney());
			userService.updateOnlineMoney(user);
			Order order = new Order();
			order.setType(11);
			order.setUuid(createOrderNo(formatDate()));
			order.setUserId(user1.getUuid());
			order.setTotalMoney(user1.getOnlineMoney());
			order.setActureMoney(user1.getOnlineMoney());
			order.setStatus(3);
			order.setCreateTime(new Date());
			orderService.insertNormalUserNew(order);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(2003);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
			return returnJson;
		}
		return returnJson;
	}


}
