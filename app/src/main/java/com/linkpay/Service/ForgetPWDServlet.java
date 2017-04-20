package com.linkpay.Service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.linkpay.Application.Const;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Entity.LoginInfoEntity;
import com.linkpay.Utils.HttpUtil;
import com.linkpay.Utils.TabToast;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by jiangmac
 * on 16/04/08.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:忘记密码
 */
public class ForgetPWDServlet extends AsyncTask<ForgetPWDServlet.wjpwdInfo, IntentDialog, LoginInfoEntity> {
    private Activity activity;

    public ForgetPWDServlet(Activity activity, ProgressDialog progressdialog) {
        super();
        this.activity = activity;
        this.loading = progressdialog;
    }

    private ProgressDialog loading = null;

    /**
     * phone money pici liushui saru
     */
    public static class wjpwdInfo {
        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }


        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        private String phone;
        private String code;
        private String password;

    }

    @Override
    protected LoginInfoEntity doInBackground(wjpwdInfo... arg0) {
        Map<String, String> mp = new HashMap<>();
        wjpwdInfo info = arg0[0];
        mp.put("phone", info.getPhone());       //手机号
        mp.put("barnd", Const.BRAND);           //品牌号
        mp.put("code", info.getCode());         //验证码
        mp.put("password", info.getPassword()); //新密码
        String res = HttpUtil.doPostforDES(Const.URL + "APP_FindPasswordServlet", mp);
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
            TabToast.makeText(activity, "请牢记新密码");
            activity.finish();
        } else {
            TabToast.makeText(activity, res.getMessage());
        }
        super.onPostExecute(res);
    }
}
