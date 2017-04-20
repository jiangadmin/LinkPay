package com.linkpay.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.linkpay.R;

/**
 * Created by jiang
 * on 2017/1/20.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:
 */
public class PushDialog extends AppCompatActivity {

    public static String mTitle;
    public static String mMessage;
    public static int mType; //0 系统信息  1 个人信息

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(mType == 0 ? R.drawable.ic_system_notice_icon : R.drawable.ic_user_notice_icon);
        builder.setTitle(mTitle);
        builder.setMessage(mMessage);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();

            }
        });

        builder.show();

    }
}
