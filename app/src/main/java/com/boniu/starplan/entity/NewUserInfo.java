package com.boniu.starplan.entity;

public class NewUserInfo {

    /**
     * isNewUser : true
     * newUserAmount : 0
     * weekSignGoldAmount : 0
     */

    private boolean isNewUser;
    private boolean isPop;
    private int newUserAmount;
    private int weekSignGoldAmount;

    public boolean isIsNewUser() {
        return isNewUser;
    }

    public void setIsNewUser(boolean isNewUser) {
        this.isNewUser = isNewUser;
    }

    public int getNewUserAmount() {
        return newUserAmount;
    }

    public void setNewUserAmount(int newUserAmount) {
        this.newUserAmount = newUserAmount;
    }

    public int getWeekSignGoldAmount() {
        return weekSignGoldAmount;
    }

    public void setWeekSignGoldAmount(int weekSignGoldAmount) {
        this.weekSignGoldAmount = weekSignGoldAmount;
    }

    public boolean isPop() {
        return isPop;
    }

    public void setPop(boolean pop) {
        isPop = pop;
    }
}
