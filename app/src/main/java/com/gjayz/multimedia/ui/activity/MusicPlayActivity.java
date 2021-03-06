package com.gjayz.multimedia.ui.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.MusicManager;
import com.gjayz.multimedia.music.bean.SongInfo;
import com.gjayz.multimedia.music.player.MusicPlayer;
import com.gjayz.multimedia.ui.adapter.MusicAdapter;
import com.gjayz.multimedia.ui.adapter.MusicPlayAdapter;
import com.gjayz.multimedia.ui.utils.IntentUtil;
import com.gjayz.multimedia.utils.TimeUtils;


import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MusicPlayActivity extends BaseActivity implements ViewPager.OnPageChangeListener,
        SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "MusicPlayActivity";
    private static final int MSG_PLAY_POSITION = 1;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.music_play_viewpager)
    ViewPager mViewPager;

    @BindView(R.id.seekbar)
    SeekBar mSeekBar;
    @BindView(R.id.play_current_time)
    TextView mCurrentTimeView;
    @BindView(R.id.play_total_time)
    TextView mTotalTimeView;
    @BindView(R.id.button_play_pause)
    ImageButton mPlayButton;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private long[] mPlayList;

    private boolean isIdle = true;
    private Handler mHandler = new Handler();
    @SuppressLint("HandlerLeak")
    private Handler mViewPagerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_PLAY_POSITION:
                    MusicPlayer.playPosition(msg.arg1);
                    break;
            }
        }
    };

    public Runnable mUpdateProgreeRunnable = new Runnable() {
        @Override
        public void run() {
            int position = (int) MusicPlayer.getPlayPosition() / 1000;
            if (mSeekBar != null) {
                mSeekBar.setProgress(position);
                if (mCurrentTimeView != null)
                    mCurrentTimeView.setText(TimeUtils.formatMusicTime(position));
            }

            int delay = 250; //not sure why this delay was so high before
            mHandler.postDelayed(mUpdateProgreeRunnable, delay); //delay
        }
    };
    private ScanMusicInfoTask mScanMusicInfoTask;

    @Override
    public int layoutId() {
        return R.layout.activity_music_play;
    }

    @Override
    public void initActivity() {
        mSwipeBackPage.setSwipeBackEnable(false);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        mPlayList = MusicPlayer.getPlayList();

        mViewPager.setOffscreenPageLimit(3);
        MusicPlayAdapter musicPlayAdapter = new MusicPlayAdapter(getSupportFragmentManager(), mPlayList);
        mViewPager.setAdapter(musicPlayAdapter);
        mViewPager.addOnPageChangeListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateSongDetails();

        mSeekBar.setOnSeekBarChangeListener(this);

        mScanMusicInfoTask = new ScanMusicInfoTask();
        mScanMusicInfoTask.execute();
    }

    private void updateSongDetails() {
        long audioId = MusicPlayer.getAudioId();
        int playPosition = (int) MusicPlayer.getPlayPosition() / 1000;
        int duration = (int) MusicPlayer.getDuration() / 1000;
        int listPosition = getListPosition(audioId);

        if (listPosition != mViewPager.getCurrentItem() && isIdle) {
            mViewPager.setCurrentItem(listPosition, false);
        }

        mTotalTimeView.setText(TimeUtils.formatMusicTime(duration));
        mCurrentTimeView.setText(TimeUtils.formatMusicTime(playPosition));
        mSeekBar.setMax(duration);
        mSeekBar.setProgress(playPosition);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(mUpdateProgreeRunnable, 250);
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(TAG, "onPageScrolled: position = " + position + ", positionOffset = " + positionOffset + ", positionOffsetPixels = " + positionOffsetPixels);
    }

    @Override
    public void onPageSelected(final int position) {
        Log.d(TAG, "onPageSelected: position = " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "onPageScrollStateChanged: state = " + state);
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            isIdle = true;
//            mOperateLayout.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MusicPlayer.playPosition(mViewPager.getCurrentItem());
                }
            }).start();
//            mViewPagerHandler.removeCallbacksAndMessages(null);
//            mViewPagerHandler.sendMessageDelayed(mViewPagerHandler.obtainMessage(MSG_PLAY_POSITION, mViewPager.getCurrentItem(), 0), 500);
        } else if (state == ViewPager.SCROLL_STATE_DRAGGING) {
            isIdle = false;
//            mOperateLayout.setVisibility(View.GONE);
        } else {
            isIdle = false;
//            mOperateLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_music_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list:
                showPlayList();
                return true;
            case R.id.jump_to_album:
                IntentUtil.startAlbumActivity(this, MusicPlayer.getAlbumId(), MusicPlayer.getAlbum());
                return true;
            case R.id.jump_to_artis:
                IntentUtil.startArtistActivity(this, MusicPlayer.getArtistId(), MusicPlayer.getArtist());
                return true;
            case R.id.all_radom:
                return true;
            case R.id.jump_to_eq:
                IntentUtil.startEffectsActivity(this);
                return true;
            case R.id.jump_to_setting:
                IntentUtil.startSettingsActivity(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.button_like, R.id.button_prev, R.id.button_play_pause, R.id.button_next,
            R.id.button_unlike,})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_like:
                showPlayList();
                break;
            case R.id.button_prev:
                MusicPlayer.playPrev();
                break;
            case R.id.button_play_pause:
                MusicPlayer.playOrPause();
                break;
            case R.id.button_next:
                MusicPlayer.playNext();
                break;
            case R.id.button_unlike:
                break;
        }
    }

    @Override
    public void onMetaChanged(long id, String title, String artist, int albumId) {
        updateSongDetails();
    }

    @Override
    public void onPlayStatusChanged(int status) {
        switch (status) {
            case 0:
                mPlayButton.setSelected(false);
                break;
            case 1:
                mPlayButton.setSelected(true);
                break;
            case 2:
                mPlayButton.setSelected(false);
                break;
            case 3:
                mPlayButton.setSelected(false);
                break;
        }
    }

    private int getListPosition(long id) {
        int result = -1;
        if (mPlayList != null) {
            for (int i = 0; i < mPlayList.length; i++) {
                if (id == mPlayList[i]) {
                    result = i;
                }
            }
        }
        return result;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        MusicPlayer.seekTo(progress);
    }

    private void showPlayList() {
        if (mRecyclerView.getVisibility() == View.GONE) {
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class ScanMusicInfoTask extends AsyncTask<Void, Void, List<SongInfo>> {

        @Override
        protected List<SongInfo> doInBackground(Void... voids) {
            return MusicManager.getInstance(MusicPlayActivity.this).getMusicInofs(mPlayList);
        }

        @Override
        protected void onPostExecute(List<SongInfo> songInfoList) {
            MusicAdapter musicAdapter = new MusicAdapter(songInfoList);
            mRecyclerView.setAdapter(musicAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        if (mScanMusicInfoTask != null) {
            if (!mScanMusicInfoTask.isCancelled()) {
                mScanMusicInfoTask.cancel(true);
            }
            mScanMusicInfoTask = null;
        }
        super.onDestroy();
    }
}