package com.robert.news.view;

import android.support.v4.view.ViewPager;
import android.view.View;

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
public class RoatePageTransformer implements ViewPager.PageTransformer {

    private final float MIN_ROTATION = 30f;

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        if (position < -1) {

            page.setRotation(0);

        } else if (position <= 0) {

            page.setPivotX(pageWidth / 2);
            page.setPivotY(page.getMeasuredHeight());
            page.setRotation(MIN_ROTATION * position);

        } else if (position <= 1) {

            page.setPivotX(pageWidth / 2);
            page.setPivotY(page.getMeasuredHeight() / 2);
            page.setRotation(MIN_ROTATION * position);

        } else {

            page.setRotation(0);

        }
    }
}
