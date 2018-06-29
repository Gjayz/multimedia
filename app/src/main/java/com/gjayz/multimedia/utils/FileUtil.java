package com.gjayz.multimedia.utils;

import java.io.File;
import java.text.DecimalFormat;

public class FileUtil {

    public static DecimalFormat sDecimalFormat = new DecimalFormat("0.0");

    public static boolean isExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static String[] SIZE_UNIT = new String[]{"B", "K", "M", "G"};

    public static String getFileSizeAutoFormat(String filePath) {
        File file = new File(filePath);
        long length = file.length();
        return formatFileSize(0, length);
    }

    public static String formatFileSize(int unit, float size){
        if (size >= 1024){
            return formatFileSize(unit + 1, size / 1024f);
        }else {
            return sDecimalFormat.format(size) + SIZE_UNIT[unit];
        }
    }
}