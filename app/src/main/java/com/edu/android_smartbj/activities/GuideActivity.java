package com.edu.android_smartbj.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.edu.android_smartbj.MainActivity;
import com.edu.android_smartbj.R;
import com.edu.android_smartbj.adapters.GuideAdapter;
import com.edu.android_smartbj.utils.PrefUtils;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {

    private static final int[] mImageIds = new int[]{R.mipmap.guide_1,
            R.mipmap.guide_2, R.mipmap.guide_3};
    private static final String TAG = "GuideActivity";
    private ViewPager vpGuide;
    private ArrayList<ImageView> mImageViewList;
    private GuideAdapter adapter;
    private LinearLayout mLinearLayout;
    private int mWidth;
    private View mViewRedPoint;
    private Button btn_Start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 去掉标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        // 隐藏标题栏 或者 在清单文件中设置 noActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_guide);
        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_point_group);
        mViewRedPoint = findViewById(R.id.view_red_point);
        btn_Start = (Button) findViewById(R.id.btn_start);
        btn_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 更新 sp 表示已经展示了新手引导页
               /* SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
                config.edit().putBoolean("is_user_guide_showed",true).commit();*/
                PrefUtils.setBoolean(GuideActivity.this, "is_user_guide_showed", true);
                // 跳转主页面
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });
        initViews();
        adapter = new GuideAdapter(mImageViewList);
        vpGuide.setAdapter(adapter);
        vpGuide.addOnPageChangeListener(new GuidePageListener());
    }

    /**
     * 初始化界面
     */
    private void initViews() {
        mImageViewList = new ArrayList<>();

        // 初始化引导页的页面
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView image = new ImageView(this);
            // 设置引导页背景
            image.setBackgroundResource(mImageIds[i]);
            mImageViewList.add(image);
        }

        // 初始化引导页的小圆点
        for (int i = 0; i < mImageIds.length; i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);// 设置引导页默认圆点

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    10, 10);
            if (i > 0) {
                params.leftMargin = 10;// 设置圆点间隔
            }
            point.setLayoutParams(params);// 设置圆点的大小

            mLinearLayout.addView(point);// 将圆点添加给线性布局
        }

        // 测量的方法：measure（测量大小）layout(界面位置),onDraw
        // 页面没有有初始化结束，拿不到两个点的距离 用
        /*int width = mLinearLayout.getChildAt(1).getLeft() - mLinearLayout.getChildAt(0).getLeft();
        Log.d(TAG, "initViews:  "+width);*/

        // 拿到视图树,对layout 结束时间进行监听
        mLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            // 当layout执行结束后回调此方法
            @Override
            public void onGlobalLayout() {
                Log.d(TAG, "Layout: 结束");
                mLinearLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mWidth = mLinearLayout.getChildAt(1).getLeft() - mLinearLayout.getChildAt(0).getLeft();
                Log.d(TAG, "initViews:  " + mWidth);
            }
        });

    }

    /**
     * viewpager的滑动监听
     *
     * @author Kevin
     */
    class GuidePageListener implements ViewPager.OnPageChangeListener {

        // 滑动事件
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            // System.out.println("当前位置:" + position + ";百分比:" + positionOffset
            // + ";移动距离:" + positionOffsetPixels);
            int len = (int) (mWidth * positionOffset) + position * mWidth;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mViewRedPoint
                    .getLayoutParams();// 获取当前红点的布局参数
            params.leftMargin = len;// 设置左边距

            mViewRedPoint.setLayoutParams(params);// 重新给小红点设置布局参数
        }

        // 某个页面被选中
        @Override
        public void onPageSelected(int position) {
            if (position == mImageIds.length - 1) {// 最后一个页面
                btn_Start.setVisibility(View.VISIBLE);// 显示开始体验的按钮
            } else {
                btn_Start.setVisibility(View.INVISIBLE);
            }
        }

        // 滑动状态发生变化
        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }
}
