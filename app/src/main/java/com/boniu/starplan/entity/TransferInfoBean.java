package com.boniu.starplan.entity;

public class TransferInfoBean {

    /**
     * changeFlag : string   YES  NO
     * realName : string
     * receivedAccount : string
     */

    private String changeFlag;
    private String realName;
    private String receivedAccount;

    public String getChangeFlag() {
        return changeFlag;
    }

    public void setChangeFlag(String changeFlag) {
        this.changeFlag = changeFlag;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getReceivedAccount() {
        return receivedAccount;
    }

    public void setReceivedAccount(String receivedAccount) {
        this.receivedAccount = receivedAccount;
    }
}
