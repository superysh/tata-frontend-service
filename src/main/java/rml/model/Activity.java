package rml.model;

import java.util.List;
import java.util.Date;

/**
 * Created by edward-echo on 2015/12/20.
 */
public class Activity {

    private String name;

    private String uuid;

    private String userId;

    private String goodIds;

    private int goodsTotalMoney;

    private String desc;

    private List<Good> list;

    private Date endTime;

    private int limitedTime;

    private float finishMoney;

    private String orderName;

    private Date startTime;

    private int finishDay;

    private String fenxiaoId;

    private float levelOneFxRate;

    private float levelTwoFxRate;

    private float levelThreeFxRate;

    private float levelOneFxMoney;

    private float levelTwoFxMoney;

    private float levelThreeFxMoney;

    private int totalJoinedPeople;

    private float totalIncomeMoney;

    private int status;

    private float levelTwoMoney;

    private float levelThreeMoney;

    private float levelTwoThreeMoney;

    public float getLevelTwoThreeMoney() {
        return levelTwoThreeMoney;
    }

    public void setLevelTwoThreeMoney(float levelTwoThreeMoney) {
        this.levelTwoThreeMoney = levelTwoThreeMoney;
    }

    public float getLevelTwoMoney() {
        return levelTwoMoney;
    }

    public void setLevelTwoMoney(float levelTwoMoney) {
        this.levelTwoMoney = levelTwoMoney;
    }

    public float getLevelThreeMoney() {
        return levelThreeMoney;
    }

    public void setLevelThreeMoney(float levelThreeMoney) {
        this.levelThreeMoney = levelThreeMoney;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalJoinedPeople() {
        return totalJoinedPeople;
    }

    public void setTotalJoinedPeople(int totalJoinedPeople) {
        this.totalJoinedPeople = totalJoinedPeople;
    }

    public float getTotalIncomeMoney() {
        return totalIncomeMoney;
    }

    public void setTotalIncomeMoney(float totalIncomeMoney) {
        this.totalIncomeMoney = totalIncomeMoney;
    }

    public float getLevelOneFxRate() {
        return levelOneFxRate;
    }

    public void setLevelOneFxRate(float levelOneFxRate) {
        this.levelOneFxRate = levelOneFxRate;
    }

    public float getLevelTwoFxRate() {
        return levelTwoFxRate;
    }

    public void setLevelTwoFxRate(float levelTwoFxRate) {
        this.levelTwoFxRate = levelTwoFxRate;
    }

    public float getLevelThreeFxRate() {
        return levelThreeFxRate;
    }

    public void setLevelThreeFxRate(float levelThreeFxRate) {
        this.levelThreeFxRate = levelThreeFxRate;
    }

    public float getLevelOneFxMoney() {
        return levelOneFxMoney;
    }

    public void setLevelOneFxMoney(float levelOneFxMoney) {
        this.levelOneFxMoney = levelOneFxMoney;
    }

    public float getLevelTwoFxMoney() {
        return levelTwoFxMoney;
    }

    public void setLevelTwoFxMoney(float levelTwoFxMoney) {
        this.levelTwoFxMoney = levelTwoFxMoney;
    }

    public float getLevelThreeFxMoney() {
        return levelThreeFxMoney;
    }

    public void setLevelThreeFxMoney(float levelThreeFxMoney) {
        this.levelThreeFxMoney = levelThreeFxMoney;
    }

    public String getFenxiaoId() {
        return fenxiaoId;
    }

    public void setFenxiaoId(String fenxiaoId) {
        this.fenxiaoId = fenxiaoId;
    }

    public int getFinishDay() {
        return finishDay;
    }

    public void setFinishDay(int finishDay) {
        this.finishDay = finishDay;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public float getFinishMoney() {
        return finishMoney;
    }

    public void setFinishMoney(float finishMoney) {
        this.finishMoney = finishMoney;
    }

    public int getLimitedTime() {
        return limitedTime;
    }

    public void setLimitedTime(int limitedTime) {
        this.limitedTime = limitedTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<Good> getList() {
        return list;
    }

    public void setList(List<Good> list) {
        this.list = list;
    }

    public int getGoodsTotalMoney() {
        return goodsTotalMoney;
    }

    public void setGoodsTotalMoney(int goodsTotalMoney) {
        this.goodsTotalMoney = goodsTotalMoney;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGoodIds() {
        return goodIds;
    }

    public void setGoodIds(String goodIds) {
        this.goodIds = goodIds;
    }
}
