package com.example.android.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.example.android.client.util.DialogUtil;
import com.example.android.client.util.HttpUtil;
import com.example.android.myapplication.databinding.ActivityLoginBinding;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    private EditText editPhoneNum;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);

        ActivityLoginBinding loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        editPhoneNum = loginBinding.editPhoneNum;

        editPassword = loginBinding.editPassword;

        loginBinding.skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()) {
                    if (loginPro()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else
                        DialogUtil.showDialog(LoginActivity.this, "手机号码或者密码填写错误，请重试", false);
                }
            }


        });


        loginBinding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private boolean validate() {
        String userName = editPhoneNum.getText().toString();
        String passWord = editPassword.getText().toString();
        if (userName.equals("")) {
            DialogUtil.showDialog(this, "手机号码是必填项", false);
            return false;
        }
        if (passWord.equals("")) {
            DialogUtil.showDialog(this, "密码是必填项", false);
            return false;
        }
        return true;
    }


    private boolean loginPro() {
        String userName = editPhoneNum.getText().toString();
        String passWord = editPassword.getText().toString();
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject = query(userName, passWord);
            if (jsonObject.getInt("userId") >= 0) {
                return true;
            }

        } catch (Exception e) {
            DialogUtil.showDialog(this, "服务器响应异常，请稍后再试", false);
            e.printStackTrace();
        }
        // TODO: 2017/6/20
        return false;
    }


    private JSONObject query(String userName, String passWord) {
        Map<String, String> map = new HashMap<>();
        map.put("phoneNumber", userName);
        map.put("passWord", passWord);
        String url = HttpUtil.BASE_URL + "login";
        try {
            return new JSONObject(HttpUtil.postRequest(url, map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            isExit.setTitle("系统提示");
            // 设置对话框消息
            isExit.setMessage("确定要退出吗");
            // 添加选择按钮并注册监听
            isExit.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            isExit.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            // 显示对话框
            isExit.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
