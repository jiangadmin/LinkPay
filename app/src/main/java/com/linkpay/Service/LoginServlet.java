package com.linkpay.Service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.google.gson.Gson;
import com.linkpay.Activity.Login.ForgetPwdActivity;
import com.linkpay.Activity.MainActivity;
import com.linkpay.Application.Const;
import com.linkpay.Application.KEY_NAME;
import com.linkpay.Application.MyApplication;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Entity.ReLogin;
import com.linkpay.Utils.HttpUtil;
import com.linkpay.Utils.LogUtil;
import com.linkpay.Utils.SharedPreferencesUtil;
import com.linkpay.Utils.TabToast;
import com.linkpay.View.LocusPassWordView;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangadmin
 * on 15/10/16.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose: 登录
 */

public class LoginServlet extends AsyncTask<LoginServlet.LoginInfo, IntentDialog, ReLogin> {
    private static final String TAG = "LoginServlet";
    Activity activity;
    private String phone, pwd = null;
    private ProgressDialog progressDialog = null;
    private LocusPassWordView locuspwd = null;

    public LoginServlet(Activity activity, ProgressDialog progressDialog) {
        this.activity = activity;
        this.progressDialog = progressDialog;
    }

    public LoginServlet(Activity activity, ProgressDialog progressDialog, LocusPassWordView locuspwd) {
        this.activity = activity;
        this.progressDialog = progressDialog;
        this.locuspwd = locuspwd;
    }

    @Override
    protected ReLogin doInBackground(LoginServlet.LoginInfo... params) {
        Map<String, String> mp = new HashMap<>();
        LoginInfo info = params[0];
        mp.put("barnd", Const.BRAND);

        String res = null;
        if (info.getUnionid() != null) {
            mp.put("unionid", info.getUnionid());
            res = HttpUtil.doPostforDES(Const.URL + "APP_OpenIdLogInServlet", mp);
        } else {
            mp.put("phone", info.getPhone());
            mp.put("password", info.getPwd());
            phone = info.getPhone();
            pwd = info.getPwd();

            res = HttpUtil.doPostforDES(Const.URL + "APP_LogInServlet", mp);
        }
        ReLogin entity = null;
        if (res != null) {
            try {
                entity = new Gson().fromJson(res, ReLogin.class);
            } catch (Exception e) {
                LogUtil.e(TAG, entity.getAccountType() + "");
                entity = new ReLogin();
                entity.setResultCode(-11);
                entity.setMessage("报文解析失败!");
            }
        } else {
            entity = new ReLogin();
            entity.setResultCode(-12);
            entity.setMessage("服务器走丢了!");
        }
        return entity;

    }

    @Override
    protected void onPostExecute(ReLogin res) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        if (res.getResultCode() == 0) {
            LogUtil.e(this.toString(), res.toString());
            // 设置帐号
            MiPushClient.setUserAccount(activity, res.getPlId(), null);
//            MiPushClient.setUserAccount(activity, res.getPlId(), null);

            if (res.getPhone() != null) {
                Const.LOGIN_PHONE = res.getPhone();
                SharedPreferencesUtil.getInstance(activity).putValue(KEY_NAME.手机号, Const.LOGIN_PHONE);
            }
            Const.PIID = res.getPlId();
            Const.SARULRUID = res.getSaruLruid();
            Const.ACCOUNT_TYPE = res.getAccountType();
            Const.USERTYPE_ERROR_MESSAGE = res.getRefuseMessage();
            Const.PROC_SN = res.getProcSn();
            Const.PROC_SN_HTTP = res.getTaiMaUrl() + "SaruPayForwardServlet?procSn=" + res.getProcSn();

            //存储账号密码
            SharedPreferencesUtil.getInstance(activity).putValue(KEY_NAME.密码, pwd);
            SharedPreferencesUtil.getInstance(activity).putValue(KEY_NAME.手机号, phone);
            SharedPreferencesUtil.getInstance(activity).putValue(KEY_NAME.PLID, res.getPlId());

            //关闭当前的页面
            MyApplication.getInstance().finishActivity(activity);

            activity.startActivity(new Intent(activity, MainActivity.class));

        } else if (res.getResultCode() == -2) {
            if (res.getErrorCount() > 3) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("警告");
                builder.setMessage("连续错误次数过多，请稍后再试！");
                builder.setPositiveButton("忘记密码", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.startActivity(new Intent(activity, ForgetPwdActivity.class));
                    }
                });
                builder.show();
            }
            TabToast.makeText(activity, res.getMessage());
        } else {
            LogUtil.e(this.toString(), res.getMessage());
            TabToast.makeText(activity, res.getMessage());
            if (locuspwd != null) {
                locuspwd.clearPassword();
            }
        }
        super.onPostExecute(res);
    }

    public static class LoginInfo {
        public String phone;
        public String pwd;
        private String unionid;

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }
    }
}
