package com.boniu.starplan.entity;

public class CollectTimeModel {


    /**
     * finishTime : 1593485889000
     * income : 38
     * isTake : true
     * startTime : 1593485985174
     */

    private long finishTime;
    private int income;
    private boolean isTake;
    private long startTime;

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public boolean isIsTake() {
        return isTake;
    }

    public void setIsTake(boolean isTake) {
        this.isTake = isTake;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
