package com.gjayz.multimedia.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.bean.Album;
import com.gjayz.multimedia.utils.ZXUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ArtistAlbumAdapter extends BaseQuickAdapter<Album, ArtistAlbumAdapter.Holder> {

    public ArtistAlbumAdapter(Context context, List<Album> albumList) {
        super(R.layout.item_album_layout, albumList);
        this.mContext = context;
    }

    @Override
    protected void convert(Holder helper, Album item) {
        helper.mAlbumNameView.setText(item.getAlbum());
        helper.mAlbumArtistView.setText(item.getArtist());
        ImageLoader.getInstance().displayImage(ZXUtils.getAlbumArtUri(item.getAlbum_id()).toString(),
                helper.mAlbumIconView, new DisplayImageOptions.Builder().cacheInMemory(true)
                        .resetViewBeforeLoading(true).build());
    }

    static class Holder extends BaseViewHolder {

        ImageView mAlbumIconView;
        TextView mAlbumNameView;
        TextView mAlbumArtistView;

        public Holder(View itemView) {
            super(itemView);
            mAlbumIconView = itemView.findViewById(R.id.album_icon_view);
            mAlbumNameView = itemView.findViewById(R.id.album_name_view);
            mAlbumArtistView = itemView.findViewById(R.id.album_artist_view);
        }
    }
}