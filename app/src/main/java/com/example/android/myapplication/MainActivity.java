package com.example.android.myapplication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.android.client.util.DialogUtil;
import com.example.android.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Button collectShipments;
    private Button releaseInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        collectShipments = activityMainBinding.collectShipments;
        releaseInfo = activityMainBinding.releaseInfo;
        collectShipments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExpressCenterActivity.class);
                startActivity(intent);
            }
        });

        releaseInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReleaseInfoActivity.class);
                startActivity(intent);
            }
        });


        activityMainBinding.myPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyPackageActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("个人信息");
        menu.add("关于");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DialogUtil.showDialog(this, "menu", false);
        return super.onOptionsItemSelected(item);
    }
}
