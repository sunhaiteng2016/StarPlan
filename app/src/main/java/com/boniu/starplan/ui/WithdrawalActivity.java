package com.boniu.starplan.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.Withdrawal2Dialog;
import com.boniu.starplan.dialog.WithdrawalDialog;
import com.boniu.starplan.entity.ExclusModel;
import com.boniu.starplan.entity.MyGoldBean;
import com.boniu.starplan.entity.PriceModel;
import com.boniu.starplan.entity.ProgressModel;
import com.boniu.starplan.entity.TransferInfoBean;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.RlvManagerUtils;
import com.boniu.starplan.utils.StringUtils;
import com.boniu.starplan.utils.Tip;
import com.boniu.starplan.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;

/**
 * 提现中心
 */
@Route(path = "/ui/WithdrawalActivity")
public class WithdrawalActivity extends BaseActivity {


    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_gold_coin)
    TextView tvGoldCoin;
    @BindView(R.id.tv_submit_s)
    TextView tvSubmitS;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_zfb)
    LinearLayout llZfb;
    @BindView(R.id.ll_we_chat)
    LinearLayout llWeChat;
    @BindView(R.id.huodong_recycler)
    RecyclerView huodongRecycler;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_days)
    TextView tvDays;
    @BindView(R.id.ll_huoyuedu)
    LinearLayout llHuoyuedu;
    @BindView(R.id.left_img)
    ImageView leftImg;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.putong_recycler)
    RecyclerView putongRecycler;
    private List<PriceModel> list = new ArrayList<>();
    private CommonAdapter<PriceModel> adapter;
    private CommonAdapter<ExclusModel> adapter1;
    private List<ExclusModel> list1 = new ArrayList<>();
    private String clickType = "";
    private Long withdrwaalMoney = 0L;
    private MyGoldBean myGoldBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdrawal;
    }

    @Override
    public void init() {
        tvBarTitle.setText("提现中心");
        tvSubmit.setText("提现记录");
        tvSubmit.setVisibility(View.VISIBLE);
        initView();
        getDates();

    }

    private void getPreferential(String type) {
        RxHttp.postEncryptJson(ComParamContact.Main.preferential)
                .add("appID", "test")
                .add("type", type)
                .asResponse(String.class)
                .subscribe(s -> {
                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                    ProgressModel progressModel = new Gson().fromJson(result, ProgressModel.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (progressModel.getArrived() == progressModel.getTarget()) {
                                llHuoyuedu.setVisibility(View.GONE);
                                tvDesc.setText("您已达到条件，可以立即提现");
                            } else {
                                clickType = "";
                                withdrwaalMoney = 0L;
                                llHuoyuedu.setVisibility(View.VISIBLE);
                                tvDesc.setText(progressModel.getDesc() + "");
                                progressbar.setMax(progressModel.getTarget());
                                progressbar.setProgress(progressModel.getArrived());

                            }
                            tvDays.setText(progressModel.getArrived() + "/" + progressModel.getTarget());
                        }
                    });
                }, (OnError) error -> {
                    error.show();
                });
    }

    private void getDates() {
        //普通提现列表
        RxHttp.postEncryptJson(ComParamContact.Main.ordinaryList)
                .asResponse(String.class)
                .subscribe(s -> {
                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                    List<String> normalPriceList = new Gson().fromJson(result, new TypeToken<List<String>>() {
                    }.getType());
                    list.clear();
                    for (String bean : normalPriceList) {
                        list.add(new PriceModel(false, bean));
                    }
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
        RxHttp.postEncryptJson(ComParamContact.Main.exclusiveList)
                .add("appID", "test")
                .asResponse(String.class)
                .subscribe(s -> {
                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                    List<ExclusModel> PriceList = new Gson().fromJson(result, new TypeToken<List<ExclusModel>>() {
                    }.getType());
                    list1.clear();
                    list1.addAll(PriceList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter1.notifyDataSetChanged();
                        }
                    });
                }, (OnError) error -> {
                    error.show();
                });
        //我的资料
        RxHttp.postEncryptJson(ComParamContact.Main.account)
                .asResponse(String.class)
                .subscribe(s -> {
                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                    myGoldBean = new Gson().fromJson(result, MyGoldBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvGoldCoin.setText("共" + myGoldBean.getAvailableBalance() + "金币");
                            if (myGoldBean.getAvailableBalance() > 100) {
                                tvMoney.setText(Utils.getDouble(myGoldBean.getAvailableBalance()) + "元");
                            } else {
                                tvMoney.setText("0元");
                            }
                        }
                    });
                    //设置签到数据
                }, (OnError) error -> {
                    error.show();

                });

    }

    private void initView() {
        RlvManagerUtils.createGridView(this, putongRecycler, 3);
        adapter = new CommonAdapter<PriceModel>(this, R.layout.item_price_list, list) {

            @Override
            protected void convert(ViewHolder holder, PriceModel priceModel, int position) {

                int price = Integer.parseInt(priceModel.price) / 10000;
                holder.setText(R.id.tv_price, price + "元");
                if (list.get(position).isSel) {
                    holder.setBackgroundRes(R.id.rl_activity_new_user, R.mipmap.duibk2);
                } else {
                    holder.setBackgroundRes(R.id.rl_activity_new_user, R.drawable.shape_board_f3);
                }
            }
        };
        putongRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                withdrwaalMoney = Long.parseLong(list.get(i).price);
                list.get(i).isSel = true;
                clearExclusiveSel();
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
        RlvManagerUtils.createGridView(this, huodongRecycler, 3);
        adapter1 = new CommonAdapter<ExclusModel>(this, R.layout.item_exclus, list1) {

            @Override
            protected void convert(ViewHolder holder, ExclusModel exclusModel, int position) {
                long price = exclusModel.getGoldNum() / 10000;

                holder.setText(R.id.tv_price, price + "元");
                if (list1.get(position).isSel()) {
                    holder.setBackgroundRes(R.id.ll_bg, R.mipmap.duibk2);
                } else {
                    holder.setBackgroundRes(R.id.ll_bg, R.drawable.shape_board_f3);
                }
                if (list1 .get(position).getCode().equals("new_user")){
                    holder.setText(R.id.tv_content," 新用户专享");
                }else{
                    holder.setText(R.id.tv_content," 连续活跃专享");
                }

            }
        };
        huodongRecycler.setAdapter(adapter1);
        adapter1.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                clearAdapterSel();
                withdrwaalMoney = list1.get(i).getGoldNum();
                list1.get(i).setSel(true);
                for (int j = 0; j < list1.size(); j++) {
                    if (i != j) {
                        list1.get(j).setSel(false);
                    }
                }
                adapter1.notifyDataSetChanged();
                clickType = list1.get(i).getCode();
                if (!list1.get(i).getCode().equals("new_user")) {
                    getPreferential(clickType);
                } else {
                    llHuoyuedu.setVisibility(View.GONE);
                }

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

    @OnClick({R.id.tv_submit, R.id.tv_submit_s})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                ARouter.getInstance().build("/ui/WithdrawalRecordActivity").navigation();
                break;
            case R.id.tv_submit_s:
                // 查看是那个提现
                if (!StringUtils.isEmpty(clickType)) {
                    if (withdrwaalMoney > myGoldBean.getAvailableBalance()) {
                        Tip.show("提现金额不足");
                        return;
                    }
                } else {
                    Tip.show("提现规则不满足");
                    return;
                }
                transferInfo();
                break;
        }
    }

    /**
     * 提现
     */
    private void transferInfo() {

        RxHttp.postEncryptJson(ComParamContact.Main.transferInfo)
                .asResponse(String.class)
                .subscribe(s -> {
                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                    TransferInfoBean transferInfoBean = new Gson().fromJson(result, TransferInfoBean.class);
                    if ("YES".equals(transferInfoBean.getChangeFlag())) {
                        WithdrawalDialog withdrawalDialog = new WithdrawalDialog(mContext, withdrwaalMoney + "", new Withdrawal2Dialog.WithdrawalInterfaces() {
                            @Override
                            public void startWithdrawal(String name, String zhanghao) {
                                withdrawalMoney(name, zhanghao);
                            }
                        });
                        withdrawalDialog.show();
                    } else {
                        Withdrawal2Dialog withdrawal2Dialog = new Withdrawal2Dialog(mContext, transferInfoBean.getReceivedAccount(), transferInfoBean.getRealName(), withdrwaalMoney + "", new Withdrawal2Dialog.WithdrawalInterfaces() {
                            @Override
                            public void startWithdrawal(String name, String zhanghao) {
                                withdrawalMoney(name, zhanghao);
                            }
                        });
                        withdrawal2Dialog.show();

                    }
                    //设置签到数据
                }, (OnError) error -> {
                    error.show();

                });
    }

    /**
     * 提现接口
     *
     * @param name
     * @param zhanghao
     */
    private void withdrawalMoney(String name, String zhanghao) {
        RxHttp.postEncryptJson(ComParamContact.Main.transferInfo)
                .add("appID", "test")
                .add("expendAccountID", "aomgdv1151@sandbox.com")
                .add("expendAccountName", "沙箱环境")
                .add("expendAccountType", "1")
                .add("expendType", clickType + "")
                .add("goldAmount", withdrwaalMoney + "")
                .add("orderNum", (long) (Math.random() * 1000000000))
                .asResponse(String.class)
                .subscribe(s -> {
                    String result = AESUtil.decrypt(s, AESUtil.KEY);

                    //设置签到数据
                }, (OnError) error -> {
                    error.show();

                });
    }

    /**
     * 删除普通列表的状态
     */
    private void clearAdapterSel() {
        for (PriceModel priceModel : list) {
            priceModel.isSel = false;
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 删除专属列表的状态
     */
    private void clearExclusiveSel() {
        for (ExclusModel priceModel : list1) {
            priceModel.setSel(false);
        }
        adapter1.notifyDataSetChanged();
    }

    @OnClick(R.id.rl_back)
    public void onClick() {
        finish();
    }
}
