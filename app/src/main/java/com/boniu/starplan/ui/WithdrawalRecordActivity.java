package com.boniu.starplan.ui;


import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.entity.MyGoldBean;
import com.boniu.starplan.entity.RecordBean;
import com.boniu.starplan.entity.WithDrawalListBean;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.DateTimeUtils;
import com.boniu.starplan.utils.RlvManagerUtils;
import com.boniu.starplan.utils.Utils;
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
 * 提现记录
 */
@Route(path = "/ui/WithdrawalRecordActivity")
public class WithdrawalRecordActivity extends BaseActivity {


    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_today_money)
    TextView tvTodayMoney;
    @BindView(R.id.tv_all_money)
    TextView tvAllMoney;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<WithDrawalListBean> lists = new ArrayList<>();
    private CommonAdapter<WithDrawalListBean> adapter;
    private MyGoldBean myGoldBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdrawal_record;
    }

    @Override
    public void init() {
        tvBarTitle.setText("提现记录");
        initView();
        getDate();
    }

    private void getDate() {
        //我的资料
        RxHttp.postEncryptJson(ComParamContact.Main.account)
                .asResponse(String.class)
                .subscribe(s -> {
                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                    myGoldBean = new Gson().fromJson(result, MyGoldBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvAllMoney.setText(myGoldBean.getAccumulateExpendGoldAmount() + "");
                            tvTodayMoney.setText(myGoldBean.getTodayEarnGoldAmount() + "");
                        }
                    });
                    //设置签到数据
                }, (OnError) error -> {
                    error.show();
                });
        RxHttp.postEncryptJson(ComParamContact.Main.withdrawalList)
                .asResponse(String.class)
                .subscribe(s -> {
                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                    ArrayList<WithDrawalListBean> list = new Gson().fromJson(result, new TypeToken<List<WithDrawalListBean>>() {
                    }.getType());
                    lists.clear();
                    lists.addAll(list);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                    //设置签到数据
                }, (OnError) error -> {
                    error.show();
                });
    }

    private void initView() {
        RlvManagerUtils.createLinearLayout(this, recyclerView);
        adapter = new CommonAdapter<WithDrawalListBean>(this, R.layout.item_record, lists) {

            @Override
            protected void convert(ViewHolder holder, WithDrawalListBean recordBean, int position) {
                holder.setText(R.id.tv_time, DateTimeUtils.format(recordBean.getCreateTime(), DateTimeUtils.FORMAT_LONG_CN))
                        .setText(R.id.tv_price, recordBean.getGoldAmount()/1000 + "元")
                        .setText(R.id.tv_state, recordBean.getStateDes() + "").setText(R.id.tv_err,recordBean.getRemark());

                String state = recordBean.getState();
                if (state.equals("1")){

                }else{

                }
            }
        };
        recyclerView.setAdapter(adapter);
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
