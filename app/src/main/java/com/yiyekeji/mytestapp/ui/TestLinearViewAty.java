package com.yiyekeji.mytestapp.ui;

import android.os.Bundle;
import android.widget.Button;

import com.yiyekeji.mytestapp.R;
import com.yiyekeji.mytestapp.bean.AxisValue;
import com.yiyekeji.mytestapp.ui.base.BaseActivity;
import com.yiyekeji.mytestapp.widget.LinearView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/26.
 */
public class TestLinearViewAty extends BaseActivity {
    @InjectView(R.id.lv_linear_chart)
    LinearView lvLinearChart;
    @InjectView(R.id.btn_set)
    Button btnSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linearview);
        ButterKnife.inject(this);
        initView();
    }

    public void initView() {
        Random random = new Random();
        int max=720;
        int min=100;
        List<AxisValue> list = new ArrayList<>();
     /*   for (int i=0;i<20;i++){
            int s=random.nextInt(max)%(max-min+1) + min;
            AxisValue axisValue = new AxisValue();
            axisValue.setX(i*35);
            axisValue.setY(random.nextInt(max)%(max-min+1) + min);
            list.add(axisValue);
        }*/
        AxisValue axisValue = new AxisValue();
        axisValue.setXLabel("2016-11-08").setY(10);

        AxisValue axisValue1 = new AxisValue();
        axisValue1.setXLabel("2016-11-09").setY(20);

        AxisValue axisValue2 = new AxisValue();
        axisValue2.setXLabel("2016-11-10").setY(30);

        AxisValue axisValue3 = new AxisValue();
        axisValue3.setXLabel("2016-11-11").setY(40);

        AxisValue axisValue4 = new AxisValue();
        axisValue4.setXLabel("2016-11-12").setY(60);

        AxisValue axisValue5 = new AxisValue();
        axisValue5.setXLabel("2016-11-13").setY(50);

        AxisValue axisValue6 = new AxisValue();
        axisValue6.setXLabel("2016-11-14").setY(30);

        AxisValue axisValue7 = new AxisValue();
        axisValue7.setXLabel("2016-11-15").setY(10);

        list.add(axisValue);
        list.add(axisValue1);
        list.add(axisValue2);
        list.add(axisValue3);
        list.add(axisValue4);
        list.add(axisValue5);
        list.add(axisValue6);
        list.add(axisValue7);
        lvLinearChart.setAxisList(list);

    }

    @OnClick(R.id.btn_set)
    public void onClick() {
        initView();
    }
}
