package com.boniu.starplan.entity;

import java.util.List;

public class SignModel {


    /**
     * continuousSign : 7
     * cumulativeSign : 11
     * list : [{"isDouble":false,"isReceive":-1,"isSign":true,"type":"normal","weekSign":1,"weekSignGold":38},{"isDouble":false,"isReceive":-1,"isSign":true,"type":"normal","weekSign":2,"weekSignGold":68},{"boxId":1,"doubleGold":500,"isDouble":true,"isReceive":0,"isSign":true,"multi":2,"type":"gif","weekSign":3,"weekSignGold":388},{"isDouble":false,"isReceive":-1,"isSign":true,"type":"normal","weekSign":4,"weekSignGold":68},{"isDouble":false,"isReceive":-1,"isSign":true,"type":"normal","weekSign":5,"weekSignGold":188},{"isDouble":false,"isReceive":-1,"isSign":true,"type":"normal","weekSign":6,"weekSignGold":68},{"boxId":5,"doubleGold":1000,"isDouble":true,"isReceive":0,"isSign":true,"multi":2,"type":"gif","weekSign":7,"weekSignGold":888}]
     */

    private int continuousSign;
    private int cumulativeSign;
    private List<ListBean> list;

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
         * isDouble : false
         * isReceive : -1
         * isSign : true
         * type : normal
         * weekSign : 1
         * weekSignGold : 38
         * boxId : 1
         * doubleGold : 500
         * multi : 2
         */

        private boolean isDouble;
        private int isReceive;
        private boolean isSign;
        private String type;
        private int weekSign;
        private int weekSignGold;
        private int boxId;
        private int doubleGold;
        private int multi;

        public boolean isIsDouble() {
            return isDouble;
        }

        public void setIsDouble(boolean isDouble) {
            this.isDouble = isDouble;
        }

        public int getIsReceive() {
            return isReceive;
        }

        public void setIsReceive(int isReceive) {
            this.isReceive = isReceive;
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

        public int getBoxId() {
            return boxId;
        }

        public void setBoxId(int boxId) {
            this.boxId = boxId;
        }

        public int getDoubleGold() {
            return doubleGold;
        }

        public void setDoubleGold(int doubleGold) {
            this.doubleGold = doubleGold;
        }

        public int getMulti() {
            return multi;
        }

        public void setMulti(int multi) {
            this.multi = multi;
        }
    }
}
