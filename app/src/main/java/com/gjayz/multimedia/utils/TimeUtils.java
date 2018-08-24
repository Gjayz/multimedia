package com.gjayz.multimedia.utils;

public class TimeUtils {

    public static String formatMusicTime(long duration) {
        StringBuilder stringBuilder = new StringBuilder();
        if (duration > 0) {
            int second = (int) (duration % 60);
            int min = (int) (duration / 60);
            if (min < 0) {
                min = 0;
            }
            stringBuilder.append(min);
            stringBuilder.append(":");

            if (second < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(second);
        } else {
            stringBuilder.append("0:00");
        }
        return stringBuilder.toString();
    }
}
