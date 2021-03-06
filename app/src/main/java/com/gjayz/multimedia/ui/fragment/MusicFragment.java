package com.gjayz.multimedia.ui.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.MusicManager;
import com.gjayz.multimedia.music.bean.SongInfo;
import com.gjayz.multimedia.ui.adapter.AllSongAdapter;

import java.util.List;

import butterknife.BindView;

public class MusicFragment extends com.gjayz.multimedia.ui.fragment.v4.BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private ScannerSongsTask mScannerSongsTask;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_songs;
    }

    @Override
    public void init() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        mScannerSongsTask = new ScannerSongsTask();
        mScannerSongsTask.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class ScannerSongsTask extends AsyncTask<Void, Void, List<SongInfo>> {

        @Override
        protected List<SongInfo> doInBackground(Void... voids) {
            return MusicManager.getInstance(getActivity()).getMusicList();
        }

        @Override
        protected void onPostExecute(List<SongInfo> result) {
            AllSongAdapter allSongAdapter = new AllSongAdapter(getContext(), result);
            mRecyclerview.setAdapter(allSongAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        mScannerSongsTask.cancel(true);
        mScannerSongsTask = null;
        super.onDestroyView();
    }
}