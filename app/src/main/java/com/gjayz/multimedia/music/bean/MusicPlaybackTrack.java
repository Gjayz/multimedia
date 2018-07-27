package com.gjayz.multimedia.music.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.gjayz.multimedia.music.player.ListType;

public class MusicPlaybackTrack implements Parcelable {

    public static final Parcelable.Creator<MusicPlaybackTrack> CREATOR = new Parcelable.Creator<MusicPlaybackTrack>() {
        @Override
        public MusicPlaybackTrack createFromParcel(Parcel source) {
            return new MusicPlaybackTrack(source);
        }

        @Override
        public MusicPlaybackTrack[] newArray(int size) {
            return new MusicPlaybackTrack[size];
        }
    };
    public long mId;
    public long mSourceId;
    public ListType mListType;
    public int mSourcePosition;

    public MusicPlaybackTrack(long id, long sourceId, ListType type, int sourcePosition) {
        mId = id;
        mSourceId = sourceId;
        mListType = type;
        mSourcePosition = sourcePosition;
    }

    public MusicPlaybackTrack(Parcel in) {
        mId = in.readLong();
        mSourceId = in.readLong();
        mListType = ListType.getTypeById(in.readInt());
        mSourcePosition = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeLong(mSourceId);
        dest.writeInt(mListType.mType);
        dest.writeInt(mSourcePosition);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MusicPlaybackTrack) {
            MusicPlaybackTrack other = (MusicPlaybackTrack) o;
            if (other != null) {
                return mId == other.mId
                        && mSourceId == other.mSourceId
                        && mListType == other.mListType
                        && mSourcePosition == other.mSourcePosition;

            }
        }
        return super.equals(o);
    }
}
