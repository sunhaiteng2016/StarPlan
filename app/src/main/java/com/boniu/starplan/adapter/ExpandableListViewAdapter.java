package com.boniu.starplan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
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
        LinearLayout ll_ll = childView.findViewById(R.id.ll_ll);
        TextView tvGoldNum = childView.findViewById(R.id.tv_gold_num);
        TextView tvState = childView.findViewById(R.id.tv_state);
        TextView tvTime = childView.findViewById(R.id.tv_time);
        TextView tvDes = childView.findViewById(R.id.tv_des);
        TextView tv_no_data = childView.findViewById(R.id.tv_no_data);
        if (null == list.get(i).getList().get(i1).getState()) {
            tv_no_data.setVisibility(View.VISIBLE);
            ll_ll.setVisibility(View.GONE);
        } else {
            ll_ll.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
            tvTitle.setText(list.get(i).getList().get(i1).getUserTaskType());
            tvGoldNum.setText("-" + list.get(i).getList().get(i1).getGoldAmount() + "金币");
            tvTime.setText(DateTimeUtils.format(list.get(i).getList().get(i1).getCreateTime(), "yyyy-MM-dd HH:mm"));
            tvDes.setText(list.get(i).getList().get(i1).getRemark());
            String state = list.get(i).getList().get(i1).getState();
            if (state.equals("1") || state.equals("4")) {
                tvDes.setVisibility(View.VISIBLE);
            } else {
                tvDes.setVisibility(View.GONE);
            }
            tvState.setText(list.get(i).getList().get(i1).getStateDes());
            setStateColor(context, state, tvState);
        }

        return childView;
    }

    public static void setStateColor(Context mContext, String state, TextView textview) {
        switch (state) {
            case "2":
            case "4":
            case "1":
                textview.setText("兑换中");
                textview.setTextColor(mContext.getResources().getColor(R.color.FA6400));
                break;
            case "5":
                textview.setText("兑换成功");
                textview.setTextColor(mContext.getResources().getColor(R.color.black));
                break;
            default:
                textview.setText("兑换失败");
                textview.setTextColor(mContext.getResources().getColor(R.color.FF5151));
                break;
        }
    }

    /**
     * 提现记录
     *
     * @param mContext
     * @param state
     * @param textview 提现记录： 审核中，审核失败，提现中，提现成功，提现失败
     */
    public static void setTixianRecordStateColor(Context mContext, String state, TextView textview) {
        switch (state) {
            case "2":
            case "4":
                textview.setText("提现中");
                textview.setTextColor(mContext.getResources().getColor(R.color.FA6400));
                break;
            case "1":
                textview.setText("审核中");
                textview.setTextColor(mContext.getResources().getColor(R.color.FA6400));
                break;
            case "3":
                textview.setText("审核失败");
                textview.setTextColor(mContext.getResources().getColor(R.color.FF5151));
                break;
            case "5":
                textview.setTextColor(mContext.getResources().getColor(R.color.black));
                break;
            default:
                textview.setText("兑换失败");
                textview.setTextColor(mContext.getResources().getColor(R.color.FF5151));
                break;
        }
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
