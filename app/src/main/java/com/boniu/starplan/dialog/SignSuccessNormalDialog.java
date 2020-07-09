package com.boniu.starplan.dialog;

/**
 * 签到成功
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;
import com.boniu.starplan.ad.ReWardVideoAdUtils;
import com.boniu.starplan.entity.MessageWrap;
import com.boniu.starplan.helper.MainActivityHelper;

import org.greenrobot.eventbus.EventBus;


public class SignSuccessNormalDialog extends Dialog {

    private int flag,inCome;
    private SubMitCallBack subMitCallBack;
    private TextView tvPrice;


    public SignSuccessNormalDialog(@NonNull Context context, int flag,int inCome) {
        super(context, R.style.CustomProgressDialog);
        this.flag = flag;
        this.inCome = inCome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign_in_normal);
        TextView tv2 = findViewById(R.id.tv_2);
        TextView tvSubmit = findViewById(R.id.tv_submit);
        if (flag == 0) {
            flag = 1;
        }
        tv2.setText("已签到" + flag + "天,继续签到可获得大礼包");
        tvSubmit.setText("我知道了");

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                EventBus.getDefault().post(new MessageWrap(1));
            }
        });
        findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MessageWrap(1));
                dismiss();
            }
        });

        tvPrice=findViewById(R .id.tv_price);
        tvPrice.setText(inCome+"");

    }

    public interface SubMitCallBack {
        void onSuccess();
    }

}
