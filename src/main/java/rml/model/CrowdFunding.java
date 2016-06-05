package rml.model;

import java.util.Date;

/**
 * Created by edward-echo on 2016/1/3.
 */
public class CrowdFunding {
    private String uuid;
    private Date startDate;
    private float totalMoney;
    private float rasiedMoney;
    private int totalRasiedPeople;
    private String name;
    private String userId;
    private float moneyRate;
    private float moneySingle;
    private int level;
    private int type;
    private String activityId;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public float getRasiedMoney() {
        return rasiedMoney;
    }

    public void setRasiedMoney(float rasiedMoney) {
        this.rasiedMoney = rasiedMoney;
    }

    public int getTotalRasiedPeople() {
        return totalRasiedPeople;
    }

    public void setTotalRasiedPeople(int totalRasiedPeople) {
        this.totalRasiedPeople = totalRasiedPeople;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getMoneyRate() {
        return moneyRate;
    }

    public void setMoneyRate(float moneyRate) {
        this.moneyRate = moneyRate;
    }

    public float getMoneySingle() {
        return moneySingle;
    }

    public void setMoneySingle(float moneySingle) {
        this.moneySingle = moneySingle;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
