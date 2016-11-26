package com.yiyekeji.mytestapp.ui;

import android.os.Bundle;

import com.yiyekeji.mytestapp.ui.base.BaseActivity;
import com.yiyekeji.mytestapp.widget.LinearView;

/**
 * Created by Administrator on 2016/11/26.
 */
public class TestLinearViewAty extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new LinearView(this));
    }

    @Override
    public void initView() {

    }
}
