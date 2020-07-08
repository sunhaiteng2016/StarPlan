package com.boniu.starplan.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.BuildConfig;


import com.boniu.starplan.R;
import com.boniu.starplan.helper.TTAdManagerHolder;
import com.boniu.starplan.http.RxHttpManager;
import com.boniu.starplan.utils.MiitHelper;
import com.boniu.starplan.view.CustomViewWithTypefaceSupport;
import com.boniu.starplan.view.TextField;
import com.bun.miitmdid.core.JLibrary;

import com.umeng.commonsdk.UMConfigure;

import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.calligraphy3.FontMapper;
import io.github.inflationx.viewpump.ViewPump;

public class ApplicationUtils extends Application {

    private static Context mContext;//全局上下文对象
    public List<Activity> allActivityList = new ArrayList<>();

    public static Context getContext() {
        return mContext;
    }

    public static ApplicationUtils mApp;

    public static ApplicationUtils newInstance() {
        return new ApplicationUtils();
    }

    private static String oaid;
    public static String accountId;
    private static boolean isSupportOaid=true;
    private static int errorCode;

    public static String getOaid() {
        return oaid;
    }
    public static String getErrorCode() {
        return String.valueOf(errorCode);
    }

    public static boolean isSupportOaid() {
        return isSupportOaid;
    }

    public static void setIsSupportOaid(boolean isSupportOaid) {
        ApplicationUtils.isSupportOaid = isSupportOaid;
    }
    public static void setIsSupportOaid(boolean isSupportOaid,int ErrorCode) {
        ApplicationUtils.isSupportOaid = isSupportOaid;
        ApplicationUtils.errorCode=ErrorCode;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        MultiDex.install(this);
        RxHttpManager.init(this);
        //路由器初始化
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init((Application) this);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setFontAttrId(R.attr.fontPath)
                                .setFontMapper(new FontMapper() {
                                    @Override
                                    public String map(String font) {
                                        return font;
                                    }
                                })
                                .addCustomViewWithSetTypeface(CustomViewWithTypefaceSupport.class)
                                .addCustomStyle(TextField.class, R.attr.textFieldStyle)
                                .build()))
                .build());
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        TTAdManagerHolder.init(this);
        try {
            JLibrary.InitEntry(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MiitHelper miitHelper = new MiitHelper(appIdsUpdater);
        miitHelper.getDeviceIds(getApplicationContext());

    }

    public void AddActivity(Activity activity) {
        allActivityList.add(activity);
    }

    public void removeActivity() {
        for (Activity activity : allActivityList) {
            activity.finish();
        }
    }
    private MiitHelper.AppIdsUpdater appIdsUpdater = new MiitHelper.AppIdsUpdater() {
        @Override
        public void OnIdsAvalid(@NonNull String ids) {
            Log.e("++++++ids: ", ids);
            oaid = ids;
        }
    };

    /**
     * 退出指定界面
     */
    public void popActivity(Class... activityClass) {
        if (activityClass == null) {
            return;
        }
        try {
            Activity activities[] = allActivityList.toArray(new Activity[allActivityList.size()]);
            if (activities != null && activities.length != 0) {
                int count = activities.length;
                for (int i = 0; i < count; i++) {
                    Activity activity = activities[i];
                    if (activity != null) {
                        for (Class activityCls : activityClass) {
                            if (activity.getClass().getName().equals(activityCls.getName())) {
                                allActivityList.remove(activity);
                                activity.finish();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
