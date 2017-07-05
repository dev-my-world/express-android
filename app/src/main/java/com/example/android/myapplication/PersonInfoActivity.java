package com.example.android.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by zhang on 2017/7/2.
 */

public class PersonInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_infomation);
        View message = findViewById(R.id.message);
        message.setVisibility(View.INVISIBLE);
        TextView title = (TextView) findViewById(R.id.title);

        ImageButton back_btn = (ImageButton) findViewById(R.id.back_btn);
        back_btn.setVisibility(View.VISIBLE);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        title.setText("个人信息");


    }
}
