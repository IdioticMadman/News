package com.robert.news.utils;

import android.content.Context;

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * <p/>
 * ━━━━━━感觉萌萌哒━━━━━━
 * Created by robert on 2016/4/10.
 */
public class DensityUtils {

    private static float density = -1F;
    private static int widthPixels = -1;
    private static int heightPixels = -1;

    private DensityUtils() {
    }

    public static float getDensity(Context ctx) {
        if (density <= 0F) {
            density = ctx.getResources().getDisplayMetrics().density;
        }
        return density;
    }

    public static int dip2px(Context ctx, float dpValue) {
        return (int) (dpValue * getDensity(ctx) + 0.5F);
    }

    public static int px2dip(Context ctx, float pxValue) {
        return (int) (pxValue / getDensity(ctx) + 0.5F);
    }

    public static int getScreenWidth(Context ctx) {
        if (widthPixels <= 0) {
            widthPixels = ctx.getResources().getDisplayMetrics().widthPixels;
        }
        return widthPixels;
    }


    public static int getScreenHeight(Context ctx) {
        if (heightPixels <= 0) {
            heightPixels = ctx.getResources().getDisplayMetrics().heightPixels;
        }
        return heightPixels;
    }

}
