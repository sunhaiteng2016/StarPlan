package com.boniu.starplan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.boniu.starplan.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hyh on 2020/4/15.
 */

public class CancellationDialog extends Dialog {

    @BindView(R.id.tv_close)
    TextView tvClose;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.img_close)
    ImageView imgClose;
    private CancellationInterfaces cancellationInterfaces;

    public CancellationDialog(@NonNull Context context, CancellationInterfaces cancellationInterfaces) {
        super(context, R.style.CustomProgressDialog);
        this.cancellationInterfaces = cancellationInterfaces;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_cancellation);
        ButterKnife.bind(this);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancellationInterfaces.cancelUser();
                dismiss();
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    public interface CancellationInterfaces {
        void cancelUser();
    }
}
