package com.boniu.starplan.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.entity.ReceiveGoldModel;
import com.boniu.starplan.helper.GlideImageEngine;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.GlideUtils;
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
                        //设置自定义遮盖层，定制自己想要的效果，当设置遮盖层后，原本的指示器会被隐藏
                        // .setCustomShadeView(showCustomShadeView ? customView : null)
                        //自定义ProgressView，不设置默认默认没有
                        //.setCustomProgressViewLayoutID(showCustomProgressView ? R.layout.layout_custom_progress_view : 0)
                        //当前位置
                        .setCurrentPosition(i)
                        //图片引擎
                        .setImageEngine(imageEngine)
                        //图片集合
                        .setImageList(mThumbViewInfoList)
                        //方向设置
                        .setScreenOrientationType(screenOrientationType)
                        //点击监听
                        /*.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(FragmentActivity activity, View view, int position, String url) {
                                //TODO:注意，这里的View可能是ImageView,也可能是自定义setCustomImageViewLayout的View
                            }
                        })
                        //长按监听
                        .setOnLongClickListener(new OnLongClickListener() {
                            @Override
                            public void onLongClick(final FragmentActivity activity, final View imageView, int position, String url) {
                                //TODO:注意，这里的View可能是ImageView,也可能是自定义setCustomImageViewLayout的View
                                if(imageView instanceof ImageView){
                                    showListDialog(activity, (ImageView) imageView);
                                }else{
                                    MToast.makeTextShort(context,"自定义setCustomImageViewLayout的View,自己实现长按功能");
                                }
                            }
                        })
                        //页面切换监听
                        .setOnPageChangeListener(new OnPageChangeListener() {
                            @Override
                            public void onPageSelected(int position) {
                                Log.i(TAG, "onPageSelected:" + position);
                                if (tv_number_indicator != null) {
                                    tv_number_indicator.setText((position + 1) + "/" + MNImageBrowser.getImageList().size());
                                }
                            }
                        })*/
                        //全屏模式
                        .setFullScreenMode(true)
                        //打开动画
                        /* .setActivityOpenAnime(openAnim)
                         //关闭动画
                         .setActivityExitAnime(exitAnim)*/
                        //手势下拉缩小效果
                        .setOpenPullDownGestureEffect(false)
                        //自定义显示View
                        //.setCustomImageViewLayoutID(showCustomImageView ? R.layout.layout_custom_image_view_fresco : 0)
                        //显示：传入当前View
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
                if (!StringUtils.isEmpty(receiveGoldModel.getTaskDetailVO().getAuditTaskVO().getToUrl())) {
                    SPUtils.getInstance().put("taskID", taskId);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(receiveGoldModel.getTaskDetailVO().getAuditTaskVO().getToUrl()));
                    startActivity(browserIntent);
                    tvEndTask.setBackgroundResource(R.drawable.shape_round_green_22);
                    tvEndTask.setTextColor(getResources().getColor(R.color.white));
                } else {
                    Tip.show("链接失效，请退出重试！");
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


}
