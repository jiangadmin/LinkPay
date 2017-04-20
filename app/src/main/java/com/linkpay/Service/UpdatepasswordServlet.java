package com.linkpay.Service;
/**
 * Created by jiangmac
 * on 15/11/18.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:修改密码
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.linkpay.Application.Const;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Entity.LoginInfoEntity;
import com.linkpay.Utils.HttpUtil;
import com.linkpay.Utils.SharedPreferencesUtil;
import com.linkpay.Utils.TabToast;

import java.util.HashMap;
import java.util.Map;

public class UpdatepasswordServlet extends AsyncTask<UpdatepasswordServlet.UpdatePasswordInfo, IntentDialog, LoginInfoEntity> {
    private Activity activity;
    String pwd;

    public UpdatepasswordServlet(Activity activity, ProgressDialog progressdialog) {
        super();
        this.activity = activity;
        this.loading = progressdialog;
    }

    private ProgressDialog loading = null;

    /**
     * phone money pici liushui saru
     */
    @Override
    protected LoginInfoEntity doInBackground(UpdatePasswordInfo... arg0) {
        Map<String, String> mp = new HashMap<>();
        UpdatePasswordInfo info = arg0[0];
        mp.put("phone", info.getPhone());
        mp.put("oldPassword", info.getOldpwd());
        mp.put("password", info.getNewpwd());
        String res = HttpUtil.doPostforDES(Const.URL + "APP_ChangePasswordServlet", mp);
        LoginInfoEntity entity = null;
        if (res != null) {
            try {
                entity = new Gson().fromJson(res, LoginInfoEntity.class);
            } catch (Exception e) {
                entity = new LoginInfoEntity();
                entity.setResultCode(-11);
                entity.setMessage("报文解析失败!");
            }
        } else {
            entity = new LoginInfoEntity();
            entity.setResultCode(-12);
            entity.setMessage("服务器走丢了!");
        }
        return entity;
    }

    @Override
    protected void onPostExecute(LoginInfoEntity res) {
        if (loading != null) {
            loading.dismiss();
        }
        if (res.getResultCode() == 0) {
            TabToast.makeText(activity, "修改密码成功");
            SharedPreferencesUtil.getInstance(activity).putValue("pwd", pwd);
            activity.finish();

        } else {
            TabToast.makeText(activity, res.getMessage());
        }
        super.onPostExecute(res);
    }

    public static class UpdatePasswordInfo {
        public String phone;
        public String oldpwd;
        public String newpwd;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getOldpwd() {
            return oldpwd;
        }

        public void setOldpwd(String oldpwd) {
            this.oldpwd = oldpwd;
        }

        public String getNewpwd() {
            return newpwd;
        }

        public void setNewpwd(String newpwd) {
            this.newpwd = newpwd;
        }

    }
}
