package com.robert.news.utils;

import android.app.Activity;
import android.widget.Toast;

public class ToastUilts {

    public static void showToast(final Activity ctx, final String text) {
        if ("main".equals(Thread.currentThread().getName())) {
            Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
        } else {
            ctx.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
