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
 * Purpose:获取短信验证码
 */
public class GetSMSCodeServlet extends AsyncTask<GetSMSCodeServlet.SMSCodeInfo, IntentDialog, LoginInfoEntity> {
    Activity activity;
    ProgressDialog dialog = null;


    public GetSMSCodeServlet(Activity activity, ProgressDialog dialog) {
        super();
        this.activity = activity;
        this.dialog = dialog;
    }

    public GetSMSCodeServlet(Activity activity) {
        super();
        this.activity = activity;
    }


    /**
     * phone money pici liushui saru
     */
    public static class SMSCodeInfo {
        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


        private String phone;
        private String type;


    }

    @Override
    protected LoginInfoEntity doInBackground(SMSCodeInfo... arg0) {
        Map<String, String> mp = new HashMap<>();
        SMSCodeInfo info = arg0[0];
        mp.put("phone", info.getPhone());//手机号
        mp.put("barnd", Const.BRAND); //品牌
        mp.put("type", info.getType());//1-安全验证 2-注册提示成功 3 密码找回
        String res = HttpUtil.doPostforDES(Const.URL + "APP_GetCodeServlet", mp);
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
        TabToast.makeText(activity, res.getMessage());


        super.onPostExecute(res);
    }
}
