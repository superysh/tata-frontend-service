package rml.model;

/**
 * Created by edward-echo on 2016/3/22.
 */
public class UserMoney {
    private String userId;

    private int onlineMoney;

    private int offlineMoney;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getOnlineMoney() {
        return onlineMoney;
    }

    public void setOnlineMoney(int onlineMoney) {
        this.onlineMoney = onlineMoney;
    }

    public int getOfflineMoney() {
        return offlineMoney;
    }

    public void setOfflineMoney(int offlineMoney) {
        this.offlineMoney = offlineMoney;
    }
}
