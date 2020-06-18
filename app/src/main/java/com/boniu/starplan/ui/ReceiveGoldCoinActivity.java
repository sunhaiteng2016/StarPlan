package com.boniu.starplan.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.entity.TaskMode;
import com.boniu.starplan.utils.RlvManagerUtils;
import com.boniu.starplan.view.LoadingView;
import com.boniu.starplan.view.TaskProgressView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 领金币
 */
@Route(path = "/ui/ReceiveGoldCoinActivity")
public class ReceiveGoldCoinActivity extends BaseActivity {


    @BindView(R.id.iv_task_img)
    TextView ivTaskImg;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rlv_task)
    RecyclerView rlvTask;
    @BindView(R.id.loadingView)
    LoadingView loadingView;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.line1)
    TaskProgressView line1;
    private List<TaskMode> lists = new ArrayList<>();
    private CommonAdapter<TaskMode> adapter;
    private AlertDialog mDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_receive_gold_coin;
    }

    @Override
    public void init() {
        tvBarTitle.setText("领金币");
        initView();
    }

    private void initView() {

        RlvManagerUtils.createLinearLayout(this, rlvTask);
        lists.add(new TaskMode());
        lists.add(new TaskMode());
        lists.add(new TaskMode());
        lists.add(new TaskMode());
        lists.add(new TaskMode());
        lists.add(new TaskMode());
        adapter = new CommonAdapter<TaskMode>(this, R.layout.item_task_we, lists) {

            @Override
            protected void convert(ViewHolder holder, TaskMode taskMode, int position) {
                holder.setOnClickListener(R.id.tv_complete, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showMyDialog();
                    }
                });
            }
        };
        rlvTask.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                if (i == 0) {
                    loadingView.setVisibility(View.VISIBLE);
                    loadingView.setText("拼命争抢中，请稍后...");
                    loadingView.showLoading();
                } else {
                    ARouter.getInstance().build("/ui/ReceiveGoldDetailsActivity").navigation();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
        line1.setProgress(1);
    }

    private void showMyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = LayoutInflater.from(this).inflate(R.layout.dialog_task, null);
        builder.setView(mView);
        mDialog = builder.create();
        mDialog.show();
        mView.findViewById(R.id.tv_back_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mView.findViewById(R.id.tv_take_new_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
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
                break;
        }
    }
}
