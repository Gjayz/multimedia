package com.gjayz.multimedia.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.ui.activity.MainActivity;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link EnjoyMusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnjoyMusicFragment extends BaseFragment  {

    private static final String ARG_TITLE = "ARG_TITLE";

    private String mTitle;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.music_type_info)
    TextView mMusicInfo;

    public EnjoyMusicFragment() {

    }

    public static EnjoyMusicFragment newInstance(String title) {
        EnjoyMusicFragment fragment = new EnjoyMusicFragment();
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
        return R.layout.fragment_enjoy_music;
    }

    @Override
    public void init() {
        initToolBar();
        initData();
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

    private void initData() {
        mMusicInfo.setText(mTitle);
    }
}