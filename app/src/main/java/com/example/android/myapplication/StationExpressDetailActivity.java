package com.example.android.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.client.util.HttpUtil;
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

    private int userId;
    private SimpleAdapter simpleAdapter;
    private int stationIdx;
    private int eid;
    private ListView stationExpress;
    private String[] weights = {"小件(<1kg)", "中件(2-4kg)", "大件(4-7kg)", "超大件(>7kg)"};
    private TextView title;

    private Handler handler;


    private String station;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        ActivityStationExpressDetailBinding stationExpressDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_station_express_detail);
        stationExpress = stationExpressDetailBinding.stationExpress;


        title = (TextView) findViewById(R.id.title);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                title.setText(StationNames.getStationName(msg.what));
            }
        };

        userId = getIntent().getIntExtra("userId", -1);
        try {
            Intent intent = getIntent();
            stationIdx = intent.getIntExtra("station", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getData();
        simpleAdapter = new SimpleAdapter(this, data, R.layout.simple_express_item, new String[]{"express", "remark", "address"},
                new int[]{R.id.express_name, R.id.remark, R.id.address});
        stationExpress.setAdapter(simpleAdapter);

        stationExpress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (userId == -1) {
                    Toast.makeText(getApplicationContext(), "你尚未登录，请登录后再进行操作", Toast.LENGTH_SHORT).show();
                } else {
                    View expressDetail = LayoutInflater.from(getApplicationContext()).inflate(R.layout.express_detail, null);
                    Map<String, Object> map = data.get(position);
                    eid = (Integer) map.get("eid");
                    String name = (String) map.get("name");
                    String phone = (String) map.get("phone");
                    String express = (String) map.get("express");
                    String message = (String) map.get("message");
                    String address = (String) map.get("address");
                    String weight = (String) map.get("weight");


                    TextView tv_name = (TextView) expressDetail.findViewById(R.id.tv_name);
                    tv_name.setText(name);
                    TextView tv_phone = (TextView) expressDetail.findViewById(R.id.tv_phone);
                    tv_phone.setText(phone);
                    TextView tv_express = (TextView) expressDetail.findViewById(R.id.tv_express);
                    tv_express.setText(express);

                    TextView tv_message = (TextView) expressDetail.findViewById(R.id.tv_message);
                    tv_message.setText(message);

                    TextView tv_address = (TextView) expressDetail.findViewById(R.id.tv_address);
                    tv_address.setText(address);

                    TextView tv_weight = (TextView) expressDetail.findViewById(R.id.tv_weight);
                    tv_weight.setText(weight);

                    final AlertDialog.Builder builder = new AlertDialog.Builder(StationExpressDetailActivity.this);

                    final View confirmView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.confirm_layout, null);

                    final AlertDialog confirmDialog = builder.setView(confirmView).create();


                    Button confirm_btn = (Button) confirmView.findViewById(R.id.confirm_btn);
                    Button cancel_btn = (Button) confirmView.findViewById(R.id.cancel_btn);


                    final Dialog dialog = builder.setView(expressDetail).create();

                    confirmDialog.show();

                    confirm_btn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            confirmDialog.dismiss();
                            String url = HttpUtil.BASE_URL + "addTransaction";
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("uid", userId + "");
                            params.put("eid", eid + "");
                            try {
                                HttpUtil.postRequest(url, params);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            getData();
                            simpleAdapter = new SimpleAdapter(getApplicationContext(), data, R.layout.simple_express_item, new String[]{"express", "remark", "address"},
                                    new int[]{R.id.express_name, R.id.remark, R.id.address});
                            stationExpress.setAdapter(simpleAdapter);
                            dialog.show();
                        }
                    });


                    cancel_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            confirmDialog.dismiss();
                        }
                    });

                }


            }
        });
    }


    public void getData() {
        data = new ArrayList<Map<String, Object>>();
        String url = HttpUtil.BASE_URL + "stationExpress?station=" + stationIdx;

        handler.sendEmptyMessage(stationIdx);

        try {
            JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
            Log.i("dataqqq", jsonArray.toString() + stationIdx);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Map<String, Object> map = new HashMap<>();
                int eid = jsonObject.getInt("id");

                String remark = jsonObject.getString("remark");
                String address = jsonObject.getString("address");
                String name = jsonObject.getString("name");
                String express = jsonObject.getString("express");
                String phone = jsonObject.getString("phone");
                String message = jsonObject.getString("message");
                Integer weight = jsonObject.getInt("weight");
                String description = jsonObject.getString("description");
                map.put("eid", eid);
                map.put("remark", remark);
                map.put("address", address);
                map.put("name", name);
                map.put("express", express);
                map.put("phone", phone);
                map.put("message", message);
                map.put("weight", weights[weight]);
                map.put("description", description);
                data.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
