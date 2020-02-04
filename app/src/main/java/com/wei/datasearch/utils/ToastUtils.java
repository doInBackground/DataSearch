package com.wei.datasearch.utils;

import android.content.Context;
import android.widget.Toast;

//import com.wei.datasearch.base.MyApplication;

/**
 * @Author WCL
 * @Date 2019/6/19 11:50
 * @Version 1.0
 * @Description toast工具, 防止重复多次点击显示多个toast.
 */
public class ToastUtils {

    private static Toast mToast = null;

    public static void showToast(Context context, String text, int duration) {
//        Context context = MyApplication.getContext();
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public static void showToast(Context context, int strId, int duration) {
//        Context context = MyApplication.getContext();
        showToast(context, context.getString(strId), duration);
    }

}

