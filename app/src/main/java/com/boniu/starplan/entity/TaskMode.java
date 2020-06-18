package com.boniu.starplan.entity;

import java.util.List;

public class TaskMode {

    /**
     * count : 2
     * rows : [{"durableTime":200,"icon":"logo2","id":22,"income":200,"mainTitle":"测试试玩赚2","subTitle":"测试试玩赚2","todayRemain":491},{"durableTime":100,"icon":"logo1","id":21,"income":100,"mainTitle":"测试试玩赚1","subTitle":"测试试玩赚1","todayRemain":475}]
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
         * durableTime : 200
         * icon : logo2
         * id : 22
         * income : 200
         * mainTitle : 测试试玩赚2
         * subTitle : 测试试玩赚2
         * todayRemain : 491
         */

        private int durableTime;
        private String icon;
        private int id;
        private int income;
        private String mainTitle;
        private String subTitle;
        private int todayRemain;

        public int getDurableTime() {
            return durableTime;
        }

        public void setDurableTime(int durableTime) {
            this.durableTime = durableTime;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public int getTodayRemain() {
            return todayRemain;
        }

        public void setTodayRemain(int todayRemain) {
            this.todayRemain = todayRemain;
        }
    }
}
