package com.boniu.starplan.helper;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.ad.ReWardVideoAdUtils;
import com.boniu.starplan.base.Url;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.EverydayLogDialog;
import com.boniu.starplan.dialog.LoadingDialog;
import com.boniu.starplan.dialog.NewPersonDialog;
import com.boniu.starplan.dialog.ReceiveGoldDialog;
import com.boniu.starplan.dialog.UpdateAppDialog;
import com.boniu.starplan.entity.ApplyTask;
import com.boniu.starplan.entity.BoxState;
import com.boniu.starplan.entity.LoginInfo;
import com.boniu.starplan.entity.MainTask;
import com.boniu.starplan.entity.NewUserInfo;
import com.boniu.starplan.entity.SignModel;
import com.boniu.starplan.entity.TaskMode;
import com.boniu.starplan.entity.VersionModel;
import com.boniu.starplan.entity.VideoAdModel;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.oaid.UuidCreator;
import com.boniu.starplan.ui.ApplicationUtils;
import com.boniu.starplan.ui.MainActivity;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.AnimatorUtil;
import com.boniu.starplan.utils.SPUtils;
import com.boniu.starplan.utils.SystemInfoUtils;
import com.boniu.starplan.utils.Tip;
import com.boniu.starplan.utils.Utils;
import com.boniu.starplan.utils.Validator;
import com.google.gson.Gson;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.Calendar;
import java.util.List;

import rxhttp.wrapper.param.RxHttp;

public class MainActivityHelper {

    private int weekSign;
    private BoxState boxState;


    public static MainActivityHelper newInstance() {
        MainActivityHelper fragment = new MainActivityHelper();
        return fragment;
    }


    /**
     * 观看视频
     */
    public void AdLook(Activity context) {

        //创建激励视频翻倍任务
        RxHttp.postEncryptJson(ComParamContact.Main.addVideoAD).asResponse(String.class).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            VideoAdModel adModel = new Gson().fromJson(result, VideoAdModel.class);
            //然后在观看激励视频
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ReWardVideoAdUtils.initAd(context, adModel.getApplyId(), adModel.getIncome());
                }
            });
        }, (OnError) error -> {
            error.show();
        });
    }

    public void downLoad(Activity context) {

        RxHttp.postJson(Url.UpLoadApp).add("appName", "LEZHUAN_STAR_BONIU").add("deviceType", "ANDROID").add("deviceModel", SystemInfoUtils.getModelName()).add("version", SystemInfoUtils.getAppVersionName(context)).add("channel", SystemInfoUtils.getAppSource(context, "UMENG_CHANNEL")).asString().subscribe(s -> {
            // VersionModel versionModel = new Gson().fromJson(s, VersionModel.class);
            VersionModel versionModel = new Gson().fromJson(s, VersionModel.class);
            if (versionModel.isSuccess()) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (versionModel.getResult().getVersionInfoVo().getVersion().compareTo(SystemInfoUtils.getAppVersionName(context)) > 0) {
                            UpdateAppDialog dialog = new UpdateAppDialog(context, versionModel);
                            dialog.show();
                        }
                    }
                });
            }
        }, (OnError) error -> {
            error.show();
        });
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(Activity context, TextView tvPhone, TextView tvMoney, TextView tvyc) {
        RxHttp.postEncryptJson(ComParamContact.Main.getUserInfo)
                .asResponse(String.class)
                .subscribe(s -> {
                    String resultStr = AESUtil.decrypt(s, AESUtil.KEY);
                    LoginInfo loginInfo = new Gson().fromJson(resultStr, LoginInfo.class);

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SPUtils.getInstance().put("accountStatus", loginInfo.getAccountStatus());
                            tvPhone.setText(Validator.Md5Phone(loginInfo.getMobile()));
                            tvMoney.setText(Utils.addComma(loginInfo.getGoldAmount()));

                            if (!loginInfo.getAccountStatus().equals("0")) {
                                tvyc.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }, (OnError) error -> {
                });
    }

    /**
     * 是否签到
     *
     * @param context
     * @param tvSign
     * @param tvMoreSign
     */
    public void IsSign(Activity context, TextView tvSign, TextView tvMoreSign) {
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
    public void signData(Activity context, List<SignModel.ListBean> signList, TextView tvSignDes, CommonAdapter<SignModel.ListBean> signAdapter) {
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
                            MainActivity.weekSign = weekSign;
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
     * 首页任务列表
     *
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
    public void mainTaskList(Activity context, LoadingDialog loadingDialog, List<MainTask.DayTaskBean> dayTaskList, List<MainTask.NewUserTaskBean> newUserTaskList,
                             CommonAdapter<MainTask.DayTaskBean> dayTaskAdapter, CommonAdapter<MainTask.NewUserTaskBean> newUserTaskAdapter,
                             RelativeLayout new_user_title_rl, RecyclerView rlvNewUserTask, TextView tvDes, ImageView ivBx) {
        loadingDialog.show();
        //任务列表
        ObjectAnimator animator = AnimatorUtil.sway(ivBx);
        animator.setRepeatCount(ValueAnimator.INFINITE);
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

                            ivBx.setBackgroundResource(R.mipmap.baoxiang);
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
                                RxHttp.postEncryptJson(ComParamContact.Main.queryTreasureBoxTaskStatus).add("type", "1").add("typeValue", "dayTask").asResponse(String.class).subscribe(s -> {
                                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                                    boxState = new Gson().fromJson(result, BoxState.class);
                                    context.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (boxState.getStatus() != 0) {
                                                ivBx.setBackgroundResource(R.mipmap.baoxiangopen);
                                                tvDes.setText("宝箱已领取");
                                            } else {
                                                animator.start();
                                            }
                                        }
                                    });

                                }, (OnError) error -> {
                                    error.show();
                                });
                                ivBx.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (boxState.getStatus() == 0) {
                                            ReceiveGoldDialog dialog = new ReceiveGoldDialog(context, boxState.getGoldCount(), boxState.getId() + "", true, new ReceiveGoldDialog.ReceiveCallback() {
                                                @Override
                                                public void receive(int flag, String applyId) {
                                                    //开启激励视频
                                                    context.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (flag == 1) {
                                                                ivBx.setBackgroundResource(R.mipmap.baoxiangopen);
                                                                tvDes.setText("宝箱已领取");
                                                                animator.clone();
                                                            }
                                                            if (flag == 2) {
                                                                ReWardVideoAdUtils.initAd(context, applyId, boxState.getGoldCount());
                                                            }
                                                        }
                                                    });

                                                }
                                            });
                                            dialog.show();
                                        } else {
                                            Tip.show("宝箱已领取");
                                        }

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
                            if (userInfo.isIsNewUser()){
                                ApplicationUtils.isNewUer=false;
                            }
                            if (userInfo.isPop()) {
                                NewPersonDialog dialog = new NewPersonDialog(activity, userInfo.getNewUserAmount());
                                dialog.show();
                            } else {


                                Calendar cd = Calendar.getInstance();
                                int month = cd.get(Calendar.MONTH) + 1;
                                int months = SPUtils.getInstance().getInt("month");
                                boolean isEvery = SPUtils.getInstance().getBoolean("isEvery", true);
                                if (month != months) {
                                    EverydayLogDialog dialog = new EverydayLogDialog(activity, userInfo.getWeekSignGoldAmount());
                                    dialog.show();
                                } else {
                                    if (isEvery) {
                                        EverydayLogDialog dialog = new EverydayLogDialog(activity, userInfo.getWeekSignGoldAmount());
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
