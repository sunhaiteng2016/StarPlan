package com.boniu.starplan.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.Tip;

import rxhttp.wrapper.param.RxHttp;

public class ReceiveGoldDialog5 extends Dialog {

    private String id;
    private int goldNum;
    private TextView tvPrice;

    public ReceiveGoldDialog5(@NonNull Context context) {
        super(context, R.style.CustomProgressDialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_submit_review);
        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) getContext();
                activity.finish();
                ARouter.getInstance().build("/ui/TryToEarnActivity").navigation();
                dismiss();
            }
        });
    }
}
