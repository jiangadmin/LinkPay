package com.linkpay.Service;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linkpay.Activity.Login.LoginActivity;
import com.linkpay.Activity.Login.LoginLoucsActivity;
import com.linkpay.Application.Const;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Dialog.UpdateWindow;
import com.linkpay.Entity.UpdateEntity;
import com.linkpay.Utils.HttpUtil;
import com.linkpay.Utils.LogUtil;
import com.linkpay.Utils.SharedPreferencesUtil;
import com.linkpay.Utils.TabToast;
import com.linkpay.Utils.ToolUtil;
import com.linkpay.Utils.VersionUtil;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jiangmac
 * on 15/12/16.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:检查更新
 */
public class UpdateService extends AsyncTask<String, IntentDialog, UpdateEntity> {
    private Activity activity;
    private ProgressDialog dialog = null;
    //判断是否从欢迎页进来
    private boolean uptologin;

    public UpdateService(Activity activity) {
        super();
        uptologin = true;
        this.activity = activity;
    }

    public UpdateService(Activity activity, ProgressDialog dialog) {
        super();
        uptologin = false;
        this.activity = activity;
        this.dialog = dialog;
    }

    @Override
    protected UpdateEntity doInBackground(String... params) {
        HashMap mp = new HashMap<>();
        mp.put("barnd", Const.BRAND);
        String result = HttpUtil.doPostforDES(Const.URL + "AndroidVersionServlet", mp);
        UpdateEntity info = null;
        if (result != null) {
            try {
                info = new Gson().fromJson(result, UpdateEntity.class);
            } catch (Exception e) {
                info = new UpdateEntity();
                info.setResultCode(-11);
                info.setMessage("解析失败!");
            }
        } else {
            info = new UpdateEntity();
            info.setResultCode(-12);
            info.setMessage("服务器走丢了!");
        }
        return info;
    }

    @Override
    protected void onPostExecute(UpdateEntity info) {
        if (dialog != null)
            dialog.dismiss();
        if (info.getVersion() == null) {
            TabToast.makeText(activity, "暂未开启服务器！", Toast.LENGTH_LONG);
            return;
        }

        String Vthis = VersionUtil.getVersion(activity).replace(".", "").trim();
        while (Vthis.length() < 7) {
            //补位操作
            Vthis = Vthis + "0";
        }
        int vthis = Integer.parseInt(Vthis);

        String Vhttp = info.getVersion().replace(".", "").trim();
        while (Vhttp.length() < 7) {
            //补位操作
            Vhttp = Vhttp + "0";
        }
        int vhttp = Integer.parseInt(Vhttp);


        if (info != null) {
            //判断是否需要更新
            if (vthis >= vhttp) {
                LogUtil.e("更新检查", "当前最新\n服务器版本:" + vhttp + "\n本地的版本:" + vthis);
                //是否登录进入
                if (uptologin) {
                    //延迟加载
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                            //判断当前账户是否有手势密码
                            if (ToolUtil.isEmpty(SharedPreferencesUtil.getInstance(activity).getStringValue(SharedPreferencesUtil.getInstance(activity).getStringValue("phone") + "locuspwd"))) {
                                //进入账号登录页
                                activity.startActivity(new Intent(activity, LoginActivity.class));
                            } else {
                                //进入手势密码登录页
                                activity.startActivity(new Intent(activity, LoginLoucsActivity.class));
                            }
                        }

                    }, 1 * 1000);

                    super.onPostExecute(info);
                } else {
                    TabToast.makeText(activity, "当前已是最新版本！");

                }
            } else {
                //本地需要更新，当前只判断强制更新，非强制更新未判断
                LogUtil.e("需要更新", "当前最新\n服务器版本:" + vhttp + "\n本地版本：" + vthis);
                //启动更新页
                Intent intent = new Intent();
                intent.setClass(activity, UpdateWindow.class);
                intent.putExtra("text", info.getText());
                intent.putExtra("needupdate", info.getNeedUpdate());
                intent.putExtra("url", info.getUrl());
                intent.putExtra("version", info.getVersion());
                intent.putExtra("uptologin", uptologin);
                activity.startActivity(intent);
            }
            return;
        }


    }
}

