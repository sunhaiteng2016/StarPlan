package com.boniu.starplan.help;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.dialog.CancellationDialog;
import com.boniu.starplan.utils.SPUtils;
import com.boniu.starplan.utils.StringUtils;
import com.boniu.starplan.utils.Tip;

import butterknife.BindView;
import butterknife.OnClick;


@Route(path = "/mime/HelpAndFeedbackActivity")
public class HelpAndFeedbackActivity extends BaseActivity implements CancellationDialog.CancellationInterfaces {


    @BindView(R.id.rl_jm)
    RelativeLayout rlJm;
    @BindView(R.id.rl_gn)
    RelativeLayout rlGn;
    @BindView(R.id.rl_nr)
    RelativeLayout rlNr;
    @BindView(R.id.rl_qt)
    RelativeLayout rlQt;
    @BindView(R.id.rl_cpjy)
    RelativeLayout rlCpjy;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.rl_zhuxiao)
    RelativeLayout rlZhuxiao;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    @BindView(R.id.rl_qq)
    RelativeLayout rlQq;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    private ClipboardManager mClipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_help_and_feedback;
    }


    @Override
    public void init() {
        tvBarTitle.setText("帮助与反馈");
        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        //initView();
    }


    @OnClick({R.id.rl_qq, R.id.rl_jm, R.id.rl_gn, R.id.rl_nr, R.id.rl_qt, R.id.rl_cpjy, R.id.rl_zhuxiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.rl_jm:
                ARouter.getInstance().build("/mime/FeedbackDetailsActivity").withInt(FeedbackDetailsActivity.FEEDBACK_TYPE, 0).navigation();
                break;
            case R.id.rl_gn:
                ARouter.getInstance().build("/mime/FeedbackDetailsActivity").withInt(FeedbackDetailsActivity.FEEDBACK_TYPE, 1).navigation();
                break;
            case R.id.rl_nr:
                ARouter.getInstance().build("/mime/FeedbackDetailsActivity").withInt(FeedbackDetailsActivity.FEEDBACK_TYPE, 2).navigation();
                break;
            case R.id.rl_qt:
                ARouter.getInstance().build("/mime/FeedbackDetailsActivity").withInt(FeedbackDetailsActivity.FEEDBACK_TYPE, 3).navigation();
                break;
            case R.id.rl_cpjy:
                ARouter.getInstance().build("/mime/FeedbackDetailsActivity").withInt(FeedbackDetailsActivity.FEEDBACK_TYPE, 4).navigation();
                break;
            case R.id.rl_zhuxiao:
                String string = SPUtils.getInstance().getString("cancleTime");
                if (StringUtils.isEmpty(string)) {
                    CancellationDialog cancellationDialog = new CancellationDialog(mContext, HelpAndFeedbackActivity.this);
                    cancellationDialog.show();
                } else {
                    ARouter.getInstance().build("/mime/AccountCancellationActivity").navigation();
                }

                break;
            case R.id.rl_qq:
                customerService();
                break;


        }
    }


    /**
     * 复制QQ号成功后，提示toast“复制成功”，复制成功1s后，唤起QQ
     * <p>
     * QQ号上线前联系运营确定
     */
    public void customerService() { // 客服QQ
        String text = tvQq.getText().toString();
        ClipData myClip = ClipData.newPlainText("text", text);
        mClipboardManager.setPrimaryClip(myClip);
        Tip.show("复制成功");
        Handler handler = new Handler();
        handler.postDelayed(mQqRunnable, 1000);
    }

    private Runnable mQqRunnable = new Runnable() {
        @Override
        public void run() {
            ClipData data = mClipboardManager.getPrimaryClip();
            if (null == data) {
                return;
            }
            try {
                ClipData.Item item = data.getItemAt(0);
                String text = item.getText().toString();
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + text;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            } catch (Exception e) {
                Tip.show("您还没有安装QQ，请先安装软件");
            }

        }
    };


    @Override
    public void cancelUser() {

    }

    @OnClick(R.id.rl_back)
    public void onClick() {
        finish();
    }
}
