package com.boniu.starplan.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.ad.ReWardVideoAdUtils;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.DownloadProgressDialog;
import com.boniu.starplan.dialog.LoadingDialog;
import com.boniu.starplan.dialog.ReceiveGoldDialog;
import com.boniu.starplan.dialog.ReceiveGoldDialog2;
import com.boniu.starplan.dialog.ReceiveGoldDialog4;
import com.boniu.starplan.dialog.ReceiveGoldDialog6;
import com.boniu.starplan.entity.BeanTaskModel;
import com.boniu.starplan.entity.ErrorInfo;
import com.boniu.starplan.entity.TaskDetailsModel;
import com.boniu.starplan.entity.TaskSuccessModel;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.GlideUtils;
import com.boniu.starplan.utils.HProgressDialogUtils;
import com.boniu.starplan.utils.OpenApp;
import com.boniu.starplan.utils.SPUtils;
import com.boniu.starplan.utils.StringUtils;
import com.boniu.starplan.utils.TimerUtils;
import com.boniu.starplan.utils.Tip;
import com.google.gson.Gson;
import com.rxjava.rxlife.RxLife;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import rxhttp.wrapper.param.RxHttp;

import static com.boniu.starplan.dialog.DownloadProgressDialog.STYLE_HORIZONTAL;

/**
 * 试玩详情
 */
@Route(path = "/ui/TryToEarnDetailsActivity")
public class TryToEarnDetailsActivity extends BaseActivity {


    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_number_gold)
    TextView tvNumberGold;
    @BindView(R.id.tv_number_gold2)
    TextView tvNumberGold2;
    @BindView(R.id.iv_app_icon)
    ImageView ivAppIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_title1)
    TextView tvTitle1;
    @BindView(R.id.tv_title2)
    TextView tvTitle2;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_start_down)
    TextView tvStartDown;
    @BindView(R.id.tv_start_play)
    TextView tvStartPlay;
    @BindView(R.id.tv_receive_rewards)
    TextView tvReceiveRewards;
    @BindView(R.id.tv_content1)
    TextView tvContent1;
    @BindView(R.id.tv_content2)
    TextView tvContent2;
    @BindView(R.id.tv_content3)
    TextView tvContent3;
    @BindView(R.id.ll_one)
    LinearLayout llOne;
    private int taskId, userTaskId;
    private TaskDetailsModel taskDetailsModel;
    private int inCome;
    private File destPath;
    private int flag;
    private int taskIncome;


    @Override
    public int getLayoutId() {
        return R.layout.activity_try_to_earn_details;
    }

    @Override
    public void init() {
        taskId = getIntent().getIntExtra("taskId", -1);
        userTaskId = getIntent().getIntExtra("userTaskId", -1);
        flag = getIntent().getIntExtra("flag", -1);
        tvBarTitle.setText("试玩赚详情");

        getData();
        tvReceiveRewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (OpenApp.isInstalled(TryToEarnDetailsActivity.this, taskDetailsModel.getTaskDetailVO().getTryTaskVO().getAppOpenUrl())) {
                    if (System.currentTimeMillis() - SPUtils.getInstance().getLong("taskStartTime") > 10000L) {
                        LoadingDialog loadingDialog = new LoadingDialog(TryToEarnDetailsActivity.this);
                        loadingDialog.show();
                        RxHttp.postEncryptJson(ComParamContact.Main.TASk_END).add("userTaskId", userTaskId).asResponse(String.class).subscribe(s -> {
                            String result = AESUtil.decrypt(s, AESUtil.KEY);
                            TaskSuccessModel taskSuccessModel = new Gson().fromJson(result, TaskSuccessModel.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadingDialog.dismiss();
                                    if (taskSuccessModel.isIsDouble()) {
                                        //如果翻倍
                                        ReceiveGoldDialog4 dialog4 = new ReceiveGoldDialog4(TryToEarnDetailsActivity.this, inCome, taskSuccessModel.getApplyId(), flag, new ReceiveGoldDialog4.ReceiveCallback() {
                                            @Override
                                            public void receive(int flag, String applyId) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (flag == 2) {
                                                            ReWardVideoAdUtils.initAd(TryToEarnDetailsActivity.this, applyId, inCome / 2);
                                                        } else {
                                                            TryToEarnDetailsActivity.this.finish();
                                                        }
                                                    }
                                                });

                                            }
                                        });
                                        dialog4.show();
                                    } else {
                                        ReceiveGoldDialog6 dialog6 = new ReceiveGoldDialog6(TryToEarnDetailsActivity.this, inCome, flag);
                                        dialog6.show();
                                    }
                                }
                            });
                        }, (OnError) error -> {
                            error.show();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadingDialog.dismiss();
                                }
                            });
                        });
                    } else {
                        if (!taskDetailsModel.getTaskDetailVO().getTryTaskVO().getSchemeUrl().equals("")) {
                            OpenApp.schemeUrl(TryToEarnDetailsActivity.this, taskDetailsModel.getTaskDetailVO().getTryTaskVO().getSchemeUrl());
                        } else {
                            OpenApp.OpenApp(TryToEarnDetailsActivity.this, taskDetailsModel.getTaskDetailVO().getTryTaskVO().getAppOpenUrl());
                        }
                    }

                } else {
                    Tip.show("请安装后，再试玩！");
                }

            }
        });
    }

    private void getData() {
        LoadingDialog dialog = new LoadingDialog(this);
        dialog.show();
        RxHttp.postEncryptJson(ComParamContact.Main.GET_TASK).add("userTaskId", userTaskId).asResponse(String.class).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            taskDetailsModel = new Gson().fromJson(result, TaskDetailsModel.class);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvTitle.setText(taskDetailsModel.getTaskDetailVO().getMainTitle());
                    tvDes.setText(taskDetailsModel.getTaskDetailVO().getSubTitle());
                    GlideUtils.getInstance().LoadContextRoundBitmap(TryToEarnDetailsActivity.this, taskDetailsModel.getTaskDetailVO().getIcon(), ivAppIcon, 8);
                    inCome = taskDetailsModel.getIncome();
                    taskIncome = taskDetailsModel.getTaskDetailVO().getIncome();
                    tvTitle1.setText(taskDetailsModel.getTaskDetailVO().getRemark());
                    if (taskIncome != taskDetailsModel.getIncome()) {
                        tvNumberGold2.setText(taskIncome + "");
                        tvNumberGold2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    tvNumberGold.setText(taskDetailsModel.getIncome() + "");

                    long curTime = System.currentTimeMillis();
                    long timers = taskDetailsModel.getExpiryTime() - curTime;
                    TimerUtils.startTimerHour1(TryToEarnDetailsActivity.this, timers, tvTime);
                    int taskID = SPUtils.getInstance().getInt("taskID");
                    if (taskId == taskID) {
                        tvStartPlay.setBackgroundResource(R.drawable.shape_round_green_16);
                        tvStartPlay.setTextColor(TryToEarnDetailsActivity.this.getResources().getColor(R.color.white));
                        tvStartPlay.setEnabled(true);
                    }
                    if (taskDetailsModel.getTaskDetailVO().getTryTaskVO().isKeepLive()) {
                        tvStartPlay.setBackgroundResource(R.drawable.shape_round_green_16);
                        tvStartPlay.setTextColor(TryToEarnDetailsActivity.this.getResources().getColor(R.color.white));
                        tvStartPlay.setEnabled(true);
                        tvContent2.setText("① 点击“开始试玩”体验10秒");
                        tvContent3.setText("②  试玩后回本页面领奖");
                        llOne.setVisibility(View.GONE);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            dialog.dismiss();
                        }
                    });
                }
            });
        }, error -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_back, R.id.tv_start_down, R.id.tv_start_play})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_start_down:
                if (taskDetailsModel.getTaskDetailVO().getTryTaskVO().getAddrType() != 1) {
                    String url = taskDetailsModel.getTaskDetailVO().getTryTaskVO().getAddr();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                } else {
                    downLoadApp();
                }
                tvStartPlay.setBackgroundResource(R.drawable.shape_round_green_16);
                tvStartPlay.setEnabled(true);
                break;
            case R.id.tv_start_play:
                //检查 是否下载了app
                if (OpenApp.isInstalled(TryToEarnDetailsActivity.this, taskDetailsModel.getTaskDetailVO().getTryTaskVO().getAppOpenUrl())) {
                    tvReceiveRewards.setBackgroundResource(R.drawable.shape_round_16_red);
                    tvReceiveRewards.setEnabled(true);
                    SPUtils.getInstance().put("taskID", taskId);
                    SPUtils.getInstance().put("beginTime", System.currentTimeMillis());
                    //还要开始任务
                    if (!StringUtils.isEmpty(taskDetailsModel.getTaskDetailVO().getTryTaskVO().getSchemeUrl())) {
                        if (OpenApp.schemeValid(TryToEarnDetailsActivity.this, taskDetailsModel.getTaskDetailVO().getTryTaskVO().getSchemeUrl())) {
                            OpenApp.schemeUrl(TryToEarnDetailsActivity.this, taskDetailsModel.getTaskDetailVO().getTryTaskVO().getSchemeUrl());
                        } else {
                            Tip.show("请按照流程操作");
                        }

                    } else {
                        OpenApp.OpenApp(this, taskDetailsModel.getTaskDetailVO().getTryTaskVO().getAppOpenUrl());
                    }
                } else {
                    Tip.show("请安装后， 再试玩！");
                }
                break;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        boolean background = OpenApp.isBackground(mContext);
        if (background) {
            long aLong = SPUtils.getInstance().getLong("beginTime", 0);
            if (System.currentTimeMillis() - aLong < 5000) {
                begin();
            }
        }

    }

    public void begin() {
        RxHttp.postEncryptJson(ComParamContact.Main.TASK_BEGIN).add("userTaskId", userTaskId).asResponse(String.class).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            BeanTaskModel beanTaskModel = new Gson().fromJson(result, BeanTaskModel.class);

            if (beanTaskModel.isIsSucceed()) {
                Tip.show("开始任务！试玩10秒后可领取奖励");
                SPUtils.getInstance().put("taskStartTime", beanTaskModel.getStartTime());
            } else {
                Tip.show("开始失败，请重试！");
            }
        }, (OnError) error -> {
            error.show();
            // 已经开始过任务
            OpenApp.OpenApp(this, taskDetailsModel.getTaskDetailVO().getTryTaskVO().getAppOpenUrl());
        });

    }

    private void downLoadApp() {
        DownloadProgressDialog progressDialog = new DownloadProgressDialog(TryToEarnDetailsActivity.this);
        // 设置ProgressDialog 标题图标
        //progressDialog.setIcon(R.drawable.a);
        // 设置ProgressDialog 进度条进度
        // 设置ProgressDialog 的进度条是否不明确
        progressDialog.setProgressStyle(STYLE_HORIZONTAL);
        // 设置ProgressDialog 是否可以按退回按键取消
        progressDialog.setCancelable(true);
        progressDialog.show();
        File externalDownloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File applicationFileDir = new File(externalDownloadsDir, "boniu");
        if (!applicationFileDir.exists()) {
            applicationFileDir.mkdirs();
        }
        destPath = new File(applicationFileDir, System.currentTimeMillis() + ".apk");
        progressDialog.setMax(100);
        long length = destPath.length();
        RxHttp.get(taskDetailsModel.getTaskDetailVO().getTryTaskVO().getAddr())
                .setRangeHeader(length, -1, true)  //设置开始下载位置，结束位置默认为文件末尾
                .asDownload(destPath.getPath(), progress -> {
                    Log.e("sht", "progress->" + progress);
                    //如果需要衔接上次的下载进度，则需要传入上次已下载的字节数length
                    progressDialog.setMax((int) progress.getTotalSize());
                    progressDialog.setProgress((int) progress.getCurrentSize());
                    //下载进度回调,0-100，仅在进度有更新时才会回调
                }, AndroidSchedulers.mainThread())
                .to(RxLife.as(this)) //加入感知生命周期的观察者
                .subscribe(s -> { //s为String类型
                    progressDialog.dismiss();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
                        if (haveInstallPermission) {
                            OpenApp.installApk(mContext, destPath);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setMessage("安装应用需要打开安装未知来源应用权限，请去设置中开启权限");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Uri packageUri = Uri.parse("package:" + TryToEarnDetailsActivity.this.getPackageName());
                                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUri);
                                    startActivityForResult(intent, 101);
                                }
                            });
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.create().show();
                        }
                    } else {
                        OpenApp.installApk(mContext, destPath);
                    }
                    //下载成功，处理相关逻辑
                }, (OnError) error -> {
                    // error.show();
                    Tip.show("下载失败");
                    //下载失败，处理相关逻辑
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                OpenApp.installApk(mContext, destPath);
            } else {
                Tip.show("未打开'安装未知来源'开关,无法安装,请打开后重试");
            }
        }


    }
}
