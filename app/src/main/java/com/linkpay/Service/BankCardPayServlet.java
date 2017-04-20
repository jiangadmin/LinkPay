package com.linkpay.Service;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.linkpay.Activity.Home.CardPayActivty;
import com.linkpay.Application.Const;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Entity.BaseResult;
import com.linkpay.Entity.SendPayEntity;
import com.linkpay.Utils.HttpUtil;
import com.linkpay.Utils.TabToast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangmac
 * on 17/01/16.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:确认交易
 */
public class BankCardPayServlet extends AsyncTask<SendPayEntity, IntentDialog, BaseResult> {
    CardPayActivty activity;
    ProgressDialog dialog = null;


    public BankCardPayServlet(CardPayActivty activity, ProgressDialog dialog) {
        super();
        this.activity = activity;
        this.dialog = dialog;
    }


    @Override
    protected BaseResult doInBackground(SendPayEntity... arg0) {
        Map<String, String> mp = new HashMap<>();
        SendPayEntity info = arg0[0];
        mp.put("saru", Const.SARULRUID);//商户号
        mp.put("phone", info.getPhone());//手机号
        mp.put("code", info.getSmscode());//短信验证码
        mp.put("orderNum", info.getOrderNum());//订单号
        String res = HttpUtil.doPostforDES(Const.URL + "BankCardPayServlet", mp);
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
        if (res.getResultCode() == 0) {
            activity.finish();
            TabToast.makeText(activity, res.getMessage());
        }

        super.onPostExecute(res);
    }
}
