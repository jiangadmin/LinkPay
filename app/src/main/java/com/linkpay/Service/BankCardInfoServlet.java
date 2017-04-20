package com.linkpay.Service;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.linkpay.Activity.Home.MyCardListActivity;
import com.linkpay.Application.Const;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Entity.CardEntity;
import com.linkpay.Utils.HttpUtil;
import com.linkpay.Utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangmac
 * on 17/01/16.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:获取绑定卡列表
 */
public class BankCardInfoServlet extends AsyncTask<String, IntentDialog, CardEntity> {
    private static final String TAG = "BankCardInfoServlet";
    MyCardListActivity activity;
    ProgressDialog dialog = null;
    CardEntity cardEntity;


    public BankCardInfoServlet(MyCardListActivity activity, ProgressDialog dialog) {
        super();
        this.activity = activity;
        this.dialog = dialog;
    }

    public BankCardInfoServlet(MyCardListActivity activity) {
        super();
        this.activity = activity;
    }


    @Override
    protected CardEntity doInBackground(String... arg0) {
        Map<String, String> mp = new HashMap<>();
        mp.put("saru", Const.SARULRUID);//商户号
        String res = HttpUtil.doPostforDES(Const.URL + "BankCardInfoServlet", mp);
        if (res != null) {
            try {
                cardEntity = new Gson().fromJson(res, CardEntity.class);
            } catch (Exception e) {
                cardEntity = new CardEntity();
                cardEntity.setResultCode(-11);
                cardEntity.setMessage("报文解析失败!");
            }
        } else {
            cardEntity = new CardEntity();
            cardEntity.setResultCode(-12);
            cardEntity.setMessage("服务器走丢了!");
        }
        return cardEntity;
    }

    @Override
    protected void onPostExecute(CardEntity res) {
        if (dialog != null) {
            dialog.dismiss();
        }
        if (res.getResultCode() == 0) {
            if (res.getResultList().size() == 0) {
                LogUtil.e(TAG,"还没有绑定的卡");
            }
            activity.updatelistview(res.getResultList());
        }
        super.onPostExecute(res);
    }

}
