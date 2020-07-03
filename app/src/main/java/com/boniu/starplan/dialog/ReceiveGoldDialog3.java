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
    private int flag;
    private int goldNum;
    private TextView tvPrice;
    private ReceiveCallback callback;
    private String typeValue;
    private  boolean isFalse;
    private TextView tvSubmit,tvSubmit2;


    public ReceiveGoldDialog3(@NonNull Context context, int goldNumber, String id,int flag,boolean isFalse,ReceiveCallback callback) {
        super(context, R.style.CustomProgressDialog);
        this.goldNum = goldNumber;
        this.id = id;
        this.flag=flag;
        this.isFalse=isFalse;
        this.callback = callback;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_receive_gold);
        tvPrice = findViewById(R.id.tv_price);
        tvSubmit = findViewById(R.id.tv_submit);
        tvSubmit2 = findViewById(R.id.tv_submit2);
        if (isFalse){
            tvSubmit.setVisibility(View.VISIBLE);
            tvSubmit2.setText("翻倍领取");
        }else{
            tvSubmit.setVisibility(View.GONE);
            tvSubmit2.setText("领取奖励");
        }
        tvPrice.setText(goldNum + "");
        if (flag == 2) {
            typeValue="threeDays";
        }else{
            typeValue="sevenDays";
        }
        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    RxHttp.postEncryptJson(ComParamContact.Main.getTreasureBox).add("id", id).add("typeValue",typeValue).add("type", "0").asResponse(String.class).subscribe(s -> {
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
                if (isFalse){
                    RxHttp.postEncryptJson(ComParamContact.Main.getDoubleTreasureBox).add("id", id).add("typeValue",typeValue).add("type", "0").asResponse(String.class).subscribe(s -> {
                        String result = AESUtil.decrypt(s, AESUtil.KEY);
                        callback.receive(2,result);
                        dismiss();
                    }, (OnError) error -> {
                        error.show();
                    });
                }else {
                    RxHttp.postEncryptJson(ComParamContact.Main.getTreasureBox).add("id", id).add("typeValue",typeValue).add("type", "0").asResponse(String.class).subscribe(s -> {
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
