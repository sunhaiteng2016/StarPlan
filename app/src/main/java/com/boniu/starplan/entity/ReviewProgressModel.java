package com.boniu.starplan.entity;

import java.util.List;

public class ReviewProgressModel {

    /**
     * count : 0
     * rows : [{"createDate":"2020-06-30T10:28:36.985Z","icon":"string","income":0,"mainTitle":"string","remark":"string","status":0,"statusDesc":"string"}]
     */

    private int count;
    private List<RowsBean> rows;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * createDate : 2020-06-30T10:28:36.985Z
         * icon : string
         * income : 0
         * mainTitle : string
         * remark : string
         * status : 0
         * statusDesc : string
         */

        private String createDate;
        private String icon;
        private int income;
        private String mainTitle;
        private String remark;
        private int status;
        private String statusDesc;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getIncome() {
            return income;
        }

        public void setIncome(int income) {
            this.income = income;
        }

        public String getMainTitle() {
            return mainTitle;
        }

        public void setMainTitle(String mainTitle) {
            this.mainTitle = mainTitle;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatusDesc() {
            return statusDesc;
        }

        public void setStatusDesc(String statusDesc) {
            this.statusDesc = statusDesc;
        }
    }
}
