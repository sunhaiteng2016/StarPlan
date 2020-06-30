package com.boniu.starplan.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.BuildConfig;
import com.boniu.starplan.R;
import com.boniu.starplan.http.RxHttpManager;
import com.boniu.starplan.utils.SystemInfoUtils;
import com.boniu.starplan.view.CustomViewWithTypefaceSupport;
import com.boniu.starplan.view.TextField;
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

    }

    public void AddActivity(Activity activity) {
        allActivityList.add(activity);
    }

    public void removeActivity() {
        for (Activity activity : allActivityList) {
            activity.finish();
        }
    }
}
