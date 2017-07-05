package com.example.android.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by zhang on 2017/7/3.
 */

public class UserService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



}
