package com.example.android.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.client.util.HttpUtil;
import com.example.android.client.util.StationNames;

import org.json.JSONObject;

/**
 * Created by zhang on 2017/7/5.
 */

public class MyExpressDetailActivity extends AppCompatActivity implements OnClickListener {

    private int eid;

    private TextView tv_name;
    private TextView tv_express;
    private TextView tv_station;
    private TextView tv_phone;
    private TextView tv_message;

    private TextView tv_real_name;
    private TextView tv_real_phone;
    private TextView tv_address;

    private TextView tv_remark;
    private Button confirm_btn;

    private TextView title;

    private ImageButton back_btn;

    private String url = HttpUtil.BASE_URL + "expressId?eid=";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_express_detail);
        eid = getIntent().getIntExtra("eid", -1);
        Log.i("eid", eid + "");
        initView();
        initEvent();
        setViewContent();
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
    }

    private void setViewContent() {
        try {
            url += eid;
            JSONObject jsonObject = new JSONObject(HttpUtil.getRequest(url));
            String name = jsonObject.getString("name");
            String express = jsonObject.getString("express");
            String message = jsonObject.getString("message");
            String remark = jsonObject.getString("remark");
            String address = jsonObject.getString("address");
            int station = jsonObject.getInt("station");
            String phone = jsonObject.getString("phone");

            int weight = jsonObject.getInt("weight");
            tv_name.setText(name);
            tv_express.setText(express);
            tv_remark.setText(remark);
            tv_message.setText(message);
            tv_address.setText(address);
            tv_phone.setText(phone);
            tv_station.setText(StationNames.getStationName(station));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        tv_name = findViewById(R.id.tv_name);
        tv_express = findViewById(R.id.tv_express);
        tv_station = findViewById(R.id.tv_station);
        tv_phone = findViewById(R.id.tv_phone);
        tv_message = findViewById(R.id.tv_message_content);
        tv_real_name = findViewById(R.id.tv_real_name);
        tv_real_phone = findViewById(R.id.tv_real_phone);
        tv_address = findViewById(R.id.tv_address);
        tv_remark = findViewById(R.id.tv_remark);
        confirm_btn = findViewById(R.id.confirm_btn);

        back_btn = findViewById(R.id.back_btn);
        back_btn.setVisibility(View.VISIBLE);

        title = findViewById(R.id.title);
        title.setText("快件详情");


    }


    @Override
    public void onClick(View view) {
        finish();
    }
}
