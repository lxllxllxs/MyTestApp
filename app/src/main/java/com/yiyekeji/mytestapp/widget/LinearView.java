package com.yiyekeji.mytestapp.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
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
 * 需要引入dp计算
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
    int XPoint=60;//原点
    int YLength;
    float YScale;
    int XLength;
    int XScale;
    private  boolean isSetting;
    private Paint axisPaint,lineaPaint;//两支笔
    DisplayMetrics dm;
    private void init(){
        color_black=ContextCompat.getColor(context,R.color.black);
        color_orange=ContextCompat.getColor(context, R.color.orange);
        color_gray=ContextCompat.getColor(context,R.color.gray_black);

        screenWeight= ScreenUtils.getScreenSize(context,true)[0];
        screenHeight=ScreenUtils.getScreenSize(context,true)[1];

        dm= new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        LogUtil.d("DisplayMetrics:", dm.densityDpi + "," + dm.scaledDensity);
        XLength=screenWeight;
        YLength=screenWeight;
        LogUtil.d("AutoUtils测算出来的X和Y轴的长度分别为：", XLength + "," + YLength);
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
        isSetting=true;
        setScale();
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
     * 为了X和Y轴都预留空间，都只取到7/8处
     * 每1单位的值对应的px值=YLength/（最大值*8/7）这样最高只到Y轴的5分4处
     * 计算XScale
     *
     *
     */
    int YMax,YMin;
    private void setScale() {
        List<Integer> list = new ArrayList<>();
        for (AxisValue value : AxisList) {
            list.add(value.getY());
        }
        Collections.sort(list);
        YMin=list.get(0);
        YMax=list.get(list.size()-1);
        LogUtil.d("最大的数为：",YMax);

        YScale = YLength / YMax*(dm.scaledDensity/1);//注意这里得出的YLength和YScale使用了不同的计算单位 要进行换算处理
        XScale=XLength/(list.size()*8/7);
        LogUtil.d("YScale为：",YScale);
    }

    /**
     * @param canvas
     *   invalidate()后会执行
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (!isSetting){
            return;
        }
        canvas.drawColor(Color.WHITE);
        drawYLine(canvas);
        drawXLine(canvas);
        drawLinearChar(canvas);
    }

    /**
     * 折线图描点连线
     * @param canvas
     */
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

    /**
     * 当最高值和最低值相差太大时 显示过于密集 应该忽略部分标注
     * 暂分5份
     * @param canvas
     */
    private void drawYLine(Canvas canvas){
        int laberNum = YMax / 5;
        LogUtil.d("可能过大的标签数为：", laberNum);
        //从屏幕下往上画
        canvas.drawLine(XPoint, YLength, XPoint,100, axisPaint);
        //添加刻度和文字
        AxisValue axisValue;
        for(int i=0; i  < AxisList.size(); i++) {
            axisValue=AxisList.get(i);
            //不能是第一个 和最后一个
            if (i!=0&&(axisValue.getY()%laberNum!=0)&&i!=AxisList.size()-1){
                continue;
            }
//            canvas.drawLine(XPoint, YLength-i*YScale,XLength, AxisList.get(i).getY()*YScale, axisPaint);  //刻度线
            canvas.drawText(axisValue.getY()+"", XPoint-20, YLength - axisValue.getY()* YScale, axisPaint);//文字
        }
    }
    private void drawXLine(Canvas canvas){
        canvas.drawLine(XPoint, YLength, XLength+XPoint, YLength, axisPaint);
        //添加刻度和文字 因为固定为30个刻度 所以应该隔五个才标一次
        for(int i=0; i  < AxisList.size(); i++) {
//            canvas.drawLine(XPoint+i*XScale,YLength, XPoint+i*XScale, YLength-30, axisPaint);  //刻度线
            //不能是第一个 和最后一个
            if (i!=0&&(i%5!=0&&i!=AxisList.size()-1)){
                continue;
            }
            canvas.drawText(AxisList.get(i).getXLabel()+"", XPoint+i*XScale, YLength+50, axisPaint);//文字
        }
    }
}
