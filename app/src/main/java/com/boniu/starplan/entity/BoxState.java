package com.boniu.starplan.entity;

public class BoxState {

    /**
     * goldCount : 500
     * id : 3
     * severalTimes : 2
     * status : 0
     * type : 1
     * typeValue : dayTask
     */

    private int goldCount;
    private int id;
    private int severalTimes;
    private int status;
    private int type;
    private String typeValue;

    public int getGoldCount() {
        return goldCount;
    }

    public void setGoldCount(int goldCount) {
        this.goldCount = goldCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeveralTimes() {
        return severalTimes;
    }

    public void setSeveralTimes(int severalTimes) {
        this.severalTimes = severalTimes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }
}
