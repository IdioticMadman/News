package com.robert.news.menu.news;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.robert.news.home.BasePage;

/**
 * Created by robert on 2016/4/14.
 */
public class SubjectPage extends BasePage{

    public SubjectPage(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(mContext);
        textView.setText("专题界面");
        return textView;
    }

    @Override
    public void initData() {

    }
}
