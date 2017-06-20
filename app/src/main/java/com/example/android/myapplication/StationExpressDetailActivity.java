package com.example.android.myapplication;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.myapplication.databinding.ExpressItemBinding;

public class StationExpressDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExpressItemBinding expressItemBinding = DataBindingUtil.setContentView(this, R.layout.express_item);



    }
}
