package com.boniu.starplan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.boniu.starplan.R;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {


    public Context context;
    private View groupView;
    private View childView;


    public ExpandableListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return 3;
    }

    @Override
    public int getChildrenCount(int i) {
        return 2;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        groupView = LayoutInflater.from(context).inflate(R.layout.item_group, null);
        return groupView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        childView = LayoutInflater.from(context).inflate(R.layout.item_child, null);
        return childView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
