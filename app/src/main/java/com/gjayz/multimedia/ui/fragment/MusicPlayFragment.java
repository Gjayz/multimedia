package com.gjayz.multimedia.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.MusicPlayCursorHelp;
import com.gjayz.multimedia.music.bean.SongInfo;
import com.gjayz.multimedia.ui.fragment.v4.BaseFragment;
import com.gjayz.multimedia.utils.ZXUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;


public class MusicPlayFragment extends BaseFragment {

    private static final String _ID = "_id";
    private static final String TAG = "MusicPlayFragment";
    private long mId;

    @BindView(R.id.music_album_iv)
    ImageView mAlbumView;
    private SongInfo mSongInfo;

    public static MusicPlayFragment createInstance(long id) {
        MusicPlayFragment musicPlayFragment = new MusicPlayFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(_ID, id);
        musicPlayFragment.setArguments(bundle);
        return musicPlayFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_music_play;
    }

    @Override
    public void init() {
        Log.d(TAG, "init: ");
        Bundle arguments = getArguments();
        if (arguments != null) {
            mId = arguments.getLong(_ID);
        }

        mSongInfo = MusicPlayCursorHelp.getSongInfo(getActivity(), mId);
        if (mSongInfo != null) {
            ImageLoader.getInstance().displayImage(ZXUtils.getAlbumArtUri(mSongInfo.getAlbumId()).toString(),
                    mAlbumView, new DisplayImageOptions.Builder().cacheInMemory(true)
                            .showImageOnLoading(R.drawable.ic_empty_music2)
                            .resetViewBeforeLoading(true).build());
        }
    }
}