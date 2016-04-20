package com.robert.news.menu.news;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.robert.news.R;
import com.robert.news.bean.NewsCenter;
import com.robert.news.home.BasePage;
import com.robert.news.menu.news.Item.NewsItemPage;
import com.robert.slidingmenulib.SlidingMenu;
import com.robert.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class NewsPage extends BasePage {

    private List<NewsCenter.DataBean.ChildrenBean> mChildrenBeans;
    private List<BasePage> basePages = new ArrayList<>();
    private int mCurrentItem;


    public NewsPage(Context context, List<NewsCenter.DataBean.ChildrenBean> childrenBeans) {
        super(context);
        this.mChildrenBeans = childrenBeans;
    }

    @Bind(R.id.indicator)
    TabPageIndicator mTabPageIndicator;
    @Bind(R.id.vp_news_center)
    ViewPager mVPNewsCenter;

    public void onResume(){
        if (mCurrentItem == 0){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.viewpager_news_center, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        //初始化新闻页面的子页面
        for (NewsCenter.DataBean.ChildrenBean child : mChildrenBeans) {
            basePages.add(new NewsItemPage(mContext, child.getUrl()));
        }
        mVPNewsCenter.setAdapter(new NewsPageAdapter());
        mTabPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentItem = position;
                //设置滑到最左侧时，侧滑菜单出现
                if (mCurrentItem == 0){
                    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                }else {
                    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }
                if (!isLoaded){
                    basePages.get(position).initData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //绑定
        mTabPageIndicator.setViewPager(mVPNewsCenter);
        mTabPageIndicator.setCurrentItem(mCurrentItem);
        //初始化第一页
        basePages.get(0).initData();
    }

    /**
     * NewsPage的ViewPager的是适配器
     */
    class NewsPageAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return mChildrenBeans.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return mChildrenBeans.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(basePages.get(position).getRootView());
            return basePages.get(position).getRootView();
        }
    }
}
