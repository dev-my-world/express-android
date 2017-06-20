package com.example.android.myapplication;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.myapplication.databinding.ActivityReleaseInfoBinding;

public class ReleaseInfoActivity extends AppCompatActivity{

    private String address;
    private String name;
    private String expressId;
    private String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityReleaseInfoBinding releaseInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_release_info);


        Spinner spinnerExpress = releaseInfoBinding.spinnerExpress;

        String[] expressStation = {"菜鸟驿站", "I Do", "联通复印店", "地瓜坊", "青春修炼营"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, expressStation);


        spinnerExpress.setAdapter(arrayAdapter);

        spinnerExpress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        releaseInfoBinding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(ReleaseInfoActivity.this, "添加完成", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
