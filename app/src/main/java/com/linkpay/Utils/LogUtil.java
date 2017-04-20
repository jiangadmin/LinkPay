
package com.linkpay.Utils;

import android.app.Activity;
import android.util.Log;

import com.linkpay.Application.Const;

/**
 * Created by 垚垚
 * on 15/7/23.
 * Email: www.fangmu@qq.com
 * Phone：18661201018
 * Purpose: LOG日志
 */
public class LogUtil {
    public static void v(String key, String msg) {
        if (Const.LOGSHOW)
            Log.v(key, ">>" + msg);
    }

    public static void d(String key, String msg) {
        if (Const.LOGSHOW)
            Log.d(key, ">>" + msg);
    }

    public static void i(String key, String msg) {
        if (Const.LOGSHOW)
            Log.i(key, ">>" + msg);
    }

    public static void w(String key, String msg) {
        if (Const.LOGSHOW)
            Log.w(key, ">>" + msg);
    }

    public static void e(String key, String msg) {
        if (Const.LOGSHOW)
            Log.e(key, ">>" + msg);
    }
    public static void e(String key, int msg) {
        if (Const.LOGSHOW)
            Log.e(key, ">>" + msg);
    }
    public static void ee(String key, String msg) {
        if (Const.LOGSHOW)
            Log.e(key, ">>" + msg);
    }

    public static void e(Activity activity, String msg) {
        if (Const.LOGSHOW)
            Log.e(activity.toString(), ">>" + msg);
    }

}
