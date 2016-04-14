package com.robert.news.home;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.robert.news.NewsApp;
import com.robert.news.R;
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
 * <p/>
 * ━━━━━━感觉萌萌哒━━━━━━
 * Created by robert on 2016/4/10.
 */
public abstract class BasePage implements View.OnClickListener{

    public Context mContext;
    public RequestQueue requestQueue;
    public View view;
    public SlidingMenu slidingMenu;

    public boolean isLoaded = false;
    public TextView mTvTitle;

    public BasePage(Context context) {
        this.mContext = context;
        slidingMenu = ((MainActivity) mContext).getSlidingMenu();
        requestQueue = NewsApp.getRequestQueue();
        view = initView();
    }

    public void initTitles(View view) {
        if (view != null) {
            Button btn_left = (Button) view.findViewById(R.id.btn_left);
            btn_left.setVisibility(View.GONE);
            ImageButton imgbtn_left = (ImageButton) view.findViewById(R.id.imgbtn_left);
            imgbtn_left.setImageResource(R.mipmap.img_menu);
            imgbtn_left.setOnClickListener(this);
            mTvTitle = (TextView) view.findViewById(R.id.txt_title);
            ImageButton btn_right = (ImageButton) view.findViewById(R.id.btn_right);
            btn_right.setVisibility(View.GONE);
        }
    }

    public View getRootView() {
        return view;
    }

    public abstract View initView();

    public abstract void initData();

    @Override
    public void onClick(View v) {
        slidingMenu.toggle();
    }
}
