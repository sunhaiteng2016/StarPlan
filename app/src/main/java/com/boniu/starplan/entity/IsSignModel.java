package com.boniu.starplan.entity;

import java.util.List;

public class IsSignModel {


    /**
     * code : 0
     * message : null
     * success : true
     * continuousSign : 1
     * cumulativeSign : 54
     * list : [{"weekSign":1,"weekSignGold":38,"isSign":true,"type":"normal","isReceive":-1,"isDouble":false,"multi":null,"doubleGold":null,"boxId":null},{"weekSign":2,"weekSignGold":68,"isSign":false,"type":"normal","isReceive":-1,"isDouble":false,"multi":null,"doubleGold":null,"boxId":null},{"weekSign":3,"weekSignGold":388,"isSign":false,"type":"gif","isReceive":-1,"isDouble":false,"multi":null,"doubleGold":null,"boxId":null},{"weekSign":4,"weekSignGold":68,"isSign":false,"type":"normal","isReceive":-1,"isDouble":false,"multi":null,"doubleGold":null,"boxId":null},{"weekSign":5,"weekSignGold":188,"isSign":false,"type":"normal","isReceive":-1,"isDouble":false,"multi":null,"doubleGold":null,"boxId":null},{"weekSign":6,"weekSignGold":68,"isSign":false,"type":"normal","isReceive":-1,"isDouble":false,"multi":null,"doubleGold":null,"boxId":null},{"weekSign":7,"weekSignGold":888,"isSign":false,"type":"gif","isReceive":-1,"isDouble":false,"multi":null,"doubleGold":null,"boxId":null}]
     */

    private String code;
    private Object message;
    private boolean success;
    private int continuousSign;
    private int cumulativeSign;
    private List<ListBean> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getContinuousSign() {
        return continuousSign;
    }

    public void setContinuousSign(int continuousSign) {
        this.continuousSign = continuousSign;
    }

    public int getCumulativeSign() {
        return cumulativeSign;
    }

    public void setCumulativeSign(int cumulativeSign) {
        this.cumulativeSign = cumulativeSign;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * weekSign : 1
         * weekSignGold : 38
         * isSign : true
         * type : normal
         * isReceive : -1
         * isDouble : false
         * multi : null
         * doubleGold : null
         * boxId : null
         */

        private int weekSign;
        private int weekSignGold;
        private boolean isSign;
        private String type;
        private int isReceive;
        private boolean isDouble;
        private Object multi;
        private Object doubleGold;
        private Object boxId;

        public int getWeekSign() {
            return weekSign;
        }

        public void setWeekSign(int weekSign) {
            this.weekSign = weekSign;
        }

        public int getWeekSignGold() {
            return weekSignGold;
        }

        public void setWeekSignGold(int weekSignGold) {
            this.weekSignGold = weekSignGold;
        }

        public boolean isIsSign() {
            return isSign;
        }

        public void setIsSign(boolean isSign) {
            this.isSign = isSign;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getIsReceive() {
            return isReceive;
        }

        public void setIsReceive(int isReceive) {
            this.isReceive = isReceive;
        }

        public boolean isIsDouble() {
            return isDouble;
        }

        public void setIsDouble(boolean isDouble) {
            this.isDouble = isDouble;
        }

        public Object getMulti() {
            return multi;
        }

        public void setMulti(Object multi) {
            this.multi = multi;
        }

        public Object getDoubleGold() {
            return doubleGold;
        }

        public void setDoubleGold(Object doubleGold) {
            this.doubleGold = doubleGold;
        }

        public Object getBoxId() {
            return boxId;
        }

        public void setBoxId(Object boxId) {
            this.boxId = boxId;
        }
    }
}
