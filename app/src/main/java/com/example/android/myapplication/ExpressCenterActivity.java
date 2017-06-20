package com.example.android.myapplication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.android.myapplication.databinding.ActivityExpressCenterBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressCenterActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityExpressCenterBinding expressCenterBinding = DataBindingUtil.setContentView(this, R.layout.activity_express_center);
        ListView listView = expressCenterBinding.listView;
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, mapList(), R.layout.epress_station_item_list, new String[]{"10"}, new int[]{R.id.count});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(this);
    }

    public List<Map<String, String>> mapList()

    {
        List<Map<String, String>> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            Map<String, String> map = new HashMap<String, String>();
            map.put("count", "100");
            list.add(map);
        }

        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(ExpressCenterActivity.this, StationExpressDetailActivity.class);
        startActivity(intent);
        finish();
    }
}
