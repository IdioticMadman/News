package com.robert.news.home;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.robert.news.activity.MainActivity;
import com.robert.news.bean.NewsCenter;
import com.robert.news.fragment.HomeFragment;
import com.robert.news.utils.HMAPI;
import com.robert.news.utils.SharedPrefenceUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
public class NewsCenterPage extends BasePage {


    public NewsCenterPage(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView textview = new TextView(mContext);
        textview.setText(NewsCenterPage.class.getSimpleName());
        return textview;
    }

    @Override
    public void initData() {
        System.out.println("进入新闻中心");
        String tempString = SharedPrefenceUtils.getString(mContext, HMAPI.NEW_CENTER, "");
        if (!TextUtils.isEmpty(tempString)){
            processData(tempString);
        }
        getData();
    }

    private void getData() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, HMAPI.NEW_CENTER, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SharedPrefenceUtils.putString(mContext,HMAPI.NEW_CENTER,response.toString());
                processData(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        System.out.println("添加到队列中");
        requestQueue.add(jsonObjectRequest);
    }

    private List<String> mListNews = new ArrayList<>();

    private void processData(String jsonObject) {
        Gson gson = new Gson();
        //isLoaded = true;
        if (mListNews!=null){
            mListNews.clear();
        }
        NewsCenter newsCenter = gson.fromJson(jsonObject, NewsCenter.class);
        for (NewsCenter.DataBean data : newsCenter.getData()) {
            mListNews.add(data.getTitle());
            System.out.println("*********" + data.getTitle());
        }
        ((MainActivity)mContext).getMenuFragment().initMenu(HomeFragment.NEWS_CENTER,mListNews);
    }


}
