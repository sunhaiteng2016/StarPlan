package com.boniu.starplan.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.DownloadProgressDialog;
import com.boniu.starplan.dialog.InvigorateDialog;
import com.boniu.starplan.dialog.LoadingDialog;
import com.boniu.starplan.dialog.RunningTaskDialog;
import com.boniu.starplan.entity.ApplyTask;
import com.boniu.starplan.entity.ErrorInfo;
import com.boniu.starplan.entity.RunningTaskModel;
import com.boniu.starplan.entity.TaskMode;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.DownloadAppUtils;
import com.boniu.starplan.utils.GlideUtils;
import com.boniu.starplan.utils.NetUtil;
import com.boniu.starplan.utils.OpenApp;
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
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
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
    private File destPath;

    @Override
    public int getLayoutId() {
        return R.layout.activity_try_to_earn;
    }

    @Override
    public void init() {
        tvSubmit.setVisibility(View.GONE);
        tvSubmit.setText("审核进度");
        tvBarTitle.setText("试玩赚");
        initView();
        getData();
        loadingDialog1 = new LoadingDialog(this);
        loadingDialog1.show();
    }

    private void getData() {
        if (NetUtil.isNetworkAvalible(TryToEarnActivity.this)) {
            RxHttp.postEncryptJson(ComParamContact.Main.TASk_LIST).add("page", page).add("pageSize", pageSize).add("type", type).asResponse(String.class).to(RxLife.toMain(this)).subscribe(s -> {
                String result = AESUtil.decrypt(s, AESUtil.KEY);
                TaskMode taskModel = new Gson().fromJson(result, TaskMode.class);
                if (page == 1) {
                    taskList.clear();
                }
                taskList.addAll(taskModel.getRows());

                loadingDialog1.dismiss();
                adapter.notifyDataSetChanged();

            }, (OnError) error -> {
                loadingDialog1.dismiss();
                error.show();
            });
            //用户进行中的任务
            RxHttp.postEncryptJson(ComParamContact.Main.List_to_Do).add("taskType", "1").asResponse(String.class).to(RxLife.toMain(this)).subscribe(s -> {
                String result = AESUtil.decrypt(s, AESUtil.KEY);
                List<RunningTaskModel> lists = new Gson().fromJson(result, new TypeToken<List<RunningTaskModel>>() {
                }.getType());
                if (lists.size() == 0) rlRunningTask.setVisibility(View.GONE);
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
                        } else {
                            rlRunningTask.setVisibility(View.GONE);
                        }
                        GlideUtils.getInstance().LoadContextRoundBitmap(TryToEarnActivity.this, runningTaskModel.getIcon(), ivTaskImg, 8);
                        long curTime = System.currentTimeMillis();
                        long timers = runningTaskModel.getExpiryTime() - curTime;
                        TimerUtils.startTimerHour(TryToEarnActivity.this, timers, tvTime);
                        tvTitle.setText(runningTaskModel.getMainTitle());
                        tvDes.setText(runningTaskModel.getSubTitle());
                    }
                });
            }, (OnError) error -> {
            });
        } else {
            Tip.show("请检查当前网络！");
        }

    }

    private void initView() {
        RlvManagerUtils.createLinearLayout(this, rlvTask);
        adapter = new CommonAdapter<TaskMode.RowsBean>(this, R.layout.item_task_try_to_earn, taskList) {

            @Override
            protected void convert(ViewHolder holder, TaskMode.RowsBean taskMode, int position) {
                GlideUtils.getInstance().LoadContextRoundBitmap(TryToEarnActivity.this, taskMode.getIcon(), holder.getView(R.id.tv1), 8);
                holder.setText(R.id.main_title, taskMode.getMainTitle()).setText(R.id.sub_title, taskMode.getSubTitle());
                holder.setText(R.id.gradient_tv, "+" + taskMode.getIncome());
                if (taskMode.getKeepLive()) {
                    holder.setVisible(R.id.iv_day_task, true);
                } else {
                    holder.setVisible(R.id.iv_day_task, false);
                }
                holder.setOnClickListener(R.id.iv_day_task, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Tip.show("此任务可每日领金币！");
                    }
                });
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
                    //如果是促活任务
                    if (taskList.get(i).getKeepLive()) {
                        //检测本地有没有包
                        if (OpenApp.isInstalled(TryToEarnActivity.this, taskList.get(i).getAppOpenUrl())) {
                            if (isRunningTask) {
                                showTaskRunningDialog();
                            } else {
                                ReceiveTask(taskList.get(i).getId());
                            }
                        } else {
                            //上报服务端没有
                            showKeepLive(taskList.get(i));
                        }
                    } else {
                        if (isRunningTask) {
                            showTaskRunningDialog();
                        } else {
                            ReceiveTask(taskList.get(i).getId());
                        }
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
                refreshlayout.finishLoadMore();//传入false表示加载失败
                page++;
                getData();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                page = 1;
                getData();
            }
        });
    }

    /**
     * 促活弹窗
     *
     * @param rowsBean
     */
    private void showKeepLive(TaskMode.RowsBean rowsBean) {
        InvigorateDialog invigorateDialog = new InvigorateDialog(TryToEarnActivity.this, rowsBean, 3, new InvigorateDialog.DownloadUrlCallback() {
            @Override
            public void onLoad() {
                //去下载
                if (rowsBean.getAddrType() != 1) {
                    String url = rowsBean.getAddr();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                } else {
                    DownloadAppUtils.newInstance().gotoLoad(TryToEarnActivity.this, rowsBean.getAddr());
                }
                RxHttp.postEncryptJson(ComParamContact.Main.repetition).add("applySource", "1").add("taskId", rowsBean.getId()).asResponse(String.class).subscribe(s -> {
                });
            }
        });
        invigorateDialog.show();
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
                        isRunningTask = false;
                        runTaskId = -1;
                        rlRunningTask.setVisibility(View.GONE);
                        //是否开始新的任务
                        RunningTaskDialog dialog = new RunningTaskDialog(TryToEarnActivity.this, 2, new RunningTaskDialog.RunningCallback() {
                            @Override
                            public void running() {
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
        RxHttp.postEncryptJson(ComParamContact.Main.TASK_APPLY).add("taskId", taskId).add("applySource", "1").asResponse(String.class).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            ApplyTask applyTask = new Gson().fromJson(result, ApplyTask.class);
            userTaskId = applyTask.getUserTaskId();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingDialog1.dismiss();
                    if (applyTask.isIsSucceed()) {
                        Tip.showCancer1("领取成功，跳转中...");
                        ARouter.getInstance().build("/ui/TryToEarnDetailsActivity").withInt("userTaskId", userTaskId).withInt("taskId", taskId).navigation();
                    } else {
                        if (applyTask.isIsExist()) {
                            showTaskRunningDialog();
                        } else {
                            Tip.show(applyTask.getMessage());
                        }
                    }
                }
            });

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

    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                OpenApp.installApk(mContext, DownloadAppUtils.newInstance().destPath);
            } else {
                Tip.show("未打开'安装未知来源'开关,无法安装,请打开后重试");
            }
        }
    }
}
