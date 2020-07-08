package com.boniu.starplan.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.ui.ApplicationUtils;
import com.boniu.starplan.ui.FinishRegisterActivity;
import com.boniu.starplan.ui.ReceiveGoldDetailsActivity;

public class ReceiveGoldDialog5 extends Dialog {


    private final int flag;

    public ReceiveGoldDialog5(@NonNull Context context, int flag) {
        super(context, R.style.CustomProgressDialog);
        this.flag=flag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_submit_review);
        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag==1){
                    ARouter.getInstance().build("/ui/MainActivity").navigation();
                }else{
                    ApplicationUtils.newInstance().popActivity(FinishRegisterActivity.class);
                    ApplicationUtils.newInstance().popActivity(ReceiveGoldDetailsActivity.class);
                    ARouter.getInstance().build("/ui/ReceiveGoldCoinActivity").navigation();
                }
                dismiss();
            }
        });
    }
}
