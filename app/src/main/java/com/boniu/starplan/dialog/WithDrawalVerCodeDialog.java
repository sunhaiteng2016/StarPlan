package com.boniu.starplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.boniu.starplan.R;
import com.boniu.starplan.constant.ComParamContact;

import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.SPUtils;
import com.boniu.starplan.utils.TimerUtils;
import com.boniu.starplan.utils.Tip;

import rxhttp.wrapper.param.RxHttp;

//提现验证码
public class WithDrawalVerCodeDialog extends Dialog {

    public static final String TAG = "WithDrawalVerCodeDialog";
    TextView tvPhone;
    EditText edCode;
    TextView tvGetCode;

    TextView tvClose;
    TextView tvRight;
    private String phone;

    private String zhanghao, name, price;
    private WithDrawalInterfaces withDrawalInterfaces;

    public WithDrawalVerCodeDialog(@NonNull Context context, String zhanghao, String name, String price, WithDrawalInterfaces withdrawalInterfaces) {
        super(context, R.style.CustomProgressDialog);

        this.zhanghao = zhanghao;
        this.name = name;
        this.price = price;
        this.withDrawalInterfaces = withdrawalInterfaces;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_withdrawal_vercode);
        initView();
        phone = SPUtils.getInstance().getString(ComParamContact.Login.MOBILE);
        tvPhone.setText("验证码已经发送到您的手机 " + getMobile(phone));

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edCode.getText().toString().trim())) {
                    Toast.makeText(getContext(), "还未输入验证码", Toast.LENGTH_SHORT).show();
                } else {
                    withDrawalInterfaces.withDrawalRight(edCode.getText().toString().trim(), name + "", zhanghao + "");
                    dismiss();
                }
            }
        });

        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimerUtils.startTimer(getContext(), tvGetCode);
                getCode();
            }
        });

    }

    private void getCode() {
        RxHttp.postEncryptJson(ComParamContact.Main.sendMsg)
                .add("mobile", phone + "")
                .asResponse(String.class)
                .subscribe(s -> {
                    String resultStr = AESUtil.decrypt((String) s, AESUtil.KEY);
                    Tip.show("发送成功！");
                    //设置签到数据
                }, (OnError) error -> {
                    error.show();
                });
    }

    private void initView() {
        tvPhone = findViewById(R.id.tv_phone);
        edCode = findViewById(R.id.ed_code);

        tvGetCode = findViewById(R.id.tv_get_code);
        tvClose = findViewById(R.id.tv_close);
        tvRight = findViewById(R.id.tv_right);
    }

    private String getMobile(String mobile) {
        String phone = mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
        return phone;
    }

    public interface WithDrawalInterfaces {
        void withDrawalRight(String code, String name, String zhanghao);
    }
}
