package com.boniu.starplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;
import com.boniu.starplan.entity.VersionModel;
import com.boniu.starplan.utils.AppMarketUtils;

/**
 * 更新弹窗
 */
public class UpdateAppDialog extends Dialog {

    private final VersionModel versionModel;
    private TextView tvContent;

    public UpdateAppDialog(@NonNull Context context, VersionModel versionModel) {
        super(context, R.style.CustomProgressDialog);
        this.versionModel = versionModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update_app);
        tvContent = findViewById(R.id.tv_update);
        tvContent = findViewById(R.id.tv_content);
        tvContent = findViewById(R.id.tv_content);
        tvContent.setText(versionModel.getResult().getVersionInfoVo().getContent());
        findViewById(R.id.tv_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AppMarketUtils.gotoMarket(getContext());
                } catch (Exception e) {
                }
            }
        });
    }

}
