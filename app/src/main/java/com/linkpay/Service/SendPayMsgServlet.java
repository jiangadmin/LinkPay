package com.linkpay.Service;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.EditText;

import com.google.gson.Gson;
import com.linkpay.Activity.Home.CardPayActivty;
import com.linkpay.Application.Const;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Entity.BindCardEntity;
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
 * Purpose:发送交易短信
 */
public class SendPayMsgServlet extends AsyncTask<SendPayEntity, IntentDialog, BindCardEntity> {
    CardPayActivty activity;
    ProgressDialog dialog = null;
    EditText money;

    public SendPayMsgServlet(CardPayActivty activity, ProgressDialog dialog, EditText money) {
        super();
        this.activity = activity;
        this.dialog = dialog;
        this.money = money;
    }


    @Override
    protected BindCardEntity doInBackground(SendPayEntity... arg0) {
        Map<String, String> mp = new HashMap<>();
        SendPayEntity info = arg0[0];
        mp.put("saru", Const.SARULRUID);//商户号
        mp.put("phone", info.getPhone());//商户号
        mp.put("saruBackCard", info.getCardnum());//商户号
        mp.put("money", info.getMoney());//商户号
        String res = HttpUtil.doPostforDES(Const.URL + "SendPayMsgServlet", mp);
        BindCardEntity entity = null;
        if (res != null) {
            try {
                entity = new Gson().fromJson(res, BindCardEntity.class);
            } catch (Exception e) {
                entity = new BindCardEntity();
                entity.setResultCode(-11);
                entity.setMessage("报文解析失败!");
            }
        } else {
            entity = new BindCardEntity();
            entity.setResultCode(-12);
            entity.setMessage("服务器走丢了!");
        }
        return entity;
    }

    @Override
    protected void onPostExecute(BindCardEntity res) {
        if (dialog != null) {
            dialog.dismiss();
        }
        if (res.getResultCode() == 0) {
            money.setEnabled(false);
            activity.orderNum = res.getOrderNum();
        }else {
            money.setEnabled(true);
            TabToast.makeText(activity,res.getMessage());
        }

        super.onPostExecute(res);
    }



}
