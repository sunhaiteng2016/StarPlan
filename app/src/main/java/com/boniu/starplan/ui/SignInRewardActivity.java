package com.boniu.starplan.ui;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.boniu.starplan.R;
import com.boniu.starplan.ad.ReWardVideoAdUtils;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.base.Response;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.LoadingDialog;
import com.boniu.starplan.dialog.ReceiveGoldDialog;
import com.boniu.starplan.dialog.ReceiveGoldDialog2;
import com.boniu.starplan.dialog.ReceiveGoldDialog3;
import com.boniu.starplan.entity.BoxState;
import com.boniu.starplan.entity.ReceiveGoldModel;
import com.boniu.starplan.entity.ReceiveGoldStateModel;
import com.boniu.starplan.entity.SignModel.ListBean;
import com.boniu.starplan.entity.NumberDay;
import com.boniu.starplan.entity.SignModel;
import com.boniu.starplan.entity.WelfareBean;
import com.boniu.starplan.helper.MainActivityHelper;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.AnimatorUtil;
import com.boniu.starplan.utils.GlideUtils;
import com.boniu.starplan.utils.RlvManagerUtils;
import com.boniu.starplan.utils.Tip;
import com.boniu.starplan.view.NumberRunningTextView;
import com.google.gson.Gson;
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
 * 签到
 */
@Route(path = "/ui/SignInRewardActivity")
public class SignInRewardActivity extends BaseActivity {


    @BindView(R.id.rlv_number_day)
    RecyclerView rlvNumberDay;
    @BindView(R.id.tv_sign_des)
    TextView tvSignDes;
    @BindView(R.id.rlv_sign_bg)
    RecyclerView rlvSignBg;
    @BindView(R.id.tv_take)
    TextView tvTake;
    @BindView(R.id.tv_take7)
    TextView tvTake7;
    @BindView(R.id.rlv_welfare)
    RecyclerView rlvWelfare;
    @BindView(R.id.tv_get_more)
    TextView tvGetMore;
    @BindView(R.id.tv_lx_day)
    TextView tv_lx_day;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private List<SignModel.ListBean> continuityList = new ArrayList<>();
    private CommonAdapter<SignModel.ListBean> signBgAdapter;
    private List<NumberDay> numDayList = new ArrayList<>();
    private CommonAdapter<NumberDay> numSignAdapter;
    private List<WelfareBean.CumulativeSignConfigListBean> welfareList = new ArrayList<>();
    private CommonAdapter<WelfareBean.CumulativeSignConfigListBean> welfareAdapter;
    private int weekSign;
    private WelfareBean welfareBean;
    private int income;
    private int cumulative;
    private LoadingDialog loadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_in_reward;
    }

    @Override
    public void init() {
        tvBarTitle.setText("签到奖励");
        initView();
        getData();
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
    }

    private void getData() {
        //签到数据
        RxHttp.postEncryptJson(ComParamContact.Main.getSignAmount)
                .asResponse(String.class)
                .subscribe(s -> {
                    String resultStr = AESUtil.decrypt((String) s, AESUtil.KEY);
                    SignModel sigModel = new Gson().fromJson(resultStr, SignModel.class);
                    continuityList.clear();
                    continuityList.addAll(sigModel.getList());
                    weekSign = 0;
                    for (SignModel.ListBean list : sigModel.getList()) {
                        if (list.isIsSign()) {
                            weekSign++;
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismiss();
                            tv_lx_day.setText("连续签到" + sigModel.getContinuousSign() + "天");
                            tvSignDes.setText(weekSign + "/7");
                            //3天按钮的状态
                            if (continuityList.get(2).getIsReceive() == 0) {
                                tvTake.setText("领取奖励");
                                tvTake.setBackgroundResource(R.drawable.shape_round_22_red);
                            } else if (continuityList.get(2).getIsReceive() == 1) {
                                tvTake.setText("已领取");
                                tvTake.setBackgroundResource(R.drawable.shape_round_16_c3);
                            } else {
                                tvTake.setText("不符合条件" + weekSign + "/3");
                                tvTake.setBackgroundResource(R.drawable.shape_round_16_c3);
                            }
                            //7天按钮的状态
                            if (continuityList.get(6).getIsReceive() == 0) {
                                tvTake7.setText("领取奖励");
                                tvTake7.setBackgroundResource(R.drawable.shape_round_22_red);
                            } else if (continuityList.get(6).getIsReceive() == 1) {
                                tvTake7.setText("已领取");
                                tvTake7.setBackgroundResource(R.drawable.shape_round_16_c3);
                            } else {
                                tvTake7.setText("不符合条件" + weekSign + "/7");
                                tvTake7.setBackgroundResource(R.drawable.shape_round_16_c3);
                            }
                            cumulative = sigModel.getCumulativeSign();//累计签到
                            if (cumulative < 10) {
                                numDayList.set(2, new NumberDay(cumulative));
                            }
                            if (cumulative < 100 && cumulative >= 10) {
                                numDayList.set(1, new NumberDay((cumulative % 100) / 10));
                                numDayList.set(2, new NumberDay(cumulative % 10));
                            }
                            if (cumulative > 100) {
                                numDayList.set(0, new NumberDay((cumulative % 1000) / 100));
                                numDayList.set(1, new NumberDay((cumulative % 100) / 10));
                                numDayList.set(2, new NumberDay(cumulative % 10));
                            }
                            numSignAdapter.notifyDataSetChanged();
                            signBgAdapter.notifyDataSetChanged();
                        }
                    });
                    //设置签到数据
                }, (OnError) error -> {
                    error.show();
                });
        //获取签到累计奖励的列表
        RxHttp.postEncryptJson(ComParamContact.Main.isGetCumulativeSignRewards).asResponse(String.class).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            welfareBean = new Gson().fromJson(result, WelfareBean.class);
            List<WelfareBean.CumulativeSignConfigListBean> newList = welfareBean.getCumulativeSignConfigList();
            welfareList.clear();
            welfareList.addAll(newList);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    welfareAdapter.notifyDataSetChanged();
                }
            });
        }, (OnError) error -> {
            error.show();
        });
    }

    private void initView() {
        RlvManagerUtils.createLinearLayoutHorizontal(this, rlvNumberDay);
        numDayList.add(new NumberDay(0));
        numDayList.add(new NumberDay(0));
        numDayList.add(new NumberDay(0));
        numSignAdapter = new CommonAdapter<NumberDay>(this, R.layout.item_number_day, numDayList) {

            @Override
            protected void convert(ViewHolder holder, NumberDay numberDay, int position) {
                NumberRunningTextView tv = holder.getView(R.id.tv_day);
                tv.setText(numberDay.G + "");
            }
        };
        rlvNumberDay.setAdapter(numSignAdapter);

        //签到
        RlvManagerUtils.createGridView(this, rlvSignBg, 7);

        signBgAdapter = new CommonAdapter<SignModel.ListBean>(this, R.layout.item_continuity_sign_day, continuityList) {

            @Override
            protected void convert(ViewHolder holder, SignModel.ListBean listBean, int position) {
                holder.setText(R.id.tv_des, listBean.getWeekSignGold() + "");
                if (listBean.isIsSign()) {
                    holder.setTextColor(R.id.tv_des, mContext.getResources().getColor(R.color.FEC50B));
                    if (listBean.getType().equals("gif")) {
                        if (listBean.getIsReceive() == 0) {//签到未领取
                            holder.setVisible(R.id.tv_hb_close, true);
                            holder.setVisible(R.id.tv_hb_open, false);
                            holder.setVisible(R.id.tv_circle, false);
                            ObjectAnimator animator = AnimatorUtil.sway(holder.getView(R.id.tv_hb_close));
                            animator.setRepeatCount(ValueAnimator.INFINITE);
                            animator.start();
                        } else {
                            holder.setBackgroundRes(R.id.tv_hb_close, R.mipmap.yiqiandao);
                            holder.setVisible(R.id.tv_hb_open, true);
                            holder.setVisible(R.id.tv_hb_close, false);
                            holder.setVisible(R.id.tv_circle, false);
                        }
                    } else {
                        //普通状态
                        GlideUtils.getInstance().LoadContextBitmap(SignInRewardActivity.this, R.mipmap.signdui, holder.getView(R.id.tv_circle));
                        holder.setVisible(R.id.tv_hb_close, false);
                        holder.setVisible(R.id.tv_hb_open, false);
                        holder.setVisible(R.id.tv_circle, true);
                    }
                } else {
                    //没签到
                    holder.setTextColor(R.id.tv_des, mContext.getResources().getColor(R.color.text_33));
                    //没签到 3 7
                    if (listBean.getType().equals("gif")) {
                        holder.setVisible(R.id.tv_hb_open, false);
                        holder.setVisible(R.id.tv_hb_close, true);
                        holder.setVisible(R.id.tv_circle, false);
                    } else {
                        holder.setVisible(R.id.tv_hb_open, false);
                        holder.setVisible(R.id.tv_hb_close, false);
                        holder.setVisible(R.id.tv_circle, true);
                        holder.setBackgroundRes(R.id.tv_circle, R.mipmap.signjinbi);
                    }
                }

            }
        };
        rlvSignBg.setAdapter(signBgAdapter);

        RlvManagerUtils.createLinearLayout(this, rlvWelfare);

        welfareAdapter = new CommonAdapter<WelfareBean.CumulativeSignConfigListBean>(this, R.layout.item_welfare, welfareList) {

            @Override
            protected void convert(ViewHolder holder, WelfareBean.CumulativeSignConfigListBean welfareBean1, int position) {
                holder.setText(R.id.tv_title, "累计签到" + welfareBean1.getCumulativeSignAmount() + "天");
                income = welfareBean1.getGoldAmount();
                if (welfareBean.getGetRewardsList().get(position).isShow()){
                    holder.setText(R.id.tv_gold, welfareBean1.getGoldAmount() + "");
                }else{
                    holder.setText(R.id.tv_gold, "******");
                }
                if (welfareBean.getGetRewardsList().get(position).getStatus() == 1) {
                    holder.setText(R.id.iv_right, "可领取");

                    GlideUtils.getInstance().LoadContextRoundBitmapInt(SignInRewardActivity.this, R.mipmap.lxqdklq, holder.getView(R.id.iv_img), 0);
                    GlideUtils.getInstance().LoadContextRoundBitmapInt(SignInRewardActivity.this, R.mipmap.kdj, holder.getView(R.id.iv_bndj), 0);
                    holder.setTextColor(R.id.tv_title, mContext.getResources().getColor(R.color.text_99)).setTextColor(R.id.tv_gold, mContext.getResources().getColor(R.color.FF5151));
                } else if (welfareBean.getGetRewardsList().get(position).getStatus() == 2) {
                    holder.setText(R.id.iv_right, "已领取");
                    GlideUtils.getInstance().LoadContextRoundBitmapInt(SignInRewardActivity.this, R.mipmap.lxqdbkdj, holder.getView(R.id.iv_img), 0);
                    GlideUtils.getInstance().LoadContextRoundBitmapInt(SignInRewardActivity.this, R.mipmap.bndj, holder.getView(R.id.iv_bndj), 0);
                    holder.setTextColor(R.id.tv_title, mContext.getResources().getColor(R.color.text_99)).setTextColor(R.id.tv_gold, mContext.getResources().getColor(R.color.text_99));
                } else {

                    holder.setText(R.id.iv_right, "不符合条件");
                    GlideUtils.getInstance().LoadContextRoundBitmapInt(SignInRewardActivity.this, R.mipmap.lxqdbkdj, holder.getView(R.id.iv_img), 0);
                    GlideUtils.getInstance().LoadContextRoundBitmapInt(SignInRewardActivity.this, R.mipmap.bndj, holder.getView(R.id.iv_bndj), 0);
                    holder.setTextColor(R.id.tv_title, mContext.getResources().getColor(R.color.text_99)).setTextColor(R.id.tv_gold, mContext.getResources().getColor(R.color.text_99));
                }

            }
        };
        rlvWelfare.setAdapter(welfareAdapter);
        welfareAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                if (welfareBean.getGetRewardsList().get(i).getStatus() == 1) {
                    RxHttp.postEncryptJson(ComParamContact.Main.getCumulativeSignRewards).add("days", welfareBean.getGetRewardsList().get(i).getDays()).asResponse(String.class).subscribe(s -> {
                        String result = AESUtil.decrypt(s, AESUtil.KEY);
                        ReceiveGoldStateModel receiveGlod = new Gson().fromJson(result, ReceiveGoldStateModel.class);
                        if (receiveGlod.isSuccess()) {
                            Tip.show("领取成功");
                            getData();
                            ReceiveGoldDialog2 dialog = new ReceiveGoldDialog2(SignInRewardActivity.this, income);
                            dialog.show();
                        } else {
                            Tip.show("领取失败");
                        }
                    }, (OnError) error -> {
                        error.show();
                    });
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

    @OnClick({R.id.rl_back, R.id.tv_submit, R.id.tv_take, R.id.tv_take7, R.id.tv_get_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_more:
                MainActivityHelper.newInstance().AdLook(this);
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_submit:

                break;
            case R.id.tv_take:
                if (continuityList == null) return;
                if (continuityList.get(2).getIsReceive() == 0) {
                    ReceiveGoldDialog3 dialog = new ReceiveGoldDialog3(SignInRewardActivity.this, continuityList.get(2).getDoubleGold(), continuityList.get(2).getBoxId() + "", 2, continuityList.get(2).isIsDouble(), new ReceiveGoldDialog3.ReceiveCallback() {
                        @Override
                        public void receive(int flag, String applyId) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (flag == 1) {
                                        getData();
                                    }
                                    //开启激励视频
                                    if (flag == 2) {
                                        ReWardVideoAdUtils.initAd(SignInRewardActivity.this, applyId, continuityList.get(2).getDoubleGold());
                                    }
                                }
                            });

                        }
                    });
                    dialog.show();
                } else {
                    Tip.show("不能领取");
                }
                break;
            case R.id.tv_take7:
                if (continuityList == null) return;
                if (continuityList.get(6).getIsReceive() == 0) {
                    ReceiveGoldDialog3 dialog = new ReceiveGoldDialog3(SignInRewardActivity.this, continuityList.get(6).getDoubleGold(), continuityList.get(6).getBoxId() + "", 6, continuityList.get(6).isIsDouble(), new ReceiveGoldDialog3.ReceiveCallback() {
                        @Override
                        public void receive(int flag, String applyId) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (flag == 1) {
                                        getData();
                                    }
                                    //开启激励视频
                                    if (flag == 2) {
                                        ReWardVideoAdUtils.initAd(SignInRewardActivity.this, applyId, continuityList.get(6).getDoubleGold());
                                    }
                                }
                            });
                        }
                    });
                    dialog.show();
                } else {
                    Tip.show("不能领取");
                }
                break;
        }
    }
}
