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
        for (int i=1;i<=30;i++) {
            AxisValue axisValue = new AxisValue();
            axisValue.setXLabel("2016-11-"+i).setY(20*i);
            list.add(axisValue);
        }
        lvLinearChart.setAxisList(list);
    }

    @OnClick(R.id.btn_set)
    public void onClick() {
        initView();
    }
}
