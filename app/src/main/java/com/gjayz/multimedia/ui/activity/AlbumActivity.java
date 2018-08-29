package com.gjayz.multimedia.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.MusicManager;
import com.gjayz.multimedia.music.bean.SongInfo;
import com.gjayz.multimedia.music.player.ListType;
import com.gjayz.multimedia.music.player.MusicPlayer;
import com.gjayz.multimedia.ui.adapter.AlbumMusicAdapter;
import com.gjayz.multimedia.utils.ZXUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;

public class AlbumActivity extends BaseActivity {

    public static final String KEY_ALBUM = "key_album";
    public static final String KEY_ALBUM_ID = "key_album_id";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.artist_icon_view)
    ImageView mIconView;

    @BindView(R.id.album_name_view)
    TextView albumNameTv;
    @BindView(R.id.album_icon_view)
    ImageView albumIconView;
    @BindView(R.id.artist_name_tv)
    TextView artistNameTv;
    @BindView(R.id.music_count_tv)
    TextView musicCountTv;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.fab)
    FloatingActionButton mFloatingActionButton;
    private int mAlbumId;
    private String mAlbum;
    private AlbumMusicAdapter mAlbumMusicAdapter;
    private List<SongInfo> mAlbumMusicList;
    private long[] mSongList;

    @Override
    public int layoutId() {
        return R.layout.activity_album;
    }

    @Override
    public void initActivity() {
        Intent intent = getIntent();
        mAlbumId = intent.getIntExtra(KEY_ALBUM_ID, 0);
        mAlbum = intent.getStringExtra(KEY_ALBUM);

        mToolbar.setTitle(mAlbum);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAlbumMusicList = MusicManager.getInstance(this).getAlbumMusicList(mAlbumId);
        mSongList = createSongList();

        mAlbumMusicAdapter = new AlbumMusicAdapter(mAlbumMusicList);
        mAlbumMusicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MusicPlayer.playList(mSongList, position, ListType.Album);
            }
        });
        mRecyclerView.setAdapter(mAlbumMusicAdapter);

        addHeadLayout();
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlayer.playList(mSongList, 0, ListType.Album);
            }
        });
    }

    private void addHeadLayout() {
        albumNameTv.setText(mAlbum);
        artistNameTv.setText(mAlbumMusicList.get(0).getArtist());
        musicCountTv.setText(mAlbumMusicList.size()+ " 首歌曲");

        ImageLoader.getInstance().displayImage(ZXUtils.getAlbumArtUri(mAlbumId).toString(),
                albumIconView, new DisplayImageOptions.Builder().cacheInMemory(true)
                        .resetViewBeforeLoading(true).build());

        ImageLoader.getInstance().displayImage(ZXUtils.getAlbumArtUri(mAlbumId).toString(),
                mIconView, new DisplayImageOptions.Builder().cacheInMemory(true)
                        .resetViewBeforeLoading(true).build());
    }

    private long[] createSongList() {
        long[] result = null;
        if (mAlbumMusicList != null) {
            int size = mAlbumMusicList.size();
            result = new long[size];
            for (int i = 0; i < size; i++) {
                result[i] = mAlbumMusicList.get(i).getId();
            }
        }
        return result;
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

    public static void newIntent(Context context, int albumId, String album) {
        Intent intent = new Intent(context, AlbumActivity.class);
        intent.putExtra(KEY_ALBUM, album);
        intent.putExtra(KEY_ALBUM_ID, albumId);
        context.startActivity(intent);
    }
}