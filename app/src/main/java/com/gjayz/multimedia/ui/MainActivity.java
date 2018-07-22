package com.gjayz.multimedia.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Image;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjayz.multimedia.IMusicPlayer;
import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.MusicManager;
import com.gjayz.multimedia.music.MusicService;
import com.gjayz.multimedia.music.bean.MusicInfo;
import com.gjayz.multimedia.ui.adapter.MusicAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mMusicRecyclerView;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initData();
        startMusicService();
    }

    private void initViews() {
        mMusicRecyclerView = findViewById(R.id.music_recyclerview);
        mMusicRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMusicIconView = findViewById(R.id.main_music_album_iv);
        mMusicNameView = findViewById(R.id.music_name_view);
        mMusicArtistView = findViewById(R.id.music_artist_view);
        mMusicPlayView = findViewById(R.id.music_play_view);
        mMusicPlayLayout = findViewById(R.id.music_play_layout);
        mMusicPlayView.setOnClickListener(this);
        mMusicPlayLayout.setOnClickListener(this);
    }

    private void initData(){
        mMusicList = MusicManager.getInstance(this).getMusicList();
        MusicAdapter musicAdapter = new MusicAdapter(this, mMusicList);
        musicAdapter.setOnItemClickListener(new MusicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int postion) {
                MusicInfo musicInfo = mMusicList.get(postion);
                playMusic(postion, musicInfo.getPath());
            }
        });
        mMusicRecyclerView.setAdapter(musicAdapter);
    }

    private void startMusicService() {
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void playMusic(int pos, String path){
        if (mMusicPlayer != null){
            showMusicInfo(pos);

            try {
                mMusicPlayer.play(path);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void showMusicInfo(int position){
        if (mMusicList != null){
            MusicInfo musicInfo = mMusicList.get(position);

            mMusicIconView.setImageURI(Uri.parse(musicInfo.getAlbumId()));
            mMusicNameView.setText(musicInfo.getTitle());
            mMusicArtistView.setText(musicInfo.getArtist());
            mMusicPlayView.setImageResource(R.drawable.ic_pause_black_large);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_play_view:
                if (mMusicPlayer != null){
                    try {
                        if (mMusicPlayer.isPlaying()){
                            mMusicPlayer.pause();
                            mMusicPlayView.setImageResource(R.drawable.ic_play_black_round);
                        }else {
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
