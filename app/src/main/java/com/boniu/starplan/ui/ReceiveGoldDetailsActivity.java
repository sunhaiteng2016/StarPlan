package com.boniu.starplan.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.DownloadProgressDialog;
import com.boniu.starplan.entity.ReceiveGoldModel;
import com.boniu.starplan.helper.GlideImageEngine;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.GlideUtils;
import com.boniu.starplan.utils.OpenApp;
import com.boniu.starplan.utils.RlvManagerUtils;
import com.boniu.starplan.utils.SPUtils;
import com.boniu.starplan.utils.StringUtils;
import com.boniu.starplan.utils.TimerUtils;
import com.boniu.starplan.utils.Tip;
import com.google.gson.Gson;


import com.maning.imagebrowserlibrary.ImageEngine;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.maning.imagebrowserlibrary.listeners.OnClickListener;
import com.maning.imagebrowserlibrary.listeners.OnLongClickListener;
import com.maning.imagebrowserlibrary.listeners.OnPageChangeListener;
import com.maning.imagebrowserlibrary.model.ImageBrowserConfig;
import com.rxjava.rxlife.RxLife;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;

import static com.boniu.starplan.dialog.DownloadProgressDialog.STYLE_HORIZONTAL;

/**
 * 领金币详情
 */
@Route(path = "/ui/ReceiveGoldDetailsActivity")
public class ReceiveGoldDetailsActivity extends BaseActivity {


    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_gold_num)
    TextView tvGoldNum;
    @BindView(R.id.tv_gold_num2)
    TextView tvGoldNum2;
    @BindView(R.id.tv_num_time)
    TextView tvNumTime;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.rlv)
    RecyclerView rlv;
    @BindView(R.id.tv_start_task)
    TextView tvStartTask;
    @BindView(R.id.tv_end_task)
    TextView tvEndTask;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_play_sm)
    TextView tvPlaySm;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_bz)
    TextView tv_bz;
    private List<ReceiveGoldModel.TaskDetailVOBean.TaskImgsVOBean> imgList = new ArrayList<>();
    private CommonAdapter<ReceiveGoldModel.TaskDetailVOBean.TaskImgsVOBean> adapter;
    private int taskId, userTaskId;
    private ReceiveGoldModel receiveGoldModel;
    private int flag;
    private ArrayList<String> mThumbViewInfoList = new ArrayList<>();
    public ImageBrowserConfig.TransformType transformType = ImageBrowserConfig.TransformType.Transform_Default;
    public ImageBrowserConfig.IndicatorType indicatorType = ImageBrowserConfig.IndicatorType.Indicator_Number;
    public ImageBrowserConfig.ScreenOrientationType screenOrientationType = ImageBrowserConfig.ScreenOrientationType.Screenorientation_Default;
    private ImageEngine imageEngine = new GlideImageEngine();
    private File destPath;

    @Override
    public int getLayoutId() {
        return R.layout.activity_receive_gold_details;
    }

    @Override
    public void init() {
        taskId = getIntent().getIntExtra("taskId", -1);
        userTaskId = getIntent().getIntExtra("userTaskId", -1);
        flag = getIntent().getIntExtra("flag", -1);
        tvBarTitle.setText("领金币详情");
        tvSubmit.setVisibility(View.GONE);
        initView();
        initWebView();
        getData();
    }

    private void initWebView() {

        WebSettings settings = webView.getSettings();
        WebViewClient webViewClient = new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        };
        webView.setWebViewClient(webViewClient);
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (dm.densityDpi > 240) {
            settings.setDefaultFontSize(24); //可以取1-72之间的任意值，默认16
        }
    }

    private void getData() {
        RxHttp.postEncryptJson(ComParamContact.Main.GET_TASK).add("userTaskId", userTaskId).asResponse(String.class).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            receiveGoldModel = new Gson().fromJson(result, ReceiveGoldModel.class);
            ReceiveGoldModel.TaskDetailVOBean.AuditTaskVOBean auditTaskVO = receiveGoldModel.getTaskDetailVO().getAuditTaskVO();
            List<ReceiveGoldModel.TaskDetailVOBean.TaskImgsVOBean> taskImgVo = receiveGoldModel.getTaskDetailVO().getTaskImgsVO();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GlideUtils.getInstance().LoadContextRoundBitmap(ReceiveGoldDetailsActivity.this, receiveGoldModel.getTaskDetailVO().getIcon(), ivImg, 8);
                    tvName.setText(receiveGoldModel.getTaskDetailVO().getMainTitle());
                    tvDes.setText(receiveGoldModel.getTaskDetailVO().getSubTitle());
                    tvPlaySm.setText(receiveGoldModel.getTaskDetailVO().getMajorDesc());
                    webView.loadDataWithBaseURL(null, auditTaskVO.getShowDesc(), "text/html", "utf-8", null);
                    if (receiveGoldModel.getTaskDetailVO().getIncome() != receiveGoldModel.getIncome()) {
                        tvGoldNum2.setText(receiveGoldModel.getTaskDetailVO().getIncome() + "");
                        tvGoldNum2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    tvGoldNum.setText(receiveGoldModel.getIncome() + "");

                    if (null != taskImgVo) {
                        imgList.clear();
                        imgList.addAll(taskImgVo);
                        for (ReceiveGoldModel.TaskDetailVOBean.TaskImgsVOBean bean : imgList) {
                            mThumbViewInfoList.add(bean.getImgUrl());
                        }
                        tv_bz.setText("共" + receiveGoldModel.getTaskDetailVO().getAuditTaskVO().getImgs() + "步教程  点击图片查看");
                        adapter.notifyDataSetChanged();
                    }

                    long curTime = System.currentTimeMillis();
                    long timers = receiveGoldModel.getExpiryTime() - curTime;
                    TimerUtils.startTimerHour1(ReceiveGoldDetailsActivity.this, timers, tvNumTime);
                    //在这里检查是否开始过任务
                    int taskID = SPUtils.getInstance().getInt("taskID");
                    if (taskId == taskID) {
                        tvEndTask.setBackgroundResource(R.drawable.shape_round_green_22);
                        tvEndTask.setTextColor(ReceiveGoldDetailsActivity.this.getResources().getColor(R.color.white));
                        tvEndTask.setEnabled(true);
                    }
                }
            });
        }, (OnError) error -> {
            error.show();
        });
    }


    private void initView() {
        RlvManagerUtils.createLinearLayoutHorizontal(this, rlv);
        adapter = new CommonAdapter<ReceiveGoldModel.TaskDetailVOBean.TaskImgsVOBean>(this, R.layout.item_img_list, imgList) {

            @Override
            protected void convert(ViewHolder holder, ReceiveGoldModel.TaskDetailVOBean.TaskImgsVOBean imgListModel, int position) {
                int num = position + 1;
                holder.setText(R.id.tv_position, num + "");
                GlideUtils.getInstance().LoadContextRoundBitmap(ReceiveGoldDetailsActivity.this, imgListModel.getImgUrl(), holder.getView(R.id.iv_img), 8);
            }
        };
        rlv.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                MNImageBrowser.with(ReceiveGoldDetailsActivity.this)
                        //页面切换效果
                        .setTransformType(transformType)
                        //指示器效果
                        .setIndicatorType(indicatorType)
                        //设置隐藏指示器
                        .setIndicatorHide(false)
                        //当前位置
                        .setCurrentPosition(i)
                        //图片引擎
                        .setImageEngine(imageEngine)
                        //图片集合
                        .setImageList(mThumbViewInfoList)
                        //方向设置
                        .setScreenOrientationType(screenOrientationType)
                        //全屏模式
                        .setFullScreenMode(true)
                        .setOpenPullDownGestureEffect(false)
                        .show(view);
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

    @OnClick({R.id.tv_start_task, R.id.tv_end_task, R.id.rl_back, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start_task:
                //外链
                if (receiveGoldModel.getTaskDetailVO().getAuditTaskVO().getToType() == 2) {
                    //下载app
                    downLoadApp(receiveGoldModel.getTaskDetailVO().getAuditTaskVO().getToUrl());
                } else {
                    if (!StringUtils.isEmpty(receiveGoldModel.getTaskDetailVO().getAuditTaskVO().getToUrl())) {
                        SPUtils.getInstance().put("taskID", taskId);
                        tvEndTask.setBackgroundResource(R.drawable.shape_round_green_22);
                        tvEndTask.setTextColor(getResources().getColor(R.color.white));
                        if (receiveGoldModel.getTaskDetailVO().getAuditTaskVO().getToUrl().contains("http")) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(receiveGoldModel.getTaskDetailVO().getAuditTaskVO().getToUrl()));
                            startActivity(browserIntent);
                        } else {
                            if (OpenApp.schemeValid(ReceiveGoldDetailsActivity.this, receiveGoldModel.getTaskDetailVO().getAuditTaskVO().getToUrl())) {
                                OpenApp.schemeUrl(ReceiveGoldDetailsActivity.this, receiveGoldModel.getTaskDetailVO().getAuditTaskVO().getToUrl());
                            } else {
                                Tip.show("请按照数据流程操作");
                            }
                        }
                    } else {
                        Tip.show("链接失效，请退出重试！");
                    }
                }

                break;
            case R.id.tv_end_task:
                if (SPUtils.getInstance().getInt("taskID", -1) == -1) {
                    Tip.show("请先开始任务！");
                    return;
                }
                ARouter.getInstance().build("/ui/FinishRegisterActivity")
                        .withBoolean("auditName", receiveGoldModel.getTaskDetailVO().getAuditTaskVO().isAuditName())
                        .withBoolean("auditMobile", receiveGoldModel.getTaskDetailVO().getAuditTaskVO().isAuditMobile())
                        .withBoolean("auditPicture", receiveGoldModel.getTaskDetailVO().getAuditTaskVO().isAuditPicture())
                        .withSerializable("list", mThumbViewInfoList).withInt("flag", flag).withInt("userTaskId", userTaskId).navigation();
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_submit:

                break;
        }
    }

    private void downLoadApp(String toUrl) {

        DownloadProgressDialog progressDialog = new DownloadProgressDialog(ReceiveGoldDetailsActivity.this);
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
        RxHttp.get(toUrl)
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
                                    Uri packageUri = Uri.parse("package:" + ReceiveGoldDetailsActivity.this.getPackageName());
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
