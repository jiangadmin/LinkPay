package com.linkpay.Service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.linkpay.Application.KEY_NAME;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Entity.WechatInfo;
import com.linkpay.Utils.HttpUtil;
import com.linkpay.Utils.LogUtil;
import com.linkpay.Utils.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangmac
 * on 17/01/04.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:微信用户资料
 */
public class WeChatUserinfoServlet extends AsyncTask<WeChatUserinfoServlet.wjpwdInfo, IntentDialog, WechatInfo> {
    private Activity activity;

    public WeChatUserinfoServlet(Activity activity, ProgressDialog progressdialog) {
        super();
        this.activity = activity;
        this.loading = progressdialog;
    }

    private ProgressDialog loading = null;

    /**
     * phone money pici liushui saru
     */
    public static class wjpwdInfo {

        private String access_token;
        private String openid;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }
    }

    @Override
    protected WechatInfo doInBackground(wjpwdInfo... arg0) {
        Map<String, String> mp = new HashMap<>();
        wjpwdInfo info = arg0[0];
        mp.put("access_token", info.getAccess_token());
        mp.put("openid", info.getOpenid());
        mp.put("lang", "zh_CN");
        String res = HttpUtil.doPostforDES("https://api.weixin.qq.com/sns/userinfo", mp);
        WechatInfo entity = null;
        if (res != null) {
            try {
                entity = new Gson().fromJson(res, WechatInfo.class);
            } catch (Exception e) {
                entity = new WechatInfo();
                entity.setResultCode(-11);
                entity.setMessage("报文解析失败!");
            }
        } else {
            entity = new WechatInfo();
            entity.setResultCode(-12);
            entity.setMessage("服务器走丢了!");
        }
        LogUtil.e("微信用户信息:", res);
        return entity;
    }

    @Override
    protected void onPostExecute(WechatInfo res) {
        if (loading != null) {
            loading.dismiss();
        }
        SharedPreferencesUtil.getInstance(activity).putValue(KEY_NAME.密码, res.getOpenid());
        LoginServlet.LoginInfo loginInfo = new LoginServlet.LoginInfo();
        loginInfo.setUnionid(res.getUnionid());
        new LoginServlet(activity, null).execute(loginInfo);

        super.onPostExecute(res);
    }

}
