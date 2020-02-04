package com.wei.datasearch.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author WCL
 * @Date 2019/6/24 15:30
 * @Version 1.0
 * @Description 加载本地图片的工具类.
 */
public class PicUtils {

    /**
     * 加载本地图片或资源图片时,根据图片,求采样率(缩放)inSampleSize的值.
     *
     * @param context   上下文
     * @param file      图片来源(String表示图片的绝对路径;Integer表示图片的资源地址)
     * @param reqHeight 图片控件最大高度
     * @param reqWidth  图片控件最大宽度
     * @return
     */
    public static int inSampleSize(Context context, Object file, int reqHeight, int reqWidth) {
        int inSampleSize = 1;
        Bitmap bitmap = null;

        //这里我们只需要图片的宽高，而不需要图片的真实数据，所以没必要将图片数据加载到内存中.
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inJustDecodeBounds = true;

        //inJustDecodeBounds属性下,得到的BitMap对象为null,但是图片信息以及载入到Options中.
        if (file instanceof String) {
            bitmap = BitmapFactory.decodeFile((String) file, ops);
        } else if (file instanceof Integer) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), (Integer) file, ops);
        }

//        if (bitmap == null) {//没有图
//            return inSampleSize;
//        }
        //得到原图的宽高.
        int height = ops.outHeight;
        int width = ops.outWidth;

        Log.d("www", "原图的宽高:" + width + "+" + height);

        if (height > reqHeight || width > reqWidth) {//原图宽高比需要的大,需要缩小.
            //去较大的缩放比例
            if (height / reqHeight > width / reqWidth) {
                inSampleSize = height / reqHeight;
            } else {
                inSampleSize = width / reqWidth;
            }
        }
        return inSampleSize;
    }


    /**
     * 根据Bitmap,保存图片到指定目录.
     *
     * @param bm      Bitmap
     * @param path    图片要保存的文件夹
     * @param picName 图片名字
     */
    public static void saveBitmapToFile(Bitmap bm, String path, String picName) {
        File outFile = new File(path, picName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(outFile);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
