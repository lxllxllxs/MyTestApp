package com.yiyekeji.mytestapp.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiyekeji.mytestapp.R;
import com.yiyekeji.mytestapp.ui.base.BaseActivity;
import com.yiyekeji.mytestapp.widget.GradualScrollview;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity implements GradualScrollview.MyScrollListener {

    @InjectView(R.id.iv_head)
    ImageView ivHead;
    @InjectView(R.id.scrollView)
    GradualScrollview scrollView;
    @InjectView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();
        initData();
    }

    private int ivHeight;

    private void initView() {
        ViewTreeObserver vto = ivHead.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ivHeight = ivHead.getHeight();
                scrollView.setMyScrollListener(MainActivity.this);
            }
        });
    }
    private void initData() {
    }


    @Override
    public void onGradualScrollViewChange(int l, int t, int oldl, int oldt) {
        if (t <= 0) {
            tvTitle.setBackgroundColor(Color.argb((int) 0, 227, 29, 26));//AGB由相关工具获得，或者美工提供
        } else if (t > 0 && t <= ivHeight) {
            float scale = (float) t / ivHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            tvTitle.setBackgroundColor(Color.argb((int) alpha, 227, 29, 26));
        } else {
            tvTitle.setBackgroundColor(Color.argb((int) 255, 227, 29, 26));
        }
    }
}
