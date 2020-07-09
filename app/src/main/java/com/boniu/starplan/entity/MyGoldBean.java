package com.boniu.starplan.entity;

public class MyGoldBean {


    /**
     * accumulateEarnGoldAmount : 0
     * accumulateExpendGoldAmount : 0
     * goldBalance : 0
     * todayEarnGoldAmount : 0
     */

    private long accumulateEarnGoldAmount;
    private long accumulateExpendGoldAmount;
    private long goldBalance;
    private long todayEarnGoldAmount;
    private long availableBalance;
    private  long todayExpendGoldAmount;

    public long getTodayExpendGoldAmount() {
        return todayExpendGoldAmount;
    }

    public void setTodayExpendGoldAmount(long todayExpendGoldAmount) {
        this.todayExpendGoldAmount = todayExpendGoldAmount;
    }

    public long getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(long availableBalance) {
        this.availableBalance = availableBalance;
    }

    public long getAccumulateEarnGoldAmount() {
        return accumulateEarnGoldAmount;
    }

    public void setAccumulateEarnGoldAmount(long accumulateEarnGoldAmount) {
        this.accumulateEarnGoldAmount = accumulateEarnGoldAmount;
    }

    public long getAccumulateExpendGoldAmount() {
        return accumulateExpendGoldAmount;
    }

    public void setAccumulateExpendGoldAmount(long accumulateExpendGoldAmount) {
        this.accumulateExpendGoldAmount = accumulateExpendGoldAmount;
    }

    public long getGoldBalance() {
        return goldBalance;
    }

    public void setGoldBalance(long goldBalance) {
        this.goldBalance = goldBalance;
    }

    public long getTodayEarnGoldAmount() {
        return todayEarnGoldAmount;
    }

    public void setTodayEarnGoldAmount(long todayEarnGoldAmount) {
        this.todayEarnGoldAmount = todayEarnGoldAmount;
    }
}
