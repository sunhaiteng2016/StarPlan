package com.boniu.starplan.entity;

public class TaskDetailsModel {

    /**
     * cycleDays : 1
     * durableTime : 4600
     * exciteDouble : false
     * icon : logo46
     * id : 146
     * income : 4600
     * mainTitle : 测试试玩赚46
     * subTitle : 测试试玩赚46
     * takeMax : 500
     * takeMaxRemain : 500
     * todayRemain : 16
     * tryTaskVO : {"addr":"https://image.baidu.com","addrType":1,"appOpenUrl":"https://163.com","taskId":146,"tryTime":10}
     * type : 1
     */

    private int cycleDays;
    private int durableTime;
    private boolean exciteDouble;
    private String icon;
    private int id;
    private int income;
    private String mainTitle;
    private String subTitle;
    private int takeMax;
    private int takeMaxRemain;
    private int todayRemain;
    private TryTaskVOBean tryTaskVO;
    private int type;

    public int getCycleDays() {
        return cycleDays;
    }

    public void setCycleDays(int cycleDays) {
        this.cycleDays = cycleDays;
    }

    public int getDurableTime() {
        return durableTime;
    }

    public void setDurableTime(int durableTime) {
        this.durableTime = durableTime;
    }

    public boolean isExciteDouble() {
        return exciteDouble;
    }

    public void setExciteDouble(boolean exciteDouble) {
        this.exciteDouble = exciteDouble;
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

    public int getTakeMax() {
        return takeMax;
    }

    public void setTakeMax(int takeMax) {
        this.takeMax = takeMax;
    }

    public int getTakeMaxRemain() {
        return takeMaxRemain;
    }

    public void setTakeMaxRemain(int takeMaxRemain) {
        this.takeMaxRemain = takeMaxRemain;
    }

    public int getTodayRemain() {
        return todayRemain;
    }

    public void setTodayRemain(int todayRemain) {
        this.todayRemain = todayRemain;
    }

    public TryTaskVOBean getTryTaskVO() {
        return tryTaskVO;
    }

    public void setTryTaskVO(TryTaskVOBean tryTaskVO) {
        this.tryTaskVO = tryTaskVO;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class TryTaskVOBean {
        /**
         * addr : https://image.baidu.com
         * addrType : 1
         * appOpenUrl : https://163.com
         * taskId : 146
         * tryTime : 10
         */

        private String addr;
        private int addrType;
        private String appOpenUrl;
        private int taskId;
        private int tryTime;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public int getAddrType() {
            return addrType;
        }

        public void setAddrType(int addrType) {
            this.addrType = addrType;
        }

        public String getAppOpenUrl() {
            return appOpenUrl;
        }

        public void setAppOpenUrl(String appOpenUrl) {
            this.appOpenUrl = appOpenUrl;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public int getTryTime() {
            return tryTime;
        }

        public void setTryTime(int tryTime) {
            this.tryTime = tryTime;
        }
    }
}
