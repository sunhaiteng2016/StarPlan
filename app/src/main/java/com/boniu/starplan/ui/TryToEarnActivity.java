package com.boniu.starplan.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
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
import com.boniu.starplan.view.LoadingView;
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
 * 试玩赚
 */
@Route(path = "/ui/TryToEarnActivity")
public class TryToEarnActivity extends BaseActivity {

    @BindView(R.id.rlv_task)
    RecyclerView rlvTask;
    @BindView(R.id.sc)
    NestedScrollView sc;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_task_img)
    ImageView ivTaskImg;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rl_running_task)
    LinearLayout rlRunningTask;
    @BindView(R.id.loadingView)
    LoadingView loadingView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int page = 1;
    private int type = 1; //任务类型 1试玩赚,2领金币,3视频任务,4激励视频翻倍任务
    private String pageSize = "10";
    private List<TaskMode.RowsBean> taskList = new ArrayList<>();
    private CommonAdapter<TaskMode.RowsBean> adapter;
    private boolean isRunningTask;
    private int userTaskId;
    private int runTaskId;
    private int clickTaskId;
    private LoadingDialog loadingDialog1;
    private LoadingDialog loadingDialog2;
    private LoadingDialog loadingDialog3;

    @Override
    public int getLayoutId() {
        return R.layout.activity_try_to_earn;
    }

    @Override
    public void init() {
        tvSubmit.setVisibility(View.VISIBLE);
        tvBarTitle.setText("试玩赚");
        tvSubmit.setText("刷新");
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
                taskList.clear();
            }
            taskList.addAll(taskModel.getRows());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingDialog1.dismiss();
                    loadingView.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
            });
        }, (OnError) error -> {
        });
        //用户进行中的任务
        RxHttp.postEncryptJson(ComParamContact.Main.List_to_Do).add("taskType", "1").asResponse(String.class).to(RxLife.toMain(this)).subscribe(s -> {
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
                                ARouter.getInstance().build("/ui/TryToEarnDetailsActivity").withInt("userTaskId", userTaskId).withInt("taskId", runTaskId).navigation();
                            }
                        });
                    }
                    GlideUtils.getInstance().LoadContextRoundBitmap(TryToEarnActivity.this, runningTaskModel.getIcon(), ivTaskImg, 8);
                    TimerUtils.startTimerHour(TryToEarnActivity.this, runningTaskModel.getExpiryTime(), tvTime);
                    tvTitle.setText(runningTaskModel.getMainTitle());
                    tvDes.setText(runningTaskModel.getSubTitle());
                }
            });
        }, (OnError) error -> {
        });
    }

    private void initView() {
        RlvManagerUtils.createLinearLayout(this, rlvTask);
        adapter = new CommonAdapter<TaskMode.RowsBean>(this, R.layout.item_task_try_to_earn, taskList) {

            @Override
            protected void convert(ViewHolder holder, TaskMode.RowsBean taskMode, int position) {
                GlideUtils.getInstance().LoadContextRoundBitmap(TryToEarnActivity.this, taskMode.getIcon(), holder.getView(R.id.tv1), 8);
                holder.setText(R.id.main_title, taskMode.getMainTitle()).setText(R.id.sub_title, taskMode.getSubTitle());
                holder.setText(R.id.gradient_tv, taskMode.getIncome() + "");
            }
        };
        rlvTask.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                clickTaskId = taskList.get(i).getId();
                if (runTaskId == taskList.get(i).getId()) {
                    ARouter.getInstance().build("/ui/TryToEarnDetailsActivity").withInt("userTaskId", userTaskId).withInt("taskId", taskList.get(i).getId()).navigation();
                } else {
                    if (isRunningTask) {
                        showTaskRunningDialog();
                    } else {
                        ReceiveTask(taskList.get(i).getId());
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
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
    private void GiveUpTask() {
        loadingDialog3 = new LoadingDialog(TryToEarnActivity.this);
        loadingDialog3.show();
        RxHttp.postEncryptJson(ComParamContact.Main.giveUp).add("userTaskId", userTaskId).asResponse(String.class).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            //放弃成功
            if (result.equals("true")) {
                //放弃成功
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog3.dismiss();
                        //是否开始新的任务
                        RunningTaskDialog dialog = new RunningTaskDialog(TryToEarnActivity.this, 2, new RunningTaskDialog.RunningCallback() {
                            @Override
                            public void running() {
                                isRunningTask = false;
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
                    loadingDialog3.dismiss();
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
        loadingDialog2 = new LoadingDialog(TryToEarnActivity.this);
        loadingDialog2.show();
        RxHttp.postEncryptJson(ComParamContact.Main.TASK_APPLY).add("taskId", taskId).asResponse(String.class).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            ApplyTask applyTask = new Gson().fromJson(result, ApplyTask.class);
            userTaskId = applyTask.getUserTaskId();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingDialog2.dismiss();
                    if (applyTask.isIsSucceed()) {
                        Tip.showCancer1("领取成功，跳转中。。。");
                        ARouter.getInstance().build("/ui/TryToEarnDetailsActivity").withInt("userTaskId", userTaskId).withInt("taskId", taskId).navigation();
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
            error.show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingDialog2.dismiss();
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

    @OnClick(R.id.rl_back)
    public void onClick() {
        finish();
    }

    @OnClick(R.id.tv_submit)
    public void submit() {
        loadingDialog1.show();
        page = 1;
        getData();
    }

}
