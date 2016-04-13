package com.robert.news.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.robert.news.R;
import com.robert.news.fragment.HomeFragment;
import com.robert.news.fragment.MenuFragment;
import com.robert.slidingmenulib.SlidingMenu;
import com.robert.slidingmenulib.app.SlidingFragmentActivity;

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
 * ------------------------------
 * Description：MainActivity:程序主界面
 * Created by robert on 2016/4/7.
 */

public class MainActivity extends SlidingFragmentActivity {

    private SlidingMenu slidingMenu;
    private MenuFragment menuFragment;
    private HomeFragment homeFragment;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBehindContentView(R.layout.activity_menu);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        slidingMenu = getSlidingMenu();

        slidingMenu.setMode(SlidingMenu.LEFT);

        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        slidingMenu.setBehindOffsetRes(R.dimen.sm_width);

        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);

        slidingMenu.setShadowDrawable(R.drawable.shadow);

        menuFragment = new MenuFragment();

        homeFragment = new HomeFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_menu, menuFragment, "menu")
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_content, homeFragment, "home")
                .commit();
    }

    public HomeFragment getHomeFragment() {
        homeFragment = (HomeFragment) getSupportFragmentManager()
                .findFragmentByTag("home");
        return homeFragment;
    }

    /**
     * 返回菜单
     *
     * @return
     */
    public MenuFragment getMenuFragment() {
        menuFragment = (MenuFragment) getSupportFragmentManager()
                .findFragmentByTag("menu");
        return menuFragment;
    }

}
