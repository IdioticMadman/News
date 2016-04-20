package com.robert.news.fragment;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.robert.news.R;
import com.robert.news.activity.MainActivity;
import com.robert.news.adapter.MyBaseAdapter;
import com.robert.news.home.NewsCenterPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MenuFragment extends BaseFragment {

    @Bind(R.id.lv_news_center)
    ListView lv_news_center;
    @Bind(R.id.lv_smart_service)
    ListView lv_smart_service;
    @Bind(R.id.lv_gov_affairs)
    ListView lv_gov_affairs;
    private MenuAdapter menuAdapter;
    private MenuAdapter serviceAdapter;
    private MenuAdapter govAffairsAdapter;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        lv_gov_affairs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                govAffairsAdapter.clickItem(position);
                slidingMenu.toggle();
            }
        });
        lv_news_center.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuAdapter.clickItem(position);
                ((NewsCenterPage) ((MainActivity) mContext).getHomeFragment().getBasePage(HomeFragment.NEWS_CENTER)).switchFragment(position);
                slidingMenu.toggle();
            }
        });
        lv_smart_service.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                serviceAdapter.clickItem(position);
                slidingMenu.toggle();
            }
        });
    }

    List<String> mMenuLists = new ArrayList<>();

    /**
     * 根据ViewPager的position设置menu的值
     * @param position ViewPager中该view的位置
     * @param titles ViewPager对应该view的侧滑菜单的值
     */
    public void initMenu(int position, List<String> titles) {
        assert mMenuLists != null;
        mMenuLists.clear();
        switch (position) {
            case HomeFragment.NEWS_CENTER:
                mMenuLists.addAll(titles);
                setMenuType(position);
                break;
            case HomeFragment.SMART_SERVICE:
                mMenuLists.addAll(titles);
                setMenuType(position);
                break;
            case HomeFragment.GOV_AFFAIRS:
                mMenuLists.addAll(titles);
                setMenuType(position);
                break;
        }
    }

    /**
     * 设置不同的ViewPager展现不同的侧滑菜单
     * @param menuType 菜单类型
     */
    public void setMenuType(int menuType) {
        lv_gov_affairs.setVisibility(View.INVISIBLE);
        lv_news_center.setVisibility(View.INVISIBLE);
        lv_smart_service.setVisibility(View.INVISIBLE);
        switch (menuType) {
            case HomeFragment.NEWS_CENTER:
                lv_news_center.setVisibility(View.VISIBLE);
                lv_gov_affairs.setVisibility(View.INVISIBLE);
                lv_smart_service.setVisibility(View.INVISIBLE);
                if (menuAdapter == null) {
                    menuAdapter = new MenuAdapter(mMenuLists,mContext);
                    lv_news_center.setAdapter(menuAdapter);
                } else {
                    menuAdapter.notifyDataSetChanged();
                }
                break;
            case HomeFragment.SMART_SERVICE:
                lv_smart_service.setVisibility(View.VISIBLE);
                lv_news_center.setVisibility(View.INVISIBLE);
                lv_news_center.setVisibility(View.INVISIBLE);
                if (serviceAdapter == null) {
                    serviceAdapter = new MenuAdapter(mMenuLists,mContext);
                    lv_smart_service.setAdapter(serviceAdapter);
                } else {
                    serviceAdapter.notifyDataSetChanged();
                }
                break;
            case HomeFragment.GOV_AFFAIRS:
                lv_gov_affairs.setVisibility(View.VISIBLE);
                lv_news_center.setVisibility(View.INVISIBLE);
                lv_news_center.setVisibility(View.INVISIBLE);
                if (govAffairsAdapter == null) {
                    govAffairsAdapter = new MenuAdapter(mMenuLists,mContext);
                    lv_gov_affairs.setAdapter(govAffairsAdapter);
                } else {
                    govAffairsAdapter.notifyDataSetChanged();
                }
                break;
        }

    }

    /**
     * 侧滑菜单的ListView的BaseAdapter
     */
    private class MenuAdapter extends MyBaseAdapter<String> {

        private int mPosition;

        public MenuAdapter(List<String> mDatas,Context context) {
            super(mDatas,context);
        }

        public void clickItem(int position) {
            this.mPosition = position;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                view = View.inflate(mContext, R.layout.menu_list_item, null);
                viewHolder.iv_left_menu = (ImageView) view.findViewById(R.id.iv_left_menu);
                viewHolder.tv_left_menu = (TextView) view.findViewById(R.id.tv_left_menu);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            if (mPosition == position) {
                viewHolder.iv_left_menu.setImageResource(R.mipmap.menu_arr_select);
                view.setBackgroundResource(R.drawable.menu_item_bg_select);
                viewHolder.tv_left_menu.setTextColor(Color.RED);
            } else {
                viewHolder.iv_left_menu.setImageResource(R.mipmap.menu_arr_normal);
                view.setBackgroundResource(R.drawable.transparent);
                viewHolder.tv_left_menu.setTextColor(Color.WHITE);
            }

            viewHolder.tv_left_menu.setText(mMenuLists.get(position));
            return view;
        }


    }

    static class ViewHolder {
        ImageView iv_left_menu;
        TextView tv_left_menu;
    }
}
