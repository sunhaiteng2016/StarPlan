package com.boniu.starplan.entity;

import java.io.Serializable;
import java.util.List;

public class ReceiveGoldModel {


    /**
     * applyId : 2be2bc8bc86d41019003ec6c8fd36998
     * expiryTime : 1593330842000
     * id : 88
     * income : 1000
     * startTime : 1593327242000
     * status : 1
     * taskDetailVO : {"auditTaskVO":{"auditMobile":false,"auditName":true,"auditPicture":false,"imgs":1,"showDesc":"玩法介绍,8","taskId":311},"cycleDays":72,"durableTime":3600,"exciteDouble":false,"icon":"logo8","id":311,"income":1000,"mainTitle":"测试领金币8","subTitle":"测试领金币8","takeMax":8000,"takeMaxRemain":7999,"taskImgsVO":[{"id":209,"imgUrl":"https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E8%8C%85%E5%8F%B0%E9%85%92&step_word=&hs=0&pn=4&spn=0&di=29370&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1875709657%2C876678375&os=3718617004%2C1108180257&simid=4047389678%2C539813287&adpicid=0&lpn=0&ln=1777&fr=&fmq=1591933671017_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fimage.yun-file.com%3A9191%2Fgroup1%2FM00%2F07%2FCA%2FrBMKKFvlMH7Ex3KUAAJsTomOT1c098.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bkwthj_z%26e3Bv54AzdH3Fuwk7AzdH3Fw88rvKxtpg-8db-8b8dbd88bl_z%26e3Bip4s&gsm=5&rpstart=0&rpnum=0&islist=&querylist=&force=undefined","sortId":0,"taskId":311}],"todayRemain":97,"type":2}
     * taskId : 311
     * taskType : 2
     * userId : 2
     * userTaskImgsList : []
     */

    private String applyId;
    private long expiryTime;
    private int id;
    private int income;
    private long startTime;
    private int status;
    private TaskDetailVOBean taskDetailVO;
    private int taskId;
    private int taskType;
    private int userId;
    private List<?> userTaskImgsList;

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

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public List<?> getUserTaskImgsList() {
        return userTaskImgsList;
    }

    public void setUserTaskImgsList(List<?> userTaskImgsList) {
        this.userTaskImgsList = userTaskImgsList;
    }

    public static class TaskDetailVOBean {
        /**
         * auditTaskVO : {"auditMobile":false,"auditName":true,"auditPicture":false,"imgs":1,"showDesc":"玩法介绍,8","taskId":311}
         * cycleDays : 72
         * durableTime : 3600
         * exciteDouble : false
         * icon : logo8
         * id : 311
         * income : 1000
         * mainTitle : 测试领金币8
         * subTitle : 测试领金币8
         * takeMax : 8000
         * takeMaxRemain : 7999
         * taskImgsVO : [{"id":209,"imgUrl":"https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E8%8C%85%E5%8F%B0%E9%85%92&step_word=&hs=0&pn=4&spn=0&di=29370&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1875709657%2C876678375&os=3718617004%2C1108180257&simid=4047389678%2C539813287&adpicid=0&lpn=0&ln=1777&fr=&fmq=1591933671017_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fimage.yun-file.com%3A9191%2Fgroup1%2FM00%2F07%2FCA%2FrBMKKFvlMH7Ex3KUAAJsTomOT1c098.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bkwthj_z%26e3Bv54AzdH3Fuwk7AzdH3Fw88rvKxtpg-8db-8b8dbd88bl_z%26e3Bip4s&gsm=5&rpstart=0&rpnum=0&islist=&querylist=&force=undefined","sortId":0,"taskId":311}]
         * todayRemain : 97
         * type : 2
         */

        private AuditTaskVOBean auditTaskVO;
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
        private int type;
        private String remark;
        private List<TaskImgsVOBean> taskImgsVO;
        public void setMajorDesc(String majorDesc) {
            this.remark = majorDesc;
        }

        public String getMajorDesc() {
            return remark;
        }
        public AuditTaskVOBean getAuditTaskVO() {
            return auditTaskVO;
        }

        public void setAuditTaskVO(AuditTaskVOBean auditTaskVO) {
            this.auditTaskVO = auditTaskVO;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<TaskImgsVOBean> getTaskImgsVO() {
            return taskImgsVO;
        }

        public void setTaskImgsVO(List<TaskImgsVOBean> taskImgsVO) {
            this.taskImgsVO = taskImgsVO;
        }

        public static class AuditTaskVOBean {
            /**
             * auditMobile : false
             * auditName : true
             * auditPicture : false
             * imgs : 1
             * showDesc : 玩法介绍,8
             * taskId : 311
             */

            private boolean auditMobile;
            private boolean auditName;
            private boolean auditPicture;
            private int imgs;
            private String showDesc;

            private String toUrl;
            private int taskId;

            public String getToUrl() {
                return toUrl;
            }

            public void setToUrl(String toUrl) {
                this.toUrl = toUrl;
            }

            public boolean isAuditMobile() {
                return auditMobile;
            }

            public void setAuditMobile(boolean auditMobile) {
                this.auditMobile = auditMobile;
            }

            public boolean isAuditName() {
                return auditName;
            }

            public void setAuditName(boolean auditName) {
                this.auditName = auditName;
            }

            public boolean isAuditPicture() {
                return auditPicture;
            }

            public void setAuditPicture(boolean auditPicture) {
                this.auditPicture = auditPicture;
            }

            public int getImgs() {
                return imgs;
            }

            public void setImgs(int imgs) {
                this.imgs = imgs;
            }

            public String getShowDesc() {
                return showDesc;
            }



            public void setShowDesc(String showDesc) {
                this.showDesc = showDesc;
            }

            public int getTaskId() {
                return taskId;
            }

            public void setTaskId(int taskId) {
                this.taskId = taskId;
            }
        }

        public static class TaskImgsVOBean implements Serializable {
            /**
             * id : 209
             * imgUrl : https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E8%8C%85%E5%8F%B0%E9%85%92&step_word=&hs=0&pn=4&spn=0&di=29370&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1875709657%2C876678375&os=3718617004%2C1108180257&simid=4047389678%2C539813287&adpicid=0&lpn=0&ln=1777&fr=&fmq=1591933671017_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fimage.yun-file.com%3A9191%2Fgroup1%2FM00%2F07%2FCA%2FrBMKKFvlMH7Ex3KUAAJsTomOT1c098.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bkwthj_z%26e3Bv54AzdH3Fuwk7AzdH3Fw88rvKxtpg-8db-8b8dbd88bl_z%26e3Bip4s&gsm=5&rpstart=0&rpnum=0&islist=&querylist=&force=undefined
             * sortId : 0
             * taskId : 311
             */
            private static final long serialVersionUID = 1L;
            private int id;
            private String imgUrl;
            private int sortId;
            private int taskId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public int getSortId() {
                return sortId;
            }

            public void setSortId(int sortId) {
                this.sortId = sortId;
            }

            public int getTaskId() {
                return taskId;
            }

            public void setTaskId(int taskId) {
                this.taskId = taskId;
            }
        }
    }
}
