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
        init();
    }
    public LinearView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }
    public LinearView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }
    private int color_black,color_orange,color_gray;
    private Paint mPaint;
    private  Path path;
    int screenHeight,screenWeight;
    private Map<Integer, Integer> coordinateMap = new LinkedHashMap<>();
    int XPoint=60;
    int YLength=700;
    int YScale=600/10;
    int XLength=700;
    int XScale=600/10;

    private void init(){
        color_black=ContextCompat.getColor(context,R.color.gray_black);
        color_orange=ContextCompat.getColor(context,R.color.orange);
        color_gray=ContextCompat.getColor(context,R.color.gray);


        screenWeight= ScreenUtils.getScreenSize(context,true)[0];
        screenHeight=ScreenUtils.getScreenSize(context,true)[1];

        XLength=screenWeight;
        YLength=screenWeight;

        XScale=XLength/10;
        YScale=YLength/10;

        LogUtil.d("sdasd",screenHeight+"=="+screenHeight);
        CornerPathEffect cornerPathEffect = new CornerPathEffect(50);
        mPaint=new Paint();
        mPaint.setColor(color_gray);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);  //画笔风格
        mPaint.setTextSize(26);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(2);           //画笔粗细
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setPathEffect(cornerPathEffect);
        path= new Path();
/*        path.moveTo(50,50);
        path.lineTo(60,150);

        path.lineTo(70,150);
        path.lineTo(140,300);
        path.lineTo(210,10);
        path.lineTo(250,50);
        path.lineTo(290,150);*/
    }

    public  void setLinkHashMap(LinkedHashMap<Integer,Integer> map){
        this.coordinateMap=map;
        invalidate();
    }

    /**
     * 画布的宽为屏宽的5/6,高为屏宽的5/8
     * 假定屏宽为720 则分别为600 450
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        drawYLine(canvas);
        drawXLine(canvas);
        drawCircle(canvas);
    }
    private void drawYLine(Canvas canvas){
        String[] YLabel={"a","b","c","d","e",
                        "f","g","h","i","j","k"};
        //添加刻度和文字
        for(int i=0; i  < 10; i++) {
            mPaint.setColor(color_gray);
            canvas.drawLine(XPoint, YLength-i*YScale,XLength, YLength-i*YScale, mPaint);  //刻度
            mPaint.setColor(color_black);
            canvas.drawText(YLabel[i], XPoint-20, YLength - i * YScale, mPaint);//文字
        }
    }
    private void drawXLine(Canvas canvas){
        String[] XLabel={"0","1","2","3","4","5",
                "6","7","8","9","10","k"};
        mPaint.setColor(ContextCompat.getColor(context,R.color.gray));
        //画X轴
        canvas.drawLine(XPoint, YLength, XLength+XPoint, YLength, mPaint);
        //添加刻度和文字
        for(int i=0; i  < 10; i++) {
            mPaint.setColor(color_black);
            canvas.drawLine(XPoint+i*XScale,YLength, XPoint+i*XScale, YLength-30, mPaint);  //刻度
            canvas.drawText(XLabel[i], XPoint+i*XScale, YLength+50, mPaint);//文字
        }
    }

    /**
     * 把（50,700）当成原坐标
     * 闭合坐标（750，700）
     */

    private  void drawCircle(Canvas canvas){
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setColor(ContextCompat.getColor(context,R.color.orange));
        Path path=new Path();

        int key=0,value=0;
        boolean isFirst = true;
        Iterator<Map.Entry<Integer,Integer>> iterator=coordinateMap.entrySet().iterator();
        int firstX=0,firstY=0;
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            key= (int)entry.getKey();
            value = (int)entry.getValue();
            if (isFirst) {
                isFirst = false;
                firstX = key+XPoint;
                firstY=YLength-value;
                //画首线 用于填充完成封闭
                mPaint.setColor(color_orange);
                path.moveTo(XPoint, YLength);
            }
            path.lineTo(key+XPoint,YLength-value);
//            canvas.drawCircle(key+XPoint,YLength-value,6,mPaint);
        }
        //第一期也要到底 才能使填充完美
        path.lineTo(XLength,YLength);
        canvas.drawPath(path, mPaint);
        //覆盖画一条起始线 改色
        Path path0=new Path();
        path0.moveTo(XPoint,YLength);
        path0.lineTo(firstX,firstY);
        mPaint.setColor(color_gray);
        canvas.drawPath(path0,mPaint);
        //覆盖画一条完结线 改色
        Path path2=new Path();
        path2.moveTo(key+XPoint,YLength-value);
        path2.lineTo(XLength,YLength);
        canvas.drawPath(path2,mPaint);
        //换色填充 应改为渐变色
        path.close();
        mPaint.setColor(ContextCompat.getColor(context,R.color.gray));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, mPaint);
    }


    private void setLinerMap(){
        coordinateMap.put(0,0);
        coordinateMap.put(1*XScale,1*YScale);
        coordinateMap.put(2*XScale,2*YScale);
        coordinateMap.put(3*XScale,3*YScale);
        coordinateMap.put(4*XScale,4*YScale);
        coordinateMap.put(5*XScale,5*YScale);
        coordinateMap.put(6*XScale,6*YScale);
        coordinateMap.put(7*XScale,7*YScale);
        coordinateMap.put(8*XScale,8*YScale);
        coordinateMap.put(9*XScale,9*YScale);
    }
}
