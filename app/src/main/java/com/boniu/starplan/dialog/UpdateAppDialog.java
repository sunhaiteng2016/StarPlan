package com.boniu.starplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

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
            findViewById(R.id.tv_update).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("url"));
                    getContext().startActivity(browserIntent);
                }
            });
    }

}
