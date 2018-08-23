package com.gjayz.multimedia.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.MusicManager;
import com.gjayz.multimedia.music.bean.SongInfo;
import com.gjayz.multimedia.ui.adapter.AlbumMusicAdapter;
import com.gjayz.multimedia.ui.adapter.MusicAdapter;
import com.gjayz.multimedia.utils.ZXUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;

public class AlbumActivity extends BaseActivity {

    private static final String KEY_ALBUM = "key_album";
    private static final String KEY_ALBUM_ID = "key_album_id";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.artist_icon_view)
    ImageView mIconView;
    private int mAlbumId;
    private String mAlbum;
//    private View mHeaderLayout;
    private AlbumMusicAdapter mAlbumMusicAdapter;
    private List<SongInfo> mAlbumMusicList;

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
        mAlbumMusicAdapter = new AlbumMusicAdapter(mAlbumMusicList);
        addHeadLayout();

        mRecyclerView.setAdapter(mAlbumMusicAdapter);

        ImageLoader.getInstance().displayImage(ZXUtils.getAlbumArtUri(mAlbumId).toString(),
                mIconView, new DisplayImageOptions.Builder().cacheInMemory(true)
                        .resetViewBeforeLoading(true).build());

    }

    private void addHeadLayout() {
//        mHeaderLayout = LayoutInflater.from(this).inflate(R.layout.album_header_layout, null);
//        mAlbumMusicAdapter.addHeaderView(mHeaderLayout);

        TextView albumNameTv = findViewById(R.id.album_name_view);
        ImageView albumIconView = findViewById(R.id.album_icon_view);
        TextView artistNameTv = findViewById(R.id.artist_name_tv);
        TextView musicCountTv = findViewById(R.id.music_count_tv);
        albumNameTv.setText(mAlbum);
        artistNameTv.setText(mAlbum);
        musicCountTv.setText(String.valueOf(mAlbumMusicList.size()));

        ImageLoader.getInstance().displayImage(ZXUtils.getAlbumArtUri(mAlbumId).toString(),
                albumIconView, new DisplayImageOptions.Builder().cacheInMemory(true)
                        .resetViewBeforeLoading(true).build());
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