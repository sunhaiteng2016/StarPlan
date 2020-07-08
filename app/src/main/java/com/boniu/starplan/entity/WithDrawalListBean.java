package com.boniu.starplan.entity;

//提现记录
public class WithDrawalListBean {

    /**
     * createTime : 2020-06-23T07:49:34.501Z
     * goldAmount : 0
     * remark : string
     * state : string
     * userTaskType : string
     */

    private long createTime;
    private long goldAmount;
    private String remark = "";
    private String state;
    private String stateDes;
    private String userTaskType;
    private String withdrawalAmount;
    private boolean isempty = false;

    public boolean isIsempty() {
        return isempty;
    }

    public void setIsempty(boolean isempty) {
        this.isempty = isempty;
    }

    public String getStateDes() {
        return stateDes;
    }

    public void setStateDes(String stateDes) {
        this.stateDes = stateDes;
    }

    public String getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(String withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(long goldAmount) {
        this.goldAmount = goldAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserTaskType() {
        return userTaskType;
    }

    public void setUserTaskType(String userTaskType) {
        this.userTaskType = userTaskType;
    }
}
