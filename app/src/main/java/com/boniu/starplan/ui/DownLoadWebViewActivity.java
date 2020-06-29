package com.boniu.starplan.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;

import butterknife.BindView;

@Route(path = "/ui/DownLoadWebViewActivity")
public class DownLoadWebViewActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout ivBlack;
    @BindView(R.id.tv_bar_title)
    TextView tvTitle;
    @BindView(R.id.web_view)
    WebView webview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void init() {
        initWebView();
    }

    private void initWebView() {
        tvTitle.setText("下载应用");
        String downLoadUrl = getIntent().getStringExtra("downLoadUrl");
        ivBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        WebSettings settings = webview.getSettings();
        WebViewClient webViewClient = new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                /*Uri uri = request.getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webview.requestLayout();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) webview.getLayoutParams();
                layoutParams.leftMargin = dp2px(12);
                layoutParams.rightMargin = dp2px(12);
                webview.setLayoutParams(layoutParams);
            }
        };
        webview.setWebViewClient(webViewClient);
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webview.loadUrl("https://sj.qq.com/myapp/detail.htm?apkName=com.b412759899.akm");
    }

    public static int dp2px(float dipValue) {
        float scale = ApplicationUtils.getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5F);
    }

}
