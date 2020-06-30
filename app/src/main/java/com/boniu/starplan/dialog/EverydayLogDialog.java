package com.boniu.starplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.utils.SPUtils;

import java.util.Calendar;

/**
 * 每天登录弹窗
 */
public class EverydayLogDialog extends Dialog {

    public EverydayLogDialog(@NonNull Context context) {
        super(context, R.style.CustomProgressDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_every_day_login);

        findViewById(R.id.tv_go_to_look).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                ARouter.getInstance().build("/ui/MyWalletActivity").navigation();
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
