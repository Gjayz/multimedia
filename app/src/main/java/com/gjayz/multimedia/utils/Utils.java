package com.gjayz.multimedia.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class Utils {
    public static void copyTextToClipBroad(Context context, String data) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Label", data);
        clipboardManager.setPrimaryClip(clipData);
    }
}
