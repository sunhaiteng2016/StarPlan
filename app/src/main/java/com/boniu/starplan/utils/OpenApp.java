package com.boniu.starplan.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;
import java.util.List;

public class OpenApp {

    public static void OpenApp(Activity context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.putExtra("type", true);
        context.startActivityForResult(intent, 123);
    }

    //检测是否安装了应用宝
    public static boolean isMobile_spExist(Context context) {
        PackageManager manager = context.getPackageManager();
        List<PackageInfo> pkgList = manager.getInstalledPackages(0);
        for (int i = 0; i < pkgList.size(); i++) {
            PackageInfo pI = pkgList.get(i);
            if (pI.packageName.equalsIgnoreCase("com.tencent.android.qqdownloader"))
                return true;
        }
        return false;
    }

    public static void channelUrl(Context context, String packageName) {
        //跳到应用宝下载安装getPackageName()
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("market://details?id=" + packageName);
        try {
            intent.setClassName("com.tencent.android.qqdownloader", "com.tencent.pangu.link.LinkProxyActivity");
            intent.setData(content_url);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //检测Android版本--是否具有安装文件的权限
    public static void checkInstallApkPermission(Context context, String filePath) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (context.getPackageManager().canRequestPackageInstalls()) {
                installApk(context, new File(filePath));
            } else {
                installApk(context, new File(filePath));
            }
        } else {
            installApk(context, new File(filePath));
        }
    }

    private static void installApk(Context mContext, File apk) {
        Uri uri = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(apk);
        } else {
            uri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", apk);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        mContext.startActivity(intent);
    }

    /**
     * 打开外部浏览器
     */
    public static void openWeb(Context mContext, String url) {
        //8.0 以上
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        } else {
            intent.setClassName("org.chromium.webview_shell", "org.chromium.webview_shell.WebViewBrowserActivity");
        }
        mContext.startActivity(intent);
    }

    /**
     * 判断应用是否已安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        boolean hasInstalled = false;
        PackageManager pm = context.getPackageManager();
        @SuppressLint("WrongConstant") List<PackageInfo> list = pm
                .getInstalledPackages(PackageManager.PERMISSION_GRANTED);
        for (PackageInfo p : list) {
            if (packageName != null && packageName.equals(p.packageName)) {
                hasInstalled = true;
                break;
            }
        }
        return hasInstalled;
    }
}
