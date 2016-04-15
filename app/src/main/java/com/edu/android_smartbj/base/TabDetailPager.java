package com.edu.android_smartbj.base;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.android_smartbj.R;
import com.edu.android_smartbj.domain.NewsData;
import com.edu.android_smartbj.domain.TabData;
import com.edu.android_smartbj.global.GlobalConstants;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Ming on 2016/1/20.
 * email 1715944993@qq.com
 */
public class TabDetailPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener {


    private static final String TAG = "TabDetailPager";
    private NewsData.NewsTabData mNewsTabData;
    private TextView tvText;
    private String mUrl;
    private TabData mTabDetailData;
    // 图片处理
    private ImageOptions imageOptions;

    @ViewInject(R.id.vp_news)
    private ViewPager mViewPager;

    // 新闻标题
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;

    // 头条新闻集合
    private ArrayList<TabData.TopNewsData> mTopNewsDatas;

    public TabDetailPager(Activity activity, NewsData.NewsTabData newsTabData) {
        super(activity);
        this.mNewsTabData = newsTabData;
        mUrl = GlobalConstants.SERVER_URL + mNewsTabData.url;

    }

    @Override
    public View initViews() {

        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        x.view().inject(this, view);
        mViewPager.addOnPageChangeListener(this);
        return view;
    }

    @Override
    public void initData() {
        getDataFromServer();
    }

    private void getDataFromServer() {

        // 请求数据
        RequestParams params = new RequestParams(mUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "onSuccess: xUtils3" + result + "***x.app()***" + x.app());
                parseData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "onError: " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 解析数据
     *
     * @param result
     */
    private void parseData(String result) {

        Gson gson = new Gson();
        mTabDetailData = gson.fromJson(result, TabData.class);
        Log.d(TAG, "parseData: " + mTabDetailData);
        // 填充数据
        mTopNewsDatas = mTabDetailData.data.topnews;
        // 这边有事件冲突 自定义 viewPager
        mViewPager.setAdapter(new TopNewsAdapter());
        tvTitle.setText(mTopNewsDatas.get(0).title);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        TabData.TopNewsData topNewsData = mTopNewsDatas.get(position);
        tvTitle.setText(topNewsData.title);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class TopNewsAdapter extends PagerAdapter {

        public TopNewsAdapter() {
            imageOptions = new ImageOptions.Builder()
                    .setLoadingDrawableId(R.mipmap.topnews_item_default)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .build();

            /**
             * 1201981843497
             * new ImageOptions.Builder()
             .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
             .setRadius(DensityUtil.dip2px(5))
             // 如果ImageView的大小不是定义为wrap_content, 不要crop.
             .setCrop(true)
             // 加载中或错误图片的ScaleType
             //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
             .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
             .setLoadingDrawableId(R.mipmap.topnews_item_default)
             .setFailureDrawableId(R.mipmap.ic_launcher)
             .build();
             */
        }

        @Override
        public int getCount() {
            return mTabDetailData.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mActivity);
            TabData.TopNewsData topNewsData = mTopNewsDatas.get(position);
            x.image().bind(imageView, topNewsData.topimage, imageOptions);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
