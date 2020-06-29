package com.boniu.starplan.entity;

public class WithDrawalListBean {

    /**
     * createTime : 1592895602000
     * goldAmount : 1
     * month : 06
     * state : 1
     * stateDes : 审核中
     * userTaskType : 最近连续签到5天并且每天完成5个任务的用户，即可获得提现机会，提现后可重新获得提现机会
     * withdrawalAmount : 0.0
     */

    private long createTime;
    private int goldAmount;
    private String month;
    private String state;
    private String stateDes;
    private String userTaskType;
    private String remark;
    private double withdrawalAmount;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateDes() {
        return stateDes;
    }

    public void setStateDes(String stateDes) {
        this.stateDes = stateDes;
    }

    public String getUserTaskType() {
        return userTaskType;
    }

    public void setUserTaskType(String userTaskType) {
        this.userTaskType = userTaskType;
    }

    public double getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(double withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }
}
