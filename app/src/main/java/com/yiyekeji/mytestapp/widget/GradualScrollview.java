package com.yiyekeji.mytestapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/11/25.
 */
public class GradualScrollview extends ScrollView {
    public GradualScrollview(Context context) {
        super(context);
    }

    public GradualScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GradualScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

   public interface  MyScrollListener{
         void onGradualScrollViewChange(int l, int t, int oldl, int oldt);
    }

    private MyScrollListener myScrollListener;
    public void setMyScrollListener(MyScrollListener myScrollListener){
        this.myScrollListener = myScrollListener;
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (myScrollListener != null) {
            myScrollListener.onGradualScrollViewChange(l,t,oldl,oldt);
        }
    }
}
