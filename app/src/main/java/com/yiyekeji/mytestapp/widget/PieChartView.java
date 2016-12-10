package com.yiyekeji.mytestapp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.yiyekeji.mytestapp.bean.Pie;
import com.yiyekeji.mytestapp.utils.LogUtils;
import com.zhy.autolayout.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxl on 2016/12/10.
 */
public class PieChartView extends View {
    private float outRadius,inRadius;
    private Paint circlePaint;
    private Context context;
    public PieChartView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.GREEN);
    }


    float[] origin=new float[2];
    int screenHeight,screenWeight;
    RectF rectF=new RectF();
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        screenWeight= ScreenUtils.getScreenSize(context,true)[0];
        screenHeight= ScreenUtils.getScreenSize(context,true)[1];
        float width=measureW(widthMeasureSpec);
        float height=measureH(heightMeasureSpec);

        setRectF(width,height);

        float diameter=width>height?height:width;
        origin[0]=width/2;
        origin[1]=height/2;
        //外圆的直径为 较小值的2/3,内圆的直径为外圆的2/3
        outRadius=diameter/2;
        inRadius=outRadius/3;
    }

    /**
     * 设置画弧的约束矩形
     * 让改矩形中心与圆心重合
     */
    private void setRectF(float width,float height) {
       //如果是宽>高  考虑left right的偏移 ,相反则同理
        float offset=Math.abs(height-width)/2;
        if (width>height){
            //取一个正方形 为什么 500*500px的 测出来高度大于宽度？
            rectF.bottom =height;//减去底部差值
            rectF.top=0;//减去顶部差值
            rectF.left=offset;
            rectF.right=width-offset;
        }else {
            //取一个正方形 为什么 500*500px的 测出来高度大于宽度？
            rectF.bottom =height-offset;//减去底部差值
            rectF.top=offset;//减去顶部差值
            rectF.left=0;
            rectF.right=width;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        circlePaint.setColor(Color.RED);
//        canvas.drawRect(rectF, circlePaint);
        drawArc(canvas);
    }
    private void drawCircle(Canvas canvas){
        //画外圆
        canvas.drawCircle(origin[0],origin[1],outRadius,circlePaint);
        //画内圆
        circlePaint.setColor(Color.WHITE);
        canvas.drawCircle(origin[0],origin[1],inRadius,circlePaint);
    }

    final  float ANGLE=360;
    private void drawArc(Canvas canvas) {
        for (int i=0;i<datas.size();i++) {
            Pie pie = datas.get(i);
            circlePaint.setColor(Color.parseColor(pie.getColor()));
            if (i==0) {
                canvas.drawArc(rectF,0,  pie.getPercent()*ANGLE, true, circlePaint);
                continue;
            }
            Pie pie2 = datas.get(i-1);
            canvas.drawArc(rectF,pie2.getPercent()*ANGLE, pie.getPercent()*ANGLE, true, circlePaint);
        }
    }

    /**
     *初始数据只要设置其数量和标签名
     */
    private List<Pie> datas = new ArrayList<>();
    public void setDatas(List<Pie> list){
        this.datas=list;
        int total=0;
        for (Pie pie : datas) {
            total=total+pie.getNumber();
        }
        for (Pie pie:datas){
            pie.setPercent(total);
            String hex=Long.toHexString((long) (Math.random() * (16777215 - 100) + 100));
            LogUtils.d("RandomColor",hex);
            pie.setColor(hex);
        }

        invalidate();
    }

    private float measureH(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            return screenHeight;
        } else if (mode == MeasureSpec.EXACTLY) {
            return size;
        }else {
            return  size;
        }
    }
    private float measureW(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            return screenWeight;
        } else if (mode == MeasureSpec.EXACTLY) {
            return size;
        }else {
            return  size;
        }
    }
}
