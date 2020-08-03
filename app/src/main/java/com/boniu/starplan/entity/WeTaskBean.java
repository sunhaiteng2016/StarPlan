package com.boniu.starplan.entity;

public class WeTaskBean {

    /**
     * addition : 0
     * cycleDays : 2
     * icon : http://boniuearth.oss-cn-hangzhou.aliyuncs.com/380791785eec4efbbfe374c883d15208.jpg
     * income : 5000
     * modelId : 2
     * subTitle : 有态度的学习圈子
     * taskId : 624
     * taskName : 知乎（测试补齐）
     * taskViewStatus : 0
     * todayRemain : 20
     * type : 2
     */

    private int addition;
    private int cycleDays;
    private String icon;
    private int income;
    private int modelId;
    private String subTitle;
    private int taskId;
    private String taskName;
    private int taskViewStatus;
    private int todayRemain;
    private int type;


    private boolean keepLive;
    private String addr;
    private String appOpenUrl;
    private String schemeUrl;


    public boolean isKeepLive() {
        return keepLive;
    }

    public void setKeepLive(boolean keepLive) {
        this.keepLive = keepLive;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAppOpenUrl() {
        return appOpenUrl;
    }

    public void setAppOpenUrl(String appOpenUrl) {
        this.appOpenUrl = appOpenUrl;
    }

    public String getSchemeUrl() {
        return schemeUrl;
    }

    public void setSchemeUrl(String schemeUrl) {
        this.schemeUrl = schemeUrl;
    }

    private String applySource;

    public String getApplySource() {
        return applySource;
    }

    public void setApplySource(String applySource) {
        this.applySource = applySource;
    }

    public int getAddition() {
        return addition;
    }

    public void setAddition(int addition) {
        this.addition = addition;
    }

    public int getCycleDays() {
        return cycleDays;
    }

    public void setCycleDays(int cycleDays) {
        this.cycleDays = cycleDays;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskViewStatus() {
        return taskViewStatus;
    }

    public void setTaskViewStatus(int taskViewStatus) {
        this.taskViewStatus = taskViewStatus;
    }

    public int getTodayRemain() {
        return todayRemain;
    }

    public void setTodayRemain(int todayRemain) {
        this.todayRemain = todayRemain;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
