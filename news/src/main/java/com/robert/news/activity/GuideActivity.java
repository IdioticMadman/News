package com.robert.news.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.robert.news.R;
import com.robert.news.utils.DensityUtils;
import com.robert.news.utils.SPrefUtils;
import com.robert.news.view.DepthPageViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 *
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
 *
 * ━━━━━━感觉萌萌哒━━━━━━
 * ------------------------------
 * Description：GuideActivity:引导页
 * Created by robert on 2016/4/13.
 */

public class GuideActivity extends Activity {

    @Bind(R.id.vp_guide)
    DepthPageViewPager vp_guide;
    @Bind(R.id.ll_point_group)
    LinearLayout ll_point_group;
    @Bind(R.id.red_point)
    View red_point;
    @Bind(R.id.btn_start)
    Button btn_start;
    //背景图片ID的集合
    private final int viewIDs[] = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    //引导页面背景图列表
    private List<ImageView> guideViews;
    //圆点之间的距离
    private int pointWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {

        guideViews = new ArrayList<>();
        for (int i = 0; i < viewIDs.length; i++) {
            //初始化ImageView，背景图片
            ImageView imageView = new ImageView(GuideActivity.this);
            imageView.setBackgroundResource(viewIDs[i]);
            guideViews.add(imageView);
            //初试化灰点点
            View view = new View(this);
            view.setBackgroundResource(R.drawable.shape_point_gray);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dip2px(GuideActivity.this,10), DensityUtils.dip2px(GuideActivity.this,10));
            if (i > 0) {
                params.leftMargin = DensityUtils.dip2px(GuideActivity.this,20);
            }
            view.setLayoutParams(params);
            ll_point_group.addView(view);

        }

        vp_guide.setAdapter(new GuideAdapter());
        //vp_guide.setPageTransformer(true,new ZoomOutPageTransformer());
        //vp_guide.setPageTransformer(true,new DepthPageTransformer());
        //vp_guide.setPageTransformer(true,new RoatePageTransformer());]
        vp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(red_point.getLayoutParams());
                params.leftMargin = (int) ((position + positionOffset) * pointWidth);
                red_point.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                //设置开始体验在最后一页出现
                if (position == viewIDs.length-1) {
                    btn_start.setVisibility(View.VISIBLE);
                } else {
                    btn_start.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //获取视图树，当layout结束进行监听
        ll_point_group.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //当layout结束时调用此方法
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                //System.out.println("layout结束");
                ll_point_group.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                pointWidth = ll_point_group.getChildAt(1).getLeft() - ll_point_group.getChildAt(0).getLeft();
                //System.out.println("圆点之间的距离为" + pointWidth);

            }
        });
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPrefUtils.putBoolean(GuideActivity.this, "is_first_open", true);
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewIDs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(guideViews.get(position));
            vp_guide.addChildView(position,guideViews.get(position));
            return guideViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            vp_guide.removeChildView(position);
        }
    }
}
