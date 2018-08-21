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
import com.gjayz.multimedia.music.bean.ArtistInfo;
import com.gjayz.multimedia.utils.ZXUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.Holder> {

    private Context mContext;
    private List<ArtistInfo> mArtistInfoList;

    public ArtistAdapter(Context context, List<ArtistInfo> artistInfoList) {
        this.mContext = context;
        this.mArtistInfoList = artistInfoList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_artist_layout, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ArtistInfo artistInfo = mArtistInfoList.get(position);
        holder.artist_name_tv.setText(artistInfo.getArtist());
        List<Integer> album_ids = artistInfo.getAlbum_ids();
        if (album_ids.size() > 0){
            ImageLoader.getInstance().displayImage(ZXUtils.getAlbumArtUri(album_ids.get(0)).toString(),
                    holder.artist_icon_iv, new DisplayImageOptions.Builder().cacheInMemory(true)
                            .resetViewBeforeLoading(true).build());
        }else {

        }
    }

    @Override
    public int getItemCount() {
        return mArtistInfoList == null ? 0 : mArtistInfoList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        ImageView artist_icon_iv;
        TextView artist_name_tv;

        public Holder(View itemView) {
            super(itemView);
            artist_icon_iv = itemView.findViewById(R.id.artist_icon_iv);
            artist_name_tv = itemView.findViewById(R.id.artist_name_tv);
        }
    }
}
