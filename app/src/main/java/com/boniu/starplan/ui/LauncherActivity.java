package com.boniu.starplan.ui;


import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.boniu.starplan.R;
import com.boniu.starplan.ad.ReWardVideoAdUtils;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.AgreementDialog;
import com.boniu.starplan.utils.SPUtils;
import com.boniu.starplan.utils.StringUtils;

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
                        ReWardVideoAdUtils.loadSplashAd(LauncherActivity.this,flLayout,token);
                    }
                });
                dialog.setCanceledOnTouchOutside(false);
            }
            dialog.show();
        } else {
            ReWardVideoAdUtils.loadSplashAd(this,flLayout,token);
        }
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
