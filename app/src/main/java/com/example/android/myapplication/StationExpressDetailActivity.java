package com.example.android.myapplication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.android.client.util.DialogUtil;
import com.example.android.client.util.ExpressJsonArrayResultUtil;
import com.example.android.client.util.StationNames;
import com.example.android.myapplication.databinding.ActivityStationExpressDetailBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationExpressDetailActivity extends AppCompatActivity {

    private List<Map<String, Object>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        ActivityStationExpressDetailBinding stationExpressDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_station_express_detail);
        ListView stationExpress = stationExpressDetailBinding.stationExpress;
        try {
            JSONArray allExpressInfo = ExpressJsonArrayResultUtil.getAllExpressInfo();
            Intent intent = getIntent();
            String stationIdx = intent.getStringExtra("station");
            Log.i("tag", stationIdx);
            String stationName = StationNames.getStationName(Integer.valueOf(stationIdx) - 1);
            setTitle(stationName);

            data = new ArrayList<Map<String, Object>>();
            int idx = 1;
            for (int i = 0; i < allExpressInfo.length(); i++) {
                JSONObject jsonObject = allExpressInfo.getJSONObject(i);
                if (jsonObject.getString("station").equals(stationIdx)) {
                    Map<String, Object> map = new HashMap<>();
                    String remark = jsonObject.getString("remark");
                    String address = jsonObject.getString("address");
                    String name = jsonObject.getString("name");
                    String express = jsonObject.getString("express");
                    String phone = jsonObject.getString("phone");
                    String message = jsonObject.getString("message");
                    map.put("index", idx++);
                    map.put("remark", remark);
                    map.put("address", address);
                    map.put("name", name);
                    map.put("express", express);
                    map.put("phone", phone);
                    map.put("message", message);
                    data.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.simple_express_item, new String[]{"index", "remark", "address"},
                new int[]{R.id.index, R.id.remark, R.id.address});
        stationExpress.setAdapter(simpleAdapter);

        stationExpress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.express_detail, null);
                Map<String, Object> map = data.get(position);
                String name = (String) map.get("name");
                String phone = (String) map.get("phone");
                String express = (String) map.get("express");
                String message = (String) map.get("message");
                String address = (String) map.get("address");
                TextView tv_name = (TextView) view1.findViewById(R.id.name);
                tv_name.setText(name);
                TextView tv_phone = (TextView) view1.findViewById(R.id.phone);
                tv_phone.setText(phone);
                TextView tv_express = (TextView) view1.findViewById(R.id.express);
                tv_express.setText(express);

                TextView tv_message = (TextView) view1.findViewById(R.id.message);
                tv_message.setText(message);

                TextView tv_address = (TextView) view1.findViewById(R.id.address);
                tv_address.setText(address);
                DialogUtil.showDialog(StationExpressDetailActivity.this, view1);

            }
        });
    }


}
