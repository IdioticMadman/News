package com.robert.news.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

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
 * Created by robert on 2016/4/7.
 */
public class DepthPageViewPager extends ViewPager {

    private Map<Integer, View> mViews = new HashMap<>();
    private final float MIN_SCALE = 0.75f;
    private View mLeftView;
    private View mRightView;

    public DepthPageViewPager(Context context) {
        super(context);
    }

    public DepthPageViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {



        mLeftView = mViews.get(position);
        mRightView = mViews.get(position + 1);

        startAnimation(mLeftView, mRightView, position, offset, offsetPixels);

        super.onPageScrolled(position, offset, offsetPixels);
    }

    private void startAnimation(View mLeftView, View mRightView, int position, float offset, int offsetPixels) {

        if (null != mRightView) {

//            System.out.println("position***************" + position);
//            System.out.println("offset***************" + offset);
//            System.out.println("offsetPixels***************" + offsetPixels);

            mRightView.setAlpha(offset);
            mRightView.setTranslationX(offsetPixels - getWidth());
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * Math.abs(offset);
            mRightView.setScaleX(scaleFactor);
            mRightView.setScaleY(scaleFactor);
        }
        if (null != mLeftView) {
            mLeftView.bringToFront();
        }
    }

    public void addChildView(int position, View view) {
        mViews.put(position, view);
    }

    public void removeChildView(int position) {
        mViews.remove(position);
    }
}
