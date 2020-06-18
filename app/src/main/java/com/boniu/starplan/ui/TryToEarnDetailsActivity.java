package com.boniu.starplan.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.entity.ErrorInfo;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.TimerUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;

/**
 * 试玩详情
 */
@Route(path = "/ui/TryToEarnDetailsActivity")
public class TryToEarnDetailsActivity extends BaseActivity {


    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_number_gold)
    TextView tvNumberGold;
    @BindView(R.id.iv_app_icon)
    ImageView ivAppIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_title1)
    TextView tvTitle1;
    @BindView(R.id.tv_title2)
    TextView tvTitle2;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private int taskId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_try_to_earn_details;
    }

    @Override
    public void init() {
        taskId = getIntent().getIntExtra("taskId", -1);
        tvBarTitle.setText("试玩赚详情");
        TimerUtils.startTimerHour(this, tvTime);
        getData();
    }

    private void getData() {
        RxHttp.postEncryptJson(ComParamContact.Main.getTask).add("id", taskId).asResponse(String.class).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            Log.e("","");
        }, error -> {
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
