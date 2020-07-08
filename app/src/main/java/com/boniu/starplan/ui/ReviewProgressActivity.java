package com.boniu.starplan.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.LoadingDialog;
import com.boniu.starplan.entity.ApplyTask;
import com.boniu.starplan.entity.ReviewProgressModel;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.DateTimeUtils;
import com.boniu.starplan.utils.GlideUtils;
import com.boniu.starplan.utils.RlvManagerUtils;
import com.boniu.starplan.utils.Tip;
import com.google.gson.Gson;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;

/**
 * 审核进度
 */
@Route(path = "/ui/ReviewProgressActivity")
public class ReviewProgressActivity extends BaseActivity {


    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.rlv)
    RecyclerView rlv;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    private List<ReviewProgressModel.RowsBean> list = new ArrayList<>();
    private CommonAdapter<ReviewProgressModel.RowsBean> adapter;
    private LoadingDialog loadingDialog;
    private int page = 1;
    private int pageSize = 10;

    @Override
    public int getLayoutId() {
        return R.layout.activity_review_progress;
    }

    @Override
    public void init() {
        tvBarTitle.setText("审核进度");
        initView();
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        getDates();
    }

    private void getDates() {
        RxHttp.postEncryptJson(ComParamContact.Main.listAuditSchedule).add("page", page).add("pageSize", pageSize).asResponse(String.class).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            ReviewProgressModel reviewProgressModel = new Gson().fromJson(result, ReviewProgressModel.class);
            List<ReviewProgressModel.RowsBean> rows = reviewProgressModel.getRows();
            if (page == 1) {
                list.clear();
            }
            list.addAll(rows);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (page == 1) {
                        if (list.size() == 0) {
                            llNodata.setVisibility(View.VISIBLE);
                        } else {
                            llNodata.setVisibility(View.GONE);
                        }
                    }
                    loadingDialog.dismiss();
                    adapter.notifyDataSetChanged();
                }
            });
        }, (OnError) error -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.dismiss();
                }
            });
            error.show();
        });
    }

    private void initView() {
        RlvManagerUtils.createLinearLayout(this, rlv);

        adapter = new CommonAdapter<ReviewProgressModel.RowsBean>(this, R.layout.item_review_progress, list) {

            @Override
            protected void convert(ViewHolder holder, ReviewProgressModel.RowsBean reviewProgressModel, int position) {
                GlideUtils.getInstance().LoadContextRoundBitmap(ReviewProgressActivity.this, reviewProgressModel.getIcon(), holder.getView(R.id.iv_img), 8);
                holder.setText(R.id.tv_title, reviewProgressModel.getMainTitle())
                        .setText(R.id.tv_income, reviewProgressModel.getIncome() + "金币")
                        .setText(R.id.tv_time, DateTimeUtils.getDate(reviewProgressModel.getCreateDate(),"yyyy-MM-dd HH:mm"));
                if (reviewProgressModel.getStatus()==3){
                    holder.setText(R.id.tv_remark, reviewProgressModel.getStatusDesc());
                }
                if (reviewProgressModel.getStatus()==4){
                    holder.setText(R.id.tv_remark, "审核失败");
                }
                if (reviewProgressModel.getStatus()==9){
                    holder.setText(R.id.tv_remark, "审核通过");
                }
            }
        };
        rlv.setAdapter(adapter);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
                page++;
                getDates();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                page = 1;
                getDates();
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
        }
    }
}
