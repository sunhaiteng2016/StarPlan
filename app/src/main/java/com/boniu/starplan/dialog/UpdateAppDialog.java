package com.boniu.starplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;

/**
 * 更新弹窗
 */
public class UpdateAppDialog extends Dialog {

    public UpdateAppDialog(@NonNull Context context) {
        super(context, R.style.CustomProgressDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update_app);
    }

}
