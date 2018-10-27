package com.gjayz.multimedia.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.audiofx.AudioEffect;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.music.player.MusicPlayer;
import com.gjayz.multimedia.ui.activity.AlbumActivity;
import com.gjayz.multimedia.ui.activity.ArtistActivity;
import com.gjayz.multimedia.ui.activity.MusicPlayActivity;
import com.gjayz.multimedia.ui.activity.ToolBarActivity;

public class IntentUtil {

    public static final String KEY_TITLE = "key_title";
    public static final String KEY_TYPE = "key_type";

    public static final int TYPE_ABOUT = 0;
    public static final int TYPE_DEVICEINFO = 1;
    public static final int TYPE_NET = 2;
    public static final int TYPE_SETTINGS = 3;
    public static final int TYPE_TEST = 4;
    public static final int TYPE_TEST2 = 5;

    public static void startAboutActivity(Context context) {
        startToolBarActivity(context, context.getString(R.string.strAbout), TYPE_ABOUT);
    }

    public static void startDeviceInfosActivity(Context context) {
        startToolBarActivity(context, context.getString(R.string.strDeviceInfo), TYPE_DEVICEINFO);
    }

    public static void startNetActivity(Context context) {
        startToolBarActivity(context, context.getString(R.string.strNetwork), TYPE_NET);
    }

    public static void startSettingsActivity(Context context) {
        startToolBarActivity(context, context.getString(R.string.strSettings), TYPE_SETTINGS);
    }

    public static void startTestActivity(Context context) {
        startToolBarActivity(context, context.getString(R.string.strTest), TYPE_TEST);
    }

    public static void startTest2Activity(Context context) {
        startToolBarActivity(context, context.getString(R.string.strTest), TYPE_TEST2);
    }

    public static void startToolBarActivity(Context context, String title, int tpye) {
        Intent intent = new Intent(context, ToolBarActivity.class);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_TYPE, tpye);
        context.startActivity(intent);
    }

    public static void startEffectsActivity(Activity activity) {
        try {
            Intent effects = new Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL);
            effects.putExtra(AudioEffect.EXTRA_AUDIO_SESSION, MusicPlayer.getAudioSessionId());
            activity.startActivityForResult(effects, 666);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startAlbumActivity(Context context, int albumid, String album) {
        Intent intent = new Intent(context, AlbumActivity.class);
        intent.putExtra(AlbumActivity.KEY_ALBUM_ID, albumid);
        intent.putExtra(AlbumActivity.KEY_ALBUM, album);
        context.startActivity(intent);
    }

    public static void startArtistActivity(Context context, int artistId, String artist) {
        Intent intent = new Intent(context, ArtistActivity.class);
        intent.putExtra(ArtistActivity.KEY_ARTIST_ID, artistId);
        intent.putExtra(ArtistActivity.KEY_ARTIST, artist);
        context.startActivity(intent);
    }
}