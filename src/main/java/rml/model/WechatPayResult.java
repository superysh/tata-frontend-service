package rml.model;
import java.util.Date;
import java.util.List;

/**
 * Created by yeshenghong on 2016/06/10.
 */
public class WechatPayResult {
	private Integer id;
	private String outTradeNo;
	private String appid;
	private String mchId;
	private String deviceInfo;
	private String nonceStr;
	private String sign;
	private String resultCode;
	private String errCode;
	private String errCodeDes; 
	private String openid;
	private boolean isSubscribe;
	private String tradeType;
	private String bankType; 
	private String totalFee;
	private String feeType;
	private Integer cashFee;	
	private String cashFeeType;
	private Integer couponFee;
	private Integer couponCount;
	private String couponId;
	private Integer couponFeeSingle;	
	private String transactionId; 	
	private String attach; 
	private String timeEnd;
	private Date createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrCodeDes() {
		return errCodeDes;
	}
	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public boolean isSubscribe() {
		return isSubscribe;
	}
	public void setSubscribe(boolean isSubscribe) {
		this.isSubscribe = isSubscribe;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public Integer getCashFee() {
		return cashFee;
	}
	public void setCashFee(Integer cashFee) {
		this.cashFee = cashFee;
	}
	public String getCashFeeType() {
		return cashFeeType;
	}
	public void setCashFeeType(String cashFeeType) {
		this.cashFeeType = cashFeeType;
	}
	public Integer getCouponFee() {
		return couponFee;
	}
	public void setCouponFee(Integer couponFee) {
		this.couponFee = couponFee;
	}
	public Integer getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public Integer getCouponFeeSingle() {
		return couponFeeSingle;
	}
	public void setCouponFeeSingle(Integer couponFeeSingle) {
		this.couponFeeSingle = couponFeeSingle;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
   
}
