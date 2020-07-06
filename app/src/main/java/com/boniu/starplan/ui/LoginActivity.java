package com.boniu.starplan.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.dialog.LoadingDialog;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.base.Response;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.entity.LoginInfo;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.SPUtils;
import com.boniu.starplan.utils.StringUtils;
import com.boniu.starplan.utils.TimerUtils;
import com.boniu.starplan.utils.Tip;
import com.boniu.starplan.utils.Validator;
import com.boniu.starplan.utils.statusbar.StatusBarUtil;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;

@Route(path = "/ui/LoginActivity")
public class LoginActivity extends BaseActivity {
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.ed_code)
    EditText edCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    private String phone, code;
    private LoadingDialog loadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        loadingDialog = new LoadingDialog(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_get_code, R.id.tv_login})
    public void onClick(View view) {
        getInput();
        //获取验证码
        if (StringUtils.isNull(phone)) {
            Tip.show("请输入手机号！");
            return;
        }
        if (!Validator.isMobile(phone)) {
            Tip.show("请输入正确的手机号！");
            return;
        }
        switch (view.getId()) {
            case R.id.tv_get_code:
                TimerUtils.startTimer(this, tvGetCode);
                RxHttp.postEncryptJson(ComParamContact.Login.GET_CODE)
                        .add(ComParamContact.Login.MOBILE, phone)
                        .asString().subscribe(s -> {
                    Response codeBean = new Gson().fromJson(s, Response.class);
                    if (codeBean.getSuccess()) {
                        Tip.show("短信发送成功！");
                    } else {
                        Tip.show(codeBean.getErrorMsg());
                    }
                }, (OnError) error -> {
                    error.show();
                });
                break;
            case R.id.tv_login:
                if (StringUtils.isNull(code)) {
                    Tip.show("请输入验证码！");
                    return;
                }
                loadingDialog.show();
                RxHttp.postEncryptJson(ComParamContact.Login.LOGIN)
                        .add(ComParamContact.Login.MOBILE, phone)
                        .add(ComParamContact.Login.CODE, code)
                        .asResponse(String .class)
                        .subscribe(s -> {
                            loadingDialog.dismiss();
                                String result = AESUtil.decrypt(s, AESUtil.KEY);
                                LoginInfo loginInfo = new Gson().fromJson(result, LoginInfo.class);
                                SPUtils.getInstance().put(ComParamContact.Login.MOBILE, loginInfo.getMobile());
                                SPUtils.getInstance().put(ComParamContact.Common.TOKEN_KEY, loginInfo.getUtoken());
                                Tip.show("登录成功！");
                                ARouter.getInstance().build("/ui/MainActivity").navigation();
                                finish();
                        }, (OnError) error -> {
                            error.show();
                            loadingDialog.dismiss();
                        });
                break;
        }
    }

    private void getInput() {
        phone = edPhone.getText().toString().trim();
        code = edCode.getText().toString().trim();
    }
}
