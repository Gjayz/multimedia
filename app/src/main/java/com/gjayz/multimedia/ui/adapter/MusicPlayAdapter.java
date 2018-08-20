package com.gjayz.multimedia.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gjayz.multimedia.ui.fragment.MusicPlayFragment;

public class MusicPlayAdapter extends FragmentStatePagerAdapter {

    private long[] mIdArr;

    public MusicPlayAdapter(FragmentManager fm, long[] idArr) {
        super(fm);
        mIdArr = idArr;
    }

    @Override
    public Fragment getItem(int position) {
        MusicPlayFragment musicPlayFragment = MusicPlayFragment.createInstance(mIdArr[position]);
        return musicPlayFragment;
    }

    @Override
    public int getCount() {
        return mIdArr == null ? 0 : mIdArr.length;
    }
}
