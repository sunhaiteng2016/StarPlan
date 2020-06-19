package com.boniu.starplan.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.entity.TaskMode;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.RlvManagerUtils;
import com.boniu.starplan.utils.TimerUtils;
import com.boniu.starplan.view.LoadingView;
import com.google.gson.Gson;
import com.rxjava.rxlife.RxLife;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import rxhttp.wrapper.param.RxHttp;

/**
 * 试玩赚
 */
@Route(path = "/ui/TryToEarnActivity")
public class TryToEarnActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

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
    @BindView(R.id.loadingView)
    LoadingView loadingView;
    private int page = 1;
    private int type = 1; //任务类型 1试玩赚,2领金币,3视频任务,4激励视频翻倍任务
    private String pageSize = "10";
    private List<TaskMode.RowsBean> taskList = new ArrayList<>();
    private CommonAdapter<TaskMode.RowsBean> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_try_to_earn;
    }

    @Override
    public void init() {
        tvBarTitle.setText("试玩赚");
        initView();
        getData();
    }

    private void getData() {
        RxHttp.postEncryptJson(ComParamContact.Main.TASk_LIST).add("page", page).add("pageSize", pageSize).add("type", type).asResponse(String.class).to(RxLife.toMain(this)).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            TaskMode taskModel = new Gson().fromJson(result, TaskMode.class);
            taskList.clear();
            taskList.addAll(taskModel.getRows());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }, (OnError) error -> {
        });
        //用户进行中的任务
        RxHttp.postEncryptJson(ComParamContact.Main.List_to_Do).add("page", "1").add("pageSize", "10").add("taskType", "1").asResponse(String.class).to(RxLife.toMain(this)).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);

        }, (OnError) error -> {
        });
    }

    private void initView() {
        RlvManagerUtils.createLinearLayout(this, rlvTask);
        adapter = new CommonAdapter<TaskMode.RowsBean>(this, R.layout.item_task_try_to_earn, taskList) {

            @Override
            protected void convert(ViewHolder holder, TaskMode.RowsBean taskMode, int position) {
                //GlideUtils.getInstance().LoadContextRoundBitmap(MainActivity.this, taskMode.getIcon(), holder.getView(R.id.iv_img), 8);
                holder.setText(R.id.main_title, taskMode.getMainTitle()).setText(R.id.sub_title, taskMode.getSubTitle());
            }

        };
        rlvTask.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                ARouter.getInstance().build("/ui/TryToEarnDetailsActivity").withInt("taskId", taskList.get(i).getId()).navigation();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
        TimerUtils.startTimerHour(this, tvTime);
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

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
