package com.boniu.starplan.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;

import com.boniu.starplan.dialog.DownloadProgressDialog;
import com.boniu.starplan.http.OnError;
import com.rxjava.rxlife.RxLife;

import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;

import static com.boniu.starplan.dialog.DownloadProgressDialog.STYLE_HORIZONTAL;

public class DownloadAppUtils {
    public File destPath;

    public static DownloadAppUtils newInstance() {
        DownloadAppUtils fragment = new DownloadAppUtils();
        return fragment;
    }

    public void gotoLoad(Activity activity, String url) {
        DownloadProgressDialog progressDialog = new DownloadProgressDialog(activity);
        progressDialog.setProgressStyle(STYLE_HORIZONTAL);
        progressDialog.setCancelable(true);
        progressDialog.show();
        File externalDownloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File applicationFileDir = new File(externalDownloadsDir, "boniu");
        if (!applicationFileDir.exists()) {
            applicationFileDir.mkdirs();
        }
        destPath = new File(applicationFileDir, System.currentTimeMillis() + ".apk");
        progressDialog.setMax(100);
        long length = destPath.length();
        RxHttp.get(url)
                .setRangeHeader(length, -1, true)  //设置开始下载位置，结束位置默认为文件末尾
                .asDownload(destPath.getPath(), progress -> {
                    Log.e("sht", "progress->" + progress);
                    //如果需要衔接上次的下载进度，则需要传入上次已下载的字节数length
                    progressDialog.setMax((int) progress.getTotalSize());
                    progressDialog.setProgress((int) progress.getCurrentSize());
                    //下载进度回调,0-100，仅在进度有更新时才会回调
                }, AndroidSchedulers.mainThread())
                .to(RxLife.as((LifecycleOwner) activity)) //加入感知生命周期的观察者
                .subscribe(s -> { //s为String类型
                    progressDialog.dismiss();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        boolean haveInstallPermission = activity.getPackageManager().canRequestPackageInstalls();
                        if (haveInstallPermission) {
                            OpenApp.installApk(activity, destPath);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setMessage("安装应用需要打开安装未知来源应用权限，请去设置中开启权限");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Uri packageUri = Uri.parse("package:" + activity.getPackageName());
                                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUri);
                                    activity.startActivityForResult(intent, 101);
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
                        OpenApp.installApk(activity, destPath);
                    }
                    //下载成功，处理相关逻辑
                }, (OnError) error -> {
                    // error.show();
                    Tip.show("下载失败");
                    //下载失败，处理相关逻辑
                });
    }
}
