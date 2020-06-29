package com.boniu.starplan.entity;

public class ApplyTask {


    /**
     * isExist : true
     * isSucceed : false
     * taskId : 307
     * userTaskId : 140
     */

    private boolean isExist;
    private boolean isSucceed;
    private int taskId;
    private int userTaskId;

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
}
