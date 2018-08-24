package com.gjayz.multimedia.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.MusicManager;
import com.gjayz.multimedia.music.bean.Album;
import com.gjayz.multimedia.ui.adapter.AlbumAdapter;

import java.util.List;

import butterknife.BindView;

public class SchoolActivity extends BaseActivity {

    private static final String KEY_SCHOOL_ID = "key_school_id";
    private static final String KEY_SCHOOL_NAME = "key_school_name";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private String mSchoolName;
    private int mSchoolId;

    @Override
    public int layoutId() {
        return R.layout.activity_school;
    }

    @Override
    public void initActivity() {
        Intent intent = getIntent();
        mSchoolId = intent.getIntExtra(KEY_SCHOOL_ID, 0);
        mSchoolName = intent.getStringExtra(KEY_SCHOOL_NAME);

        mToolbar.setTitle(mSchoolName);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        List<Album> schoolAlbum = MusicManager.getInstance(this).getSchoolAlbum(mSchoolId);
        mRecyclerView.setAdapter(new AlbumAdapter(this, schoolAlbum));
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

    public static void newIntent(Context context, int schoolId, String schoolName) {
        Intent intent = new Intent(context, SchoolActivity.class);
        intent.putExtra(KEY_SCHOOL_ID, schoolId);
        intent.putExtra(KEY_SCHOOL_NAME, schoolName);
        context.startActivity(intent);
    }
}