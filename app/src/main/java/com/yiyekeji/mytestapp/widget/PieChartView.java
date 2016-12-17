package com.yiyekeji.mytestapp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.github.lzyzsd.randomcolor.RandomColor;
import com.yiyekeji.mytestapp.bean.Pie;
import com.yiyekeji.mytestapp.utils.LogUtils;
import com.zhy.autolayout.utils.ScreenUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lxl on 2016/12/10.
 *
 * 暂时应该只关注以宽为基点的设计
 */
public class PieChartView extends View {
    private float outRadius,inRadius;
    private Paint circlePaint;
    private Context context;
    private boolean isReady=false;

    /**
     * Label排列方式
     */
    private final  int LABEL_TYPE_SINGLE=0;
    private final  int LABEL_TYPE_DOUBLE=1;
    private int mLabelType = LABEL_TYPE_SINGLE;
    final  float ANGLE=360;
    private final int colorMax=0xffffff;
    RandomColor rc = new RandomColor();

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
        circlePaint.setAntiAlias(true);
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
        blockSize=inRadius/3;

        circlePaint.setTextSize(blockSize);
    }
    /**
     * 设置画弧的约束矩形
     * 让改矩形中心与圆心重合
     *
     * 暂时只关注 width
     */
    private void setRectF(float width,float height) {
       //如果是宽>高  考虑left right的偏移 ,相反则同理
        float offset=Math.abs(height/2-width)/2;
//        if (width>height){
            //取一个正方形 为什么 500*500px的 测出来高度大于宽度？
            rectF.bottom =height/2;//减去底部差值
            rectF.top=0;//减去顶部差值
            rectF.left=offset;
            rectF.right=width-offset;
     /*   }else {
            //取一个正方形 为什么 500*500px的 测出来高度大于宽度？
            rectF.bottom =height/2;//减去底部差值
            rectF.top=0;//减去顶部差值
            rectF.left=offset;
            rectF.right=width-offset;
        }*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isReady){
            return;
        }



        numberWidht = circlePaint.measureText(datas.get(0).getNumber()+"");
        percentWidth = circlePaint.measureText(formatPercent(datas.get(0).getPercent()));
        for (Pie pie : datas) {
            if (labelWidth<circlePaint.measureText(pie.getLabel())){
                labelWidth=circlePaint.measureText(pie.getLabel());
            }
        }

        if (datas.size() >= 13 && (wholeLableWidth <rectF.width()/2)) {
            mLabelType = LABEL_TYPE_DOUBLE;
        } else {
            mLabelType = LABEL_TYPE_SINGLE;
        }
        /**
         * 如果小于7个Pie或整label宽度超过控件的/2采用单列
         * 如果大于或等于 7个Pie,采用双列
         */
        //这里作为整个label矩形区域的 判断依据
        wholeLableWidth  = 4 * blockSize+ labelWidth + numberWidht + percentWidth;
        LogUtils.d("maxWidth", wholeLableWidth + "=" + rectF.width());

        //为了校准用的矩形
        circlePaint.setColor(Color.WHITE);
        canvas.drawRect(rectF,circlePaint);
        drawArc(canvas);
        drawInCircle(canvas);
        drawLabel(canvas);
    }

    /**
     * 画色块统计
     * 色块从左到右从上到下画
     * 每个色块宽高均为inRadius的四分之一
     * 间隔为一个色块高度
     *
     * 单列最多画13个label，大于或等于13应该两列
     * 单列时：
     *色块左边为 ：origin[0] + ((int) rectF.width()) / 4
     * 双列时：
     * 第一列色块左边RectF的Left ，其第一个色块距RectF bottom值为一半的inRadius
     * 第二列色块左边为圆心X轴，
     *
     *
     * 应该用Pie记录其所在坐标区域，从而设置点击事件
     * @param canvas
     */
    private   float blockSize;
    private void drawLabel(Canvas canvas) {
        RectF rect=null;
        int count=1;//要从1开始
        for (Pie pie : datas) {
            circlePaint.setColor(pie.getColor());
            rect= new RectF();
            switch (mLabelType) {
                case LABEL_TYPE_DOUBLE:
                    if (count % 2 != 0) {
                        rect.left = origin[0] - ((int) rectF.width()) / 2;
                        rect.top = rectF.bottom + ((count + 1) / 2) * (inRadius / 2);
                        rect.bottom = rect.top +blockSize ;
                        rect.right = rect.left + blockSize;
                        LogUtils.d("drawLabel", "左");
                    } else {
                        //右边应该以左边末+2个方块 +label里的3个方块
                        rect.left = 5*inRadius/4+wholeLableWidth+rectF.left;
                        rect.top = rectF.bottom + (count / 2) * (inRadius / 2);
                        rect.bottom = rect.top + blockSize;
                        rect.right = rect.left + blockSize;
                        LogUtils.d("drawLabel", "右");
                    }
                    break;
                case LABEL_TYPE_SINGLE:
                    rect.left = origin[0] - ((int) rectF.width()) / 4 - blockSize;
                    rect.top = rectF.bottom + count * (inRadius / 2);
                    rect.bottom = rect.top + blockSize;
                    rect.right = rect.left + blockSize;

                    pie.setX1(rect.left);
                    pie.setX2(rect.right+wholeLableWidth);
                    pie.setY1(rect.top);
                    pie.setY2(rect.bottom);

//                    LogUtils.d("drawLabel", "单列居中");
                    break;
            }
            //设置字体锚点
            float textAnchor=rect.right;
            canvas.drawRect(rect, circlePaint);
            circlePaint.setColor(Color.BLACK);
            Paint.FontMetricsInt fontMetrics = circlePaint.getFontMetricsInt();
            // 转载请注明出处：http://blog.csdn.net/hursing
            int baseline = (int) (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2;

//            text=text.substring(0,text.indexOf(".")+3)+"%";
            //分三次画 label number  percent 为了对齐应该测算出最宽的一个 取其值
            canvas.drawText(pie.getLabel(), textAnchor+blockSize/2, baseline, circlePaint);
            String number=(int)pie.getNumber()+"";
            canvas.drawText(number, textAnchor+blockSize/2+labelWidth, baseline, circlePaint);
            DecimalFormat decimalFormat=new DecimalFormat("#.##");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            String percent=decimalFormat.format(pie.getPercent()*100);//format 返回的是字符串

            canvas.drawText(percent,textAnchor+blockSize/2+labelWidth+numberWidht, baseline, circlePaint);
            count++;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction();

        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (eventaction ) {
            case MotionEvent.ACTION_DOWN:
                for (Pie pie : datas) {
                    if (x < pie.getX2() && x > pie.getX1() && y < pie.getY2() && y > pie.getY1()) {
                        LogUtils.d("PieChartView:onTouchEvent",pie.getLabel());
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
    //画内圆 当作裁剪
    private void drawInCircle(Canvas canvas) {
        circlePaint.setColor(Color.WHITE);
        canvas.drawCircle(origin[0],origin[1],inRadius,circlePaint);
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
     *
     *利用measureText 取得Label number percent 的最大宽值
     * 注意在此之前必须先设置paint的textSize
     * 测量
     */
    private final  String OTHER="其他";
    private  float wholeLableWidth;
    private  float labelWidth,numberWidht,percentWidth;
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
        //排序 从大到小排序 应该从这里取第一个直接测最宽的一个number和percent
        Collections.sort(datas);
        total=0;
        List<Pie> tempList = new ArrayList<>();
        int colorScale = colorMax / 10;
        //根据List大小获得固定差值的颜色集合，419430
        for (int i=0; i<datas.size();i++){
            Pie pie = datas.get(i);
            pie.setColor(Color.parseColor(colors[i]));
            if (total>=0.90){
                pie.setLabel(OTHER);
                pie.setDirectPercent(1 - total);
                tempList.add(datas.get(i));
                datas = tempList;
                break;
            }
            tempList.add(datas.get(i));
            total=total+datas.get(i).getPercent();
            LogUtils.d("setPercent", datas.get(i).getPercent());
        }
        isReady = true;
        invalidate();
    }

    private String formatPercent(float f){
        DecimalFormat decimalFormat=new DecimalFormat("#.##");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return  decimalFormat.format(f*100);//format 返回的是字符串
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

    protected  final String[] colors={
            "#FF4081","#FF9966","#F7B64A","#565E8D","#D63C0D","#3883EF",
            "#98C782","#FF5608","#d03e4b","#008800","#4FC1E9","#00ff00",
            "#20B2AA"
    };
}
