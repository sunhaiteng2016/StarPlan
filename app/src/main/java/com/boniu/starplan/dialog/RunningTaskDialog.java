package com.boniu.starplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;

public class RunningTaskDialog extends Dialog {
    private  int flag;

    public RunningTaskDialog(@NonNull Context context) {
        super(context, R.style.CustomProgressDialog);
    }

    public RunningCallback callback;

    public RunningTaskDialog(@NonNull Context context,int flag, RunningCallback callback) {
        super(context, R.style.CustomProgressDialog);
        this.callback=callback;
        this.flag=flag;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_running_task);
        TextView des = findViewById(R.id.tv_des);
        if (flag==1){
            des.setText("不能同时抢多个任务，是否放弃正在进行的任务？");
        }else{
            des.setText("任务已放弃，是否开始当前任务？");
        }
        findViewById(R.id.tv_dialog_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.running();
                dismiss();
            }
        });
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public interface RunningCallback {
        void running();
    }
}
