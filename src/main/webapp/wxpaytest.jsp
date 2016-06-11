<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微信支付测试页面</title>
<script language="JavaScript" src="js/jquery.js"></script>
<script language="JavaScript" src="js/config.js"></script>
<style type="text/css">
.btn {
	display: inline-block;
	padding: 12px 22px;
	margin-bottom: 0px;
	background-color: #31B0D5;
	font-size: 14px;
	font-weight: 400;
	line-height: 1.42857;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	cursor: pointer;
	-moz-user-select: none;
	background-image: none;
	border: 1px solid transparent;
	border-radius: 4px;
}
</style>
<script type="text/javascript">
function wechatPay(){
	$.ajax({
		url:baseUrl + "/wechatpay/topay.html?orderId=" + $("#orderId").val(),
		type:"POST",
		dataType:"json",
		success:function(res)
		{
			alert(res.returnMessage);		
		}
	})
}
function wechatPayCallback(){
	$.ajax({
		url:baseUrl + "/wechatpay/notify.html",
		type:"POST",
		dataType:"json",
		success:function(res)
		{
			alert(res);		
		}
	})
}
</script>
</head>
<body>
	<div style="width: 80%;margin-left:50px;">
		<h1>Hello WXPay!</h1>

		<h3>微信调起支付，此页面请在微信客户端打开</h3>
		<ul>
			<li>来自Sunlight的微信支付测试</li>
			<li>支付1分钱测试</li>
			<li>输入订单号：<input type="text" id="orderId" name="orderId" /></li>
		</ul>
		<input type="button" value="测试支付接口" onclick="wechatPay()"/>
		<input type="button" value="测试回调接口" onclick="wechatPayCallback()"/>
	</div>
</body>
</html>
