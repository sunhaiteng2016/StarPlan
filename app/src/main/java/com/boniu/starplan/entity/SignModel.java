package com.boniu.starplan.entity;

public class SignModel {

    /**
     * continuousSign : 2
     * cumulativeSign : 2
     * weekSign : 2
     *-1--宝箱未创建（无法领取）    0--未领取  1--已领取  2---失效
     */

    private int continuousSign;
    private int cumulativeSign;
    private int weekSign;
    private int sevenDays;
    private int threeDays;

    public int getSevenDays() {
        return sevenDays;
    }

    public void setSevenDays(int sevenDays) {
        this.sevenDays = sevenDays;
    }

    public int getThreeDays() {
        return threeDays;
    }

    public void setThreeDays(int threeDays) {
        this.threeDays = threeDays;
    }

    public int getContinuousSign() {
        return continuousSign;
    }

    public void setContinuousSign(int continuousSign) {
        this.continuousSign = continuousSign;
    }

    public int getCumulativeSign() {
        return cumulativeSign;
    }

    public void setCumulativeSign(int cumulativeSign) {
        this.cumulativeSign = cumulativeSign;
    }

    public int getWeekSign() {
        return weekSign;
    }

    public void setWeekSign(int weekSign) {
        this.weekSign = weekSign;
    }
}
