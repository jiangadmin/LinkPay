package com.linkpay.Service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.linkpay.Activity.Mine.StoreActivity;
import com.linkpay.Application.Const;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Entity.BaseResult;
import com.linkpay.Entity.UserInfoEntity;
import com.linkpay.Utils.HttpUtil;
import com.linkpay.Utils.TabToast;

import java.util.HashMap;

/**
 * Created by jiangmac
 * on 16/04/08.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:姓名身份证核对
 */

public class RealIdCardServlet extends AsyncTask<RealIdCardServlet.RealIDCardInfo, IntentDialog, RealIdCardServlet.RealIDCardInfo> {
    Activity activity;
    ProgressDialog dialog = null;

    public RealIdCardServlet(Activity activity, ProgressDialog dialog) {
        super();
        this.activity = activity;
        this.dialog = dialog;
    }

    @Override
    protected RealIDCardInfo doInBackground(RealIDCardInfo... realIDCardInfos) {
        HashMap mp = new HashMap<>();
        RealIDCardInfo info = realIDCardInfos[0];
        mp.put("name", info.getName());
        mp.put("idCard", info.getIdCard());
        String res = HttpUtil.doPostforDES(Const.URL + "APP_IDCardMatchServlet", mp);
        RealIDCardInfo entity = null;
        if (res != null) {
            try {
                UserInfoEntity.name = info.getName();
                UserInfoEntity.idcard = info.getIdCard();
                entity = new Gson().fromJson(res, RealIDCardInfo.class);
            } catch (Exception e) {
                entity = new RealIDCardInfo();
                entity.setResultCode(-11);
                entity.setMessage("报文解析失败!");
            }
        } else {
            entity = new RealIDCardInfo();
            entity.setResultCode(-12);
            entity.setMessage("服务器走丢了!");
        }
        return entity;

    }

    @Override
    protected void onPostExecute(RealIDCardInfo res) {
        if (dialog != null) {
            dialog.dismiss();
        }

        if (res.getResultCode() == 0) {
            activity.finish();
            activity.startActivity(new Intent(activity, StoreActivity.class));
        } else {
            if (res.getMessage() != null) {
                TabToast.makeText(activity, res.getMessage());
            } else {
                TabToast.makeText(activity, "姓名身份证号不匹配！");
            }
        }
        super.onPostExecute(res);
    }

    public static class RealIDCardInfo extends BaseResult {
        private String name;
        private String idCard;

        public String getName() {

            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }


    }

}