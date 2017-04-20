package com.linkpay.Service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.linkpay.Activity.Login.LoginActivity;
import com.linkpay.Application.Const;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Entity.LoginInfoEntity;
import com.linkpay.Utils.HttpUtil;
import com.linkpay.Utils.TabToast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 垚垚
 * on 16/03/09.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose: 注册接口
 */
public class Register_Servlet extends AsyncTask<Register_Servlet.Registerinfo, IntentDialog, LoginInfoEntity> {
    private ProgressDialog dialog = null;
    private Activity activity;

    public Register_Servlet(Activity activity, ProgressDialog dialog) {
        super();
        this.activity = activity;
        this.dialog = dialog;
    }

    @Override
    protected LoginInfoEntity doInBackground(Register_Servlet.Registerinfo... registers) {
        Map<String, String> mp = new HashMap<>();
        Register_Servlet.Registerinfo info = registers[0];
        mp.put("phone", info.getPhone());       //手机号
        mp.put("barnd", Const.BRAND);           //品牌号
        mp.put("password", info.getPwd());      //密码
        mp.put("procSn", info.getProcsn());     //台码
        mp.put("code", info.getCode());         //验证码

        String res = null;
        res = HttpUtil.doPostforDES(Const.URL + "APP_RegisterServlet", mp);
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
        if (dialog != null) {
            dialog.dismiss();
        }
        if (res.getResultCode() == 0) {
            activity.finish();
            activity.startActivity(new Intent(activity, LoginActivity.class));
            TabToast.makeText(activity, "注册成功，现在可以登录了");
        } else {
            TabToast.makeText(activity, res.getMessage());
        }
        super.onPostExecute(res);
    }

    public static class Registerinfo {
        public String phone;
        public String code;
        public String pwd;
        public String procsn;

        public String getProcsn() {
            return procsn;
        }

        public void setProcsn(String procsn) {
            this.procsn = procsn;
        }

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

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }


    }
}
