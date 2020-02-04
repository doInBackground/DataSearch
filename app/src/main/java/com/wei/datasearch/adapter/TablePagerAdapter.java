package com.wei.datasearch.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @Author WCL
 * @Date 2019/6/21 10:56
 * @Version 1.0
 * @Description 展示表格的ViewPager的适配器
 */
public class TablePagerAdapter extends PagerAdapter {

    private List<View> mViewList;

    public TablePagerAdapter(List<View> viewList) {
        this.mViewList = viewList;
    }

    @Override
    public int getCount() {
        return mViewList == null ? 0 : mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

}
