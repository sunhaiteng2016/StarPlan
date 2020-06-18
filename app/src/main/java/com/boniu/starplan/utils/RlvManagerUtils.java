package com.boniu.starplan.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

public class RlvManagerUtils {

    @SuppressLint("WrongConstant")
    public static void createLinearLayout(Context mContext, RecyclerView rlv) {
        rlv.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        //设置布局管理器
        rlv.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
    }

    @SuppressLint("WrongConstant")
    public static void createLinearLayoutHorizontal(Context mContext, RecyclerView rlv) {
        rlv.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        //设置布局管理器
        rlv.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
    }

    //表格布局
    public static void createGridView(Context mContext, RecyclerView rlv, int number) {
        rlv.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, number);
        //设置布局管理器
        rlv.setLayoutManager(layoutManager);
    }
}
