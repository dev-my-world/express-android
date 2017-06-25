package com.example.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by zhang on 2017/6/25.
 */

public class Fragment2 extends Fragment implements View.OnClickListener {

    private Button commit_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.task_fragment, container, false);
        commit_btn = (Button) rootView.findViewById(R.id.btn_commit);
        commit_btn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), ReleaseExpressInfo.class);
        startActivity(intent);
    }
}
