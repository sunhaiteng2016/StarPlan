package com.boniu.starplan.entity;

import java.util.List;

public class MainTask {


    private List<DayTaskBean> dayTask;
    private List<NewUserTaskBean> newUserTask;

    public List<DayTaskBean> getDayTask() {
        return dayTask;
    }

    public void setDayTask(List<DayTaskBean> dayTask) {
        this.dayTask = dayTask;
    }

    public List<NewUserTaskBean> getNewUserTask() {
        return newUserTask;
    }

    public void setNewUserTask(List<NewUserTaskBean> newUserTask) {
        this.newUserTask = newUserTask;
    }

    public static class DayTaskBean {
        /**
         * cycleDays : 20
         * icon : logo4
         * income : 400
         * subTitle : 测试试玩赚4
         * taskId : 104
         * taskName : 测试试玩赚4
         * taskViewStatus : 0
         * todayRemain : 305
         * type : 1
         */

        private int cycleDays;
        private String icon;
        private int income;
        private String subTitle;
        private int taskId;
        private String taskName;
        private int taskViewStatus;
        private int todayRemain;
        private int type;

        public int getCycleDays() {
            return cycleDays;
        }

        public void setCycleDays(int cycleDays) {
            this.cycleDays = cycleDays;
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

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public int getTaskViewStatus() {
            return taskViewStatus;
        }

        public void setTaskViewStatus(int taskViewStatus) {
            this.taskViewStatus = taskViewStatus;
        }

        public int getTodayRemain() {
            return todayRemain;
        }

        public void setTodayRemain(int todayRemain) {
            this.todayRemain = todayRemain;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class NewUserTaskBean {
        /**
         * cycleDays : 20
         * icon : logo4
         * income : 400
         * subTitle : 测试试玩赚4
         * taskId : 104
         * taskName : 测试试玩赚4
         * taskViewStatus : 0
         * todayRemain : 305
         * type : 1
         */

        private int cycleDays;
        private String icon;
        private int income;
        private String subTitle;
        private int taskId;
        private String taskName;
        private int taskViewStatus;
        private int todayRemain;
        private int type;

        public int getCycleDays() {
            return cycleDays;
        }

        public void setCycleDays(int cycleDays) {
            this.cycleDays = cycleDays;
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

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public int getTaskViewStatus() {
            return taskViewStatus;
        }

        public void setTaskViewStatus(int taskViewStatus) {
            this.taskViewStatus = taskViewStatus;
        }

        public int getTodayRemain() {
            return todayRemain;
        }

        public void setTodayRemain(int todayRemain) {
            this.todayRemain = todayRemain;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
