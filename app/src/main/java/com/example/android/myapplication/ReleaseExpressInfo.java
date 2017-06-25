package com.example.android.myapplication;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.client.util.DialogUtil;
import com.example.android.client.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhang on 2017/6/25.
 */

public class ReleaseExpressInfo extends AppCompatActivity implements OnItemSelectedListener, View.OnClickListener {

    private EditText edit_name;
    private EditText edit_phone;
    private EditText edit_express;
    private EditText edit_message;
    private EditText edit_address;
    private EditText edit_remark;
    private Button btn_submit;
    private Map<String, String> map = new HashMap<>();
    private int station;
    private Spinner spinner_station;
    private String[] stations = {"菜鸟驿站", "I Do", "联通复印店", "地瓜坊", "青春修炼营"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_info);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_dropdown_item, stations);
        initView();
        spinner_station.setAdapter(arrayAdapter);
        spinner_station.setOnItemSelectedListener(this);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRequestData();
                DialogUtil.showDialog(ReleaseExpressInfo.this, "发布成功，请稍后在主页中查看", false);
            }
        });
    }

    private void initView() {
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_phone = (EditText) findViewById(R.id.edit_phone_num);
        edit_express = (EditText) findViewById(R.id.edit_express_name);
        edit_message = (EditText) findViewById(R.id.edit_message);
        edit_address = (EditText) findViewById(R.id.edit_address);
        edit_remark = (EditText) findViewById(R.id.edit_remark);
        spinner_station = (Spinner) findViewById(R.id.spinner_station);
        btn_submit = (Button) findViewById(R.id.submit);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        station = position+1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        station=1;
    }


    @Override
    public void onClick(View view) {

    }

    private void setRequestData() {
        String url = HttpUtil.BASE_URL + "addExpress";
        String name = edit_name.getText().toString();
        String phone = edit_phone.getText().toString();
        String express = edit_express.getText().toString();
        String message = edit_message.getText().toString();
        String address = edit_address.getText().toString();
        String remark = edit_remark.getText().toString();
        int station = this.station;
        map.put("name", name);
        map.put("phone", phone);
        map.put("express", express);
        map.put("message", message);
        map.put("address", address);
        map.put("remark", remark);
        map.put("station", String.valueOf(station));
        Log.i("station",station+"");
        try {
            HttpUtil.postRequest(url, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
