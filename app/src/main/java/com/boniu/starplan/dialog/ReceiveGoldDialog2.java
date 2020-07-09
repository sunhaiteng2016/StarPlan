package com.boniu.starplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.entity.MessageWrap;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.Tip;

import org.greenrobot.eventbus.EventBus;

import rxhttp.wrapper.param.RxHttp;

public class ReceiveGoldDialog2 extends Dialog {


    private  int flag;
    private int goldNum;
    private TextView tvPrice;


    public ReceiveGoldDialog2(@NonNull Context context) {
        super(context, R.style.CustomProgressDialog);
    }

    public ReceiveGoldDialog2(@NonNull Context context, int goldNumber) {
        super(context, R.style.CustomProgressDialog);
        this.goldNum = goldNumber;
        this.flag=flag;

    }
    public ReceiveGoldDialog2(@NonNull Context context, int goldNumber,int flag) {
        super(context, R.style.CustomProgressDialog);
        this.goldNum = goldNumber;
        this.flag=flag;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_receive_gold2);
        tvPrice = findViewById(R.id.tv_price);
        tvPrice.setText(goldNum + "");

        findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MessageWrap(1));
                dismiss();
            }
        });
        findViewById(R.id.tv_submit2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EventBus.getDefault().post(new MessageWrap(1));
                dismiss();
            }
        });
    }


}
