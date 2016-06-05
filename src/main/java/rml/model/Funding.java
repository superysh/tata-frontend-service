package rml.model;

import java.util.Date;

/**
 * Created by edward-echo on 2015/12/26.
 */
public class Funding {

  private String uuid;

  private Date startDate;

  private Date endDate;

  private int limitedDays;

  private String orderId;

  private float totalMoney;

  private float raisedMoney;

  private int totalRasiedPeople;

  private String name;

  private String lDesc;

  private String userId;

  private String activityId;

  private int status;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getLimitedDays() {
        return limitedDays;
    }

    public void setLimitedDays(int limitedDays) {
        this.limitedDays = limitedDays;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public float getRaisedMoney() {
        return raisedMoney;
    }

    public void setRaisedMoney(float raisedMoney) {
        this.raisedMoney = raisedMoney;
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

    public String getlDesc() {
        return lDesc;
    }

    public void setlDesc(String lDesc) {
        this.lDesc = lDesc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
