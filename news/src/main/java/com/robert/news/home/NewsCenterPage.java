package com.robert.news.home;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.robert.news.R;
import com.robert.news.activity.MainActivity;
import com.robert.news.bean.NewsCenter;
import com.robert.news.fragment.HomeFragment;
import com.robert.news.menu.news.NewsItemPage;
import com.robert.news.menu.news.NewsPage;
import com.robert.news.menu.news.PicturePage;
import com.robert.news.menu.news.TopicPage;
import com.robert.news.utils.HMAPI;
import com.robert.news.utils.SharedPrefenceUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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
public class NewsCenterPage extends BasePage {

    public final static int NEWS = 0;
    public final static int NEWS_ITEM = 1;
    public final static int PICTURE = 2;
    public final static int TOPIC = 3;

    @Bind(R.id.fl_news_center)
    FrameLayout mFlNewsCenter;

    private List<String> mListNews = new ArrayList<>();
    private List<BasePage> mNewsPages = new ArrayList<>();

    public NewsCenterPage(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.news_center_frame, null);
        ButterKnife.bind(this, view);
        initTitles(view);
        return view;
    }

    @Override
    public void initData() {
        System.out.println("进入新闻中心");

        String tempString = SharedPrefenceUtils.getString(mContext, HMAPI.NEW_CENTER, "");
        if (!TextUtils.isEmpty(tempString)) {
            processData(tempString);
        }
        getData();
        if (mNewsPages != null) {
            mNewsPages.clear();
        }
        mNewsPages.add(new NewsPage(mContext));
        mNewsPages.add(new NewsItemPage(mContext));
        mNewsPages.add(new PicturePage(mContext));
        mNewsPages.add(new TopicPage(mContext));
        switchFragment(NEWS);
    }

    private void getData() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, HMAPI.NEW_CENTER, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SharedPrefenceUtils.putString(mContext, HMAPI.NEW_CENTER, response.toString());
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


    private void processData(String jsonObject) {
        Gson gson = new Gson();
        //isLoaded = true;
        if (mListNews != null) {
            mListNews.clear();
        }
        NewsCenter newsCenter = gson.fromJson(jsonObject, NewsCenter.class);
        for (NewsCenter.DataBean data : newsCenter.getData()) {
            mListNews.add(data.getTitle());
            System.out.println("*********" + data.getTitle());
        }
        ((MainActivity) mContext).getMenuFragment().initMenu(HomeFragment.NEWS_CENTER, mListNews);
    }

    public void switchFragment(int position) {
        BasePage basePage = mNewsPages.get(position);
        switch (position) {
            case NEWS:
                mTvTitle.setText("新闻");
                break;
            case NEWS_ITEM:
                mTvTitle.setText("专题");
                break;
            case PICTURE:
                mTvTitle.setText("组图");
                break;
            case TOPIC:
                mTvTitle.setText("互动");
                break;
        }
        mFlNewsCenter.removeAllViews();
        mFlNewsCenter.addView(basePage.getRootView());

    }

}
