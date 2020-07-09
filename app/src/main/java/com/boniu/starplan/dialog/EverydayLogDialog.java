package com.boniu.starplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.entity.MessageWrap;
import com.boniu.starplan.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

/**
 * 每天登录弹窗
 */
public class EverydayLogDialog extends Dialog {

    private int inCome;
    private TextView tvGold;

    public EverydayLogDialog(@NonNull Context context, int inCome) {
        super(context, R.style.CustomProgressDialog);
        this.inCome = inCome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_every_day_login);
        tvGold = findViewById(R.id.tv_gold);
        tvGold.setText(inCome + "金币");
        findViewById(R.id.tv_go_to_look).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                EventBus.getDefault().post(new MessageWrap(2));
            }
        });
        findViewById(R.id.tv_no_remind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Calendar cd = Calendar.getInstance();
                int month = cd.get(Calendar.MONTH) + 1;
                SPUtils.getInstance().put("month", month);
                SPUtils.getInstance().put("isEvery", false);
            }
        });

    }
}
