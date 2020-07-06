package com.boniu.starplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;
import com.boniu.starplan.entity.NoticeBean;


/**
 * 通用失败弹窗
 */
public class GeneralFailDialog extends Dialog {
    private NoticeBean noticeBean;
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvSubmit;
    private ImageView imgClose;
    private DialogReturnInterfaces dialogReturnInterfaces;

    public GeneralFailDialog(@NonNull Context context, NoticeBean noticeBean, DialogReturnInterfaces dialogReturnInterfaces) {
        super(context, R.style.CustomProgressDialog);
        this.noticeBean = noticeBean;
        this.dialogReturnInterfaces = dialogReturnInterfaces;
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
                dialogReturnInterfaces.dismiss();
                dismiss();
            }
        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogReturnInterfaces.clickType(noticeBean.getClickType()+"");
                dismiss();
            }
        });

    }

    private void initView() {
        tvTitle = ((TextView) findViewById(R.id.tv_title));
        tvContent = ((TextView) findViewById(R.id.tv_content));
        tvSubmit = ((TextView) findViewById(R.id.tv_submit));
        imgClose = ((ImageView) findViewById(R.id.img_close));
        tvTitle.setText(noticeBean.getTitle()+"");
        tvContent.setText(noticeBean.getContent()+"");
        tvSubmit.setText(noticeBean.getClickText()+"");
        imgClose.setVisibility(noticeBean.isCanClose()?View.GONE:View.VISIBLE);

    }
}
