package com.gjayz.multimedia.ui.fragment;

import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.MusicManager;
import com.gjayz.multimedia.music.bean.Album;
import com.gjayz.multimedia.ui.adapter.AlbumAdapter;
import com.gjayz.multimedia.ui.fragment.v4.BaseFragment;

import java.util.List;

import butterknife.BindView;

public class AlbumFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private AlbumAsyncTask mAlbumAsyncTask;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_album;
    }

    @Override
    public void init() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mAlbumAsyncTask = new AlbumAsyncTask();
        mAlbumAsyncTask.execute();
    }

    public class AlbumAsyncTask extends AsyncTask<Void, Void, List<Album>> {

        @Override
        protected List<Album> doInBackground(Void... voids) {
            return MusicManager.getInstance(mContext).getAlbumList();
        }

        @Override
        protected void onPostExecute(List<Album> albums) {
            AlbumAdapter albumAdapter = new AlbumAdapter(mContext, albums);
            mRecyclerView.setAdapter(albumAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        if (mAlbumAsyncTask != null) {
            if (!mAlbumAsyncTask.isCancelled()) {
                mAlbumAsyncTask.cancel(true);
            }
            mAlbumAsyncTask = null;
        }
        super.onDestroyView();
    }
}