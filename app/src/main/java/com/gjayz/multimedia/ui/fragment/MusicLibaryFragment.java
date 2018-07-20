package com.gjayz.multimedia.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.ui.activity.MainActivity;
import com.gjayz.multimedia.ui.adapter.MusicPageAdapter;
import com.gjayz.multimedia.utils.TabLayoutUtil;

import java.util.ArrayList;

import butterknife.BindView;

public class MusicLibaryFragment extends BaseFragment {

    private static final String ARG_TITLE = "ARG_TITLE";

    @BindView(R.id.music_tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.music_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private MusicPageAdapter mMusicPageAdapter;
    private String mTitle;

    public MusicLibaryFragment() {
    }

    public static MusicLibaryFragment newInstance(String title) {
        MusicLibaryFragment fragment = new MusicLibaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_music_libary;
    }

    @Override
    public void init() {
        initToolBar();
        initData();
    }

    private void initData() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new PlayListFragment());
        fragments.add(new PlayListFragment());
        fragments.add(new PlayListFragment());
        fragments.add(new PlayListFragment());
        fragments.add(new PlayListFragment());

        String[] stringArray = getResources().getStringArray(R.array.music_type_array);
        mMusicPageAdapter = new MusicPageAdapter(getFragmentManager(), stringArray, fragments);
        mViewPager.setAdapter(mMusicPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        TabLayoutUtil.fixTabLayoutWidthWarp(mTabLayout);
    }

    private void initToolBar(){
        Activity activity = getActivity();
        if (activity instanceof MainActivity){
            MainActivity mainActivity = (MainActivity) activity;

            mToolbar.setTitle(mTitle);
            mainActivity.setSupportActionBar(mToolbar);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    mainActivity, mainActivity.getDrawerLayout(), mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            mainActivity.getDrawerLayout().addDrawerListener(toggle);
            toggle.syncState();
        }
    }

    @Override
    public void onDestroyView() {
        mMusicPageAdapter = null;
        mViewPager = null;
        mTabLayout = null;
        super.onDestroyView();
    }
}