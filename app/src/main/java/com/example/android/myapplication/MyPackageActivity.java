package com.example.android.myapplication;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.myapplication.databinding.MyPackageInfoBinding;

public class MyPackageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyPackageInfoBinding myPackageInfoBinding = DataBindingUtil.setContentView(this, R.layout.my_package_info);

    }
}
