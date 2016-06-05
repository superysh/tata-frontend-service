package rml.model;

import java.util.Date;

/**
 * Created by Administrator on 2015/9/12.
 */
public class User{

    private String keyWord;

    private String uuid;

    private String mobile;

    private String icon;

    private String nickName;

    private String password;

    private Date createTime;

    private Date updateTime;

    private String token;

    private String validCode;

    private String email;

    private String payPassword;

    private int level;

    private int type;

    private int identification;

    private String startDate;

    private String endDate;

    private String thirdPartUuid;

    private int channel;

    private String qq;

    private String msn;

    private String valid;

    private String officePhone;

    private String homePhone;

    private int userPayStatus;

    private String groupId;

    private String trueName;

    private int sex;

    private float accountTotal;

    private int status;

    private int fundingLevel;

    private String pid;

    private String activityId;

    private String userId;

    private UserAddress userAddress;

    private String oldPassword;

    private int degree;

    private Date birthday;

    private int offlineMoney;

    private int onlineMoney;

    private String accountMoney;

    private int money;

    private String shopId;

    private String weixinId;

    private String weiboId;

    private String qqId;

    private String weixinIcon;

    private String weiboIcon;

    private String qqIcon;

    private String accountName;

    private String sessionId;

    private String verifyCode;

    private boolean isQQBind;

    private boolean IsWeiboBind;

    private boolean isWeinxinBind;

    private String secretKey;

    private String randomKey;

    private String randomNum;


    public String getRandomNum() {
        return randomNum;
    }

    public void setRandomNum(String randomNum) {
        this.randomNum = randomNum;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    public boolean isQQBind() {
        return isQQBind;
    }

    public void setQQBind(boolean QQBind) {
        isQQBind = QQBind;
    }

    public boolean isWeiboBind() {
        return IsWeiboBind;
    }

    public void setWeiboBind(boolean weiboBind) {
        IsWeiboBind = weiboBind;
    }

    public boolean isWeinxinBind() {
        return isWeinxinBind;
    }

    public void setWeinxinBind(boolean weinxinBind) {
        isWeinxinBind = weinxinBind;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
    }

    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    public String getWeixinIcon() {
        return weixinIcon;
    }

    public void setWeixinIcon(String weixinIcon) {
        this.weixinIcon = weixinIcon;
    }

    public String getWeiboIcon() {
        return weiboIcon;
    }

    public void setWeiboIcon(String weiboIcon) {
        this.weiboIcon = weiboIcon;
    }

    public String getQqIcon() {
        return qqIcon;
    }

    public void setQqIcon(String qqIcon) {
        this.qqIcon = qqIcon;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getOfflineMoney() {
        return offlineMoney;
    }

    public void setOfflineMoney(int offlineMoney) {
        this.offlineMoney = offlineMoney;
    }

    public int getOnlineMoney() {
        return onlineMoney;
    }

    public void setOnlineMoney(int onlineMoney) {
        this.onlineMoney = onlineMoney;
    }

    public String getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(String accountMoney) {
        this.accountMoney = accountMoney;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getFundingLevel() {
        return fundingLevel;
    }

    public void setFundingLevel(int fundingLevel) {
        this.fundingLevel = fundingLevel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getAccountTotal() {
        return accountTotal;
    }

    public void setAccountTotal(float accountTotal) {
        this.accountTotal = accountTotal;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public int getUserPayStatus() {
        return userPayStatus;
    }

    public void setUserPayStatus(int userPayStatus) {
        this.userPayStatus = userPayStatus;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getThirdPartUuid() {
        return thirdPartUuid;
    }

    public void setThirdPartUuid(String thirdPartUuid) {
        this.thirdPartUuid = thirdPartUuid;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIdentification() {
        return identification;
    }

    public void setIdentification(int identification) {
        this.identification = identification;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (!uuid.equals(other.uuid))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [uuid=" + uuid + ", name=" + nickName + ", password=" + password
                + ", mobile =" + mobile + ", token =" + token + "]";
    }


}

