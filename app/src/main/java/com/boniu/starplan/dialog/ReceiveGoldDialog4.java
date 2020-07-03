package com.boniu.starplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.entity.MessageWrap;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.Tip;

import org.greenrobot.eventbus.EventBus;

import rxhttp.wrapper.param.RxHttp;

public class ReceiveGoldDialog4 extends Dialog {

    private String id;
    private int goldNum;
    private TextView tvPrice;
    private ReceiveCallback callback;



    public ReceiveGoldDialog4(@NonNull Context context, int goldNumber, String id, ReceiveCallback callback) {
        super(context, R.style.CustomProgressDialog);
        this.goldNum = goldNumber;
        this.id = id;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_receive_gold);
        tvPrice = findViewById(R.id.tv_price);
        tvPrice.setText(goldNum + "");
        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //翻倍视频
        findViewById(R.id.tv_submit2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog loadingDialog= new LoadingDialog(getContext());
                loadingDialog.show();
                RxHttp.postEncryptJson(ComParamContact.Main.addPrepositionDouble).add("applyId", id).asResponse(String.class).subscribe(s -> {
                    loadingDialog.dismiss();
                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                    if (result.equals("true")) {
                        Tip.show("领取成功");
                        callback.receive(1, result);
                        dismiss();
                    } else {
                        Tip.show("领取失败");
                    }
                }, (OnError) error -> {
                    error.show();
                });

            }
        });
        findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MessageWrap(1));
                dismiss();
            }
        });
    }

    public interface ReceiveCallback {
        void receive(int flag, String applyId);
    }
}
