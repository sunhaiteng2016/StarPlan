package com.boniu.starplan.dialog;

/**
 * 新用户首次登录
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;


public class NewPersonDialog extends Dialog {


    private int income;
    private TextView tvPrice;


    public NewPersonDialog(@NonNull Context context, int inCome) {
        super(context, R.style.CustomProgressDialog);
        this.income = inCome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_person);
        tvPrice = findViewById(R.id.tv_price);
        tvPrice.setText(income+"");
        findViewById(R.id.tv_look).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/ui/MyWalletActivity").navigation();
                dismiss();
            }
        });
        findViewById(R.id.img_qx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

}
