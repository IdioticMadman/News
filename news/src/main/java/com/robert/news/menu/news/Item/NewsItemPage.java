package com.robert.news.menu.news.Item;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.robert.news.NewsApp;
import com.robert.news.R;
import com.robert.news.adapter.MyBaseAdapter;
import com.robert.news.bean.NewsItem;
import com.robert.news.home.BasePage;
import com.robert.news.utils.GsonTools;
import com.robert.news.utils.HMAPI;
import com.robert.news.utils.SPrefUtils;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by robert on 2016/4/14.
 */
public class NewsItemPage extends BasePage {

    private String mUrl;
    @Bind(R.id.lv_news_center)
    ListView mLVNews;

    public NewsItemPage(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.view_news_center, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {

        isLoaded = true;
        String tempString = SPrefUtils.getString(mContext, HMAPI.NEW_CENTER + mUrl, "");
        if (!TextUtils.isEmpty(tempString)) {
            processData(tempString);
        }
        getData();
    }

    private void getData() {

        NewsApp.getRequestQueue().add(new JsonObjectRequest(Request.Method.GET, HMAPI.BASE_URL + mUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SPrefUtils.putString(mContext, HMAPI.NEW_CENTER + mUrl, response.toString());
                processData(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));

    }

    private void processData(String response) {
        NewsItem newsItem = GsonTools.changeGsonToBean(response, NewsItem.class);

        List<NewsItem.DataBean.NewsBean> newsBeans = newsItem.getData().getNews();

        mLVNews.setAdapter(new NewsItemAdapter(newsBeans, mContext));

    }

    class NewsItemAdapter extends MyBaseAdapter<NewsItem.DataBean.NewsBean> {

        public NewsItemAdapter(List<NewsItem.DataBean.NewsBean> newsBeans, Context context) {
            super(newsBeans, context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = View.inflate(mContext, R.layout.lvitem_news_center, null);
                viewHolder = new ViewHolder();
                viewHolder.iv_img = (ImageView) view.findViewById(R.id.iv_img);
                viewHolder.tv_text = (TextView) view.findViewById(R.id.tv_text);
                viewHolder.tv_comment_count = (TextView) view.findViewById(R.id.tv_comment_count);
                viewHolder.tv_pub_date = (TextView) view.findViewById(R.id.tv_pub_date);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.tv_text.setText(mDatas.get(position).getTitle());
            viewHolder.tv_pub_date.setText(mDatas.get(position).getPubdate());
            Glide.with(mContext)
                    .load(mDatas.get(position).getListimage())
                    .into(viewHolder.iv_img);
            return view;
        }
    }

    static class ViewHolder {
        ImageView iv_img;
        TextView tv_text;
        TextView tv_comment_count;
        TextView tv_pub_date;
    }
}
