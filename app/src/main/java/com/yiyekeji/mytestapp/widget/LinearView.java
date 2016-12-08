package com.yiyekeji.mytestapp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.yiyekeji.mytestapp.R;
import com.yiyekeji.mytestapp.bean.AxisValue;
import com.yiyekeji.mytestapp.utils.LogUtil;
import com.zhy.autolayout.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    int screenHeight,screenWeight;
    private List<AxisValue> AxisList = new ArrayList<>();
    int XPoint=60;
    int YLength=700;
    int YScale;
    int XLength=700;
    int XScale=600/10;

    private Paint axisPaint,lineaPaint;//两支笔

    private void init(){
        color_black=ContextCompat.getColor(context,R.color.black);
        color_orange=ContextCompat.getColor(context, R.color.orange);
        color_gray=ContextCompat.getColor(context,R.color.gray_black);

        screenWeight= ScreenUtils.getScreenSize(context,true)[0];
        screenHeight=ScreenUtils.getScreenSize(context,true)[1];

        XLength=screenWeight;
        YLength=screenWeight;
        setAxisPaint();
        setLinearPaint();
    }

    private void setAxisPaint(){
        axisPaint=new Paint();
        axisPaint.setColor(color_gray);
        axisPaint.setAntiAlias(true);
        axisPaint.setStyle(Paint.Style.FILL);  //画笔风格
        axisPaint.setTextSize(25);
        axisPaint.setStrokeWidth(3);           //画笔粗细
        axisPaint.setTextAlign(Paint.Align.CENTER);
        axisPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
    }

    private void setLinearPaint(){
        CornerPathEffect cornerPathEffect = new CornerPathEffect(10);
        lineaPaint=new Paint();
        lineaPaint.setColor(color_orange);
        lineaPaint.setAntiAlias(true);
        lineaPaint.setStyle(Paint.Style.STROKE);  //画笔风格
        lineaPaint.setTextSize(26);
        lineaPaint.setStrokeJoin(Paint.Join.ROUND);
        lineaPaint.setStrokeWidth(5);           //画笔粗细
        lineaPaint.setTextAlign(Paint.Align.CENTER);
    }


    /**
     * 先确定X轴总共有多长
     * X的数据类型为日期 计算日期的区间和刻度数（List的size），
     * @param list
     */

    public void setAxisList(List<AxisValue> list){
        this.AxisList=list;
        XScale=XLength/list.size();
        setY();
        setXLabel();
        invalidate();
    }

    private void setXLabel() {
        for (AxisValue value : AxisList) {
            String x = value.getXLabel();
            value.setXLabel(x.substring(x.length()-5,x.length()));
        }
    }

    /**计算YScale
     * 每1单位的值对应的px值=YLength/（最大值*5/4）这样最高只到Y轴的5分4处
     */
    private void setY() {
        List<Integer> list = new ArrayList<>();
        for (AxisValue value : AxisList) {
            list.add(value.getY());
        }
        Collections.sort(list);
        int max=list.get(list.size()-1);
        LogUtil.d("最大的数为：",max);
        YScale=YLength/(max*3/2);
        LogUtil.d("YScale为：",YScale);
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
        drawLinearChar(canvas);
    }

    private void drawLinearChar(Canvas canvas) {
        Path path=new Path();
        AxisValue axisValue;
        for(int i=0;i<AxisList.size();i++){
            axisValue = AxisList.get(i);
            if (i==0){
                path.moveTo(XScale*i+XPoint,YLength-axisValue.getY()*YScale);
            }
            path.lineTo(XScale*i+XPoint,YLength-axisValue.getY()*YScale);
        }
        canvas.drawPath(path,lineaPaint);

    }

    private void drawYLine(Canvas canvas){
        //从屏幕上从下画
        canvas.drawLine(XPoint, 100, XPoint, YLength, axisPaint);
        //添加刻度和文字
        for(int i=0; i  < AxisList.size(); i++) {
//            canvas.drawLine(XPoint, YLength-i*YScale,XLength, YLength-i*YScale, axisPaint);  //刻度
            canvas.drawText(AxisList.get(i).getY()+"", XPoint-20, YLength - AxisList.get(i).getY()* YScale, axisPaint);//文字
        }
    }
    private void drawXLine(Canvas canvas){
        canvas.drawLine(XPoint, YLength, XLength+XPoint, YLength, axisPaint);
        //添加刻度和文字
        for(int i=0; i  < AxisList.size(); i++) {
//            canvas.drawLine(XPoint+i*XScale,YLength, XPoint+i*XScale, YLength-30, axisPaint);  //刻度
            canvas.drawText(AxisList.get(i).getXLabel()+"", XPoint+i*XScale, YLength+50, axisPaint);//文字
        }
    }
}
