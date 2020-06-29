package com.boniu.starplan.entity;

public class ProfitModel {
    /**
     * createTime : 2020-06-23T07:48:13.399Z
     * goldAmount : 0
     * userTaskTypeName : string
     */

    private long createTime;
    private String goldAmount;
    private String userTaskTypeName;
    private String month;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(String goldAmount) {
        this.goldAmount = goldAmount;
    }

    public String getUserTaskTypeName() {
        return userTaskTypeName;
    }

    public void setUserTaskTypeName(String userTaskTypeName) {
        this.userTaskTypeName = userTaskTypeName;
    }
}
