package com.boniu.starplan.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.GlideUtils;
import com.boniu.starplan.utils.RlvManagerUtils;
import com.boniu.starplan.utils.SPUtils;
import com.boniu.starplan.utils.StringUtils;
import com.boniu.starplan.utils.TimerUtils;
import com.boniu.starplan.utils.Tip;
import com.google.gson.Gson;
import com.liji.imagezoom.util.ImageZoom;
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
    private List<ReceiveGoldModel.TaskDetailVOBean.TaskImgsVOBean> imgList = new ArrayList<>();
    private CommonAdapter<ReceiveGoldModel.TaskDetailVOBean.TaskImgsVOBean> adapter;
    private int taskId, userTaskId;
    private ReceiveGoldModel receiveGoldModel;
    private List<String> zoomList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_receive_gold_details;
    }

    @Override
    public void init() {
        taskId = getIntent().getIntExtra("taskId", -1);
        userTaskId = getIntent().getIntExtra("userTaskId", -1);
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
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
    }

    private void getData() {
        RxHttp.postEncryptJson(ComParamContact.Main.GET_TASK).add("userTaskId", userTaskId).add("type", "2").asResponse(String.class).subscribe(s -> {
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
                    imgList.clear();
                    imgList.addAll(taskImgVo);
                    for (ReceiveGoldModel.TaskDetailVOBean.TaskImgsVOBean bean : imgList) {
                        zoomList.add(bean.getImgUrl());
                    }
                    adapter.notifyDataSetChanged();
                    long curTime = System.currentTimeMillis();
                    long timers = receiveGoldModel.getExpiryTime() - curTime;
                    TimerUtils.startTimerHour1(ReceiveGoldDetailsActivity.this, timers, tvNumTime);
                    //在这里检查是否开始过任务
                    int taskID = SPUtils.getInstance().getInt("taskID");
                    if (taskId == taskID) {
                        tvEndTask.setBackgroundResource(R.drawable.shape_round_green_22);
                        tvEndTask.setTextColor(ReceiveGoldDetailsActivity.this.getResources().getColor(R.color.white));
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
                ImageZoom.show(ReceiveGoldDetailsActivity.this, i, zoomList);
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
                } else {
                    Tip.show("链接失效，请退出重试！");
                }
                break;
            case R.id.tv_end_task:
                ARouter.getInstance().build("/ui/FinishRegisterActivity").withInt("userTaskId", userTaskId).navigation();
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_submit:

                break;
        }
    }


}
