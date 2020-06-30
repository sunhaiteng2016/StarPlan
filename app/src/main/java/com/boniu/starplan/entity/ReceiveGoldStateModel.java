package com.boniu.starplan.entity;

public class ReceiveGoldStateModel {


    /**
     * code : 1
     * message : 金币发放成功
     * success : true
     */

    private String code;
    private String message;
    private boolean success;

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
