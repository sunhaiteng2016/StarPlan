package com.boniu.starplan.entity;

public class TaskDetailsModel {


    /**
     * applyId : 65fed623e9724f7d936c02a3ddc30704
     * expiryTime : 1593407632000
     * id : 152
     * income : 10500
     * taskDetailVO : {"cycleDays":840,"durableTime":10500,"exciteDouble":false,"icon":"logo105","id":408,"income":10500,"mainTitle":"测试试玩赚105","subTitle":"测试试玩赚105","takeMax":105000,"takeMaxRemain":104999,"todayRemain":17,"tryTaskVO":{"addr":"https://image.baidu.com","addrType":1,"appOpenUrl":"https://163.com","taskId":408,"tryTime":10},"type":1}
     * taskId : 408
     * taskType : 1
     * userId : 1
     */

    private String applyId;
    private long expiryTime;
    private int id;
    private int income;
    private TaskDetailVOBean taskDetailVO;
    private int taskId;
    private int taskType;
    private int userId;
    private boolean  isDouble;

    public boolean isDouble() {
        return isDouble;
    }

    public void setDouble(boolean aDouble) {
        isDouble = aDouble;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
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

    public TaskDetailVOBean getTaskDetailVO() {
        return taskDetailVO;
    }

    public void setTaskDetailVO(TaskDetailVOBean taskDetailVO) {
        this.taskDetailVO = taskDetailVO;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static class TaskDetailVOBean {
        /**
         * cycleDays : 840
         * durableTime : 10500
         * exciteDouble : false
         * icon : logo105
         * id : 408
         * income : 10500
         * mainTitle : 测试试玩赚105
         * subTitle : 测试试玩赚105
         * takeMax : 105000
         * takeMaxRemain : 104999
         * todayRemain : 17
         * tryTaskVO : {"addr":"https://image.baidu.com","addrType":1,"appOpenUrl":"https://163.com","taskId":408,"tryTime":10}
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
        private String remark;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

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
             * taskId : 408
             * tryTime : 10
             */

            private String addr;
            private int addrType;
            private String appOpenUrl;
            private int taskId;
            private int tryTime;
            private String schemeUrl;

            public String getSchemeUrl() {
                return schemeUrl;
            }

            public void setSchemeUrl(String schemeUrl) {
                this.schemeUrl = schemeUrl;
            }

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
}
