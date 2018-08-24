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
import com.gjayz.multimedia.music.bean.School;
import com.gjayz.multimedia.music.bean.SongInfo;
import com.gjayz.multimedia.ui.activity.SchoolActivity;
import com.gjayz.multimedia.utils.ZXUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.Holder> {

    private Context mContext;
    private List<School> mSchoolsList;

    public SchoolAdapter(Context context, List<School> schools) {
        this.mContext = context;
        this.mSchoolsList = schools;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_school_layout, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final School school = mSchoolsList.get(position);
        holder.school_name_tv.setText(school.getName());
        List<SongInfo> songInfoList = school.getAlbum_id();
        if (songInfoList.size() > 0) {
            ImageLoader.getInstance().displayImage(ZXUtils.getAlbumArtUri(songInfoList.get(0).getAlbumId()).toString(),
                    holder.school_icon_iv, new DisplayImageOptions.Builder().cacheInMemory(true)
                            .resetViewBeforeLoading(true).build());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SchoolActivity.newIntent(mContext, school.getId(), school.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSchoolsList == null ? 0 : mSchoolsList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        ImageView school_icon_iv;
        TextView school_name_tv;

        public Holder(View itemView) {
            super(itemView);
            school_icon_iv = itemView.findViewById(R.id.school_icon_iv);
            school_name_tv = itemView.findViewById(R.id.school_name_tv);
        }
    }
}
