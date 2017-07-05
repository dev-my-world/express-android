package com.example.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.android.client.util.HttpUtil;
import com.example.android.client.util.StationNames;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhang on 2017/6/25.
 */

public class Fragment1 extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private String[] stations = {"菜鸟驿站", "I DO", "联通复印店", "地瓜坊", "青春修炼营"};


    private Button mMessageBtn;


    private LinearLayout stationLayout;
    private LinearLayout notCollectedLayout;
    private SimpleAdapter simpleAdapter;
    private ListView listView;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        stationLayout = (LinearLayout) rootView.findViewById(R.id.station_layout);
        notCollectedLayout = (LinearLayout) rootView.findViewById(R.id.not_collected_layout);

        stationLayout.setOnClickListener(this);
        notCollectedLayout.setOnClickListener(this);


        listView = (ListView) rootView.findViewById(R.id.list_item);

        simpleAdapter = new SimpleAdapter(getContext(), getData(), R.layout.express_station_item_list, new String[]{"station", "count"},
                new int[]{R.id.station, R.id.package_count});

        listView.setAdapter(simpleAdapter);

        mMessageBtn = (Button) rootView.findViewById(R.id.message);

        mMessageBtn.setVisibility(View.VISIBLE);

        mMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "暂时没有更多消息", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(this);
        return rootView;
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            String url = HttpUtil.BASE_URL + "stationExpressCount";
            String stationExpressCount = HttpUtil.getRequest(url);
            JSONArray jsonArray = new JSONArray(stationExpressCount);
            JSONObject jsonObject;
            for (int j = 0; j < jsonArray.length(); j++) {
                Map<String, Object> map = new HashMap<>();
                jsonObject = jsonArray.getJSONObject(j);
                map.put("count", jsonObject.getInt("count"));
                map.put("station", StationNames.getStationName(jsonObject.getInt("station")));
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(getContext(), StationExpressDetailActivity.class);
        intent.putExtra("station", position);
        intent.putExtra("userId", getActivity().getIntent().getIntExtra("userId", -1));
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        Toast toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        switch (view.getId()) {
            case R.id.station_layout:
                toast.setText("这里是站点");
                toast.show();
                break;

            case R.id.not_collected_layout:
                toast.setText("这里是未代收");
                toast.show();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        simpleAdapter = new SimpleAdapter(getContext(), getData(), R.layout.express_station_item_list, new String[]{"station", "count"},
                new int[]{R.id.station, R.id.package_count});

        listView.setAdapter(simpleAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        simpleAdapter = new SimpleAdapter(getContext(), getData(), R.layout.express_station_item_list, new String[]{"station", "count"},
                new int[]{R.id.station, R.id.package_count});

        listView.setAdapter(simpleAdapter);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            simpleAdapter = new SimpleAdapter(getContext(), getData(), R.layout.express_station_item_list, new String[]{"station", "count"},
                    new int[]{R.id.station, R.id.package_count});

            listView.setAdapter(simpleAdapter);
        }
        return super.onCreateAnimation(transit, enter, nextAnim);

    }
}
