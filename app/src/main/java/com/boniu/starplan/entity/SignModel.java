package com.boniu.starplan.entity;

public class SignModel {

    /**
     * continuousSign : 2
     * cumulativeSign : 2
     * weekSign : 2
     */

    private int continuousSign;
    private int cumulativeSign;
    private int weekSign;

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
