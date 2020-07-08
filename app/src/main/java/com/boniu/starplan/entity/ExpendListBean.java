package com.boniu.starplan.entity;

import java.util.ArrayList;

public class ExpendListBean {
    private String month;
    private ArrayList<WithDrawalListBean> list;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public ArrayList<WithDrawalListBean> getList() {
        return list;
    }

    public void setList(ArrayList<WithDrawalListBean> list) {
        this.list = list;
    }
}
