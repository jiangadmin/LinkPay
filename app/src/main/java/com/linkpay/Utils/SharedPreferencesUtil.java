package com.linkpay.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by 垚垚
 * on 15/7/23.
 * Email: www.fangmu@qq.com
 * Phone：18661201018
 * Purpose: 本地存储工具
 */

public class SharedPreferencesUtil {
    private SharedPreferences sp;
    private Editor edit;
    private static SharedPreferencesUtil spu;

    public static SharedPreferencesUtil getInstance(Context c) {
        if (spu == null) {
            spu = new SharedPreferencesUtil();
            spu.sp = c.getSharedPreferences("Agreement", 0);
            spu.edit = spu.sp.edit();
        }
        return spu;
    }

    private SharedPreferencesUtil() {
    }

    public String getStringValue(String key) {
        return sp.getString(key, null);
    }

    public long getLongValue(String key) {
        return sp.getLong(key, 0);
    }

    public int getIntValue(String key) {
        return sp.getInt(key, 0);
    }

    public boolean getbooleanValue(String key) {
        return sp.getBoolean(key, false);
    }

    public void putValue(String key, boolean value) {
        edit.putBoolean(key, value);
        edit.commit();
    }

    public void putValue(String key, String value) {
        edit.putString(key, value);
        edit.commit();
    }

    public void putValue(String key, long value) {
        edit.putLong(key, value);
        edit.commit();
    }

    public void putValue(String key, int value) {
        edit.putInt(key, value);
        edit.commit();
    }


}
