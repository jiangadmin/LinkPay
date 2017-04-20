package com.linkpay.wxapi;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;
import com.linkpay.Application.MyApplication;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Entity.BaseResult;
import com.linkpay.Service.WeChatUserinfoServlet;
import com.linkpay.Utils.HttpUtil;
import com.linkpay.Utils.LogUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiang
 * on 2016/12/5.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:接收微信的请求及返回值
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册API
        api = WXAPIFactory.createWXAPI(this, MyApplication.WeChat_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    //  发送到微信请求的响应结果
    @Override
    public void onResp(BaseResp resp) {
        if (resp instanceof SendAuth.Resp) {
            SendAuth.Resp newResp = (SendAuth.Resp) resp;

            //获取微信传回的code
            LogUtil.e("微信返回", newResp.code);
            LogUtil.e("微信返回", newResp.errStr);
            LogUtil.e("微信返回", newResp.openId);
            LogUtil.e("微信返回", newResp.transaction);

            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    //发送成功
                    LogUtil.e("微信返回", "发送成功");
                    new Wxinfo(this).execute(newResp.code);
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    //发送取消
                    LogUtil.e("微信返回", "发送取消");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    //发送被拒绝
                    LogUtil.e("微信返回", "发送被拒绝");
                    finish();
                    break;
                default:
                    //发送返回
                    LogUtil.e("微信返回", "发送返回");
                    finish();
                    break;
            }
        } else {
            //发送返回
            LogUtil.e("微信返回", "貌似出了点问题");
            LogUtil.e("微信返回", resp.transaction);
            LogUtil.e("微信返回", resp.openId);
            LogUtil.e("微信返回", resp.errCode);
            LogUtil.e("微信返回", resp.errStr);
            finish();
        }
    }

    class Wxinfo extends AsyncTask<String, IntentDialog, WxInfoEntity> {
        Activity activity;

        public Wxinfo(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected WxInfoEntity doInBackground(String... strings) {
            Map<String, String> mp = new HashMap<>();
            mp.put("appid", MyApplication.WeChat_APP_ID);
            mp.put("secret", MyApplication.WeChat_SECRET);
            mp.put("code", strings[0]);
            mp.put("grant_type", "authorization_code");
            String res = HttpUtil.doPostforDES("https://api.weixin.qq.com/sns/oauth2/access_token", mp);
            WxInfoEntity entity = null;
            if (res != null) {
                try {
                    entity = new Gson().fromJson(res, WxInfoEntity.class);
                } catch (Exception e) {
                    entity = new WxInfoEntity();
                    entity.setResultCode(-11);
                    entity.setMessage("报文解析失败!");
                }
            } else {
                entity = new WxInfoEntity();
                entity.setResultCode(-12);
                entity.setMessage("服务器走丢了!");
            }
            return entity;
        }

        @Override
        protected void onPostExecute(WxInfoEntity s) {
            switch (s.errcode) {
                case 0:
                    WeChatUserinfoServlet.wjpwdInfo wjpwdInfo = new WeChatUserinfoServlet.wjpwdInfo();
                    wjpwdInfo.setOpenid(s.openid);
                    wjpwdInfo.setAccess_token(s.access_token);
                    new WeChatUserinfoServlet(WXEntryActivity.this, null).execute(wjpwdInfo);
                    activity.finish();
                    break;
            }
            super.onPostExecute(s);
        }


    }

    class WxInfoEntity extends BaseResult {

        /**
         * access_token : 接口调用凭证
         * expires_in : access_token接口调用凭证超时时间，单位（秒）
         * refresh_token : 用户刷新access_token
         * openid : 授权用户唯一标识
         * scope : 用户授权的作用域，使用逗号（,）分隔
         * unionid : 当且仅当该移动应用已获得该用户的userinfo授权时，才会出现该字段
         */

        private String access_token;
        private int expires_in;
        private String refresh_token;
        private String openid;
        private String scope;
        private String unionid;
        /**
         * errcode : 错误代码
         * errmsg : invalid code
         */

        private int errcode;
        private String errmsg;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public int getErrcode() {
            return errcode;
        }

        public void setErrcode(int errcode) {
            this.errcode = errcode;
        }

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }
    }
}
