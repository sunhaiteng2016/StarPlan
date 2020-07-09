package com.boniu.starplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;
import com.boniu.starplan.entity.MessageWrap;
import com.boniu.starplan.entity.NoticeBean;

import org.greenrobot.eventbus.EventBus;


/**
 * 通用失败弹窗
 */
public class GeneralFailDialog2 extends Dialog {
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvSubmit;
    private ImageView imgClose;

    public GeneralFailDialog2(@NonNull Context context) {
        super(context, R.style.CustomProgressDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_task_fail);
        initView();
        initClick();
    }

    private void initClick() {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageWrap(1));
                dismiss();
            }
        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageWrap(1));
                dismiss();
            }
        });

    }

    private void initView() {
        tvTitle = ((TextView) findViewById(R.id.tv_title));
        tvContent = ((TextView) findViewById(R.id.tv_content));
        tvSubmit = ((TextView) findViewById(R.id.tv_submit));
        imgClose = ((ImageView) findViewById(R.id.img_close));

    }
}
