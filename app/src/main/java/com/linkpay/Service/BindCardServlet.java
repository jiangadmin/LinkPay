package com.linkpay.Service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.linkpay.Application.Const;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Entity.BaseResult;
import com.linkpay.Utils.HttpUtil;
import com.linkpay.Utils.TabToast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangmac
 * on 17/01/16.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:绑定卡
 */
public class BindCardServlet extends AsyncTask<BindCardServlet.BingcardFrom, IntentDialog, BaseResult> {
    Activity activity;
    ProgressDialog dialog = null;


    public BindCardServlet(Activity activity, ProgressDialog dialog) {
        super();
        this.activity = activity;
        this.dialog = dialog;
    }

    public BindCardServlet(Activity activity) {
        super();
        this.activity = activity;
    }


    @Override
    protected BaseResult doInBackground(BingcardFrom... arg0) {
        Map<String, String> mp = new HashMap<>();
        BingcardFrom from = arg0[0];
        mp.put("saru", Const.SARULRUID);//商户号
        mp.put("phone", from.getPhone());//手机号
        mp.put("orderNum", from.getOrderNum());//订单号
        mp.put("code", from.getCode());//短信验证码
        String res = HttpUtil.doPostforDES(Const.URL + "BindCardServlet", mp);
        BaseResult entity = null;
        if (res != null) {
            try {
                entity = new Gson().fromJson(res, BaseResult.class);
            } catch (Exception e) {
                entity = new BaseResult();
                entity.setResultCode(-11);
                entity.setMessage("报文解析失败!");
            }
        } else {
            entity = new BaseResult();
            entity.setResultCode(-12);
            entity.setMessage("服务器走丢了!");
        }
        return entity;
    }

    @Override
    protected void onPostExecute(BaseResult res) {
        if (dialog != null) {
            dialog.dismiss();
        }
        switch (res.getResultCode()) {
            case 0:
                activity.finish();
                break;
            default:
                TabToast.makeText(activity, res.getMessage());
                break;
        }


        super.onPostExecute(res);
    }

    public static class BingcardFrom {
        private String phone;
        private String orderNum;
        private String code;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
