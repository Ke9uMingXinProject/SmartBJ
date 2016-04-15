package com.edu.android_smartbj.base.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.edu.android_smartbj.base.BaseMenuDetailPager;

/**
 * Created by Ming on 2016/1/19.
 * email 1715944993@qq.com
 * 菜单详情页 --- 新闻
 */
public class TopicMenuDetailPager extends BaseMenuDetailPager {

    public TopicMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initViews() {

        TextView text = new TextView(mActivity);
        text.setText("菜单详情页---专题");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);


        return text;
    }
}
