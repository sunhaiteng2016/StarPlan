package com.boniu.starplan.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.dialog.WithdrawalDialog;
import com.boniu.starplan.entity.PriceModel;
import com.boniu.starplan.utils.RlvManagerUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提现中心
 */
@Route(path = "/ui/WithdrawalActivity")
public class WithdrawalActivity extends BaseActivity {


    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_gold_coin)
    TextView tvGoldCoin;
    @BindView(R.id.rl_activity_new_user)
    RelativeLayout rlActivityNewUser;
    @BindView(R.id.rl_activity_day_one)
    RelativeLayout rlActivityDayOne;
    @BindView(R.id.rl_activity_day_ten)
    RelativeLayout rlActivityDayTen;
    @BindView(R.id.rlv_normal)
    RecyclerView rlvNormal;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.progress_bar_h)
    ProgressBar progressBarH;
    @BindView(R.id.tv_submit_s)
    TextView tvSubmitS;
    private List<PriceModel> list = new ArrayList<>();
    private CommonAdapter<PriceModel> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdrawal;
    }

    @Override
    public void init() {
        tvBarTitle.setText("提现中心");
        tvSubmit.setText("审核进度");
        tvSubmit.setVisibility(View.VISIBLE);
        initView();
    }

    private void initView() {
        RlvManagerUtils.createGridView(this, rlvNormal, 3);
        list.add(new PriceModel());
        list.add(new PriceModel());
        list.add(new PriceModel());
        list.add(new PriceModel());
        list.add(new PriceModel());
        adapter = new CommonAdapter<PriceModel>(this, R.layout.item_price_list, list) {

            @Override
            protected void convert(ViewHolder holder, PriceModel priceModel, int position) {
                if (position == 0) {
                    holder.setText(R.id.tv_price, "30元");
                }
                if (position == 1) {
                    holder.setText(R.id.tv_price, "50元");
                }
                if (position == 2) {
                    holder.setText(R.id.tv_price, "100元");
                }
                if (position == 3) {
                    holder.setText(R.id.tv_price, "200元");
                }
                if (position == 4) {
                    holder.setText(R.id.tv_price, "500元");
                }
                if (list.get(position).isSel) {
                    holder.setBackgroundRes(R.id.rl_activity_new_user, R.mipmap.duibk2);
                } else {
                    holder.setBackgroundRes(R.id.rl_activity_new_user, R.drawable.shape_board_f3);
                }
            }
        };
        rlvNormal.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                clearButtonSel();
                list.get(i).isSel = true;
                //其他的都不选中
                for (int j = 0; j < list.size(); j++) {
                    if (i != j) {
                        list.get(j).isSel = false;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_activity_new_user, R.id.rl_activity_day_one, R.id.rl_activity_day_ten, R.id.tv_submit, R.id.tv_submit_s})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_activity_new_user:
                rlActivityNewUser.setBackgroundResource(R.mipmap.duibk);
                rlActivityDayOne.setBackgroundResource(R.drawable.shape_board_f3);
                rlActivityDayTen.setBackgroundResource(R.drawable.shape_board_f3);
                clearAdapterSel();
                break;
            case R.id.rl_activity_day_one:
                rlActivityNewUser.setBackgroundResource(R.drawable.shape_board_f3);
                rlActivityDayOne.setBackgroundResource(R.mipmap.duibk);
                rlActivityDayTen.setBackgroundResource(R.drawable.shape_board_f3);
                clearAdapterSel();
                break;
            case R.id.rl_activity_day_ten:
                rlActivityNewUser.setBackgroundResource(R.drawable.shape_board_f3);
                rlActivityDayOne.setBackgroundResource(R.drawable.shape_board_f3);
                rlActivityDayTen.setBackgroundResource(R.mipmap.duibk);
                clearAdapterSel();
                break;
            case R.id.tv_submit:
                ARouter.getInstance().build("/ui/WithdrawalRecordActivity").navigation();
                break;
            case R.id.tv_submit_s:
                WithdrawalDialog dialog = new WithdrawalDialog(this);
                dialog.show();
                break;
        }
    }

    //擦除List选中状态
    private void clearAdapterSel() {
        for (PriceModel priceModel : list) {
            priceModel.isSel = false;
        }
        adapter.notifyDataSetChanged();
    }

    //擦除button选中状态
    private void clearButtonSel() {
        rlActivityNewUser.setBackgroundResource(R.drawable.shape_board_f3);
        rlActivityDayOne.setBackgroundResource(R.drawable.shape_board_f3);
        rlActivityDayTen.setBackgroundResource(R.drawable.shape_board_f3);
    }

    @OnClick(R.id.rl_back)
    public void onClick() {

    }
}
