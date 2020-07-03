package com.boniu.starplan.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;

public class ReceiveGoldDialog5 extends Dialog {


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
                ARouter.getInstance().build("/ui/TryToEarnActivity").navigation();
                dismiss();
            }
        });
    }
}
