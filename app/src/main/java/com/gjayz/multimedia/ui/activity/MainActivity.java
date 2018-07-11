package com.gjayz.multimedia.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjayz.multimedia.IMusicPlayer;
import com.gjayz.multimedia.R;
import com.gjayz.multimedia.media.MediaInfoUtil;
import com.gjayz.multimedia.music.MusicService;
import com.gjayz.multimedia.music.bean.MusicInfo;
import com.gjayz.multimedia.swipeback.SwipeBackHelper;
import com.gjayz.multimedia.ui.adapter.MusicPageAdapter;
import com.gjayz.multimedia.ui.fragment.PlayListFragment;
import com.gjayz.multimedia.utils.TabLayoutUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.music_tablayout)
    TabLayout mTableLayout;
    @BindView(R.id.musci_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private ImageView mMusicIconView;
    private TextView mMusicNameView;
    private TextView mMusicArtistView;
    private ImageView mMusicPlayView;
    private IMusicPlayer mMusicPlayer;
    private List<MusicInfo> mMusicList;
    private View mMusicPlayLayout;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicPlayer = IMusicPlayer.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initActivity() {
        initViews();
        initActionBar();
        initData();
        startMusicService();

        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(false);
        SwipeBackHelper.getCurrentPage(this).setDisallowInterceptTouchEvent(true);
    }

    private void initViews() {
        mMusicIconView = findViewById(R.id.main_music_album_iv);
        mMusicNameView = findViewById(R.id.music_name_view);
        mMusicArtistView = findViewById(R.id.music_artist_view);
        mMusicPlayView = findViewById(R.id.music_play_view);
        mMusicPlayLayout = findViewById(R.id.music_play_layout);
        mMusicPlayView.setOnClickListener(this);
        mMusicPlayLayout.setOnClickListener(this);
    }

    private void initActionBar() {
        mToolbar.setTitle(R.string.strMusicKu);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initData() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new PlayListFragment());
        fragments.add(new PlayListFragment());
        fragments.add(new PlayListFragment());
        fragments.add(new PlayListFragment());
        fragments.add(new PlayListFragment());

        String[] stringArray = getResources().getStringArray(R.array.music_type_array);
        MusicPageAdapter musicPageAdapter = new MusicPageAdapter(getSupportFragmentManager(), stringArray, fragments);
        mViewPager.setAdapter(musicPageAdapter);
        mTableLayout.setupWithViewPager(mViewPager);

        TabLayoutUtil.fixTabLayoutWidthWarp(mTableLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void startMusicService() {
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void playMusic(int pos, String path) {
        if (mMusicPlayer != null) {
            showMusicInfo(pos);

            try {
                mMusicPlayer.play(path);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void showMusicInfo(int position) {
        if (mMusicList != null) {
            MusicInfo musicInfo = mMusicList.get(position);

            mMusicIconView.setImageBitmap(MediaInfoUtil.getArtwork(this, musicInfo.getId(), musicInfo.getAlbumId(), false, true));
            mMusicNameView.setText(musicInfo.getTitle());
            mMusicArtistView.setText(musicInfo.getArtist());
            mMusicPlayView.setImageResource(R.drawable.ic_pause_black_large);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_play_view:
                if (mMusicPlayer != null) {
                    try {
                        if (mMusicPlayer.isPlaying()) {
                            mMusicPlayer.pause();
                            mMusicPlayView.setImageResource(R.drawable.ic_play_black_round);
                        } else {
                            mMusicPlayer.resume();
                            mMusicPlayView.setImageResource(R.drawable.ic_pause_black_large);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.music_play_layout:
                Intent intent = new Intent(this, MusicPlayActivity.class);
                startActivity(intent);
                break;
        }
    }
}