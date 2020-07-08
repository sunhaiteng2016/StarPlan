package com.boniu.starplan.entity;

public class BeanTaskModel {

    /**
     * taskId : 423
     * userTaskId : 669
     * startTime : 1594101459249
     * message : null
     * isSucceed : true
     */

    private int taskId;
    private int userTaskId;
    private long startTime;
    private Object message;
    private boolean isSucceed;

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

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public boolean isIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(boolean isSucceed) {
        this.isSucceed = isSucceed;
    }
}
