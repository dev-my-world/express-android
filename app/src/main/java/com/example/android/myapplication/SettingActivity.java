package com.example.android.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by zhang on 2017/7/3.
 */

public class SettingActivity extends AppCompatActivity {

    private TextView mTvTitle;
    private TextView mTvMessage;
    private ImageButton mBtnBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);


        initView();
        initEvent();

    }

    private void initEvent() {

        mTvTitle.setText("设置");
        mTvMessage.setVisibility(View.INVISIBLE);

        mBtnBack.setVisibility(View.VISIBLE);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        mTvMessage = (TextView) findViewById(R.id.message);
        mTvTitle = (TextView) findViewById(R.id.title);
        mBtnBack = (ImageButton) findViewById(R.id.back_btn);
    }
}
