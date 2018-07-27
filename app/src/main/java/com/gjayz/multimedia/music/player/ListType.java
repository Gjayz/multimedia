package com.gjayz.multimedia.music.player;

public enum ListType {
    Song(0),
    Artist(1),
    Album(2),
    PlayList(3);

    public int mType;

    ListType(final int id) {
        mType = id;
    }

    public static ListType getTypeById(int type) {
        for (ListType listType : values()) {
            if (listType.mType == type) {
                return listType;
            }
        }

        throw new IllegalArgumentException("Unrecognized id: " + type);
    }
}