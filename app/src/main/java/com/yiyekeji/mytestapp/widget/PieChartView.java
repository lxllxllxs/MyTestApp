package com.yiyekeji.mytestapp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.github.lzyzsd.randomcolor.RandomColor;
import com.yiyekeji.mytestapp.bean.Pie;
import com.yiyekeji.mytestapp.utils.LogUtils;
import com.zhy.autolayout.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lxl on 2016/12/10.
 */
public class PieChartView extends View {
    private float outRadius,inRadius;
    private Paint circlePaint;
    private Context context;
    private boolean isReady=false;

    final  float ANGLE=360;

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
    float width;
    float height;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        screenWeight= ScreenUtils.getScreenSize(context,true)[0];
        screenHeight= ScreenUtils.getScreenSize(context,true)[1];
        width =measureW(widthMeasureSpec);
        height=measureH(heightMeasureSpec);

        setRectF(width,height);

        float diameter=width>height?height:width;
        origin[0]=width/2;
        origin[1]=height/4;
        outRadius=diameter/4;
        inRadius=outRadius/2;
    }

    /**
     * 设置画弧的约束矩形
     * 让改矩形中心与圆心重合
     */
    private void setRectF(float width,float height) {
       //如果是宽>高  考虑left right的偏移 ,相反则同理
        float offset=Math.abs(height/2-width)/2;
        if (width>height){
            //取一个正方形 为什么 500*500px的 测出来高度大于宽度？
            rectF.bottom =height/2;//减去底部差值
            rectF.top=0;//减去顶部差值
            rectF.left=offset;
            rectF.right=width-offset;
        }else {
            //取一个正方形 为什么 500*500px的 测出来高度大于宽度？
            rectF.bottom =height/2;//减去底部差值
            rectF.top=0;//减去顶部差值
            rectF.left=offset;
            rectF.right=width-offset;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isReady){
            return;
        }
//        drawOutCircle(canvas);
        circlePaint.setColor(Color.WHITE);
        canvas.drawRect(rectF,circlePaint);
        drawArc(canvas);
        drawInCircle(canvas);
    }

    //画内圆 当作裁剪
    private void drawInCircle(Canvas canvas) {
        circlePaint.setColor(Color.WHITE);
        canvas.drawCircle(origin[0],origin[1],inRadius,circlePaint);
    }
    //画外圆
    private void drawOutCircle(Canvas canvas){
        canvas.drawCircle(origin[0],origin[1],outRadius,circlePaint);
    }
    /**
     * 画弧要记得角度的累加
     *
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        float totalAngle=0;
        for (int i=0;i<datas.size();i++) {
            Pie pie = datas.get(i);
            circlePaint.setColor(pie.getColor());
            canvas.drawArc(rectF,
                    totalAngle,
                    pie.getPercent() * ANGLE,
                    true, circlePaint);
            totalAngle = totalAngle + pie.getPercent()*ANGLE;
        }
    }

    /**
     * 初始数据只要设置其数量和标签名
     */
    RandomColor rc = new RandomColor();
    private List<Pie> datas = new ArrayList<>();
    public void setDatas(List<Pie> list){
        this.datas=list;
        float total=0;
        for (Pie pie : datas) {
            total=total+pie.getNumber();
        }
        for (Pie pie : datas) {
            pie.setPercent(total);
        }
        //排序
        Collections.sort(datas);
        total=0;
        List<Pie> tempList = new ArrayList<>();
        for (Pie pie:datas){
            pie.setColor(rc.randomColor());
        /*    if (total>=0.7){
                pie.setColor(Color.BLACK);
                pie.setLabel("其他");
                pie.setDirectPercent(1 - total);
                tempList.add(pie);
                datas = tempList;
                break;
            }*/
            tempList.add(pie);
            total=total+pie.getPercent();
            LogUtils.d("setPercent", pie.getPercent());
        }
        isReady = true;
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
