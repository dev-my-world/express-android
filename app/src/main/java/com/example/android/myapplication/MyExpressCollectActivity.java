package com.example.android.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.android.client.util.HttpUtil;
import com.example.android.client.util.StationNames;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by zhang on 2017/7/5.
 */

public class MyExpressCollectActivity extends AppCompatActivity {
    private int uid;
    private int[] to = {R.id.tv_name, R.id.tv_express,
            R.id.tv_station, R.id.tv_phone,
            R.id.tv_message, R.id.tv_real_name, R.id.tv_real_phone, R.id.tv_address, R.id.tv_remark, R.id.tv_time};
    private String[] from = {"name", "express", "station", "phone", "message", "name", "phone", "address", "remark", "time"};

    private ImageButton back_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_express_collect);
        uid = getIntent().getIntExtra("uid", -1);

        back_btn = (ImageButton) findViewById(R.id.back_btn);
        back_btn.setVisibility(View.VISIBLE);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView title = (TextView) findViewById(R.id.title);

        title.setText("代收信息");

        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), getData(), R.layout.my_collected_express_detail_list_item, from, to);
        ListView listView = (ListView) findViewById(R.id.express_item);


        listView.setAdapter(simpleAdapter);
    }


    private List<? extends Map<String, ?>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();
        String url = HttpUtil.BASE_URL + "collectedExpress?uid=" + uid;
        try {
            JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Map<String, Object> map = new HashMap<>();
                String name = jsonObject.getString("name");
                String express = jsonObject.getString("express");
                String message = jsonObject.getString("message");
                String remark = jsonObject.getString("remark");
                String address = jsonObject.getString("address");
                Integer station = jsonObject.getInt("station");
                String phone = jsonObject.getString("phone");
                int weight = jsonObject.getInt("weight");
                Long insertTime = jsonObject.getLong("insertTime");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                String time = simpleDateFormat.format(insertTime);
                map.put("name", name);
                map.put("express", express);
                map.put("message", message);
                map.put("remark", remark);
                map.put("address", address);
                map.put("station", StationNames.getStationName(station));
                map.put("phone", phone);
                map.put("weight", weight);
                map.put("time", time);
                list.add(map);
                Log.i("list", list.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
