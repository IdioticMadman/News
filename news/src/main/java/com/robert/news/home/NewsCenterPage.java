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
import com.robert.news.menu.news.NewsPage;
import com.robert.news.menu.news.PicturePage;
import com.robert.news.menu.news.SubjectPage;
import com.robert.news.menu.news.TopicPage;
import com.robert.news.utils.HMAPI;
import com.robert.news.utils.SPrefUtils;

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
    public final static int SUBJECT = 1;
    public final static int PICTURE = 2;
    public final static int TOPIC = 3;

    @Bind(R.id.fl_news_center)
    FrameLayout mFlNewsCenter;
    //新闻中心侧滑菜单数据
    private List<String> mListNews = new ArrayList<>();
    //新闻中心侧滑菜单，对应子项目的view集合
    private List<BasePage> mNewsPages = new ArrayList<>();
    private int index;

    public NewsCenterPage(Context context) {
        super(context);
    }

    public void onResume() {
        if (null != mNewsPages && mNewsPages.size() > 0) {
            mNewsPages.get(index).onResume();
        }
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.frame_news_center, null);
        ButterKnife.bind(this, view);
        initTitles(view);
        return view;
    }

    @Override
    public void initData() {
        System.out.println("进入新闻中心");

        String tempString = SPrefUtils.getString(mContext, HMAPI.NEW_CENTER, "");
        if (!TextUtils.isEmpty(tempString)) {
            processData(tempString);
        }
        getData();
    }

    private void getData() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, HMAPI.NEW_CENTER, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SPrefUtils.putString(mContext, HMAPI.NEW_CENTER, response.toString());
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
        if (mListNews != null) {
            mListNews.clear();
        }
        isLoaded = true;
        NewsCenter newsCenter = gson.fromJson(jsonObject, NewsCenter.class);
        for (NewsCenter.DataBean data : newsCenter.getData()) {
            mListNews.add(data.getTitle());
            //System.out.println("*********" + data.getTitle());
        }
        if (mNewsPages != null) {
            mNewsPages.clear();
        }
        mNewsPages.add(new NewsPage(mContext, newsCenter.getData().get(NEWS).getChildren()));
        mNewsPages.add(new SubjectPage(mContext));
        mNewsPages.add(new PicturePage(mContext));
        mNewsPages.add(new TopicPage(mContext));
        switchFragment(NEWS);
        ((MainActivity) mContext).getMenuFragment().initMenu(HomeFragment.NEWS_CENTER, mListNews);
    }

    public void switchFragment(int position) {
        BasePage basePage = mNewsPages.get(position);
        switch (position) {
            case NEWS:
                mTvTitle.setText("新闻");
                break;
            case SUBJECT:
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
        basePage.initData();
        index = position;
    }
}
