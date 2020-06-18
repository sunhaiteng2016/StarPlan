package com.boniu.starplan.entity;

public class IsSignModel {


    /**
     * code : 1
     * continuousSign : 2
     * cumulativeSign : 2
     * isDouble : false
     * message : 金币发放成功
     * success : true
     * weekSign : 2
     */

    private String code;
    private int continuousSign;
    private int cumulativeSign;
    private boolean isDouble;
    private String message;
    private boolean success;
    private int weekSign;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public boolean isIsDouble() {
        return isDouble;
    }

    public void setIsDouble(boolean isDouble) {
        this.isDouble = isDouble;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getWeekSign() {
        return weekSign;
    }

    public void setWeekSign(int weekSign) {
        this.weekSign = weekSign;
    }
}
