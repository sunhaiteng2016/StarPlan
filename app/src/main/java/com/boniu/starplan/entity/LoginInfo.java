package com.boniu.starplan.entity;

public class LoginInfo {

    /**
     * code : 200
     * message : 登录成功
     * mobile : 13561173094
     * success : true
     * uToken : 19e473afe41a4cc2a49f027e8f211af4
     */

    private String code;
    private String message;
    private String mobile;
    private boolean success;
    private String uToken;
    private String goldAmount;

    public String getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(String goldAmount) {
        this.goldAmount = goldAmount;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUToken() {
        return uToken;
    }

    public void setUToken(String uToken) {
        this.uToken = uToken;
    }
}
