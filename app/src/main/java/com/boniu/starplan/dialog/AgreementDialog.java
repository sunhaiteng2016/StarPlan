package com.boniu.starplan.dialog;

/**
 * Created by hyh on 2020/4/9.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.ui.LauncherActivity;
import com.boniu.starplan.utils.SPUtils;
import com.boniu.starplan.utils.StringUtils;

/**
 * 用户协议和隐私政策
 */

public class AgreementDialog extends Dialog {

    private TextView tvXieyi;
    private CallBlack mCallBlack;

    public AgreementDialog(@NonNull Context context) {
        super(context, R.style.CustomProgressDialog);
    }

    public AgreementDialog(@NonNull Context context, CallBlack callBlack) {
        super(context, R.style.CustomProgressDialog);
        this.mCallBlack = callBlack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_agree_ment);
        tvXieyi = ((TextView) findViewById(R.id.tv_xieyi));
        findViewById(R.id.tv_agree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.getInstance().put(ComParamContact.Common.IS_FIRST, "isCheck");
                dismiss();
                mCallBlack.onDismiss();
            }
        });
        findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scanForActivity(getContext()) != null) {
                    dismiss();
                    scanForActivity(getContext()).exitApp();
                } else {
                    dismiss();
                }
            }
        });
        initXieyi();
    }

    private static LauncherActivity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof LauncherActivity)
            return (LauncherActivity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());
        return null;
    }


    private void initXieyi() {

        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append("亲爱的用户：\n" +
                "     您好，在您使用本应用前，请您认真阅读并了解《用户协议》和《隐私政策》。点击“同意”即表示已阅读并同意全部条款。");

        /**
         * 颜色和点击事件
         */
        //设置字体颜色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FFAF4B"));
        spannableString.setSpan(colorSpan, 33, 39, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);//去掉下划线
            }

            @Override
            public void onClick(View view) {
                //用户协议
                ARouter.getInstance().build("/home/WebActivity")
                        .withString("WEB_TYPE", "1").navigation();
            }
        };
        spannableString.setSpan(clickableSpan, 33, 39, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        /**
         * 颜色和点击事件
         */
        //设置字体颜色
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#FFAF4B"));
        spannableString.setSpan(colorSpan1, 40, 46, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //点击事件
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);//去掉下划线
            }

            @Override
            public void onClick(View view) {
                //隐私协议
               ARouter.getInstance().build("/home/WebActivity")
                        .withString("WEB_TYPE", "2").navigation();
            }
        };
        spannableString.setSpan(clickableSpan1, 40, 46, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        tvXieyi.setText(spannableString);
    }


    public interface CallBlack {
        void onDismiss();
    }
}
