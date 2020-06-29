package com.boniu.starplan.entity;

import java.util.List;

public class ExpendModel {

    /**
     * month : 06
     * list : [{"createTime":1592895602000,"goldAmount":1,"month":"06","state":"1","stateDes":"审核中","userTaskType":"new_user"},{"createTime":1592189411000,"goldAmount":88,"month":"06","state":"1","stateDes":"审核中","userTaskType":"new_user"}]
     */

    private String month;
    private List<ListBean> list;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * createTime : 1592895602000
         * goldAmount : 1
         * month : 06
         * state : 1
         * stateDes : 审核中
         * userTaskType : new_user
         */

        private long createTime;
        private int goldAmount;
        private String month;
        private String state;
        private String stateDes;
        private String userTaskType;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getGoldAmount() {
            return goldAmount;
        }

        public void setGoldAmount(int goldAmount) {
            this.goldAmount = goldAmount;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStateDes() {
            return stateDes;
        }

        public void setStateDes(String stateDes) {
            this.stateDes = stateDes;
        }

        public String getUserTaskType() {
            return userTaskType;
        }

        public void setUserTaskType(String userTaskType) {
            this.userTaskType = userTaskType;
        }
    }
}
