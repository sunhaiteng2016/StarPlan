package com.boniu.starplan.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.adapter.ExpandableListViewAdapter;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.entity.ProfitModel;
import com.boniu.starplan.utils.RlvManagerUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的钱包
 */
@Route(path = "/ui/MyWalletActivity")
public class MyWalletActivity extends BaseActivity {


    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_gold_str)
    TextView tvGoldStr;
    @BindView(R.id.tv_gold_num)
    TextView tvGoldNum;
    @BindView(R.id.tv_exchange)
    TextView tvExchange;
    @BindView(R.id.tv_can_take_gold)
    TextView tvCanTakeGold;
    @BindView(R.id.tab_bar)
    TableLayout tabBar;
    @BindView(R.id.rlv)
    RecyclerView rlv;
    @BindView(R.id.elv)
    ExpandableListView elv;
    private List<ProfitModel> list = new ArrayList<>();
    private CommonAdapter<ProfitModel> rlvAdapter;
    private ExpandableListViewAdapter elvAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    public void init() {
        tvBarTitle.setText("我的钱包");
        tvSubmit.setText("刷新");
        tvSubmit.setVisibility(View.VISIBLE);
        initView();
    }

    private void initView() {
        RlvManagerUtils.createLinearLayout(this, rlv);
        list.add(new ProfitModel());
        list.add(new ProfitModel());
        list.add(new ProfitModel());
        list.add(new ProfitModel());
        list.add(new ProfitModel());
        list.add(new ProfitModel());
        rlvAdapter = new CommonAdapter<ProfitModel>(this, R.layout.item_profit, list) {

            @Override
            protected void convert(ViewHolder holder, ProfitModel profitModel, int position) {

            }
        };
        rlv.setAdapter(rlvAdapter);

        elvAdapter = new ExpandableListViewAdapter(this);
        elv.setAdapter(elvAdapter);
        elv.setGroupIndicator(null);

        for (int i = 0; i < 3; i++) {
            elv.expandGroup(i);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_back, R.id.tv_submit,R.id.tv_exchange})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_submit:

                break;
            case R.id.tv_exchange:
                ARouter.getInstance().build("/ui/WithdrawalActivity").navigation();
                break;
        }
    }
}
