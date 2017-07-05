package com.example.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.client.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MyExpressActivity extends AppCompatActivity {


    private ListView mExpressListView;
    private SimpleAdapter mSimpleAdapter;
    private int uid;
    private int eid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_express);


        TextView title = (TextView) findViewById(R.id.title);
        title.setText("我的快件");

        ImageButton back_btn = (ImageButton) findViewById(R.id.back_btn);

        back_btn.setVisibility(View.VISIBLE);

        uid = getIntent().getIntExtra("uid", -1);


        mExpressListView = (ListView) findViewById(R.id.my_express_list);


        mSimpleAdapter = new SimpleAdapter(this, getData(-1), R.layout.my_express_list_item, new String[]{"express", "message", "insertTime"},
                new int[]{R.id.tv_express_name, R.id.tv_message, R.id.tv_time});


        mExpressListView.setAdapter(mSimpleAdapter);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        mExpressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MyExpressDetailActivity.class);
                getData(i);
                intent.putExtra("eid", eid);
                startActivity(intent);
            }
        });
    }

    private List<Map<String, Object>> getData(int position) {
        List<Map<String, Object>> list = new ArrayList<>();
        String url = HttpUtil.BASE_URL + "userExpress";
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid + "");
        try {
            JSONArray jsonArray = new JSONArray(HttpUtil.postRequest(url, params));
            Log.i("data", jsonArray.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                Map<String, Object> map = new HashMap<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (i == position) {
                    eid = jsonObject.getInt("id");
                }
                String message = jsonObject.getString("message");
                Long insertTime = (Long) jsonObject.get("insertTime");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                String time = simpleDateFormat.format(insertTime);
                String express = jsonObject.getString("express");
                map.put("message", message);
                map.put("insertTime", time);
                map.put("express", express);
                list.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;

    }
}
