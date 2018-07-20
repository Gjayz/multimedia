package com.gjayz.multimedia.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.permission.PermissionCallback;
import com.gjayz.multimedia.swipeback.SwipeBackHelper;
import com.gjayz.multimedia.ui.fragment.EnjoyMusicFragment;
import com.gjayz.multimedia.ui.fragment.MusicLibaryFragment;
import com.gjayz.multimedia.permission.ZXPermission;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG_ENJOY = "TAG_ENJOY";
    private static final String TAG_MUSIC_LIST = "TAG_MUSIC_LIST";

    @BindView(R.id.music_containter_layout)
    FrameLayout mFrameLayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.main_music_album_iv)
    ImageView mMusicIconView;
    @BindView(R.id.music_name_view)
    TextView mMusicNameView;
    @BindView(R.id.music_artist_view)
    TextView mMusicArtistView;
    @BindView(R.id.music_play_view)
    ImageView mMusicPlayView;
    @BindView(R.id.music_play_layout)
    View mMusicPlayLayout;

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initActivity() {
        mNavigationView.setNavigationItemSelectedListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ZXPermission.init(this);
            checkPermissionAndThenLoad();
        }

        showMusicListFragment();

        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(false);
        SwipeBackHelper.getCurrentPage(this).setDisallowInterceptTouchEvent(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermissionAndThenLoad() {
        if (ZXPermission.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE) && ZXPermission.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

        } else {
            if (ZXPermission.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(mDrawerLayout, getString(R.string.strNeedPermissionToShowSongDetails), Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.strConfirm), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ZXPermission.askForPermission(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, permissionReadstorageCallback);
                            }
                        }).show();
            } else {
                ZXPermission.askForPermission(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, permissionReadstorageCallback);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_enjoynow:
                if (!mNavigationView.getMenu().getItem(0).isChecked()) {
                    showEnjoyMusicFragment();
                }
                break;
            case R.id.nav_musiclibary:
                if (!mNavigationView.getMenu().getItem(1).isChecked()){
                    showMusicListFragment();
                }
                break;
            case R.id.action_setting:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_about:
                Intent intent1 = new Intent(this, AboutActivity.class);
                startActivity(intent1);
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 显示列表界面
     */
    private void showMusicListFragment(){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = MusicLibaryFragment.newInstance(getString(R.string.strMusicKu));
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.music_containter_layout, fragment)
                .commit();
    }

    /**
     * 显示列表界面
     */
    private void showEnjoyMusicFragment(){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = EnjoyMusicFragment.newInstance(getString(R.string.strEnjoyNow));
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.music_containter_layout, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.music_play_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_play_layout:
                Intent intent = new Intent(this, MusicPlayActivity.class);
                startActivity(intent);
                break;
        }
    }

    private final PermissionCallback permissionReadstorageCallback = new PermissionCallback() {
        @Override
        public void permissionGranted() {

        }

        @Override
        public void permissionRefused() {

        }
    };
}