package com.boniu.starplan.dialog;

/**
 * 提现成功
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;


public class WithdrawalSuccessDialog extends Dialog {


    public WithdrawalSuccessDialog(@NonNull Context context) {
        super(context, R.style.CustomProgressDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_withdrawal_success1);

    }


}
