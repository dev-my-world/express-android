package com.example.android.myapplication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.android.client.util.HttpUtil;
import com.example.android.myapplication.databinding.ActivityExpressCenterBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressCenterActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private String[] stations = {"菜鸟驿站", "I DO()", "联通复印店", "地瓜坊", "青春修炼营"};

    private int[] bgp = {R.drawable.ems_logo, R.drawable.shunfeng_logo, R.drawable.shentong_logo};
    private ImageView banner;
    private Handler handler;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        ActivityExpressCenterBinding expressCenterBinding = DataBindingUtil.setContentView(this, R.layout.activity_express_center);
        ListView listView = expressCenterBinding.listItem;

        List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
        try {
            List<String> list = stationExpressCountList();

            Log.i("tag", list.get(0));

            for (int i = 0; i < 5; i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("station", stations[i]);
                if (list.get(i) == null) {
                    map.put("count", 0);
                } else
                    map.put("count", list.get(i));
                itemList.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, itemList, R.layout.express_station_item_list, new String[]{"station", "count"},
                new int[]{R.id.station, R.id.package_count});

        listView.setAdapter(simpleAdapter);
        banner = expressCenterBinding.banner;
        listView.setOnItemClickListener(this);
        handler = new Handler();

        handler.postAtTime(new Runnable() {
            @Override
            public void run() {
                banner.setImageResource(bgp[(int) Math.floor(Math.random() * 3)]);
            }
        }, 1000);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(ExpressCenterActivity.this, StationExpressDetailActivity.class);
        Toast.makeText(this, "position:" + position, Toast.LENGTH_SHORT).show();
        intent.putExtra("station", (position + 1) + "");
        startActivity(intent);
    }


    private List<String> stationExpressCountList() throws Exception {
        List<String> stationExpressCountList = new ArrayList<>();
        int[] count = new int[5];
        String url = HttpUtil.BASE_URL + "allExpress";
        JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            String station = o.getString("station");
            if ("1".equals(station)) {
                count[0] += 1;
            } else if ("2".equals(station)) {
                count[1] += 1;
            } else if ("3".equals(station)) {
                count[2] += 1;
            } else if ("4".equals(station)) {
                count[3] += 1;
            } else
                count[4] += 1;
        }

        for (int i : count
                ) {
            stationExpressCountList.add("" + i);
        }

        return stationExpressCountList;
    }


}


