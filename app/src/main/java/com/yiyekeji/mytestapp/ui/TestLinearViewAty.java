package com.yiyekeji.mytestapp.ui;

import android.os.Bundle;
import android.widget.Button;

import com.yiyekeji.mytestapp.R;
import com.yiyekeji.mytestapp.ui.base.BaseActivity;
import com.yiyekeji.mytestapp.widget.LinearView;

import java.util.LinkedHashMap;
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
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.btn_set)
    public void onClick() {
        Random random = new Random();
        int max=720;
        int min=100;
        LinkedHashMap<Integer, Integer> hashMap = new LinkedHashMap<>();
        for (int i=0;i<20;i++){
            int s=random.nextInt(max)%(max-min+1) + min;
            hashMap.put(i*35,random.nextInt(max)%(max-min+1) + min);
        }
        lvLinearChart.setLinkHashMap(hashMap);
    }
}
