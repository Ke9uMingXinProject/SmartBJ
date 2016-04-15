package com.edu.android_smartbj.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.edu.android_smartbj.base.BasePager;


/**
 * 政务
 * 
 * Created by Ming on 2016/1/19.
 * 
 */
public class GovAffairsPager extends BasePager {

	private static final String TAG = "GovAffairsPager";

	public GovAffairsPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		Log.d(TAG, "初始化政府服务数据....");

		tvTitle.setText("人口管理");
		setSlidingMenuEnable(true);// 打开侧边栏

		TextView text = new TextView(mActivity);
		text.setText("政务");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);

		// 向FrameLayout中动态添加布局
		flContent.addView(text);
	}

}
