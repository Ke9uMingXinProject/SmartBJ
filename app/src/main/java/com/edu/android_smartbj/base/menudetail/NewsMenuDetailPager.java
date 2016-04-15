package com.edu.android_smartbj.base.menudetail;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.edu.android_smartbj.MainActivity;
import com.edu.android_smartbj.R;
import com.edu.android_smartbj.base.BaseMenuDetailPager;
import com.edu.android_smartbj.base.TabDetailPager;
import com.edu.android_smartbj.domain.NewsData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Ming on 2016/1/19.
 * email 1715944993@qq.com
 * 菜单详情页 --- 新闻
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener {


    private static final String TAG = NewsMenuDetailPager.class.getSimpleName();

    @ViewInject(R.id.vp_menu_detail)
    private ViewPager mViewPager;
    private ArrayList<NewsData.NewsTabData> mNewsTabData;// 页签网络数据

    private ArrayList<TabDetailPager> mPagerList;
    @ViewInject(R.id.indicator)
    private TabPageIndicator indicator;

    public NewsMenuDetailPager(Activity activity, ArrayList<NewsData.NewsTabData> mNewsTabData) {
        super(activity);
        this.mNewsTabData = mNewsTabData;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
        // 用注解
        // mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);
        x.view().inject(this,view);
        // 初始化自定义控件 TabPageIndicator  用注解
        // indicator = (TabPageIndicator) view.findViewById(R.id.indicator);

        // 方案一 ：自定义 viewPagers  HorizontalViewPager 在布局文件中引入  HorizontalViewPager

        // 方案二 ：解决 sliding menu  和 viewPager 的事件冲突
 /*       mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();

                int width = v.getWidth();
                if (x / width > 0.1) {
                    mViewPager.requestDisallowInterceptTouchEvent(true);
                } else {
                    // 为了补全判断
                    mViewPager.requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });*/
        // 注意:当viewpager和Indicator绑定时, 调用 setOnPageChangeListener(this) 时 方法过时
        // mViewPager.setOnPageChangeListener(this);
        // 滑动监听需要设置给 indicator 而不是viewpager
        indicator.setOnPageChangeListener(this);

        // 如果用 addOnPageChangeListener 可以不使用  indicator 的监听
        // mViewPager.addOnPageChangeListener(this);


        return view;
    }

    // 跳转下一个页面
    @Event( value = R.id.btn_next,type = View.OnClickListener.class)
    private void nextPage(View view) {
        Log.d(TAG, "nextPage: 点击进来了");
        int currentItem = mViewPager.getCurrentItem();
        mViewPager.setCurrentItem(++currentItem);
    }

    @Override
    public void initData() {
        mPagerList = new ArrayList<>();
        for (int i = 0; i < mNewsTabData.size(); i++) {
            TabDetailPager tabDetailPager = new TabDetailPager(mActivity,mNewsTabData.get(i));
            mPagerList.add(tabDetailPager);
        }
        mViewPager.setAdapter(new MenuDetailAdapter());
        // 必须要  mViewPager 设置 adapter 后才能调用这个
        indicator.setViewPager(mViewPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        Log.d(TAG, "onPageSelected: 进来了");

        // 获取 mainActivity
        MainActivity mainUi = (MainActivity) mActivity;
        // 获取 slidingmenu
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        // 只有在第一个页面(北京), 侧边栏才允许出来
        if (position==0){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



    class MenuDetailAdapter extends PagerAdapter {

        private static final String TAG = "MenuDetailAdapter";

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            TabDetailPager pager = mPagerList.get(position);
            container.addView(pager.mRootView);
            pager.initData();
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        /**
         * 显示 页签 viewpagerIndicator 的页签显示
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            Log.d(TAG, "getPageTitle: "+mNewsTabData.get(position).title);
            return mNewsTabData.get(position).title;
        }
    }
}
