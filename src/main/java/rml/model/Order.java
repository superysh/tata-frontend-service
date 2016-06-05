package rml.model;
import java.util.Date;
import java.util.List;

/**
 * Created by edward-echo on 2015/12/20.
 */
public class Order {

    private String  uuid;
    private String  cargoIds;
    private Date    createTime;
    private String  no;
    private int  deliveryFee;
    private String  couponIds;
    private int feeSavings;
    private int totalMoney;
    private String savingMoney;
    private String realMoney;
    private String lDesc;
    private String pid;
    private int type;
    private float paidMoney;
    private String seriesNo;
    private int status;
    private int payType;
    private Date payTime;
    private String userId;
    private Date limitedTime;
    private boolean isDeliveryFeeFree;
    private int limitedDays;
    private int actureMoney;
    private String activityId;
    private String comment;
    private String name;
    private String addressId;
    private String fundingCommmentId;
    private int userLevel;
    private Date updateTime;
    private UserAddress userAddress;
    private String deliveryAddress;
    private List<Product> products;
    private Date paidTime;
    private boolean isMoneyPay;
    private int offlineMoney;
    private int money;
    private String startDate;
    private String endDate;
    private int subStatus;
    private int shopTotalMoney;
    private String shopName;

    private String secretKey;

    private String randomKey;

    private String productName;

    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getShopTotalMoney() {
        return shopTotalMoney;
    }

    public void setShopTotalMoney(int shopTotalMoney) {
        this.shopTotalMoney = shopTotalMoney;
    }

    public int getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(int subStatus) {
        this.subStatus = subStatus;
    }

    public void setMoneyPay(boolean moneyPay) {
        isMoneyPay = moneyPay;
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    private int needPay;

    private int returnBack;

    private String shopId;

    private String nickName;

    private int shopPayBack;

    private int pageSize;

    private int pageNo;

    private int startSize;


    public int getShopPayBack() {
        return shopPayBack;
    }

    public void setShopPayBack(int shopPayBack) {
        this.shopPayBack = shopPayBack;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getOfflineMoney() {
        return offlineMoney;
    }

    public void setOfflineMoney(int offlineMoney) {
        this.offlineMoney = offlineMoney;
    }

    public int getNeedPay() {
        return needPay;
    }

    public void setNeedPay(int needPay) {
        this.needPay = needPay;
    }

    public int getReturnBack() {
        return returnBack;
    }

    public void setReturnBack(int returnBack) {
        this.returnBack = returnBack;
    }

    public boolean isMoneyPay() {
        return isMoneyPay;
    }

    public void setIsMoneyPay(boolean isMoneyPay) {
        this.isMoneyPay = isMoneyPay;
    }

    private int userMoneyTotal;

    public int getUserMoneyTotal() {
        return userMoneyTotal;
    }

    public void setUserMoneyTotal(int userMoneyTotal) {
        this.userMoneyTotal = userMoneyTotal;
    }

    public Date getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(Date paidTime) {
        this.paidTime = paidTime;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    public void setIsDeliveryFeeFree(boolean isDeliveryFeeFree) {
        this.isDeliveryFeeFree = isDeliveryFeeFree;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    private User user;

    private List<Cargo> cargos;

    private String userAddressId;

    public String getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(String userAddressId) {
        this.userAddressId = userAddressId;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFundingCommmentId() {
        return fundingCommmentId;
    }

    public void setFundingCommmentId(String fundingCommmentId) {
        this.fundingCommmentId = fundingCommmentId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public int getLimitedDays() {
        return limitedDays;
    }

    public void setLimitedDays(int limitedDays) {
        this.limitedDays = limitedDays;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCargoIds() {
        return cargoIds;
    }

    public void setCargoIds(String cargoIds) {
        this.cargoIds = cargoIds;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(String couponIds) {
        this.couponIds = couponIds;
    }

    public int getFeeSavings() {
        return feeSavings;
    }

    public void setFeeSavings(int feeSavings) {
        this.feeSavings = feeSavings;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getActureMoney() {
        return actureMoney;
    }

    public void setActureMoney(int actureMoney) {
        this.actureMoney = actureMoney;
    }

    public String getSavingMoney() {
        return savingMoney;
    }

    public void setSavingMoney(String savingMoney) {
        this.savingMoney = savingMoney;
    }

    public String getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(String realMoney) {
        this.realMoney = realMoney;
    }

    public String getlDesc() {
        return lDesc;
    }

    public void setlDesc(String lDesc) {
        this.lDesc = lDesc;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public float getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(float paidMoney) {
        this.paidMoney = paidMoney;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSeriesNo() {
        return seriesNo;
    }

    public void setSeriesNo(String seriesNo) {
        this.seriesNo = seriesNo;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getLimitedTime() {
        return limitedTime;
    }

    public void setLimitedTime(Date limitedTime) {
        this.limitedTime = limitedTime;
    }

    public boolean isDeliveryFeeFree() {
        return isDeliveryFeeFree;
    }

    public void setDeliveryFeeFree(boolean deliveryFeeFree) {
        isDeliveryFeeFree = deliveryFeeFree;
    }

    public int getStartSize() {
        return (pageNo-1)*pageSize;
    }

    public void setStartSize(int startSize) {
        this.startSize = startSize;
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

}
