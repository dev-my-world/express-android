package com.example.android.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private Button mHomeBtn;
    private Button mTaskBtn;
    private Button mDiscoveryBtn;
    private Button mPersonalBtn;


    private Fragment mFragment1;
    private Fragment mFragment2;
    private Fragment mFragment3;
    private Fragment mFragment4;


    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        setSelect(0);
        initEvent();
    }

    private void initEvent() {
        mHomeBtn.setOnClickListener(this);
        mDiscoveryBtn.setOnClickListener(this);
        mTaskBtn.setOnClickListener(this);
        mPersonalBtn.setOnClickListener(this);

    }

    private void initView() {
        mHomeBtn = (Button) findViewById(R.id.home_btn);
        mTaskBtn = (Button) findViewById(R.id.task_btn);
        mDiscoveryBtn = (Button) findViewById(R.id.discovery_btn);
        mPersonalBtn = (Button) findViewById(R.id.personal_btn);
    }


    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (i) {
            case 0:
                if (mFragment1 == null) {
                    mFragment1 = new Fragment1();
                    transaction.add(R.id.id_content, mFragment1);
                } else {
                    transaction.show(mFragment1);
                }
                break;
            case 1:
                if (mFragment2 == null) {
                    mFragment2 = new Fragment2();
                    transaction.add(R.id.id_content, mFragment2);
                } else {
                    transaction.show(mFragment2);
                }
                break;
            case 2:
                if (mFragment3 == null) {
                    mFragment3 = new Fragment3();
                    transaction.add(R.id.id_content, mFragment3);
                } else {
                    transaction.show(mFragment3);
                }
                break;
            case 3:
                if (mFragment4 == null) {
                    mFragment4 = new Fragment4();
                    transaction.add(R.id.id_content, mFragment4);
                } else {
                    transaction.show(mFragment4);
                }
                break;

            default:
                break;
        }

        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mFragment1 != null) {
            transaction.hide(mFragment1);
        }
        if (mFragment2 != null) {
            transaction.hide(mFragment2);
        }
        if (mFragment3 != null) {
            transaction.hide(mFragment3);
        }
        if (mFragment4 != null) {
            transaction.hide(mFragment4);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_btn:
                setSelect(0);
                break;
            case R.id.task_btn:
                setSelect(1);
                break;
            case R.id.discovery_btn:
                setSelect(2);
                break;
            case R.id.personal_btn:
                setSelect(3);
                break;
        }
    }
}
