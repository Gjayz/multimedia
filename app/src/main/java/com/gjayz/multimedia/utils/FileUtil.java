package com.gjayz.multimedia.utils;

import java.io.File;
import java.text.DecimalFormat;

public class FileUtil {

    public static String[] SIZE_UNIT = new String[]{"B", "K", "M", "G"};

    public static final int UNIT_B = 0x00000001;
    public static final int UNIT_K = 0x00000400;
    public static final int UNIT_M = 0x00100000;
    public static final int UNIT_G = 0x40000000;

    public static DecimalFormat sDecimalFormat = new DecimalFormat("0.0");

    public static boolean isExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

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

    /**
     * 转换文件单位大小
     * @param fromUnit
     * @param toUnit
     * @param size
     * @return
     */
    public static double changeFileUnit(int fromUnit, int toUnit, long size){
        return  size * fromUnit * 1d / toUnit ;
    }
}