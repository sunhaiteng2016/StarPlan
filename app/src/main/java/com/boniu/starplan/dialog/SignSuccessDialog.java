package com.boniu.starplan.dialog;

/**
 * 签到成功
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;


public class SignSuccessDialog extends Dialog {

    private int flag;
    private SubMitCallBack subMitCallBack;

    public SignSuccessDialog(@NonNull Context context) {
        super(context, R.style.CustomProgressDialog);
    }

    public SignSuccessDialog(@NonNull Context context, int flag, SubMitCallBack subMitCallBack) {
        super(context, R.style.CustomProgressDialog);
        this.flag = flag;
        this.subMitCallBack = subMitCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign_in_gift);
        TextView tv2 = findViewById(R.id.tv_2);
        tv2.setText("已签到" + flag + "天，召唤大礼包");
        /*RelativeLayout rl = findViewById(R.id.rl);
        Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        if (rotate != null) {
            rl.startAnimation(rotate);
        } else {
            rl.setAnimation(rotate);
            rl.startAnimation(rotate);
        }*/
        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //领取签到奖励
                subMitCallBack.onSuccess();
            }
        });
        findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    public interface SubMitCallBack {
        void onSuccess();
    }

}
