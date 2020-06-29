package com.boniu.starplan.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.entity.ErrorInfo;
import com.boniu.starplan.entity.TaskDetailsModel;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.OpenApp;
import com.boniu.starplan.utils.TimerUtils;
import com.boniu.starplan.utils.Tip;
import com.google.gson.Gson;
import com.rxjava.rxlife.RxLife;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;

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
    private int taskId, userTaskId;
    private TaskDetailsModel taskDetailsModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_try_to_earn_details;
    }

    @Override
    public void init() {
        taskId = getIntent().getIntExtra("taskId", -1);
        userTaskId = getIntent().getIntExtra("userTaskId", -1);
        tvBarTitle.setText("试玩赚详情");
        getData();
        tvReceiveRewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxHttp.postEncryptJson(ComParamContact.Main.TASk_END).add("userTaskId", userTaskId).asResponse(String.class).subscribe(s -> {
                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                    Log.e("", "");
                }, (OnError) error -> {
                    error.show();
                });
            }
        });
    }

    private void getData() {
        RxHttp.postEncryptJson(ComParamContact.Main.GET_TASK).add("userTaskId", userTaskId).add("type", "1").asResponse(String.class).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            taskDetailsModel = new Gson().fromJson(result, TaskDetailsModel.class);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvTitle.setText(taskDetailsModel.getTaskDetailVO().getMainTitle());
                    tvDes.setText(taskDetailsModel.getTaskDetailVO().getSubTitle());
                    tvNumberGold.setText(taskDetailsModel.getIncome() + "");
                    TimerUtils.startTimerHour(TryToEarnDetailsActivity.this, taskDetailsModel.getTaskDetailVO().getDurableTime(), tvTime);
                }
            });
        }, error -> {
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
                if (taskDetailsModel.getTaskDetailVO().getTryTaskVO().getAddrType() == 1) {
                    if (OpenApp.isMobile_spExist(this)) {
                        OpenApp.channelUrl(this, "com.boniu.qushuiyin");
                    } else {
                        String url = "https://sj.qq.com/myapp/detail.htm?apkName=com.boniu.qushuiyin";
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);
                    }
                } else {
                    downLoadApp();
                }
                tvStartPlay.setBackgroundResource(R.drawable.shape_round_green_16);
                tvStartPlay.setEnabled(true);
                break;
            case R.id.tv_start_play:
                //检查 是否下载了app
                if (OpenApp.isInstalled(TryToEarnDetailsActivity.this, "com.boniu.qushuiyin")) {
                    //还要开始任务
                    RxHttp.postEncryptJson(ComParamContact.Main.TASK_BEGIN).add("userTaskId", userTaskId).asResponse(String.class).subscribe(s -> {
                        String result = AESUtil.decrypt(s, AESUtil.KEY);
                        Log.e("", "");
                        if (result.equals("1")) {
                            tvReceiveRewards.setBackgroundResource(R.drawable.shape_round_16_red);
                            tvReceiveRewards.setEnabled(true);
                            OpenApp.OpenApp(this, "com.boniu.qushuiyin");
                        }else{
                            Tip.show("开始失败，请重试！");
                        }
                    }, (OnError) error -> {
                        error.show();
                    });

                } else {
                    Tip.show("请安装后，在试玩！");
                }
                break;

        }
    }

    private void downLoadApp() {
        String destPath = getExternalCacheDir() + "/" + System.currentTimeMillis() + ".apk";
        RxHttp.get("/miaolive/Miaolive.apk")
                .setDomainToUpdateIfAbsent()//使用指定的域名
                .asDownload(destPath, progress -> {
                    //下载进度回调,0-100，仅在进度有更新时才会回调，最多回调101次，最后一次回调文件存储路径
                    int currentProgress = progress.getProgress(); //当前进度 0-100
                    long currentSize = progress.getCurrentSize(); //当前已下载的字节大小
                    long totalSize = progress.getTotalSize();     //要下载的总字节大小
                }, AndroidSchedulers.mainThread()) //指定回调(进度/成功/失败)线程,不指定,默认在请求所在线程回调
                .to(RxLife.to(this)) //感知生命周期
                .subscribe(s -> {
                    //下载完成，处理相关逻辑
                    //安装app
                    OpenApp.checkInstallApkPermission(this, destPath);
                }, (OnError) error -> {
                    //下载失败，处理相关逻辑
                    Tip.show("下载失败,请稍后再试!");
                });
    }


}
