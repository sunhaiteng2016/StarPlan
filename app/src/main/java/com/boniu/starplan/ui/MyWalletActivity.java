package com.boniu.starplan.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.adapter.ExpandableListViewAdapter;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.LoadingDialog;
import com.boniu.starplan.entity.ExpendModel;
import com.boniu.starplan.entity.MyGoldBean;
import com.boniu.starplan.entity.ProfitModel;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.DateTimeUtils;
import com.boniu.starplan.utils.RlvManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;

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
    @BindView(R.id.recyclerView)
    RecyclerView rlv;
    @BindView(R.id.expandableList)
    ExpandableListView elv;
    @BindView(R.id.tv_goldBalance)
    TextView tvGoldBalance;
    @BindView(R.id.tv_exchange)
    TextView tvExchange;
    @BindView(R.id.tv_availableBalance)
    TextView tvAvailableBalance;
    @BindView(R.id.tv_todayEarnGoldAmount)
    TextView tvTodayEarnGoldAmount;
    @BindView(R.id.tv_accumulateEarnGoldAmount)
    TextView tvAccumulateEarnGoldAmount;
    @BindView(R.id.tv_accumulateExpendGoldAmount)
    TextView tvAccumulateExpendGoldAmount;
    RecyclerView recyclerView;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.f_view)
    CardView fView;
    @BindView(R.id.tv_shouyi)
    TextView tvShouyi;
    @BindView(R.id.tv_zhichu)
    TextView tvZhichu;
    @BindView(R.id.view_shouyi)
    View viewShouyi;
    @BindView(R.id.view_zhichu)
    View viewZhichu;
    private List<ProfitModel> list = new ArrayList<>();
    private CommonAdapter<ProfitModel> rlvAdapter;
    private ExpandableListViewAdapter elvAdapter;
    private LoadingDialog loadingDialog;
    private List<ExpendModel> expendList1 = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    public void init() {
        tvBarTitle.setText("我的钱包");
        tvSubmit.setText("刷新");
        tvSubmit.setVisibility(View.VISIBLE);
        loadingDialog = new LoadingDialog(this);
        initView();
        getDates();
    }

    private void getDates() {
        loadingDialog.show();
        //我的账户资料
        RxHttp.postEncryptJson(ComParamContact.Main.account)
                .asResponse(String.class)
                .subscribe(s -> {
                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                    MyGoldBean myGoldBean = new Gson().fromJson(result, MyGoldBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismiss();
                            tvGoldBalance.setText(myGoldBean.getGoldBalance() + "");
                            tvAccumulateEarnGoldAmount.setText(myGoldBean.getAccumulateEarnGoldAmount() + "");
                            tvAccumulateExpendGoldAmount.setText(myGoldBean.getAccumulateExpendGoldAmount() + "");
                            tvAvailableBalance.setText("可提现金币：" + myGoldBean.getAvailableBalance());
                            tvTodayEarnGoldAmount.setText(myGoldBean.getTodayEarnGoldAmount() + "");
                        }
                    });
                    //设置签到数据
                }, (OnError) error -> {
                    error.show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismiss();
                        }
                    });
                });
        //收益明细
        RxHttp.postEncryptJson(ComParamContact.Main.earning)
                .asResponse(String.class)
                .subscribe(s -> {
                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                    ArrayList<ProfitModel> expendlist = new Gson().fromJson(result, new TypeToken<List<ProfitModel>>() {
                    }.getType());
                    list.clear();
                    list.addAll(expendlist);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rlvAdapter.notifyDataSetChanged();
                        }
                    });
                    //设置签到数据
                }, (OnError) error -> {
                    error.show();
                });
        //支出明细
        RxHttp.postEncryptJson(ComParamContact.Main.expend)
                .asResponse(String.class)
                .subscribe(s -> {
                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                    ArrayList<ExpendModel> expendlist = new Gson().fromJson(result, new TypeToken<List<ExpendModel>>() {
                    }.getType());
                    expendList1.clear();
                    expendList1.addAll(expendlist);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            elvAdapter.notifyDataSetChanged();
                        }
                    });
                    //设置签到数据
                }, (OnError) error -> {
                    error.show();
                });
    }

    private void initView() {
        RlvManagerUtils.createLinearLayout(this, rlv);
        rlvAdapter = new CommonAdapter<ProfitModel>(this, R.layout.item_profit, list) {

            @Override
            protected void convert(ViewHolder holder, ProfitModel profitModel, int position) {
                holder.setText(R.id.tv_title, profitModel.getUserTaskTypeName()).setText(R.id.tv_gold_num, "+" + profitModel.getGoldAmount()).setText(R.id.tv_time,  DateTimeUtils.format(profitModel.getCreateTime(), DateTimeUtils.FORMAT_LONG_CN));
            }
        };
        rlv.setAdapter(rlvAdapter);

        elvAdapter = new ExpandableListViewAdapter(this, expendList1);
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

    @OnClick({R.id.rl_back, R.id.tv_submit, R.id.tv_exchange, R.id.tv_shouyi, R.id.tv_zhichu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_submit:
                getDates();
                break;
            case R.id.tv_shouyi:
                viewShouyi.setVisibility(View.VISIBLE);
                viewZhichu.setVisibility(View.GONE);
                tvShouyi.setTextColor(getResources().getColor(R.color.text_33));
                tvZhichu.setTextColor(getResources().getColor(R.color.text_99));
                rlv.setVisibility(View.VISIBLE);
                elv.setVisibility(View.GONE);
                break;
            case R.id.tv_zhichu:
                viewShouyi.setVisibility(View.GONE);
                viewZhichu.setVisibility(View.VISIBLE);
                tvShouyi.setTextColor(getResources().getColor(R.color.text_99));
                tvZhichu.setTextColor(getResources().getColor(R.color.text_33));
                rlv.setVisibility(View.GONE);
                elv.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_exchange:
                ARouter.getInstance().build("/ui/WithdrawalActivity").navigation();
                break;
        }
    }
}
