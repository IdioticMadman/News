package com.robert.news.home;

import android.content.Context;
import android.view.View;

import com.android.volley.RequestQueue;
import com.robert.news.NewsApp;
import com.robert.news.activity.MainActivity;
import com.robert.slidingmenulib.SlidingMenu;

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
 * <p>
 * ━━━━━━感觉萌萌哒━━━━━━
 * Created by robert on 2016/4/10.
 */
public abstract class BasePage {


    public Context mContext;
    public RequestQueue requestQueue;
    public View view;
    public SlidingMenu slidingMenu;

    public boolean isLoaded = false;

    public BasePage(Context context){
        this.mContext = context;
        slidingMenu = ((MainActivity) mContext).getSlidingMenu();
        requestQueue = NewsApp.getRequestQueue();
        view = initView();
    }

    public View getRootView(){
        return view;
    }

    public abstract View initView();

    public abstract void initData();
}
