package com.example.android.client.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by zhang on 2017/6/19.
 */

public class DialogUtil {
    public static void showDialog(final Context ctx, String msg, boolean goHome) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setMessage(msg).setCancelable(false);

        if (goHome) {
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        } else

        {
            builder.setNegativeButton("确定", null);
        }

        builder.show();

    }

    public static void showDialog(Context ctx, View view) {
        new AlertDialog.Builder(ctx).setView(view).setCancelable(false).setPositiveButton("确定", null).create().show();
    }
}
