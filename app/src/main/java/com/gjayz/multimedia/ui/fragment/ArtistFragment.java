package com.gjayz.multimedia.ui.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.MusicManager;
import com.gjayz.multimedia.music.bean.ArtistInfo;
import com.gjayz.multimedia.ui.adapter.ArtistAdapter;
import com.gjayz.multimedia.ui.fragment.v4.BaseFragment;

import java.util.List;

import butterknife.BindView;

public class ArtistFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private ArtistSannerTask mArtistSannerTask;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_artist;
    }

    @Override
    public void init() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mArtistSannerTask = new ArtistSannerTask();
        mArtistSannerTask.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class ArtistSannerTask extends AsyncTask<Void, Void, List<ArtistInfo>> {

        @Override
        protected List<ArtistInfo> doInBackground(Void... voids) {
            return MusicManager.getInstance(mContext).getArtistList();
        }

        @Override
        protected void onPostExecute(List<ArtistInfo> artistInfos) {
            ArtistAdapter artistAdapter = new ArtistAdapter(mContext, artistInfos);
            mRecyclerView.setAdapter(artistAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        if (mArtistSannerTask != null) {
            if (!mArtistSannerTask.isCancelled()) {
                mArtistSannerTask.cancel(true);
            }
            mArtistSannerTask = null;
        }
        super.onDestroyView();
    }
}
