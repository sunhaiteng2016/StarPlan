package com.boniu.starplan.ui;


import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.entity.RecordBean;
import com.boniu.starplan.utils.RlvManagerUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提现记录
 */
@Route(path = "/ui/WithdrawalRecordActivity")
public class WithdrawalRecordActivity extends BaseActivity {

    @BindView(R.id.tv_day_money)
    TextView tvDayMoney;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.rlv)
    RecyclerView rlv;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;

    private List<RecordBean> lists = new ArrayList<>();
    private CommonAdapter<RecordBean> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdrawal_record;
    }

    @Override
    public void init() {
        tvBarTitle.setText("提现记录");
        initView();
    }

    private void initView() {
        RlvManagerUtils.createLinearLayout(this, rlv);
        lists.add(new RecordBean());
        lists.add(new RecordBean());
        lists.add(new RecordBean());
        lists.add(new RecordBean());
        adapter = new CommonAdapter<RecordBean>(this, R.layout.item_record, lists) {

            @Override
            protected void convert(ViewHolder holder, RecordBean recordBean, int position) {

            }
        };
        rlv.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.rl_back)
    public void onClick() {
        finish();
    }
}
