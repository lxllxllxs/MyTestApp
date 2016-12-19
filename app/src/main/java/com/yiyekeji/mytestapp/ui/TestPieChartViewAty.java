package com.yiyekeji.mytestapp.ui;

import android.os.Bundle;

import com.yiyekeji.mytestapp.R;
import com.yiyekeji.mytestapp.bean.Pie;
import com.yiyekeji.mytestapp.ui.base.BaseActivity;
import com.yiyekeji.mytestapp.widget.PieChartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lxl on 2016/12/10.
 */
public class TestPieChartViewAty extends BaseActivity {
    @InjectView(R.id.pieChartView)
    PieChartView pieChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart_view);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private List<Pie> pieList = new ArrayList<>();

    private void initData() {
        for (int i = 0; i < 10; i++) {
            Pie pie = new Pie();
            pie.setNumber(5*i);
            pie.setLabel("w" + i);
            pieList.add(pie);
        }
    }

    private void initView() {
        pieChartView.setDatas(pieList);
    }

}
