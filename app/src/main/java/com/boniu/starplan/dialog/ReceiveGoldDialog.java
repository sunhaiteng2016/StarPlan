package com.boniu.starplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.entity.BoxState;
import com.boniu.starplan.entity.MessageWrap;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.ui.MainActivity;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.Tip;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import rxhttp.wrapper.param.RxHttp;

public class ReceiveGoldDialog extends Dialog {

    private String id;
    private int goldNum;
    private boolean isDouble;
    private TextView tvPrice;
    private ReceiveCallback callback;



    public ReceiveGoldDialog(@NonNull Context context, int goldNumber, String id,boolean isDouble, ReceiveCallback callback) {
        super(context, R.style.CustomProgressDialog);
        this.goldNum = goldNumber;
        this.id = id;
        this.isDouble=isDouble;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_receive_gold);
        tvPrice = findViewById(R.id.tv_price);
        tvPrice.setText(goldNum + "");
        EventBus.getDefault().post(new MessageWrap(1));
        TextView tvSubMit = findViewById(R.id.tv_submit);
        TextView tvSubMit2 = findViewById(R.id.tv_submit2);
        if (isDouble){
            tvSubMit.setVisibility(View.VISIBLE);
            tvSubMit2.setText("领取×2倍的奖励");
        }else {
            tvSubMit2.setText("领取奖励");
            tvSubMit.setVisibility(View.GONE);
        }
        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    RxHttp.postEncryptJson(ComParamContact.Main.getTreasureBox).add("id", id).add("type", "1").add("typeValue", "dayTask").asResponse(String.class).subscribe(s -> {
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
                if (isDouble) {
                    RxHttp.postEncryptJson(ComParamContact.Main.getDoubleTreasureBox).add("id", id).add("type", "1").add("typeValue", "dayTask").asResponse(String.class).subscribe(s -> {
                        String result = AESUtil.decrypt(s, AESUtil.KEY);
                        callback.receive(2,result);
                        dismiss();
                    }, (OnError) error -> {
                        error.show();
                    });
                }else{
                    RxHttp.postEncryptJson(ComParamContact.Main.getTreasureBox).add("id", id).add("type", "1").add("typeValue", "dayTask").asResponse(String.class).subscribe(s -> {
                        String result = AESUtil.decrypt(s, AESUtil.KEY);
                        callback.receive(2,result);
                        dismiss();
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
        void receive(int flag,String applyId);
    }
}
