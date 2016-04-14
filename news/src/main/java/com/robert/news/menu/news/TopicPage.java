package com.robert.news.menu.news;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.robert.news.home.BasePage;


public class TopicPage extends BasePage{
    public TopicPage(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(mContext);
        textView.setText("互动界面");
        return textView;
    }

    @Override
    public void initData() {

    }
}
