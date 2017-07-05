package com.example.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by zhang on 2017/6/25.
 */

public class Fragment4 extends Fragment implements View.OnClickListener {


    private String name;
    private String phone;
    private TextView tv_name;
    private TextView tv_phone;
    private Button my_express_btn;
    private Button my_express_collect_btn;
    private int uid;

    private Button collected_btn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.personal_fragment, container, false);
        View message = rootView.findViewById(R.id.message);


        message.setVisibility(View.INVISIBLE);

        View forward = rootView.findViewById(R.id.forward);
        forward.setOnClickListener(this);

        name = getActivity().getIntent().getStringExtra("name");
        phone = getActivity().getIntent().getStringExtra("phone");
        uid = getActivity().getIntent().getIntExtra("userId", -1);

        tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        tv_phone = (TextView) rootView.findViewById(R.id.tv_phone);


        my_express_btn = (Button) rootView.findViewById(R.id.my_express_btn);

        my_express_collect_btn = (Button) rootView.findViewById(R.id.my_express_collect_btn);


        if (name != null && phone != null) {
            tv_phone.setText(phone);
            tv_name.setText(name);
        }

        TextView title = (TextView) rootView.findViewById(R.id.title);
        title.setText("个人");

        final View setting = rootView.findViewById(R.id.setting);
        setting.setVisibility(View.VISIBLE);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        my_express_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyExpressActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
        });


        my_express_collect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyExpressCollectActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
        });


        View layout = rootView.findViewById(R.id.linearLayout);

        layout.setOnClickListener(this);

        return rootView;

    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), PersonInfoActivity.class);
        startActivity(intent);
    }
}
