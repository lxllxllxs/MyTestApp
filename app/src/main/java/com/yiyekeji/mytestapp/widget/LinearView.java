package com.yiyekeji.mytestapp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.yiyekeji.mytestapp.R;
import com.yiyekeji.mytestapp.utils.LogUtil;
import com.zhy.autolayout.utils.ScreenUtils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 流量统计折线图
 * Created by lxl on 2016/11/26.
 */
public class LinearView extends View {
    private Context context;
    public LinearView(Context context) {
        super(context);
        this.context = context;
        initPaint();
    }
    public LinearView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initPaint();
    }
    public LinearView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initPaint();
    }

    private Paint mPaint;
    private  Path path;
    int screenHeight,screenWeight;
    private void initPaint(){
        screenWeight= ScreenUtils.getScreenSize(context,true)[0];
        screenHeight=ScreenUtils.getScreenSize(context,true)[1];
        LogUtil.d("sdasd",screenHeight+"=="+screenHeight);
        CornerPathEffect cornerPathEffect = new CornerPathEffect(200);
        mPaint=new Paint();
        mPaint.setColor(ContextCompat.getColor(context, R.color.orange));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);  //画笔风格
        mPaint.setTextSize(26);
//        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(3);           //画笔粗细
//        mPaint.setPathEffect(cornerPathEffect);
        path= new Path();
        path.moveTo(50,50);
        path.lineTo(60,150);

        path.lineTo(70,150);
        path.lineTo(140,300);
        path.lineTo(210,10);
        path.lineTo(250,50);
        path.lineTo(290,150);
    }


    /**
     * 画布的宽为屏宽的5/6,高为屏宽的5/8
     * 假定屏宽为720 则分别为600 450
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        int paddingTop=(screenHeight-screenWeight*5/8)/2;
        canvas.drawColor(Color.WHITE);
     /*   canvas.drawRect(screenWeight/12, paddingTop,
                screenWeight*11/12,paddingTop+screenWeight*5/8, mPaint);*/
//        canvas.drawPath(path, mPaint);
        drawYLine(canvas);
        drawXLine(canvas);
        drawCircle(canvas);
    }

    int XPoint=50;
    int YPoint=30;
    int YLength=1000;
    int YScale=900/10;
    private void drawYLine(Canvas canvas){
        String[] YLabel={"a","b","c","d","e",
                        "f","g","h","i","j","k"};

        //画Y轴
        canvas.drawLine(XPoint, YLength, XPoint, YPoint, mPaint);

        //Y轴箭头
        canvas.drawLine(XPoint, YPoint, XPoint - 10, YPoint + 30, mPaint);  //箭头
        canvas.drawLine(XPoint, YPoint, XPoint + 10, YPoint + 30 ,mPaint);

        //添加刻度和文字
        for(int i=0; i  < 10; i++) {
            canvas.drawLine(XPoint, YLength-i*YScale, XPoint + 20, YLength-i*YScale, mPaint);  //刻度
            canvas.drawText(YLabel[i], XPoint-20, YLength - i * YScale, mPaint);//文字
        }
    }

    int XLength=700;
    int XScale=600/10;
    private void drawXLine(Canvas canvas){
        String[] XLabel={"1","2","3","4","5",
                "6","7","8","9","10","k"};

        //画X轴
        canvas.drawLine(XPoint, YLength, XLength, YLength, mPaint);

        //X轴箭头
        canvas.drawLine(XLength, YLength, XLength - 30, YLength - 10, mPaint);  //箭头
        canvas.drawLine(XLength, YLength, XLength - 30, YLength +10 ,mPaint);

        //添加刻度和文字
        for(int i=0; i  < 10; i++) {
            canvas.drawLine(XPoint+i*XScale,YLength, XPoint+i*XScale, YLength-10, mPaint);  //刻度
            canvas.drawText(XLabel[i], XPoint+i*XScale, YLength+30, mPaint);//文字
        }
    }

    /**
     * 把（50,1000）当成原坐标
     */

    private Map<Integer, Integer> coordinateMap = new LinkedHashMap<>();
    private  void drawCircle(Canvas canvas){
        coordinateMap.put(60,90);
        coordinateMap.put(120,270);
        coordinateMap.put(180,360);
        coordinateMap.put(240,180);
        coordinateMap.put(300,90);
        coordinateMap.put(360,810);

        Path path=new Path();
        path.moveTo(XPoint, YLength);
        Iterator<Map.Entry<Integer,Integer>> iterator=coordinateMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            int key = (int)entry.getKey();
            int value = (int)entry.getValue();
            canvas.drawCircle(key,YLength-value,3,mPaint);
            path.lineTo(key,YLength-value);
        }
        canvas.drawPath(path, mPaint);
    }
}
