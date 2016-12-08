package com.yiyekeji.mytestapp.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by Administrator on 2016/10/23.
 */
public   class BaseActivity extends AutoLayoutActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private Toast shortToast, longToast ;

    protected void showShortToast(CharSequence text) {
        if(shortToast==null){
            shortToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        }else{
            shortToast.setText(text);
        }
        shortToast.show();
    }

    public void startActivity(Class<?extends BaseActivity> activity){
        Intent intent =new Intent(this,activity);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }



}
