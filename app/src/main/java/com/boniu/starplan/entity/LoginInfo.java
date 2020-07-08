package com.boniu.starplan.entity;

public class LoginInfo {


    /**
     * success : true
     * code : 200
     * message : 登录成功
     * mobile : 13561173094
     * utoken : fe3a492ba4424136b1bc66b609647e45
     */

    private boolean success;
    private String code;
    private String message;
    private String mobile;
    private String utoken;
    private String goldAmount;
    private String accountStatus;
    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String goldAmount) {
        this.accountStatus = accountStatus;
    }

    public String getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(String goldAmount) {
        this.goldAmount = goldAmount;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUtoken() {
        return utoken;
    }

    public void setUtoken(String utoken) {
        this.utoken = utoken;
    }
}
