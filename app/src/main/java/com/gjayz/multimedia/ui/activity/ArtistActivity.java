package com.gjayz.multimedia.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.MusicManager;
import com.gjayz.multimedia.music.bean.Album;
import com.gjayz.multimedia.ui.adapter.AlbumAdapter;
import com.gjayz.multimedia.utils.ZXUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ArtistActivity extends BaseActivity {

    private static final String KEY_ARTIST = "key_artist";
    private static final String KEY_ARTIST_ID = "key_artist_id";
    @BindView(R.id.artist_icon_view)
    ImageView mArtistView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private int mArtist_id;
    private String mArtist;

    @Override
    public int layoutId() {
        return R.layout.activity_artist;
    }

    @Override
    public void initActivity() {
        mArtist_id = getIntent().getIntExtra(KEY_ARTIST_ID, 0);
        mArtist = getIntent().getStringExtra(KEY_ARTIST);

        mToolbar.setTitle(mArtist);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        List<Album> albumList = MusicManager.getInstance(this).getAlbumList(mArtist_id);
        AlbumAdapter albumAdapter = new AlbumAdapter(this, albumList);
        mRecyclerView.setAdapter(albumAdapter);

        ImageLoader.getInstance().displayImage(ZXUtils.getAlbumArtUri(albumList.get(0).getAlbum_id()).toString(),
                mArtistView, new DisplayImageOptions.Builder().cacheInMemory(true)
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

    public static Intent newIntent(Context context, int artistId, String artist) {
        Intent intent = new Intent(context, ArtistActivity.class);
        intent.putExtra(KEY_ARTIST, artist);
        intent.putExtra(KEY_ARTIST_ID, artistId);
        return intent;
    }
}
