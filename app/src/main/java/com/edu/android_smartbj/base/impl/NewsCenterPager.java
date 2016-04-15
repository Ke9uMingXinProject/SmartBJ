package com.edu.android_smartbj.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.android_smartbj.MainActivity;
import com.edu.android_smartbj.base.BaseMenuDetailPager;
import com.edu.android_smartbj.base.BasePager;
import com.edu.android_smartbj.base.menudetail.InteractMenuDetailPager;
import com.edu.android_smartbj.base.menudetail.NewsMenuDetailPager;
import com.edu.android_smartbj.base.menudetail.PhotoMenuDetailPager;
import com.edu.android_smartbj.base.menudetail.TopicMenuDetailPager;
import com.edu.android_smartbj.domain.NewsData;
import com.edu.android_smartbj.fragments.LeftMenuFragment;
import com.edu.android_smartbj.global.GlobalConstants;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 新闻中心
 * <p/>
 * Created by Ming on 2016/1/19.
 */
public class NewsCenterPager extends BasePager {
    private static final String TAG = "NewsCenterPager";

    // 4个菜单详情页的集合
    private ArrayList<BaseMenuDetailPager> mPagers;
    private NewsData mNewsData;

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        Log.d(TAG, "初始化新闻中心数据....");

        tvTitle.setText("新闻");
        setSlidingMenuEnable(true);// 打开侧边栏
        getDataFromServer();
    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {
        RequestParams params = new RequestParams(GlobalConstants.CATEGORIES_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {

            // 成功 在主线程中执行
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "onSuccess: xUtils3" + result + "***x.app()***" + x.app());
                parseData(result);
            }

            // 失败
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "onError: " + ex.getMessage());
            }

            // 取消
            @Override
            public void onCancelled(CancelledException cex) {
                Log.d(TAG, "cancelled: ");
            }

            @Override
            public void onFinished() {

            }
        });
    }


    /**
     * 解析网络数据
     *
     * @param result
     */
    protected void parseData(String result) {
        Gson gson = new Gson();
        mNewsData = gson.fromJson(result, NewsData.class);
        Log.d(TAG, "解析结果:" + mNewsData);

        // 刷新测边栏的数据
        MainActivity mainUi = (MainActivity) mActivity;
        LeftMenuFragment leftMenuFragment = mainUi.getLeftMenuFragment();
        leftMenuFragment.setMenuData(mNewsData);

        // 准备4个菜单详情页
        mPagers = new ArrayList<>();
        mPagers.add(new NewsMenuDetailPager(mActivity,mNewsData.data.get(0).children));
        mPagers.add(new TopicMenuDetailPager(mActivity));
        mPagers.add(new PhotoMenuDetailPager(mActivity));
        mPagers.add(new InteractMenuDetailPager(mActivity));

        // 设置菜单详情页-新闻为默认当前页
        setCurrentMenuDetailPager(0);
    }


    /**
     * 设置当前菜单详情页
     */
    public void setCurrentMenuDetailPager(int position) {
        // 获取当前要显示的菜单详情页
        BaseMenuDetailPager pager = mPagers.get(position);
        // 清除之前的布局
        flContent.removeAllViews();
        // 将菜单详情页的布局设置给帧布局
        flContent.addView(pager.mRootView);
        // 设置当前页的标题
		NewsData.NewsMenuData menuData = mNewsData.data.get(position);
        tvTitle.setText(menuData.title);
        // 初始化当前页面的数据
        pager.initData();
    }
}
