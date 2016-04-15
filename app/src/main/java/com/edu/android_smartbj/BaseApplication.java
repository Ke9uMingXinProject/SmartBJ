package com.edu.android_smartbj;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Ming on 2016/1/19.
 * email 1715944993@qq.com
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
