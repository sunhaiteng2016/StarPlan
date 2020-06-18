package com.boniu.starplan.dialog;

/**
 * 新用户首次登录
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;


public class NewPersonDialog extends Dialog {


    public NewPersonDialog(@NonNull Context context) {
        super(context, R.style.CustomProgressDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_person);
    }


}
