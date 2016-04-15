package com.edu.android_smartbj.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Ming on 2016/1/19.
 */
public class NoScrollViewPager extends ViewPager {


    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }


    /**
     * 表示事件是否是否被拦截，返回 false 表示不拦截  可以使嵌套在内部的 viewPager 响应左右滑的效果
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    /**
     * 重写 onTouchEvent 事件，什么也不用做
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
