package com.boniu.starplan.entity;

import java.util.List;

public class SubmitAuditModel {

    /**
     * submitAudit : string
     * userMobile : string
     * userName : string
     * userTaskId : 0
     * userTaskImgsSaveParamSet : [{"imgUrl":"string","order":0}]
     */

    private String submitAudit;
    private String userMobile;
    private String userName;
    private int userTaskId;
    private List<UserTaskImgsSaveParamSetBean> userTaskImgsSaveParamSet;

    public String getSubmitAudit() {
        return submitAudit;
    }

    public void setSubmitAudit(String submitAudit) {
        this.submitAudit = submitAudit;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserTaskId() {
        return userTaskId;
    }

    public void setUserTaskId(int userTaskId) {
        this.userTaskId = userTaskId;
    }

    public List<UserTaskImgsSaveParamSetBean> getUserTaskImgsSaveParamSet() {
        return userTaskImgsSaveParamSet;
    }

    public void setUserTaskImgsSaveParamSet(List<UserTaskImgsSaveParamSetBean> userTaskImgsSaveParamSet) {
        this.userTaskImgsSaveParamSet = userTaskImgsSaveParamSet;
    }

    public static class UserTaskImgsSaveParamSetBean {
        /**
         * imgUrl : string
         * order : 0
         */

        private String imgUrl;
        private int order;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
    }
}
