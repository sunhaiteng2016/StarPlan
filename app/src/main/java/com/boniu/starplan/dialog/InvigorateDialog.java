package com.boniu.starplan.dialog;

/**
 * 促活
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;
import com.boniu.starplan.entity.MainTask;
import com.boniu.starplan.entity.TaskMode;
import com.boniu.starplan.entity.WeTaskBean;
import com.boniu.starplan.ui.MainActivity;
import com.boniu.starplan.utils.GlideUtils;


public class InvigorateDialog extends Dialog {


    private TaskMode.RowsBean rowsBean;
    private MainTask.DayTaskBean dayTaskBean;
    private TextView tvDwonLoad;
    private ImageView ivIcon;
    private TextView tvName;
    private  WeTaskBean weTaskBean ;
    private MainTask.NewUserTaskBean newUserTaskBean;
    private DownloadUrlCallback downLoadCallBack;
    private int flag;

    public InvigorateDialog(@NonNull Context context, TaskMode.RowsBean rowsBean, int flag, DownloadUrlCallback downLoadCallBack) {
        super(context, R.style.CustomProgressDialog);
        this.rowsBean = rowsBean;
        this.downLoadCallBack = downLoadCallBack;
        this.flag = flag;
    }

    public InvigorateDialog(MainActivity context, MainTask.NewUserTaskBean newUserTaskBean, int flag, DownloadUrlCallback downLoadCallBack) {
        super(context, R.style.CustomProgressDialog);
        this.newUserTaskBean = newUserTaskBean;
        this.downLoadCallBack = downLoadCallBack;
        this.flag = flag;
    }

    public InvigorateDialog(MainActivity mainActivity, MainTask.DayTaskBean dayTaskBean, int flag, DownloadUrlCallback downloadUrlCallback) {
        super(mainActivity, R.style.CustomProgressDialog);
        this.downLoadCallBack = downloadUrlCallback;
        this.dayTaskBean = dayTaskBean;
        this.flag = flag;
    }

    public InvigorateDialog(MainActivity mainActivity, WeTaskBean weTaskBean, int flag, DownloadUrlCallback downloadUrlCallback) {
        super(mainActivity, R.style.CustomProgressDialog);
        this.weTaskBean = weTaskBean;
        this.downLoadCallBack = downloadUrlCallback;
        this.flag = flag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_invigorate);
        tvDwonLoad = findViewById(R.id.tv_down_load);
        ivIcon = findViewById(R.id.iv_icon);
        tvName = findViewById(R.id.tv_name);
        switch (flag) {
            case 0:
                //新手任务
                tvName.setText(newUserTaskBean.getTaskName());
                GlideUtils.getInstance().LoadContextRoundBitmap(getContext(), newUserTaskBean.getIcon(), ivIcon, 8);
                break;
            case 1:
                //今日必做
                tvName.setText(dayTaskBean.getTaskName());
                GlideUtils.getInstance().LoadContextRoundBitmap(getContext(), dayTaskBean.getIcon(), ivIcon, 8);
                break;
            case 2:
                //大家都在做
                tvName.setText(weTaskBean.getTaskName());
                GlideUtils.getInstance().LoadContextRoundBitmap(getContext(), weTaskBean.getIcon(), ivIcon, 8);
                break;
            case 3:
                //试玩赚
                tvName.setText(rowsBean.getMainTitle());
                GlideUtils.getInstance().LoadContextRoundBitmap(getContext(), rowsBean.getIcon(), ivIcon, 8);
                break;
            default:
                break;
        }
        tvDwonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                downLoadCallBack.onLoad();
            }
        });
    }


    public interface DownloadUrlCallback {
        void onLoad();
    }
}
