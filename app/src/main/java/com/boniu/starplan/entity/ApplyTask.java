package com.boniu.starplan.entity;

public class ApplyTask {


    /**
     * isExist : false
     * isSucceed : false
     * message : 活动火爆，该任务已被抢完，请明日再来
     * todayRemain : 0
     */

    private boolean isExist;
    private boolean isSucceed;
    private String message;
    private int todayRemain;
    private  int userTaskId;
    private int taskId;


    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getUserTaskId() {
        return userTaskId;
    }

    public void setUserTaskId(int userTaskId) {
        this.userTaskId = userTaskId;
    }

    public boolean isIsExist() {
        return isExist;
    }

    public void setIsExist(boolean isExist) {
        this.isExist = isExist;
    }

    public boolean isIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(boolean isSucceed) {
        this.isSucceed = isSucceed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTodayRemain() {
        return todayRemain;
    }

    public void setTodayRemain(int todayRemain) {
        this.todayRemain = todayRemain;
    }
}
