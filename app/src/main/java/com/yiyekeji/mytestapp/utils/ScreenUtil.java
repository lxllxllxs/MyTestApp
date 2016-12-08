package com.yiyekeji.mytestapp.utils;

import android.util.DisplayMetrics;

/**
 * Created by lxl on 2016/12/8.
 */
public class ScreenUtil {
    public  static int convertDpToPixel(int dp) {
        DisplayMetrics displayMetrics = MyApp.getContext().getResources().getDisplayMetrics();
        return (int)(dp*displayMetrics.density);
    }

    public static int convertPixelToDp(int pixel) {
        DisplayMetrics displayMetrics = MyApp.getContext().getResources().getDisplayMetrics();
        return (int)(pixel/displayMetrics.density);
    }
}
