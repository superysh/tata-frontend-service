package rml.model;

import java.util.List;

/**
 * Created by edward-echo on 2016/4/11.
 */
public class ShopAccount {

    private int accountAmount;

    private int daliyAmount;

    private int monthAmount;

    private int weekAmount;

    private int totalAmount;

    private List<Integer> weekValues;

    public List<Integer> getWeekValues() {
        return weekValues;
    }

    public void setWeekValues(List<Integer> weekValues) {
        this.weekValues = weekValues;
    }

    public int getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(int accountAmount) {
        this.accountAmount = accountAmount;
    }

    public int getDaliyAmount() {
        return daliyAmount;
    }

    public void setDaliyAmount(int daliyAmount) {
        this.daliyAmount = daliyAmount;
    }

    public int getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(int monthAmount) {
        this.monthAmount = monthAmount;
    }

    public int getWeekAmount() {
        return weekAmount;
    }

    public void setWeekAmount(int weekAmount) {
        this.weekAmount = weekAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
