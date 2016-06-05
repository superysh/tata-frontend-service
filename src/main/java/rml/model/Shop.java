package rml.model;

import java.util.Date;

/**
 * Created by edward-echo on 2016/3/28.
 */
public class Shop {
    private String uuid;
    private String mobile;
    private String password;
    private String inviteCode;
    private String name;
    private String provinceId;
    private String cityId;
    private String areaId;
    private String address;
    private String phone;
    private String validCode;
    private Date createTime;
    private int returnRate;
    private int offlineMoney;
    private int type;
    private String statusShow;
    private String typeShow;
    private String randomNum;

    public String getRandomNum() {
        return randomNum;
    }

    public void setRandomNum(String randomNum) {
        this.randomNum = randomNum;
    }

    private int pageSize;

    private int pageNo;

    private int startSize;

    private int moneyDaily;

    private int moneyTotal;


    private String secretKey;

    private String randomKey;


    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }


    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    public int getMoneyDaily() {
        return moneyDaily;
    }

    public void setMoneyDaily(int moneyDaily) {
        this.moneyDaily = moneyDaily;
    }

    public int getMoneyTotal() {
        return moneyTotal;
    }

    public void setMoneyTotal(int moneyTotal) {
        this.moneyTotal = moneyTotal;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }


    public int getStartSize() {
        return (pageNo-1)*pageSize;
    }

    public void setStartSize(int startSize) {
        this.startSize = startSize;
    }

    public String getTypeShow() {
        return typeShow;
    }

    public void setTypeShow(String typeShow) {
        this.typeShow = typeShow;
    }

    public String getStatusShow() {

        return statusShow;
    }

    public void setStatusShow(String statusShow) {
        this.statusShow = statusShow;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOfflineMoney() {
        return offlineMoney;
    }

    public void setOfflineMoney(int offlineMoney) {
        this.offlineMoney = offlineMoney;
    }

    public int getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(int returnRate) {
        this.returnRate = returnRate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBizLicensePath() {
        return bizLicensePath;
    }

    public void setBizLicensePath(String bizLicensePath) {
        this.bizLicensePath = bizLicensePath;
    }

    public String getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront;
    }

    public String getIdCardBack() {
        return idCardBack;
    }

    public void setIdCardBack(String idCardBack) {
        this.idCardBack = idCardBack;
    }

    public String getIdCardHand() {
        return idCardHand;
    }

    public void setIdCardHand(String idCardHand) {
        this.idCardHand = idCardHand;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private String bizLicensePath;
    private String idCardFront;
    private String idCardBack;
    private String idCardHand;
    private int status;




}
