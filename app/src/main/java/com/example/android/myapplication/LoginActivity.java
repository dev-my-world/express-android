package com.example.android.myapplication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
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
            if (jsonObject.getInt("userId") > 0) {
                return true;
            }

        } catch (Exception e) {
            DialogUtil.showDialog(this, "服务器响应异常，请稍后再试", false);
            e.printStackTrace();
        }
        // TODO: 2017/6/20
        return true;
    }


    private JSONObject query(String userName, String passWord) {
        Map<String, String> map = new HashMap<>();
        map.put("user", userName);
        map.put("pass", passWord);
        String url = HttpUtil.BASE_URL + "login.jsp";
        try {
            return new JSONObject(HttpUtil.postRequest(url, map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
