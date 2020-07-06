package com.boniu.starplan.ui;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.LoadingDialog;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;

/***
 * 玩游戏
 */
@Route(path = "/ui/GameWebViewActivity")
public class GameWebViewActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.web_view)
    WebView webView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_game_web_view;
    }

    @Override
    public void init() {
        tvBarTitle.setText("刷游戏赚");
        initView();
        getData();
    }

    private void initView() {
        WebSettings settings = webView.getSettings();
        WebViewClient webViewClient = new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.requestLayout();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) webView.getLayoutParams();
                layoutParams.leftMargin = dp2px(12);
                layoutParams.rightMargin = dp2px(12);
                webView.setLayoutParams(layoutParams);
                //replaceLicenseAppName(webview);

            }
        };
        webView.setWebViewClient(webViewClient);
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.loadUrl("https://www.baidu.com/");
    }

    private void getData() {
        LoadingDialog dialog = new LoadingDialog(this);
        dialog.show();
        RxHttp.postEncryptJson(ComParamContact.Main.gameUrl).add("gameChannel","1").add("deviceId","")
                .asResponse(String.class)
                .subscribe(s -> {
                    String resultStr = AESUtil.decrypt(s, AESUtil.KEY);
                     runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();

                       // webView.loadUrl(resultStr);
                        }
                    });
                }, (OnError) error -> {
                    error.show();
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        finish();
    }
    public static int dp2px(float dipValue) {
        float scale = ApplicationUtils.getContext().getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5F);
    }
}