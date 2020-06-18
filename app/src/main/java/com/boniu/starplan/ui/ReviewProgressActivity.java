package com.boniu.starplan.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.entity.ReviewProgressModel;
import com.boniu.starplan.utils.RlvManagerUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.rlv)
    RecyclerView rlv;
    private List<ReviewProgressModel> list = new ArrayList<>();
    private CommonAdapter<ReviewProgressModel> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_review_progress;
    }

    @Override
    public void init() {
        tvBarTitle.setText("审核进度");
        initView();
    }

    private void initView() {
        RlvManagerUtils.createLinearLayout(this, rlv);
        list.add(new ReviewProgressModel());
        list.add(new ReviewProgressModel());
        list.add(new ReviewProgressModel());
        adapter = new CommonAdapter<ReviewProgressModel>(this, R.layout.item_review_progress, list) {

            @Override
            protected void convert(ViewHolder holder, ReviewProgressModel reviewProgressModel, int position) {

            }
        };
        rlv.setAdapter(adapter);
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
