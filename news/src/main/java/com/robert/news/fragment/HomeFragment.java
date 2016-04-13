package com.robert.news.fragment;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.robert.news.R;
import com.robert.news.home.BasePage;
import com.robert.news.home.FunctionPage;
import com.robert.news.home.GovAffairsPage;
import com.robert.news.home.NewsCenterPage;
import com.robert.news.home.SettingPage;
import com.robert.news.home.SmartServicePage;
import com.robert.news.view.CustomViewPager;
import com.robert.news.view.LazyViewPager;
import com.robert.slidingmenulib.SlidingMenu;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HomeFragment extends BaseFragment implements LazyViewPager.OnPageChangeListener{

    public static final int NEWS_CENTER = 1;
    public static final int SMART_SERVICE = 2;
    public static final int GOV_AFFAIRS = 3;
    public static final int FUNCTION = 0;
    public static final int SETTING = 4;
    @Bind(R.id.vp_home)
    CustomViewPager mHomeViewPager;
    @Bind(R.id.rg_home)
    RadioGroup mRadioGroupHome;
    private ArrayList<BasePage> views;
    private MenuFragment menuFragment;

    public HomeFragment() {

    }

    @Override
    public void initData() {
        views = new ArrayList<>();
        views.add(new FunctionPage(mContext));
        views.add(new NewsCenterPage(mContext));
        views.add(new SmartServicePage(mContext));
        views.add(new GovAffairsPage(mContext));
        views.add(new SettingPage(mContext));
        mHomeViewPager.setAdapter(new HomeAdapter());
        mHomeViewPager.setOnPageChangeListener(this);
        mRadioGroupHome.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                menuFragment = (MenuFragment) getActivity().getSupportFragmentManager().findFragmentByTag("menu");
                if (menuFragment == null) {
                    System.out.println("menuFragment为空");
                } else {
                    switch (checkedId) {
                        case R.id.rb_function:
                            mHomeViewPager.setCurrentItem(FUNCTION, false);
                            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                            break;
                        case R.id.rb_news_center:
                            mHomeViewPager.setCurrentItem(NEWS_CENTER, false);
                            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                            System.out.println("设置菜单类型");
                            menuFragment.setMenuType(NEWS_CENTER);
                            break;
                        case R.id.rb_smart_service:
                            mHomeViewPager.setCurrentItem(SMART_SERVICE, false);
                            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                            menuFragment.setMenuType(SMART_SERVICE);
                            break;
                        case R.id.rb_gov_affairs:
                            mHomeViewPager.setCurrentItem(GOV_AFFAIRS, false);
                            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                            menuFragment.setMenuType(GOV_AFFAIRS);
                            break;
                        case R.id.rb_setting:
                            mHomeViewPager.setCurrentItem(SETTING, false);
                            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                            break;
                    }
                }
            }
        });
        mRadioGroupHome.check(R.id.rb_function);
    }

    public BasePage getBasePage(int postion) {
        return views.get(postion);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        BasePage basePage = views.get(position);
       // if (!basePage.isLoaded){
            basePage.initData();
      //  }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class HomeAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position).getRootView());
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position).getRootView());
            return views.get(position).getRootView();
        }
    }
}
