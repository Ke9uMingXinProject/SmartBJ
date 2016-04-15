package com.edu.android_smartbj.fragments;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioGroup;

import com.edu.android_smartbj.R;
import com.edu.android_smartbj.base.BasePager;
import com.edu.android_smartbj.base.impl.GovAffairsPager;
import com.edu.android_smartbj.base.impl.HomePager;
import com.edu.android_smartbj.base.impl.NewsCenterPager;
import com.edu.android_smartbj.base.impl.SettingPager;
import com.edu.android_smartbj.base.impl.SmartServicePager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming on 2016/1/18.
 */
public class ContentFragment extends BaseFragment {

    @ViewInject(R.id.rg_group)
    private RadioGroup rgGroup;

    @ViewInject(R.id.vp_content)
    private ViewPager mViewPager;

    private List<BasePager> mPagerList;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        //rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);

        // 跟换 xutils 注入view 和事件
        x.view().inject(this, view);

        return view;
    }

    @Override
    public void initData() {
        // 勾选首页
        rgGroup.check(R.id.rb_home);

        // 初始化5个子页面
        mPagerList = new ArrayList<>();

       /* for (int i = 0; i < 5; i++) {
            BasePager basePager = new BasePager(mActivity);
            mPagerList.add(basePager);
        }*/

        mPagerList.add(new HomePager(mActivity));
        mPagerList.add(new NewsCenterPager(mActivity));
        mPagerList.add(new SmartServicePager(mActivity));
        mPagerList.add(new GovAffairsPager(mActivity));
        mPagerList.add(new SettingPager(mActivity));

        mViewPager.setAdapter(new ContentAdapter());

        // 监听 RadioGroup 的选择事件
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int index = 0;
                switch (checkedId) {
                    case R.id.rb_home:
                        index = 0;
                        break;

                    case R.id.rb_news:
                        index = 1;
                        break;

                    case R.id.rb_smart:
                        index = 2;
                        break;

                    case R.id.rb_gov:
                        index = 3;
                        break;
                    case R.id.rb_setting:
                        index = 4;
                        break;
                    default:
                        break;
                }
                // 去掉切换页面的动画
                mViewPager.setCurrentItem(index,false);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 获取当前选中的页面，初始化改页面
                mPagerList.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPagerList.get(0).initData();
    }


    private class ContentAdapter extends PagerAdapter {
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
            BasePager basePager = mPagerList.get(position);
            container.addView(basePager.mRootView);
            // 初始化数据.....不要放在此处初始化，否则会预加载下一个页面 viewpager 的特性
            // basePager.initData();
            return basePager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


       /**
     * 获取新闻中心页面
     *
     * @return
     */
    public NewsCenterPager getNewsCenterPager() {
        return (NewsCenterPager) mPagerList.get(1);
    }

}
