package com.gjayz.multimedia.ui.fragment;

import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.data.MediaStoreHelper;
import com.gjayz.multimedia.ui.adapter.MusicCursorAdapter;

import butterknife.BindView;

public class MusicFragment extends BaseFragment {

    @BindView(R.id.listview)
    ListView mListView;

    private Cursor mCursor;
    private AdapterView.OnItemClickListener onItemClickListener;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_songs;
    }

    @Override
    public void init() {
        ScannerSongsTask scannerSongsTask = new ScannerSongsTask();
        scannerSongsTask.execute();
    }

    private class ScannerSongsTask extends AsyncTask<Void, Void, Void> {

        String orderStr;

        @Override
        protected Void doInBackground(Void... voids) {
            mCursor = MediaStoreHelper.getAllSongsCursor(getContext(), orderStr);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            MusicCursorAdapter musicCursorAdapter = new MusicCursorAdapter(getContext(), mCursor);
            mListView.setAdapter(musicCursorAdapter);
            mListView.setOnItemClickListener(onItemClickListener);
        }
    }
}