package com.boniu.starplan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.boniu.starplan.R;
import com.boniu.starplan.entity.ExpendModel;
import com.boniu.starplan.utils.DateTimeUtils;

import java.util.List;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {


    public Context context;
    private View groupView;
    private View childView;
    private List<ExpendModel> list;


    public ExpandableListViewAdapter(Context context, List<ExpendModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (list.size() == 0) return 0;
        return list.get(i).getList().size();
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
        TextView groupName = groupView.findViewById(R.id.tv_group_name);
        groupName.setText(list.get(i).getMonth() + "月");
        return groupView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        childView = LayoutInflater.from(context).inflate(R.layout.item_child, null);
        TextView tvTitle = childView.findViewById(R.id.tv_title);
        TextView tvGoldNum = childView.findViewById(R.id.tv_gold_num);
        TextView tvState = childView.findViewById(R.id.tv_state);
        TextView tvTime = childView.findViewById(R.id.tv_time);
        TextView tvDes = childView.findViewById(R.id.tv_des);

        tvTitle.setText(list.get(i).getList().get(i1).getUserTaskType());
        tvGoldNum.setText("-" + list.get(i).getList().get(i1).getGoldAmount()+"金币");
        tvTime.setText(DateTimeUtils.format(list.get(i).getList().get(i1).getCreateTime(), DateTimeUtils.FORMAT_LONG_CN));
        tvDes.setText(list.get(i).getList().get(i1).getStateDes());
        String state = list.get(i).getList().get(i1).getState();
        if (state.equals("1")){
            tvDes.setVisibility(View.VISIBLE);
        }else{
            tvDes.setVisibility(View.GONE);
        }
        tvState.setText(list.get(i).getList().get(i1).getRemark());
        switch (state) {
            case "2":
            case "5":
                tvDes.setTextColor(context.getResources().getColor(R.color.black));
                tvDes.setText("兑换成功");
                break;
            case "1":
                tvDes.setTextColor(context.getResources().getColor(R.color.FA6400));
                tvDes.setText("审核中");
                break;
            default:
                tvDes.setTextColor(context.getResources().getColor(R.color.FF5151));
                tvDes.setText("兑换失败");
                break;
        }
        return childView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
