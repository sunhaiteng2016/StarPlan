package com.boniu.starplan.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Path;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.LoadingDialog;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.oaid.UuidCreator;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.DevicesUtil;
import com.boniu.starplan.utils.OpenApp;
import com.boniu.starplan.utils.SystemInfoUtils;
import com.rxjava.rxlife.RxLife;

import java.io.File;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import okhttp3.OkHttpClient;
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


    private WebSettings mWebSettings;


    @Override
    public int getLayoutId() {
        return R.layout.activity_game_web_view;
    }

    @Override
    public void init() {
        tvBarTitle.setText("玩游戏赚");
        initView();
        getData();
    }

    @SuppressLint("JavascriptInterface")
    private void initView() {
        WebSettings settings = webView.getSettings();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {

            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        }

        webView.getSettings().setBlockNetworkImage(false);


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
        webView.addJavascriptInterface(new WebMethod(), "Android");
    }

    private File mPath;//文件保存路径

    private void getData() {
        LoadingDialog dialog = new LoadingDialog(this);
        dialog.show();
        RxHttp.postEncryptJson(ComParamContact.Main.gameUrl)
                .add("gameChannel", "1").add("deviceId", UuidCreator.getInstance(this).getDeviceId())
                .add("deviceSource", 2).add("oaid", UuidCreator.getInstance(this).getDeviceId()).add("osVersion", SystemInfoUtils.getOSVersionName()).add("phoneModel", SystemInfoUtils.getBrandName())
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(s -> {
                    String resultStr = AESUtil.decrypt(s, AESUtil.KEY);
                    String s1 = resultStr.replace("\"", "");
                    String s2 = s1.replace("\"", "");
                    webView.loadUrl(s2);
                    dialog.dismiss();
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
        if (webView.canGoBack())
            webView.goBack();
        else finish();
    }

    public static int dp2px(float dipValue) {
        float scale = ApplicationUtils.getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5F);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }

    //h5调用方法
    private class WebMethod {
        public WebMethod() {

        }

        /**
         * @param packageName 应用包名
         */
        @JavascriptInterface
        public void CheckAppIsInstall(final String packageName) {
            Log.e(TAG, "CheckAppIsInstall: " + packageName);
            boolean flag = OpenApp.isInstalled(mContext, packageName);//true 已安装  false 未安装
            if (flag) {
                //通知js应用已安装
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl("javascript:CheckAppCallback('" + packageName + "')");
                    }
                });
            } else {
                //通知js应用未安装
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl("javascript:CheckAppNoInstall('" + packageName + "')");
                    }
                });
            }
        }

        /**
         * @param packageName 指定打开应用的包名
         */
        @JavascriptInterface
        public void startAnotherApp(String packageName) {
            //自行完善根据包名打开app的方法
            Log.e(TAG, "startAnotherApp: " + packageName);
            OpenApp.OpenApp(GameWebViewActivity.this, packageName);
        }


        /**
         * @param whichTask apk标识
         * @param isInstall 下载完成后是否主动唤起安装
         * @param url       下载地址
         */
        @JavascriptInterface
        public void downloadApkFile(int whichTask, int isInstall, String url) {
            Log.e(TAG, "downloadApkFile: " + whichTask + ":" + isInstall + ":" + url);
            //自行封装下载apk的方法，下载回调中，通知js下载状态即可

            File externalDownloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File applicationFileDir = new File(externalDownloadsDir, "asd");
            if (!applicationFileDir.exists()) {
                applicationFileDir.mkdirs();
            }
            mPath = new File(applicationFileDir, "other.apk");
            downloadApk(url, whichTask, isInstall);
        }

        /**
         * @param whichTask apk标识
         * @param path      apk本地路径
         */
        @JavascriptInterface
        public void InstallApk(final int whichTask, String path) {
            Log.e(TAG, "InstallApk: " + whichTask + ":" + path);
            if (TextUtils.isEmpty(path)) {
                if (webView != null) {
                    webView.post(new Runnable() {
                        @Override
                        public void run() {
                            //通知js
                            webView.loadUrl("javascript:InstallApkListener(" + whichTask + "," + 0 + ",'" + "安装路径为空" + "')");
                        }
                    });
                }
                return;
            }
            File localFile = new File(path);
            if (!localFile.exists()) {
                if (webView != null) {
                    webView.post(new Runnable() {
                        @Override
                        public void run() {
                            //通知js
                            webView.loadUrl("javascript:InstallApkListener(" + whichTask + "," + 0 + ",'" + "安装路径不存在" + "')");
                        }
                    });
                }
                return;
            }
            //开始安装 自行实现
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
                if (haveInstallPermission) {
                    OpenApp.installApk(mContext, new File(path));
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("安装应用需要打开安装未知来源应用权限，请去设置中开启权限");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Uri packageUri = Uri.parse("package:" + GameWebViewActivity.this.getApplicationContext().getPackageName());
                            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUri);
                            startActivityForResult(intent, 10086);
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            } else {
                OpenApp.installApk(mContext, new File(path));
            }
            if (webView != null) {
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        //通知js
                        webView.loadUrl("javascript:InstallApkListener(" + whichTask + "," + 1 + ",'" + "唤起安装成功" + "')");
                    }
                });
            }
        }


        /**
         * 根据指定的包名卸载apk
         */
        @JavascriptInterface
        public void uninstallApk(String packName) {
            Log.e(TAG, "uninstallApk: " + packName);
            //卸载代码自行完善
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + packName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        /**
         * 外部浏览器打开指定链接url
         */
        @JavascriptInterface
        public void Browser(String url) {
            Log.e(TAG, "Browser: " + url);
            //跳转外部浏览器
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

        /**
         * 刷新当前web页面
         */
        @JavascriptInterface
        public void RefreshWeb() {
            Log.e(TAG, "RefreshWeb: ");
            if (null == mHandler || null == webView)
                return;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    String subUrl = webView.getUrl();
                    if (!TextUtils.isEmpty(subUrl)) {
                        webView.loadUrl(subUrl);
                    }
                }
            });
        }


    }

    private Handler mHandler = new Handler();

    //下载apk
    private void downloadApk(String url, final int whichTask, final int isInstall) {
        Log.e(TAG, "downloadApk: " + url);
        RxHttp.get(url)
                //.setRangeHeader(length, -1, true)  //设置开始下载位置，结束位置默认为文件末尾
                .asDownload(mPath.getPath(), progress -> {
                    Log.e("sht", "progress->" + progress);
                    //如果需要衔接上次的下载进度，则需要传入上次已下载的字节数length

                    //下载进度回调,0-100，仅在进度有更新时才会回调
                    Message message = new Message();
                    message.what = 2;
                    message.obj = "javascript:downloadApkFileProcessListener("
                            + whichTask + "," + progress.getProgress() + ")";
                    myHanlder.sendMessage(message);
                }, AndroidSchedulers.mainThread())

                .to(RxLife.as(this)) //加入感知生命周期的观察者
                .subscribe(
                        s -> {
                            Message message = new Message();
                            message.what = 4;
                            message.obj = "javascript:downloadApkFileFinishListener("
                                    + whichTask + ",'" + mPath.getPath() + "')";
                            myHanlder.sendMessage(message);
                            if (1 == isInstall) {
                                myHanlder.sendEmptyMessage(0);
                            }


                        }, error -> {
                            Message message = new Message();
                            message.what = 3;
                            message.obj = "javascript:downloadApkFileErrorListener("
                                    + whichTask + ",'" + error.toString() + "')";
                            myHanlder.sendMessage(message);
                        }
                );

    }


    private Handler myHanlder = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
                    if (haveInstallPermission) {
                        OpenApp.installApk(mContext, new File(mPath.getPath()));
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("安装应用需要打开安装未知来源应用权限，请去设置中开启权限");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri packageUri = Uri.parse("package:" + GameWebViewActivity.this.getApplicationContext().getPackageName());
                                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUri);
                                startActivityForResult(intent, 10087);
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                } else {
                    OpenApp.installApk(mContext, new File(mPath.getPath()));
                }
            } else if (msg.what == 2 || msg.what == 3 || msg.what == 4) {
                String objs = (String) msg.obj;
                webView.loadUrl(objs);
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}