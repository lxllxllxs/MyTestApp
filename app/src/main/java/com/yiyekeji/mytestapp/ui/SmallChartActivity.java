package com.yiyekeji.mytestapp.ui;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.idtk.smallchart.chart.LineChart;
import com.idtk.smallchart.data.LineData;
import com.idtk.smallchart.interfaces.iData.ILineData;
import com.yiyekeji.mytestapp.R;
import com.yiyekeji.mytestapp.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lxl on 2016/12/7.
 */
public class SmallChartActivity extends BaseActivity {

    protected float[][] points = new float[][]{{1, 10}, {3, 11}, {4, 38}, {5, 9},{10, 14}, {20, 37}, {22, 29}};
    protected float[][] points2 = new float[][]{{1, 52},{3, 51}, {4, 20}, {5, 19}, {10, 54}, {20, 7}};
    protected int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    @InjectView(R.id.id_linearChart)
    LineChart linearChart;

    protected float pxTodp(float value) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float valueDP = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics);
        return valueDP;
    }

    private LineData mLineData = new LineData();
    private ArrayList<ILineData> mDataList = new ArrayList<>();
    private ArrayList<PointF> mPointFArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_chart_activity);
        ButterKnife.inject(this);
        initData();
        linearChart.setDataList(mDataList);
    }


    private void initData() {
        for (int i = 0; i < points.length; i++) {
            mPointFArrayList.add(new PointF(points[i][0], points[i][1]));
        }
        mLineData.setValue(mPointFArrayList);
        mLineData.setColor(Color.MAGENTA);
        mLineData.setPaintWidth(pxTodp(3));
        mLineData.setTextSize(pxTodp(10));
        mDataList.add(mLineData);
    }
}
