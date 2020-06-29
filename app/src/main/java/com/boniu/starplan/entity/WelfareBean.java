package com.boniu.starplan.entity;

import java.util.List;

public class WelfareBean {


    private List<CumulativeSignConfigListBean> cumulativeSignConfigList;
    private List<GetRewardsListBean> getRewardsList;

    public List<CumulativeSignConfigListBean> getCumulativeSignConfigList() {
        return cumulativeSignConfigList;
    }

    public void setCumulativeSignConfigList(List<CumulativeSignConfigListBean> cumulativeSignConfigList) {
        this.cumulativeSignConfigList = cumulativeSignConfigList;
    }

    public List<GetRewardsListBean> getGetRewardsList() {
        return getRewardsList;
    }

    public void setGetRewardsList(List<GetRewardsListBean> getRewardsList) {
        this.getRewardsList = getRewardsList;
    }

    public static class CumulativeSignConfigListBean {
        /**
         * cumulativeSignAmount : 50
         * goldAmount : 50000
         */

        private int cumulativeSignAmount;
        private int goldAmount;

        public int getCumulativeSignAmount() {
            return cumulativeSignAmount;
        }

        public void setCumulativeSignAmount(int cumulativeSignAmount) {
            this.cumulativeSignAmount = cumulativeSignAmount;
        }

        public int getGoldAmount() {
            return goldAmount;
        }

        public void setGoldAmount(int goldAmount) {
            this.goldAmount = goldAmount;
        }
    }

    public static class GetRewardsListBean {
        /**
         * days : 50
         * status : 0
         */

        private int days;
        private int status;

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
