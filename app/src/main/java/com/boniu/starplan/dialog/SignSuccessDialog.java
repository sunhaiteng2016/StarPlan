package com.boniu.starplan.dialog;

/**
 * 签到成功
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;


public class SignSuccessDialog extends Dialog {


    public SignSuccessDialog(@NonNull Context context) {
        super(context, R.style.CustomProgressDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign_in_gift);

    }


}
