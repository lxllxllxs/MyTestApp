package com.yiyekeji.mytestapp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.yiyekeji.mytestapp.R;
import com.yiyekeji.mytestapp.bean.AxisValue;
import com.yiyekeji.mytestapp.utils.LogUtils;
import com.zhy.autolayout.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 流量统计折线图
 * 需要引入dp计算
 * 最终传进的是px
 * Created by lxl on 2016/11/26.
 */
public class LinearChartView extends View {
    private Context context;
    private int color_black,color_orange,color_gray;
    int screenHeight,screenWeight;
    private List<AxisValue> AxisList = new ArrayList<>();
    int XPoint=60,YPoint=60;//原点
    float YLength;//一定用float
    float YScale;
    float XLength;
    float XScale;
    int paintWidth=3;//默认笔粗


    private Paint axisPaint,lineaPaint;//两支笔
    DisplayMetrics dm;

    public LinearChartView(Context context) {
        super(context);
        this.context = context;
        init();
    }
    public LinearChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }
    public LinearChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }
    private void init(){
        color_black= ContextCompat.getColor(context, R.color.black);
        color_orange= ContextCompat.getColor(context, R.color.orange);
        color_gray= ContextCompat.getColor(context,R.color.gray_black);

        screenWeight= ScreenUtils.getScreenSize(context,true)[0];
        screenHeight= ScreenUtils.getScreenSize(context,true)[1];

        setAxisPaint();
        setLinearPaint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=measureW(widthMeasureSpec);
        float height=measureH(heightMeasureSpec);
        XLength=width-XPoint;
        YLength=height-YPoint;
        refresh();
        LogUtils.d("onMeasure",width+","+height);
    }

    private int measureH(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            return 800;
        } else if (mode == MeasureSpec.EXACTLY) {
            return size;
        }else {
            return  size;
        }
    }
    private int measureW(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            return 800;
        } else if (mode == MeasureSpec.EXACTLY) {
            return size;
        }else {
            return  size;
        }
    }

    /**
     * 先确定X轴总共有多长
     * X的数据类型为日期 计算日期的区间和刻度数（List的size），
     * @param list
     */
    public void setAxisList(List<AxisValue> list){
        this.AxisList=list;
        needSetting = false;
        refresh();
    }

    private void refresh() {
        setScale();
        setLabel();
        invalidate();
    }

    private void setLabel() {
        for (AxisValue value : AxisList) {
            String x = value.getX().toString();
            String y = value.getY()+"";
            value.setXLabel(x.substring(x.length()-5,x.length()));
            value.setYLabel(y);
        }
    }

    /**计算YScale
     * 为了X和Y轴都预留空间，都只取到7/8处
     * 每1单位的值对应的px值=YLength/（最大值*8/7）这样最高只到Y轴的5分4处
     * 计算XScale
     * BUG:在Y的最大值为2到6的情况下  Y轴刻度显示不全 原因：YMax没有用float
     */
    float YMax,YMin;
    boolean needSetting=true;
    private void setScale() {
        if (needSetting){
            return;
        }
        List<Integer> list = new ArrayList<>();
        for (AxisValue value : AxisList) {
            list.add(value.getY());
        }
        Collections.sort(list);
        YMin=list.get(0);
        YMax=list.get(list.size()-1);
        LogUtils.d("最大的数为：",YMax);
        //注意这里  YMax曾没有用float导致数据丢失
        YScale =YLength /(YMax*8/7);
        XScale=XLength/(list.size()*8/7);
        LogUtils.d("YScale为：",YScale);
    }

    /**
     * @param canvas
     *   invalidate()后会执行
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        drawYLine(canvas);
        drawXLine(canvas);
        drawLinearChar(canvas);
        drawCircle(canvas);
    }

    /**
     * 画节点 要不要隔开五个？
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        lineaPaint.setStyle(Paint.Style.FILL);
        for (int i=0;i<AxisList.size(); i++) {
            if (i!=0&&(i%5!=0)&&(i!=AxisList.size()-1)){
                continue;
            }
            AxisValue axisValue = AxisList.get(i);
            canvas.drawCircle(XPoint+i*XScale,YLength-axisValue.getY()*YScale,paintWidth*2,lineaPaint);
        }
        lineaPaint.setStyle(Paint.Style.STROKE);
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
                continue;
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
        float laberNum = YMax>10?YMax / 5:1;
        LogUtils.d("可能过大的标签数为：", laberNum);
        //从屏幕下往上画
        canvas.drawLine(XPoint, YLength, XPoint,0, axisPaint);
        //添加刻度和文字
        AxisValue axisValue;
        for(int i=0; i  <=YMax; i++) {
            //不能是第一个 和最后一个值（最大值）
            if (i!=0&&(i%laberNum!=0&&i!=YMax)){
                continue;
            }
//            canvas.drawLine(XPoint, YLength-i*YScale,XLength, AxisList.get(i).getY()*YScale, axisPaint);  //刻度线
            //计算label需要的宽度
            float textWidth= XPoint-5*(String.valueOf(i).length())-5;
            float scale=YLength - i* YScale;
                canvas.drawText(i+"",textWidth,scale , axisPaint);//文字
        }
    }


    private void drawXLine(Canvas canvas){
        canvas.drawLine(XPoint, YLength, XLength, YLength, axisPaint);
        //添加刻度和文字 因为固定为30个刻度 所以应该隔五个才标一次
        for(int i=0; i  < AxisList.size(); i++) {
//           canvas.drawLine(XPoint+i*XScale,YLength, XPoint+i*XScale, YLength-30, axisPaint);  //刻度线
            //不能是第一个 和最后一个
            if (i!=0&&(i%5!=0&&i!=AxisList.size()-1)){
                continue;
            }
            canvas.drawText(AxisList.get(i).getXLabel()+"", XPoint+i*XScale, YLength+30, axisPaint);//文字
        }
    }


    private void setAxisPaint(){
        axisPaint=new Paint();
        axisPaint.setColor(color_gray);
        axisPaint.setAntiAlias(true);
        axisPaint.setStyle(Paint.Style.FILL);  //画笔风格
        axisPaint.setTextSize(25);
        axisPaint.setStrokeWidth(paintWidth);           //画笔粗细
        axisPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void setLinearPaint(){
        CornerPathEffect cornerPathEffect = new CornerPathEffect(paintWidth*2);
        lineaPaint=new Paint();
        lineaPaint.setColor(color_orange);
        lineaPaint.setAntiAlias(true);
        lineaPaint.setStyle(Paint.Style.STROKE);  //画笔风格
        lineaPaint.setTextSize(26);
        lineaPaint.setStrokeJoin(Paint.Join.ROUND);
        lineaPaint.setStrokeWidth(paintWidth);           //画笔粗细
        lineaPaint.setTextAlign(Paint.Align.CENTER);
        lineaPaint.setPathEffect(cornerPathEffect);
    }

}
