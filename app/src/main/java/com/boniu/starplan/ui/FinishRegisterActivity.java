package com.boniu.starplan.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提交资料
 */
@Route(path = "/ui/FinishRegisterActivity")
public class FinishRegisterActivity extends BaseActivity {


    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_ex1)
    ImageView ivEx1;
    @BindView(R.id.iv_ex2)
    ImageView ivEx2;
    @BindView(R.id.tv_sub)
    TextView tvSub;

    @Override
    public int getLayoutId() {
        return R.layout.activity_finish_resgister;
    }

    @Override
    public void init() {
        tvBarTitle.setText("完成注册");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_back, R.id.tv_sub})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_sub:

                break;
        }
    }
}
