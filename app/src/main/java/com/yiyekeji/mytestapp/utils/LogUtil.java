package com.yiyekeji.mytestapp.utils;

import android.util.Config;
import android.util.Log;


/**
 * Created by zhouyaozhong on 15/12/2.
 */
public class LogUtil {
    public static void i(String tag, String msg) {
        if(Config.DEBUG) {
            Log.i(tag, msg);
        }
    }


    public static void i(String tag, Object msg) {
        if(Config.DEBUG) {
            Log.i(tag, msg.toString());
        }
    }


    public static void w(String tag, String msg) {
        if(Config.DEBUG) {
            Log.w(tag, msg);
        }
    }


    public static void w(String tag, Object msg) {
        if(Config.DEBUG) {
            Log.w(tag, msg.toString());
        }
    }


    public static void e(String tag, String msg) {
        if(Config.DEBUG) {
            Log.e(tag, msg);
        }
    }


    public static void e(String tag, Object msg) {
        if(Config.DEBUG) {
            Log.e(tag, msg.toString());
        }
    }


    public static void d(String tag, String msg) {
        if(Config.DEBUG) {
            Log.d(tag, msg);
        }
    }


    public static void d(String tag, Object msg) {
        if(Config.DEBUG) {
            Log.d(tag, msg.toString());
        }
    }


    public static void v(String tag, String msg) {
        if(Config.DEBUG) {
            Log.v(tag, msg);
        }
    }


    public static void v(String tag, Object msg) {
        if(Config.DEBUG) {
            Log.v(tag, msg.toString());
        }
    }

}
