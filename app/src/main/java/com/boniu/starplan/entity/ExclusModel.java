package com.boniu.starplan.entity;

public class ExclusModel {


    /**
     * code : new_user
     * goldNum : 10000
     */

    private String code;
    private Long goldNum;
    private boolean isSel;

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getGoldNum() {
        return goldNum;
    }

    public void setGoldNum(Long goldNum) {
        this.goldNum = goldNum;
    }
}
