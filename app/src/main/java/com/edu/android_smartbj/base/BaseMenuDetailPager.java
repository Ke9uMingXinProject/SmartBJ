package com.edu.android_smartbj.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by Ming on 2016/1/19.
 * email 1715944993@qq.com
 */
public abstract class BaseMenuDetailPager {

    public Activity mActivity;

    // 根布局对象
    public View mRootView;
    public BaseMenuDetailPager(Activity activity) {
        this.mActivity = activity;
        this.mRootView = initViews();
    }

    /**
     * 初始化界面
     */
    public abstract View initViews();

    /**
     * 初始化数据
     */
    public void initData() {

    }
}
