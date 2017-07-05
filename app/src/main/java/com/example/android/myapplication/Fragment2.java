package com.example.android.myapplication;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.client.util.DialogUtil;
import com.example.android.client.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhang on 2017/6/25.
 */

public class Fragment2 extends Fragment implements OnItemSelectedListener {


    private EditText edit_name;
    private EditText edit_phone;
    private EditText edit_express;
    private EditText edit_message;
    private EditText edit_address;
    private EditText edit_remark;
    private EditText edit_description;

    private Button btn_submit;
    private int station;
    private Spinner spinner_station;
    private Spinner spinner_weight;
    private int weight;
    private int userId;
    private String[] stations = {"菜鸟驿站", "I Do", "联通复印店", "地瓜坊", "青春修炼营"};
    private String[] weights = {"小件(<1kg)", "中件(2-4kg)", "大件(4-7kg)", "超大件(>7kg)"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        View rootView = inflater.inflate(R.layout.publish_collection_fragment, container, false);
        View message = rootView.findViewById(R.id.message);
        message.setVisibility(View.INVISIBLE);
        TextView title = (TextView) rootView.findViewById(R.id.title);
        title.setText("发布代收");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_dropdown_item, stations);

        ArrayAdapter<String> weightAdapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_dropdown_item, weights);


        spinner_weight = (Spinner) rootView.findViewById(R.id.weight_spinner);
        spinner_weight.setAdapter(weightAdapter);


        spinner_weight.setOnItemSelectedListener(this);

        userId = getActivity().getIntent().getIntExtra("userId", -1);

        edit_name = (EditText) rootView.findViewById(R.id.edit_name);
        edit_phone = (EditText) rootView.findViewById(R.id.edit_phone_num);
        edit_express = (EditText) rootView.findViewById(R.id.edit_express_name);
        edit_message = (EditText) rootView.findViewById(R.id.edit_message);
        edit_address = (EditText) rootView.findViewById(R.id.edit_address);
        edit_remark = (EditText) rootView.findViewById(R.id.edit_remark);
        edit_description = (EditText) rootView.findViewById(R.id.edit_description);
        spinner_station = (Spinner) rootView.findViewById(R.id.spinner_station);
        btn_submit = (Button) rootView.findViewById(R.id.submit);


        spinner_station.setAdapter(arrayAdapter);
        spinner_station.setOnItemSelectedListener(this);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (userId == -1) {
                    DialogUtil.showDialog(getContext(), "您目前尚未登录，请登录后进行发布快件操作", false);
                } else {
                    setRequestData();
                    DialogUtil.showDialog(getContext(), "发布成功，请稍后在主页中查看", false);
                }

            }


        });


        return rootView;
    }


    private void setRequestData() {
        {
            String url = HttpUtil.BASE_URL + "addExpress";
            String name = edit_name.getText().toString();
            String phone = edit_phone.getText().toString();
            String express = edit_express.getText().toString();
            String message = edit_message.getText().toString();
            String address = edit_address.getText().toString();
            String remark = edit_remark.getText().toString();
            //   String weight = this.weight;
            String description = edit_description.getText().toString();


            int station = this.station;
            Map<String, String> map = new HashMap<>();

            map.put("name", name);
            map.put("phone", phone);
            map.put("express", express);
            map.put("message", message);
            map.put("address", address);
            map.put("remark", remark);
            map.put("station", String.valueOf(station));
            map.put("description", description);
            map.put("weight", String.valueOf(weight));


            Log.i("weight+name", weight + "" + name + "," + phone);



            map.put("userId", String.valueOf(userId));


            try {
                HttpUtil.postRequest(url, map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.spinner_station)
            station = i;
        if (adapterView.getId() == R.id.weight_spinner) {
            weight = i + 1;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        station = 1;
        weight = 1;
    }
}
