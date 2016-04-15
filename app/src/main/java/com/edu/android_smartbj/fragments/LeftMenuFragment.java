package com.edu.android_smartbj.fragments;

import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.android_smartbj.MainActivity;
import com.edu.android_smartbj.R;
import com.edu.android_smartbj.base.impl.NewsCenterPager;
import com.edu.android_smartbj.domain.NewsData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Ming on 2016/1/18.
 */
public class LeftMenuFragment extends BaseFragment {

    private static final String TAG = "LeftMenuFragment";
    @ViewInject(R.id.lv_list)
    private ListView lvList;

    private ArrayList<NewsData.NewsMenuData> mMenuList;
    private MenuAdapter mAdapter;
    // 当前被点击的菜单项
    private int mCurrentPos;

    @Override
    public View initViews() {

        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        x.view().inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }


    // 设置网络数据
    public void setMenuData(NewsData data) {
        Log.d(TAG, "侧边栏拿到数据啦:" + data);
        mMenuList = data.data;
        mAdapter = new MenuAdapter();
        lvList.setAdapter(mAdapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentPos = position;
                mAdapter.notifyDataSetChanged();
                setCurrentMenuDetailPager(position);
                // 隐藏
                toggleSlidingMenu();
            }
        });
    }

    /**
     * 切换SlidingMenu的状态
     *
     */
    protected void toggleSlidingMenu() {
        MainActivity mainUi = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        // 切换状态, 显示时隐藏, 隐藏时显示
        slidingMenu.toggle();
    }

    /**
     * 设置当前菜单详情页
     *
     * @param position
     */
    private void setCurrentMenuDetailPager(int position) {
        MainActivity mainUi = (MainActivity) mActivity;
        // 获取主页面fragment
        ContentFragment contentFragment = mainUi.getContentFragment();
        // 获取新闻中心页面
        NewsCenterPager newsCenterPager = contentFragment.getNewsCenterPager();
        newsCenterPager.setCurrentMenuDetailPager(position);
    }


    /**
     * 侧边栏数据适配器
     *
     * @author Kevin
     */
    private class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mMenuList.size();
        }

        @Override
        public NewsData.NewsMenuData getItem(int position) {
            return mMenuList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity, R.layout.list_menu_item, null);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            NewsData.NewsMenuData newsMenuData = getItem(position);
            Log.d(TAG, "getView: " + newsMenuData.title);
            tvTitle.setText(newsMenuData.title);
            if (mCurrentPos == position) {// 判断当前绘制的view是否被选中
                // 显示红色
                tvTitle.setEnabled(true);
            } else {
                // 显示白色
                tvTitle.setEnabled(false);
            }

            return view;
        }

    }
}
