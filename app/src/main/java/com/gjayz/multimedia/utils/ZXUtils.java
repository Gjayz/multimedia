package com.gjayz.multimedia.utils;

import android.content.ContentUris;
import android.net.Uri;

public class ZXUtils {
    public static Uri getAlbumArtUri(int albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }
}
