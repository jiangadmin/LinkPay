package com.linkpay.Dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.Activity.Login.LoginActivity;
import com.linkpay.Activity.Login.LoginLoucsActivity;
import com.linkpay.R;
import com.linkpay.Service.MyDownService;
import com.linkpay.Utils.SharedPreferencesUtil;
import com.linkpay.Utils.ToolUtil;

import java.io.File;

/**
 * Created by jiangmac
 * on 15/12/16.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:下载更新
 */
public class UpdateWindow extends BaseActivity {
    TextView title;
    TextView text;
    RelativeLayout jinduview;
    ProgressBar jindutiao;
    TextView jindu;
    Button esc, ok;
    LinearLayout btnview;
    String fileName;

    String TEXT, URL, VERSION;
    int NEEDUPDATE;
    boolean UPTOLOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_update);
        initview();
        Intent intent = getIntent();
        TEXT = intent.getStringExtra("text");
        URL = intent.getStringExtra("url");
        VERSION = intent.getStringExtra("version");
        NEEDUPDATE = intent.getIntExtra("needupdate", 0);
        UPTOLOGIN = intent.getBooleanExtra("uptologin", false);

        text.setText("是否下载更新？");
        jinduview.setVisibility(View.GONE);

        if (NEEDUPDATE == 0) {
            ok.setVisibility(View.VISIBLE);
            esc.setVisibility(View.GONE);
        } else {
            ok.setVisibility(View.VISIBLE);
            esc.setVisibility(View.VISIBLE);
        }

    }

    private void initview() {
        btnview = (LinearLayout) findViewById(R.id.update_btn_view);
        esc = (Button) findViewById(R.id.update_esc);
        ok = (Button) findViewById(R.id.update_ok);
        title = (TextView) findViewById(R.id.update_title);
        text = (TextView) findViewById(R.id.update_info);
        jinduview = (RelativeLayout) findViewById(R.id.update_view);
        jindutiao = (ProgressBar) findViewById(R.id.update_jindutiao);
        jindu = (TextView) findViewById(R.id.update_jindu);

        title.setText("发现新版本");
    }

    public void updateok(View view) {
        register();
        title.setText("正在下载...");
        text.setVisibility(View.GONE);
        jinduview.setVisibility(View.VISIBLE);
        btnview.setVisibility(View.GONE);
        Intent it = new Intent(this, MyDownService.class);

        fileName = URL.substring(URL.lastIndexOf("/") + 1);
        it.putExtra("msg", URL);
        startService(it);
        jindu.setText("0%");
    }

    public void updateesc(View view) {
        finish();
        if (UPTOLOGIN) {
            if (ToolUtil.isEmpty(SharedPreferencesUtil.getInstance(this).getStringValue(SharedPreferencesUtil.getInstance(this).getStringValue("phone") + "locuspwd"))) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                startActivity(new Intent(this, LoginLoucsActivity.class));
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (NEEDUPDATE == 0)
            return;
        super.onBackPressed();
    }

    // 退出activity时，关闭服务，取消注册广播
    @Override
    protected void onDestroy() {
        stopService(new Intent(this, MyDownService.class));
        try {
            unregisterReceiver(recceiver);
        } catch (Exception e) {
        }
        super.onDestroy();
    }

    // 注册广播
    public void register() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("progress");
        registerReceiver(recceiver, filter);
    }

    // 广播接受处理，progress进度在0-100之间，-1时表示下载已完成
    BroadcastReceiver recceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            int i = intent.getIntExtra("progress", 0);
            if (i != -1) {
                jindu.setText(i + "%");
                jindutiao.setProgress(i);
            } else {
                stopService(new Intent(UpdateWindow.this, MyDownService.class));
                jindu.setText("下载完成");
                installApk();
            }
        }

    };

    private void installApk() {

        /*********下载完成，点击安装***********/
        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + File.separator + fileName));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        /**********加这个属性是因为使用Context的startActivity方法的话，就需要开启一个新的task**********/
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        this.startActivity(intent);
    }
}
