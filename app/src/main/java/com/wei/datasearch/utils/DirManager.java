package com.wei.datasearch.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @Author WCL
 * @Date 2019/6/24 11:07
 * @Version 1.0
 * @Description 管理产品对应简图的存储位置
 * <p>
 * [internal storage(需root才能看到)]
 * shared preference文件，数据库文件，也都存储在这里.目录为data/data/< package name >/files/
 * getFilesDir:获取到 Android/data/data/你的应用的包名/files/目录.对应应用详情里的"清除数据".
 * getCacheDir:获取到 Android/data/data/你的应用的包名/cache/目录.对应应用详情里的"清除缓存".
 * [external  storage]
 * getExternalFilesDir:获取到 SDCard/Android/data/你的应用的包名/files/目录.对应应用详情里的"清除数据".
 * getExternalCacheDir:获取到 SDCard/Android/data/你的应用的包名/cache/目录.对应应用详情里的"清除缓存".
 * 应用被卸载,这些目录下的所有文件都会被删除.
 */
public class DirManager {

    /**
     * 获取APP专属文件(随APP删除一起删除)的绝对路径.
     * 有SD卡就优先获取外部路径,没有就获取内部路径.
     *
     * @param context
     * @return
     */
    public static String getFilesDir(Context context) {
        String absolutePath;//绝对路径
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {//外部存储可用
            File externalFile = context.getExternalFilesDir(null);//外部数据目录
            absolutePath = externalFile.getAbsolutePath();//绝对路径
        } else { //外部存储不可用
            File internalFile = context.getFilesDir();//内部数据目录
            absolutePath = internalFile.getAbsolutePath();//绝对路径
        }
        return absolutePath;
    }

    /**
     * 获取APP专属缓存(随APP删除一起删除)的绝对路径.
     * 有SD卡就优先获取外部路径,没有就获取内部路径.
     *
     * @param context
     * @return
     */
    public static String getCacheDir(Context context) {
        String absolutePath;//绝对路径
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {//外部存储可用
            File externalFile = context.getExternalCacheDir();//外部缓存目录
            absolutePath = externalFile.getAbsolutePath();//绝对路径
        } else { //外部存储不可用
            File internalFile = context.getCacheDir();//内部缓存目录
            absolutePath = internalFile.getAbsolutePath();//绝对路径
        }
        return absolutePath;
    }

}
