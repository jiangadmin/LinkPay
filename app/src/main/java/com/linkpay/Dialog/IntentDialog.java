package com.linkpay.Dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.UiThread;

import com.linkpay.Application.MyApplication;
import com.linkpay.R;

@SuppressLint("ShowToast")
public class IntentDialog {


    /**
     * 退出应用弹框
     *
     * @param activity
     */
    public static void ExitDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(R.drawable.ic_home_nav_exit);
        builder.setTitle("退出应用");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyApplication.getInstance().finishAllActivity();
            }
        });
        builder.show();
    }



}
