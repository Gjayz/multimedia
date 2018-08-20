package com.gjayz.multimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class MusicPageAdapter extends FragmentStatePagerAdapter {

    private String[] mTitleArray;
    private List<Fragment> mFragmentList;

    public MusicPageAdapter(FragmentManager fm, String[] titleArray, List<Fragment> fragmentList) {
        super(fm);
        mTitleArray = titleArray;
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleArray[position];
    }
}
