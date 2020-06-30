package com.boniu.starplan.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.LoadingDialog;
import com.boniu.starplan.dialog.RunningTaskDialog;
import com.boniu.starplan.entity.ApplyTask;
import com.boniu.starplan.entity.RunningTaskModel;
import com.boniu.starplan.entity.TaskMode;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.GlideUtils;
import com.boniu.starplan.utils.RlvManagerUtils;
import com.boniu.starplan.utils.TimerUtils;
import com.boniu.starplan.utils.Tip;
import com.boniu.starplan.view.TaskProgressView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rxjava.rxlife.RxLife;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
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
 * 领金币
 */
@Route(path = "/ui/ReceiveGoldCoinActivity")
public class ReceiveGoldCoinActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rlv_task)
    RecyclerView rlvTask;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_task_img)
    ImageView ivTaskImg;
    @BindView(R.id.line1)
    TaskProgressView line1;
    @BindView(R.id.rl_running_task)
    LinearLayout rlRunningTask;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List<TaskMode.RowsBean> lists = new ArrayList<>();
    private CommonAdapter<TaskMode.RowsBean> adapter;
    private int page = 1;
    private int type = 2; //任务类型 1试玩赚,2领金币,3视频任务,4激励视频翻倍任务
    private String pageSize = "10";
    private boolean isRunningTask;
    private int userTaskId;
    private int runTaskId;
    private int clickTaskId;
    private LoadingDialog loadingDialog1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_receive_gold_coin;
    }

    @Override
    public void init() {
        tvBarTitle.setText("领金币");
        tvSubmit.setVisibility(View.VISIBLE);
        tvSubmit.setText("审核进度");
        initView();
        loadingDialog1 = new LoadingDialog(this);
        loadingDialog1.show();
        getData();
    }

    private void getData() {
        RxHttp.postEncryptJson(ComParamContact.Main.TASk_LIST).add("page", page).add("pageSize", pageSize).add("type", type).asResponse(String.class).to(RxLife.toMain(this)).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            TaskMode taskModel = new Gson().fromJson(result, TaskMode.class);
            if (page == 1) {
                lists.clear();
            }
            lists.addAll(taskModel.getRows());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingDialog1.dismiss();
                    adapter.notifyDataSetChanged();
                }
            });
        }, (OnError) error -> {
            error.show();
        });
        //用户进行中的任务
        RxHttp.postEncryptJson(ComParamContact.Main.List_to_Do).add("taskType", type).asResponse(String.class).to(RxLife.toMain(this)).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            List<RunningTaskModel> lists = new Gson().fromJson(result, new TypeToken<List<RunningTaskModel>>() {
            }.getType());
            RunningTaskModel runningTaskModel = lists.get(0);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (runningTaskModel.getId() != 0) {
                        isRunningTask = true;
                        runTaskId = runningTaskModel.getTaskId();
                        userTaskId = runningTaskModel.getId();
                        rlRunningTask.setVisibility(View.VISIBLE);
                        rlRunningTask.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ARouter.getInstance().build("/ui/ReceiveGoldDetailsActivity").withInt("userTaskId", userTaskId).withInt("taskId", runTaskId).navigation();
                            }
                        });
                    }
                    GlideUtils.getInstance().LoadContextRoundBitmap(ReceiveGoldCoinActivity.this, runningTaskModel.getIcon(), ivTaskImg, 8);
                    long curTime = System.currentTimeMillis();
                    long timers = runningTaskModel.getExpiryTime() - curTime;
                    TimerUtils.startTimerHour(ReceiveGoldCoinActivity.this, timers, tvTime);
                    tvTitle.setText(runningTaskModel.getMainTitle());
                    tvDes.setText(runningTaskModel.getSubTitle());
                }
            });
        }, (OnError) error -> {
        });
    }

    private void initView() {

        RlvManagerUtils.createLinearLayout(this, rlvTask);

        adapter = new CommonAdapter<TaskMode.RowsBean>(this, R.layout.item_task_we, lists) {

            @Override
            protected void convert(ViewHolder holder, TaskMode.RowsBean taskMode, int position) {

                GlideUtils.getInstance().LoadContextRoundBitmap(ReceiveGoldCoinActivity.this, taskMode.getIcon(), holder.getView(R.id.iv_img), 8);
                holder.setText(R.id.main_title, taskMode.getMainTitle()).setText(R.id.sub_title, taskMode.getSubTitle());
                holder.setText(R.id.gold, taskMode.getIncome() + "");
            }
        };
        rlvTask.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                clickTaskId = lists.get(i).getId();
                if (runTaskId == lists.get(i).getId()) {
                    ARouter.getInstance().build("/ui/TryToEarnDetailsActivity").withInt("taskId", lists.get(i).getId()).navigation();
                } else {
                    if (isRunningTask) {
                        showTaskRunningDialog();
                    } else {
                        ReceiveTask(lists.get(i).getId());
                    }
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
        line1.setProgress(1);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
                page++;
                getData();
            }
        });
    }

    private void showTaskRunningDialog() {

        RunningTaskDialog dialog = new RunningTaskDialog(this, 1, new RunningTaskDialog.RunningCallback() {
            @Override
            public void running() {
                GiveUpTask();
            }
        });
        dialog.show();
    }

    /**
     * 放弃任务
     *
     * @param
     */
    /**
     * 放弃任务
     *
     * @param
     */
    private void GiveUpTask() {
        loadingDialog1.show();
        RxHttp.postEncryptJson(ComParamContact.Main.giveUp).add("userTaskId", userTaskId).asResponse(String.class).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            //放弃成功
            if (result.equals("true")) {
                //放弃成功
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog1.dismiss();
                        //是否开始新的任务
                        RunningTaskDialog dialog = new RunningTaskDialog(ReceiveGoldCoinActivity.this, 2, new RunningTaskDialog.RunningCallback() {
                            @Override
                            public void running() {
                                isRunningTask = false;
                                rlRunningTask.setVisibility(View.GONE);
                                ReceiveTask(clickTaskId);
                            }
                        });
                        dialog.show();

                    }
                });
            } else {
                Tip.show("放弃失败，请重试！");
            }

        }, (OnError) error -> {
            error.show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingDialog1.dismiss();
                }
            });
        });
    }

    /**
     * 领取任务
     *
     * @param taskId
     */
    private void ReceiveTask(int taskId) {
        loadingDialog1.show();
        RxHttp.postEncryptJson(ComParamContact.Main.TASK_APPLY).add("taskId", taskId).asResponse(String.class).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            ApplyTask applyTask = new Gson().fromJson(result, ApplyTask.class);
            userTaskId = applyTask.getUserTaskId();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingDialog1.dismiss();
                    if (applyTask.isIsSucceed()) {

                        ARouter.getInstance().build("/ui/ReceiveGoldDetailsActivity").withInt("userTaskId", userTaskId).withInt("taskId", taskId).navigation();
                    } else {
                        if (applyTask.isIsExist()) {
                            showTaskRunningDialog();
                        } else {
                            Tip.show("领取失败！");
                        }
                    }
                }
            });

        }, (OnError) error -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingDialog1.dismiss();
                }
            });
            error.show();
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_back, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_submit:
                ARouter.getInstance().build("/ui/ReviewProgressActivity").navigation();
                break;
        }
    }
}
