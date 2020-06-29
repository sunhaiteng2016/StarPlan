package com.boniu.starplan.dialog;

/**
 * 签到成功
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;


public class SignSuccessNormalDialog extends Dialog {

    private int flag;
    private SubMitCallBack subMitCallBack;

    public SignSuccessNormalDialog(@NonNull Context context) {
        super(context, R.style.CustomProgressDialog);
    }

    public SignSuccessNormalDialog(@NonNull Context context, int flag, SubMitCallBack subMitCallBack) {
        super(context, R.style.CustomProgressDialog);
        this.flag = flag;
        this.subMitCallBack = subMitCallBack;
    }

    public SignSuccessNormalDialog(@NonNull Context context, int flag) {
        super(context, R.style.CustomProgressDialog);
        this.flag = flag;
        this.subMitCallBack = subMitCallBack;
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
        tv2.setText("已签到" + flag + "天，召唤大礼包");
        tvSubmit.setText("看视频领取更多");
        /*RelativeLayout rl = findViewById(R.id.rl);
        Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        if (rotate != null) {
            rl.startAnimation(rotate);
        } else {
            rl.setAnimation(rotate);
            rl.startAnimation(rotate);
        }*/
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //领取签到奖励
                if (subMitCallBack != null) {
                    subMitCallBack.onSuccess();
                } else {

                }

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