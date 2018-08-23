package com.gjayz.multimedia.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.bean.Album;
import com.gjayz.multimedia.ui.activity.AlbumActivity;
import com.gjayz.multimedia.utils.ZXUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.Holder> {

    private Context mContext;
    private List<Album> mAlbumList;

    public AlbumAdapter(Context context, List<Album> albumList) {
        this.mContext = context;
        this.mAlbumList = albumList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_album_layout, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Album album = mAlbumList.get(position);
        holder.mAlbumNameView.setText(album.getAlbum());
        holder.mAlbumArtistView.setText(album.getArtist());
        ImageLoader.getInstance().displayImage(ZXUtils.getAlbumArtUri(album.getAlbum_id()).toString(),
                holder.mAlbumIconView, new DisplayImageOptions.Builder().cacheInMemory(true)
                        .resetViewBeforeLoading(true).build());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlbumActivity.newIntent(mContext, album.getAlbum_id(), album.getAlbum());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAlbumList == null ? 0 : mAlbumList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

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
