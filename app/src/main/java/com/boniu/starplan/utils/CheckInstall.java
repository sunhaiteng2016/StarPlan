package com.boniu.starplan.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import java.io.File;
import java.util.List;

public class CheckInstall {

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

    /**
     * 获取文件安装的Intent
     *
     * @param file
     * @return
     */
    public static Intent getFileIntent(File file) {
        Uri uri = Uri.fromFile(file);
        String type = "application/vnd.android.package-archive";
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, type);
        return intent;
    }

    /**
     * 判断应用是否正在运行
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : list) {
            String processName = appProcess.processName;
            if (processName != null && processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    //通过判断手机里的所有进程是否有这个App的进程
  //从而判断该App是否有打开
    public static boolean shouldInit(Context context,String packageName) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            UsageStatsManager m=(UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);
            long time =System.currentTimeMillis()-10*1000;
            List<UsageStats> list=m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, time, System.currentTimeMillis());
            for (int i = 0; i < list.size(); i++) {
                UsageStats usageStats = list.get(i);
                String packageName1 = usageStats.getPackageName();
                if (packageName1.equals(packageName)){
                    return true;
                }
            }
            return false;
        }



        return false;
    }
}
