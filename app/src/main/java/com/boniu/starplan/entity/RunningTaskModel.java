package com.boniu.starplan.entity;

public class RunningTaskModel {


    /**
     * applyId : f372957ef7a144f7a45af3d3ddd0825a
     * expiryTime : 1592916990000
     * icon : logo146
     * id : 8
     * income : 14600
     * mainTitle : 测试试玩赚146
     * subTitle : 测试试玩赚146
     * taskId : 449
     * taskType : 1
     */

    private String applyId;
    private long expiryTime;
    private String icon;
    private int id;
    private int income;
    private String mainTitle;
    private String subTitle;
    private int taskId;
    private int taskType;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }
}
