package com.boniu.starplan.help;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.utils.Tip;

import butterknife.BindView;
import butterknife.OnClick;


@Route(path = "/mime/FeedbackDetailsActivity")
public class FeedbackDetailsActivity extends BaseActivity {


    //
//    FUNCTION - 功能问题
//    UI - 界面问题
//    ORDER - 订单问题
//    CONTENT - 内容问题
//    OTHER - 其他问题
//    PROD_SUGGEST - 产品建议
    public static final String FEEDBACK_TYPE = "FEEDBACK_TYPE";


    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.edt_feedback)
    EditText edtFeedback;
    @BindView(R.id.tv_return)
    TextView tvReturn;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private int extra;
    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback_details;
    }


    @Override
    public void init() {

        extra = getIntent().getIntExtra(FEEDBACK_TYPE, 0);
        switch (extra) {
            case 0:
                type = "UI";
                tvType.setText("界面问题");
                break;
            case 1:
                type = "FUNCTION";
                tvType.setText("功能问题");
                break;
            case 2:
                type = "CONTENT";
                tvType.setText("内容问题");
                break;
            case 3:
                type = "OTHER";
                tvType.setText("其他问题");
                break;
            case 4:
                type = "PROD_SUGGEST";
                tvType.setText("产品建议");
                break;
        }

        tvBarTitle.setText("帮助与反馈");
        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tip.show(" 提交成功 ");
            }
        });

    }


    @OnClick(R.id.rl_back)
    public void onClick() {
        finish();
    }
}
