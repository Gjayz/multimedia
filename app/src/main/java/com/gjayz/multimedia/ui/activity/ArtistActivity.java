package com.gjayz.multimedia.ui.activity;

import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.MusicManager;
import com.gjayz.multimedia.music.bean.Album;
import com.gjayz.multimedia.music.bean.SongInfo;
import com.gjayz.multimedia.music.player.ListType;
import com.gjayz.multimedia.music.player.MusicPlayer;
import com.gjayz.multimedia.ui.adapter.ArtistAlbumAdapter;
import com.gjayz.multimedia.utils.ZXUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ArtistActivity extends BaseActivity {

    public static final String KEY_ARTIST = "key_artist";
    public static final String KEY_ARTIST_ID = "key_artist_id";

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.artist_icon_view)
    ImageView mArtistView;
    @BindView(R.id.fab)
    FloatingActionButton mFloatingActionButton;

    private int mArtist_id;
    private String mArtist;
    private ImageView mArtistIconView;
    private List<Album> mAlbumList;
    private ArrayList<SongInfo> mMusiclist;
    private ArtisMusicListTask mArtisMusicListTask;

    @Override
    public int layoutId() {
        return R.layout.activity_artist;
    }

    @Override
    public void initActivity() {
        View headerView = LayoutInflater.from(this).inflate(R.layout.artist_header_layout, null);

        mArtist_id = getIntent().getIntExtra(KEY_ARTIST_ID, 0);
        mArtist = getIntent().getStringExtra(KEY_ARTIST);

        mToolbar.setTitle(mArtist);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAlbumList = MusicManager.getInstance(this).getAlbumList(mArtist_id);
        ArtistAlbumAdapter artistAlbumAdapter = new ArtistAlbumAdapter(this, mAlbumList);
        mRecyclerView.setAdapter(artistAlbumAdapter);
        artistAlbumAdapter.addHeaderView(headerView);
        artistAlbumAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Album album = mAlbumList.get(position);
                AlbumActivity.newIntent(ArtistActivity.this, album.getAlbum_id(), album.getAlbum());
            }
        });

        ImageLoader.getInstance().displayImage(ZXUtils.getAlbumArtUri(mAlbumList.get(0).getAlbum_id()).toString(),
                mArtistView, new DisplayImageOptions.Builder().cacheInMemory(true)
                        .resetViewBeforeLoading(true).build());

        mArtisMusicListTask = new ArtisMusicListTask();
        mArtisMusicListTask.execute();
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
            Toast.makeText(this, R.string.strSearch, Toast.LENGTH_SHORT).show();
        } else if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.fab})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                playAllMusic();
                break;
        }
    }

    private void playAllMusic() {
        MusicPlayer.playList(mMusiclist, 0, ListType.Artist);
    }

    public class ArtisMusicListTask extends AsyncTask<Void, Void, List<SongInfo>> {

        @Override
        protected List<SongInfo> doInBackground(Void... voids) {
            mMusiclist = new ArrayList<>();
            for (Album album : mAlbumList) {
                mMusiclist.addAll(MusicManager.getInstance(ArtistActivity.this).getAlbumMusicList(album.getAlbum_id()));
            }
            return mMusiclist;
        }
    }

    @Override
    protected void onDestroy() {
        if (mArtisMusicListTask != null) {
            if (!mArtisMusicListTask.isCancelled()) {
                mArtisMusicListTask.cancel(true);
            }
            mArtisMusicListTask = null;
        }
        super.onDestroy();
    }
}