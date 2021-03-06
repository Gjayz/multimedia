package com.gjayz.multimedia.utils;

import android.content.Context;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DeviceUtils {

    public static StringBuilder getDeviceInfos(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("=========================================\n");
        stringBuilder.append("=");
        stringBuilder.append(Build.MANUFACTURER);
        stringBuilder.append(" ");
        stringBuilder.append(Build.MODEL);
        stringBuilder.append("\n");
        stringBuilder.append("=========================================\n");
        stringBuilder.append("\n");
        stringBuilder.append("\n");
        stringBuilder.append(Build.MANUFACTURER);
        stringBuilder.append(" ");
        stringBuilder.append(Build.MODEL);
        stringBuilder.append(" (");
        stringBuilder.append(Build.DEVICE);
        stringBuilder.append(")\n");
        stringBuilder.append("===================ABI===================\n");
        stringBuilder.append("\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (int i = 0; i < Build.SUPPORTED_ABIS.length; i++) {
                stringBuilder.append("CPU ABI");
                stringBuilder.append(i);
                stringBuilder.append(":");
                stringBuilder.append(Build.SUPPORTED_ABIS[i]);
                stringBuilder.append("\n");
            }
        }
        stringBuilder.append("\n");
        stringBuilder.append("\n");

        stringBuilder.append("===================CPU===================\n");
        stringBuilder.append("\n");
        stringBuilder.append(getCpuName());
        stringBuilder.append("\n");
        stringBuilder.append("\n");

        return stringBuilder;
    }

    public static String getScreenSize(Context context) {
        StringBuilder sb = new StringBuilder();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        sb.append("密度：");
        sb.append(displayMetrics.density * DisplayMetrics.DENSITY_DEFAULT);
        sb.append("dp");
        sb.append("/");
        sb.append(displayMetrics.scaledDensity);
        sb.append("/");
        sb.append(displayMetrics.scaledDensity);
        sb.append("x");
        Log.d("TAG", "getScreenSize: " + displayMetrics);
        return sb.toString();
    }

    /**
     * 获取CPU型号
     *
     * @return
     */
    public static String getCpuName() {
        StringBuilder stringBuilder = new StringBuilder();
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr);
            while ((str2 = localBufferedReader.readLine()) != null) {
                stringBuilder.append(str2);
                stringBuilder.append("\n");
            }
            localBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static StringBuilder getEncoderAndDecoders() {
        StringBuilder sb = new StringBuilder();

        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(codecInfo.getName());
            stringBuilder.append("\n");
            String[] supportedTypes = codecInfo.getSupportedTypes();
            for (String supportedType : supportedTypes) {
                stringBuilder.append(supportedType);
                stringBuilder.append("\n");
            }
            stringBuilder.append("\n");

            if (codecInfo.isEncoder()) {
                sb.insert(0, stringBuilder);
            } else {
                sb.append(stringBuilder);
            }
        }
        sb.append("\n");

        sb.insert(0, "\n");
        sb.insert(0, "\n");
        sb.insert(0, "============Encoders and Decoders========\n");
        return sb;
    }
}