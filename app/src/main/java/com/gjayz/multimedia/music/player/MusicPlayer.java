package com.gjayz.multimedia.music.player;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.gjayz.multimedia.IMediaPlaybackService;
import com.gjayz.multimedia.music.service.MusicService;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class MusicPlayer {

    public static IMediaPlaybackService sService = null;
    private static ConcurrentHashMap<ContextWrapper, ServiceBinder> mConnectionMap;
    private static final long[] sPlayList;

    static {
        mConnectionMap = new ConcurrentHashMap<>();
        sPlayList = new long[0];
    }

    public static ServiceToken bindToService(Context context, ServiceConnection serviceConnection) {
        Activity realActivity = ((Activity) context).getParent();
        if (realActivity == null) {
            realActivity = (Activity) context;
        }

        ContextWrapper contextWrapper = new ContextWrapper(realActivity);
        Intent service = new Intent(contextWrapper, MusicService.class);
        contextWrapper.startService(service);
        ServiceBinder serviceBinder = new ServiceBinder(serviceConnection, contextWrapper.getApplicationContext());
        boolean binded = contextWrapper.bindService(service, serviceBinder, Context.BIND_AUTO_CREATE);
        if (binded) {
            mConnectionMap.put(contextWrapper, serviceBinder);
            return new ServiceToken(contextWrapper);
        }
        return null;
    }

    public static void unbindFromService(final ServiceToken token) {
        if (token == null) {
            return;
        }
        final ContextWrapper mContextWrapper = token.mWrappedContext;
        final ServiceBinder mBinder = mConnectionMap.remove(mContextWrapper);
        if (mBinder == null) {
            return;
        }
        mContextWrapper.unbindService(mBinder);
        if (mConnectionMap.isEmpty()) {
            sService = null;
        }
    }

    private static void initPlaybackServiceWithSettings(Context context) {

    }

    public static void playList(long[] songIds, int position, ListType listType) {
        if (songIds == null || songIds.length == 0 || sService == null) {
            return;
        }

        try {
            long audioId = sService.getAudioId();
            int queuePosition = sService.getQueuePosition();
            //正在播放当前列表的点击歌曲，直接返回
            if (position >= 0 && queuePosition == position && audioId == songIds[position]) {
                long[] queue = sService.getQueue();
                if (Arrays.equals(queue, songIds)) {
                    sService.play();
                    return;
                }
            }

            if (position < 0) {
                position = 0;
            }

            sService.open(songIds, position, listType.mType, -1);
            sService.play();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static final class ServiceToken {
        public ContextWrapper mWrappedContext;

        public ServiceToken(final ContextWrapper context) {
            mWrappedContext = context;
        }
    }

    public static final class ServiceBinder implements ServiceConnection {
        private final ServiceConnection mCallback;
        private final Context mContext;


        public ServiceBinder(final ServiceConnection callback, final Context context) {
            mCallback = callback;
            mContext = context;
        }

        @Override
        public void onServiceConnected(final ComponentName className, final IBinder service) {
            sService = (IMediaPlaybackService) IMediaPlaybackService.Stub.asInterface(service);
            if (mCallback != null) {
                mCallback.onServiceConnected(className, service);
            }
            initPlaybackServiceWithSettings(mContext);
        }


        @Override
        public void onServiceDisconnected(final ComponentName className) {
            if (mCallback != null) {
                mCallback.onServiceDisconnected(className);
            }
            sService = null;
        }
    }
}