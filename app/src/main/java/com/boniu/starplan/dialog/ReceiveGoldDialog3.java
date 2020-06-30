package com.boniu.starplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.Tip;

import rxhttp.wrapper.param.RxHttp;

public class ReceiveGoldDialog3 extends Dialog {

    private String id;
    private int goldNum;
    private TextView tvPrice;
    private ReceiveCallback callback;

    public ReceiveGoldDialog3(@NonNull Context context) {
        super(context, R.style.CustomProgressDialog);
    }

    public ReceiveGoldDialog3(@NonNull Context context, int goldNumber) {
        super(context, R.style.CustomProgressDialog);
        this.goldNum = goldNumber;
    }

    public ReceiveGoldDialog3(@NonNull Context context, int goldNumber, String id, ReceiveCallback callback) {
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
                RxHttp.postEncryptJson(ComParamContact.Main.getTreasureBox).add("id", id).add("type", "0").asResponse(String.class).subscribe(s -> {
                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                    if (result.equals("true")) {
                        Tip.show("领取成功");
                        callback.receive(1,result);
                        dismiss();
                    } else {
                        Tip.show("领取失败");
                    }

                }, (OnError) error -> {
                    error.show();
                });

            }
        });
        //翻倍视频
        findViewById(R.id.tv_submit2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxHttp.postEncryptJson(ComParamContact.Main.getDoubleTreasureBox).add("id", id).add("type", "0").asResponse(String.class).subscribe(s -> {
                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                    callback.receive(2,result);
                    dismiss();
                }, (OnError) error -> {
                    error.show();
                });

            }
        });
        findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public interface ReceiveCallback {
        void receive(int flag, String applyId);
    }
}
