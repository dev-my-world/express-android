package com.example.android.myapplication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.client.util.DialogUtil;
import com.example.android.client.util.HttpUtil;
import com.example.android.myapplication.databinding.ActivityRegisterBinding;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private Button register;
    private String passWord;
    private String password2;
    private String phoneNum;
    private EditText editPhoneNum;
    private EditText editPassword;
    private EditText editPassword2;
    private EditText editName;
    private JSONObject jsonObject;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ActivityRegisterBinding registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        editPhoneNum = registerBinding.editPhoneNum;


        editPassword = registerBinding.editPassword;


        editPassword2 = registerBinding.editPassword2;


        register = registerBinding.register;

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    if (registerPro()) {
                        {
                            final Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(intent);
                                    finish();
                                }
                            }, 500);


                        }
                    }
                }
            }
        });


    }


    private boolean validate() {
        passWord = editPassword.getText().toString();
        password2 = editPassword2.getText().toString();
        phoneNum = editPhoneNum.getText().toString();
        if (password2.length() > 0 || password2.length() > 0) {
            if (!passWord.equals(password2)) {
                DialogUtil.showDialog(this, "两次密码输入不一致", false);
                return false;
            }

        }
        if (phoneNum.equals("")) {
            DialogUtil.showDialog(this, "手机号码不能为空", false);
            return false;
        }
        if (passWord.equals("") || password2.equals("")) {
            DialogUtil.showDialog(this, "密码不能为空", false);
            return false;
        }

        if (phoneNum.length() != 11) {
            DialogUtil.showDialog(this, "手机号码不正确", false);
            return false;
        }


        return true;
    }


    private boolean registerPro() {
        String phoneNumber = editPhoneNum.getText().toString();
        String passWord = editPassword.getText().toString();
        JSONObject jsonObject;
        try {
            jsonObject = query(phoneNumber, passWord);
            if (jsonObject.getString("register").equals("1")) {
                return true;
            }

        } catch (Exception e) {
            DialogUtil.showDialog(this, "服务器响应异常，请稍后再试", false);
            e.printStackTrace();
        }
        return false;
    }

    private JSONObject query(String phoneNumber, String password) {
        Map<String, String> map = new HashMap<>();
        String url = HttpUtil.BASE_URL + "register";
        map.put("phoneNumber", phoneNumber);
        map.put("passWord", password);
        try {
            return new JSONObject(HttpUtil.postRequest(url, map));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
