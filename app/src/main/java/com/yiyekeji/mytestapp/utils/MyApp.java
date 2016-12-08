package com.yiyekeji.mytestapp.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/11/25.
 */
public class MyApp extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }

    public  static Context getContext(){
        return context;
    }
}
