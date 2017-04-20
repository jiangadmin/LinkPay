package com.linkpay.Service;

import android.app.Activity;
import android.os.AsyncTask;

import com.linkpay.Application.Const;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Utils.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiang
 * on 16/8/17
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:资金变动流水信息详情信息获取
 * update:
 */
public class AccountWaterDetilsServlet extends AsyncTask<AccountWaterDetilsServlet.AccountWaterDetils, IntentDialog, String> {

    Activity activity;

    public AccountWaterDetilsServlet(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(AccountWaterDetils... params) {
        Map<String, String> mp = new HashMap<>();
        AccountWaterDetils accountWaterDetils = params[0];
        mp.put("id", accountWaterDetils.getId() + "");
        mp.put("saleruType", accountWaterDetils.getSaleruType() + "");

        String res = HttpUtil.doPostforDES(Const.URL + "AccountWaterServlet", mp);
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    public static class AccountWaterDetils {
        int id;
        int saleruType;// 1消费查询详情  6清算查询详情

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSaleruType() {
            return saleruType;
        }

        public void setSaleruType(int saleruType) {
            this.saleruType = saleruType;
        }
    }
}
