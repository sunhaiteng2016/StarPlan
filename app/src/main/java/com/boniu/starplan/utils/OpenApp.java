package com.boniu.starplan.utils;


import android.app.Activity;
import android.content.Intent;

public class OpenApp {

    public static  void OpenApp(Activity context,String packageName){
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.putExtra("type", true);
        context.startActivityForResult(intent,123);
    }
}
