package com.gjayz.multimedia.utils;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class DeviceUtils {

    public static StringBuilder getDeviceInfos(Context context){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("=========================================\n");
        stringBuilder.append("=");
        stringBuilder.append(Build.MANUFACTURER);
        stringBuilder.append(" ");
        stringBuilder.append(Build.MODEL);
        stringBuilder.append("\n");
        stringBuilder.append("=========================================\n");
        stringBuilder.append("\n");
        stringBuilder.append(Build.MANUFACTURER);
        stringBuilder.append(" ");
        stringBuilder.append(Build.MODEL);
        stringBuilder.append(" (");
        stringBuilder.append(Build.DEVICE);
        stringBuilder.append(")\n");
        stringBuilder.append("===================ABI===================\n");
        stringBuilder.append("\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            for (int i = 0; i <Build.SUPPORTED_ABIS.length; i ++){
                stringBuilder.append("CPU ABI");
                stringBuilder.append(i);
                stringBuilder.append(":");
                stringBuilder.append(Build.SUPPORTED_ABIS[i]);
                stringBuilder.append("\n");
            }
        }
        stringBuilder.append("\n");

        stringBuilder.append("===================CPU===================\n");
        stringBuilder.append("\n");
        stringBuilder.append(getCpuName());
        stringBuilder.append("\n");
        getScreenSize(context);
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
     * @return
     */
    public static String getCpuName(){
        StringBuilder stringBuilder = new StringBuilder();
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr);
            while ((str2=localBufferedReader.readLine()) != null) {
                stringBuilder.append(str2);
                stringBuilder.append("\n");
            }
            localBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
