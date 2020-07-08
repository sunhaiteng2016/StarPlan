package com.boniu.starplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;
import com.boniu.starplan.utils.StringUtils;


//提现绑定支付宝信息
public class WithdrawalDialog extends Dialog {

    private TextView tvSubmit;
    private ImageView imgClose;
    private EditText edtZhanghao;
    private EditText edtName;
    private String price;
    private WithDrawalVerCodeDialog.WithDrawalInterfaces withdrawalInterfaces;
    public WithdrawalDialog(@NonNull Context context, String price, WithDrawalVerCodeDialog.WithDrawalInterfaces withdrawalInterfaces) {
        super(context, R.style.CustomProgressDialog);
        this.price = price;
        this.withdrawalInterfaces = withdrawalInterfaces;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_withdrawal);
        initView();
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(edtZhanghao.getText().toString())){
                    Toast.makeText(getContext(), "还未输入支付宝账号", Toast.LENGTH_SHORT).show();
                }else if (StringUtils.isEmpty(edtName.getText().toString())){
                    Toast.makeText(getContext(), "还未输入真实姓名", Toast.LENGTH_SHORT).show();
                }else{
                    //绑定成功
                    Withdrawal2Dialog withdrawal2Dialog = new Withdrawal2Dialog(getContext(),edtZhanghao.getText().toString().trim(),edtName.getText().toString().trim(),price,withdrawalInterfaces);
                    withdrawal2Dialog.show();
                    dismiss();
                }
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
        edtZhanghao = ((EditText) findViewById(R.id.edt_zhanghao));
        edtName = ((EditText) findViewById(R.id.edt_name));

    }
}
