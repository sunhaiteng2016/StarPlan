package com.boniu.starplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;


//提现确认支付宝信息
public class Withdrawal2Dialog extends Dialog {

    private TextView tvSubmit;
    private ImageView imgClose;
    private TextView edtZhanghao;
    private TextView edtName;
    private TextView tvPrice;

    private String zhanghao, name, price;
    private WithDrawalVerCodeDialog.WithDrawalInterfaces withdrawalInterfaces;

    public Withdrawal2Dialog(@NonNull Context context, String zhanghao, String name, String price, WithDrawalVerCodeDialog.WithDrawalInterfaces withdrawalInterfaces) {
        super(context, R.style.CustomProgressDialog);
        this.zhanghao = zhanghao;
        this.name = name;
        this.price = price;
        this.withdrawalInterfaces = withdrawalInterfaces;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_withdrawal2);
        initView();
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WithDrawalVerCodeDialog withDrawalVerCodeDialog = new WithDrawalVerCodeDialog(getContext(),zhanghao,name,price,withdrawalInterfaces);
                withDrawalVerCodeDialog.show();
                dismiss();
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initView() {
        tvSubmit = ((TextView) findViewById(R.id.tv_submit));
        imgClose = ((ImageView) findViewById(R.id.img_close));
        edtZhanghao = ((TextView) findViewById(R.id.edt_zhanghao));
        tvPrice = ((TextView) findViewById(R.id.tv_price));
        edtName = ((TextView) findViewById(R.id.edt_name));

        tvPrice.setText( Integer.parseInt(price)/10000+"元");
        edtName.setText(Html.fromHtml("<font color='#999999'>支付宝真实姓名：</font><font color='#333333'>" + name + "</font>"));
        edtZhanghao.setText(Html.fromHtml("<font color='#999999'>支付宝账号：</font><font color='#333333'>" + zhanghao + "</font>"));
    }

    public interface WithdrawalInterfaces {
        void startWithdrawal(String name, String zhanghao);

    }
}
