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
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.entity.TaskDetailsModel;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
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
    private int taskId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_try_to_earn_details;
    }

    @Override
    public void init() {
        taskId = getIntent().getIntExtra("taskId", -1);
        tvBarTitle.setText("试玩赚详情");
        TimerUtils.startTimerHour(this, tvTime);
        getData();
    }

    private void getData() {
        RxHttp.postEncryptJson(ComParamContact.Main.getTask).add("id", taskId).add("type", "1").asResponse(String.class).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            TaskDetailsModel taskDetailsModel = new Gson().fromJson(result, TaskDetailsModel.class);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvTitle.setText(taskDetailsModel.getMainTitle());
                    tvDes.setText(taskDetailsModel.getSubTitle());
                    tvNumberGold.setText(taskDetailsModel.getIncome());
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


    @OnClick({R.id.rl_back, R.id.tv_start_down, R.id.tv_start_play, R.id.tv_receive_rewards})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_start_down:
                if (true) {
                    //有链接
                } else {
                    downLoadApp();
                }
                break;
            case R.id.tv_start_play:

                break;
            case R.id.tv_receive_rewards:
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
                    installApk(new File(destPath));
                }, (OnError) error -> {
                    //下载失败，处理相关逻辑
                    Tip.show("下载失败,请稍后再试!");
                });
    }

    private void installApk(File apk) {
        Uri uri = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(apk);
        } else {
            uri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", apk);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        mContext.startActivity(intent);
    }
}
