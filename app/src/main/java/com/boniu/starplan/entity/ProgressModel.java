package com.boniu.starplan.entity;

public class ProgressModel {

    /**
     * arrived : 0
     * desc : 最近连续签到10天并且每天完成5个任务的用户，即可获得提现机会，提现后可重新获得提现机会
     * name : 10元连续活跃专享
     * target : 10
     * type : ten_series
     */

    private int arrived;
    private String desc;
    private String name;
    private int target;
    private String type;

    public int getArrived() {
        return arrived;
    }

    public void setArrived(int arrived) {
        this.arrived = arrived;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
