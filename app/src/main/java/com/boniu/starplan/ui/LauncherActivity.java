package com.boniu.starplan.ui;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.AgreementDialog;
import com.boniu.starplan.utils.SPUtils;
import com.boniu.starplan.utils.StringUtils;
import com.boniu.starplan.utils.Tip;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LauncherActivity extends BaseActivity {

    AgreementDialog dialog;
    @BindView(R.id.fl_layout)
    FrameLayout flLayout;
    private String token;

    @Override
    public int getLayoutId() {
        return R.layout.activity_launcher;
    }

    @Override
    public void init() {
        token = SPUtils.getInstance().getString(ComParamContact.Common.TOKEN_KEY);
        String isFirst = SPUtils.getInstance().getString(ComParamContact.Common.IS_FIRST);
        if (StringUtils.isEmpty(isFirst)) {
            if (dialog == null) {
                dialog = new AgreementDialog(mContext, new AgreementDialog.CallBlack() {
                    @Override
                    public void onDismiss() {
                        gotoMain();
                    }
                });
                dialog.setCanceledOnTouchOutside(false);
            }
            dialog.show();
        } else {
            gotoMain();
        }
    }

    private void gotoMain() {
        new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long l) {
               // Tip.show("dsd");
            }

            @Override
            public void onFinish() {
                if (StringUtils.isEmpty(token)) {
                    ARouter.getInstance().build("/ui/LoginActivity").navigation();
                } else {
                    ARouter.getInstance().build("/ui/MainActivity").navigation();
                }
                finish();
            }
        }.start();
    }

    public void exitApp() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
