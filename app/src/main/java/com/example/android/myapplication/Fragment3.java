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

public class Fragment3 extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.discovery_fragment, container, false);
        View message = rootView.findViewById(R.id.message);
        message.setVisibility(View.INVISIBLE);
        TextView title = (TextView) rootView.findViewById(R.id.title);
        Button btn1 = (Button) rootView.findViewById(R.id.btn1);
        Button btn2 = (Button) rootView.findViewById(R.id.btn2);
        Button btn3 = (Button) rootView.findViewById(R.id.btn3);

        btn1.setOnClickListener(this);
        btn3.setOnClickListener(this);

        title.setText("发现");

        return rootView;


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = new Intent(getContext(), ActivityWebView1.class);
        switch (id) {
            case R.id.btn1:
                intent.putExtra("choose", 1);
                startActivity(intent);
                break;
            case R.id.btn3:
                intent.putExtra("choose", 3);
                startActivity(intent);
                break;
        }
        ;
    }
}
