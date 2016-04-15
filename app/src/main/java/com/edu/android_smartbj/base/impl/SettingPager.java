package com.edu.android_smartbj.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.edu.android_smartbj.base.BasePager;


/**
 * 设置页面
 * 
 * Created by Ming on 2016/1/19.
 * 
 */
public class SettingPager extends BasePager {

	private static final String TAG = "SettingPager";

	public SettingPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		Log.d(TAG, "初始化设置服务数据....");

		tvTitle.setText("设置");
		btnMenu.setVisibility(View.GONE);// 隐藏菜单按钮
		setSlidingMenuEnable(false);// 关闭侧边栏

		TextView text = new TextView(mActivity);
		text.setText("设置");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);

		// 向FrameLayout中动态添加布局
		flContent.addView(text);
	}

}
