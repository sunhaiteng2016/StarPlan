package com.boniu.starplan.helper;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.ad.ReWardVideoAdUtils;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.EverydayLogDialog;
import com.boniu.starplan.dialog.LoadingDialog;
import com.boniu.starplan.dialog.NewPersonDialog;
import com.boniu.starplan.dialog.ReceiveGoldDialog;
import com.boniu.starplan.entity.ApplyTask;
import com.boniu.starplan.entity.BoxState;
import com.boniu.starplan.entity.LoginInfo;
import com.boniu.starplan.entity.MainTask;
import com.boniu.starplan.entity.NewUserInfo;
import com.boniu.starplan.entity.SignModel;
import com.boniu.starplan.entity.TaskMode;
import com.boniu.starplan.entity.VideoAdModel;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.ui.MainActivity;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.AnimatorUtil;
import com.boniu.starplan.utils.SPUtils;
import com.boniu.starplan.utils.Tip;
import com.boniu.starplan.utils.Validator;
import com.google.gson.Gson;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.Calendar;
import java.util.List;

import rxhttp.wrapper.param.RxHttp;

public class MainActivityHelper {

    private int weekSign;


    public static MainActivityHelper newInstance() {
        MainActivityHelper fragment = new MainActivityHelper();
        return fragment;
    }


    /**
     * 观看视频
     */
    public void AdLook(Activity context){

        //创建激励视频翻倍任务
        RxHttp.postEncryptJson(ComParamContact.Main.addVideoAD).asResponse(String.class).subscribe(s->{
            String result = AESUtil.encrypt(s, AESUtil.KEY);
            VideoAdModel adModel = new Gson().fromJson(result, VideoAdModel.class);
            //然后在观看激励视频
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ReWardVideoAdUtils.initAd(context,adModel.getApplyId(),adModel.getIncome());
                }
            });

        },(OnError) error->{
            error.show();
        });
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(Activity context, TextView tvPhone,TextView tvMoney){
        RxHttp.postEncryptJson(ComParamContact.Main.getUserInfo)
                .asResponse(String.class)
                .subscribe(s -> {
                    String resultStr = AESUtil.decrypt(s, AESUtil.KEY);
                    LoginInfo loginInfo = new Gson().fromJson(resultStr, LoginInfo.class);
                    context. runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvPhone.setText(Validator.Md5Phone(loginInfo.getMobile()));
                            tvMoney.setText(loginInfo.getGoldAmount());
                        }
                    });
                }, (OnError) error -> {
                });
    }

    /**
     * 是否签到
     * @param context
     * @param tvSign
     * @param tvMoreSign
     */
    public void IsSign(Activity context,TextView tvSign,TextView tvMoreSign){
        //签到相关
        RxHttp.postEncryptJson(ComParamContact.Main.IS_SIGN)
                .asResponse(String.class)
                .subscribe(s -> {
                    String resultStr = AESUtil.decrypt(s, AESUtil.KEY);
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultStr.equals("1")) {
                                tvSign.setVisibility(View.GONE);
                                tvMoreSign.setVisibility(View.VISIBLE);
                            } else {
                                tvMoreSign.setVisibility(View.GONE);
                                tvSign.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                }, (OnError) error -> {

                });
    }

    /**
     * 获取签到数据
     */
    public void signData(Activity context,  List<SignModel.ListBean> signList, TextView tvSignDes, CommonAdapter<SignModel.ListBean> signAdapter ){
        RxHttp.postEncryptJson(ComParamContact.Main.getSignAmount)
                .asResponse(String.class)
                .subscribe(s -> {
                    String resultStr = AESUtil.decrypt((String) s, AESUtil.KEY);
                    SignModel sigModel = new Gson().fromJson(resultStr, SignModel.class);
                    weekSign = 0;
                    for (SignModel.ListBean list : sigModel.getList()) {
                        if (list.isIsSign()) {
                            weekSign++;
                        }
                    }
                    signList.clear();
                    signList.addAll(sigModel.getList());
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvSignDes.setText("已连续签到 " + weekSign + "/7 天");
                            signAdapter.notifyDataSetChanged();
                        }
                    });
                    //设置签到数据
                }, (OnError) error -> {
                    error.show();
                });
    }

    /**
     *  首页任务列表
     * @param context
     * @param dayTaskList
     * @param newUserTaskList
     * @param dayTaskAdapter
     * @param newUserTaskAdapter
     * @param new_user_title_rl
     * @param rlvNewUserTask
     * @param tvDes
     * @param ivBx
     */
    public  void mainTaskList(Activity context,LoadingDialog loadingDialog, List<MainTask.DayTaskBean> dayTaskList, List<MainTask.NewUserTaskBean> newUserTaskList,
                              CommonAdapter<MainTask.DayTaskBean> dayTaskAdapter, CommonAdapter<MainTask.NewUserTaskBean> newUserTaskAdapter,
                              RelativeLayout new_user_title_rl, RecyclerView rlvNewUserTask, TextView tvDes, ImageView ivBx){

        //任务列表
        RxHttp.postEncryptJson(ComParamContact.Main.queryTaskMarketList)
                .asResponse(String.class)
                .subscribe(s -> {
                    String resultStr = AESUtil.decrypt(s, AESUtil.KEY);
                    MainTask mainTask = new Gson().fromJson(resultStr, MainTask.class);
                    dayTaskList.clear();
                    //每日任务
                    dayTaskList.addAll(mainTask.getDayTask());
                    //新手任务
                    newUserTaskList.clear();
                    newUserTaskList.addAll(mainTask.getNewUserTask());
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismiss();
                            if (newUserTaskList.size() <= 0) {
                                new_user_title_rl.setVisibility(View.GONE);
                                rlvNewUserTask.setVisibility(View.GONE);
                            }
                            dayTaskAdapter.notifyDataSetChanged();
                            newUserTaskAdapter.notifyDataSetChanged();
                            int i = 0;
                            for (MainTask.DayTaskBean bean : dayTaskList) {
                                if (bean.getTaskViewStatus() == 0) {
                                    i++;
                                }
                            }


                            SpannableStringBuilder spannableString = new SpannableStringBuilder();
                            if (i == 0) {
                                spannableString.append("任务达成，点击领取宝箱");
                            } else {
                                spannableString.append("还差" + i + "个任务，开启宝箱领金币");
                                /**
                                 * 颜色
                                 */
                                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FB4E42"));
                                spannableString.setSpan(colorSpan, 2, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                            }
                            tvDes.setText(spannableString);
                            if (i == 0) {
                                //生成宝箱
                                ObjectAnimator animator = AnimatorUtil.sway(ivBx);
                                animator.setRepeatCount(ValueAnimator.INFINITE);
                                animator.start();
                                ivBx.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LoadingDialog loadingDialog1 = new LoadingDialog(context);
                                        loadingDialog1.show();
                                        //查询每日任务宝箱状态
                                        RxHttp.postEncryptJson(ComParamContact.Main.queryTreasureBoxTaskStatus).add("type", "1").add("typeValue", "dayTask").asResponse(String.class).subscribe(s -> {
                                            String result = AESUtil.decrypt(s, AESUtil.KEY);
                                            BoxState boxState = new Gson().fromJson(result, BoxState.class);
                                            context.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    loadingDialog1.dismiss();
                                                    ReceiveGoldDialog dialog = new ReceiveGoldDialog(context, boxState.getGoldCount(), boxState.getId() + "", true,new ReceiveGoldDialog.ReceiveCallback() {
                                                        @Override
                                                        public void receive(int flag, String applyId) {
                                                            //开启激励视频
                                                            context.runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    if (flag == 1) {
                                                                                ivBx.setBackgroundResource(R.mipmap.baoxiang);
                                                                                tvDes.setText("宝箱已领取");
                                                                    }
                                                                    if (flag == 2) {
                                                                        ReWardVideoAdUtils.initAd(context,applyId,boxState.getGoldCount());
                                                                    }
                                                                }
                                                            });

                                                        }
                                                    });
                                                    dialog.show();
                                                }
                                            });

                                        }, (OnError) error -> {
                                            error.show();
                                        });

                                    }
                                });
                            } else {
                                ivBx.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Tip.show("任务未达标，请先完成任务");
                                    }
                                });
                            }
                        }
                    });
                }, (OnError) error -> {
                    loadingDialog.dismiss();
                });
    }

    /**
     * 是否新用户
     */
    public void initNewUserInfo(Activity activity) {
        RxHttp.postEncryptJson(ComParamContact.Main.isNewUserAndGetGoldWithoutToken)
                .asResponse(String.class)
                .subscribe(s -> {
                    String resultStr = AESUtil.decrypt(s, AESUtil.KEY);
                    NewUserInfo userInfo = new Gson().fromJson(resultStr, NewUserInfo.class);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (userInfo.isIsNewUser()) {
                                NewPersonDialog dialog = new NewPersonDialog(activity);
                                dialog.show();
                            } else {
                                Calendar cd = Calendar.getInstance();
                                int month = cd.get(Calendar.MONTH) + 1;
                                int months = SPUtils.getInstance().getInt("month");
                                boolean isEvery = SPUtils.getInstance().getBoolean("isEvery", true);
                                if (month != months) {
                                    EverydayLogDialog dialog = new EverydayLogDialog(activity);
                                    dialog.show();
                                } else {
                                    if (isEvery) {
                                        EverydayLogDialog dialog = new EverydayLogDialog(activity);
                                        dialog.show();
                                    }
                                }
                            }
                        }
                    });
                }, (OnError) error -> {
                });

    }





}
