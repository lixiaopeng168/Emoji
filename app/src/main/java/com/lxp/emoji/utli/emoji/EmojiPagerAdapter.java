package com.lxp.emoji.utli.emoji;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * 表情滑动适配器
 */

public class EmojiPagerAdapter extends PagerAdapter {

    List<View> mListViews;
    public EmojiPagerAdapter(List<View> mListViews ){
        this.mListViews=mListViews;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        Log.d("kk", "destroyItem");
        ((ViewPager) arg0).removeView(mListViews.get(arg1));
    }

    @Override
    public void finishUpdate(View arg0) {
        Log.d("kk", "finishUpdate");
    }

    @Override
    public int getCount() {
        Log.d("kk", "getCount"+mListViews.size());
        return mListViews.size();
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        Log.d("kk", "instantiateItem");
        ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
        return mListViews.get(arg1);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        Log.d("kk", "isViewFromObject");
        return arg0 == (arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        Log.d("kk", "restoreState");
    }

    @Override
    public Parcelable saveState() {
        Log.d("kk", "saveState");
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
        Log.d("kk", "startUpdate");
    }
}
