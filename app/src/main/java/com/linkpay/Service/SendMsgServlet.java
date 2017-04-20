package com.linkpay.Service;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.EditText;

import com.google.gson.Gson;
import com.linkpay.Activity.Home.AddCardActivity;
import com.linkpay.Application.Const;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Entity.BindCardEntity;
import com.linkpay.Utils.HttpUtil;
import com.linkpay.Utils.TabToast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangmac
 * on 17/01/16.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:发送绑定短信
 */
public class SendMsgServlet extends AsyncTask<SendMsgServlet.SendCardInfo, IntentDialog, BindCardEntity> {
    AddCardActivity activity;
    ProgressDialog dialog = null;
    EditText smscode;


    public SendMsgServlet(AddCardActivity activity, ProgressDialog dialog) {
        super();
        this.activity = activity;
        this.dialog = dialog;
    }

    public SendMsgServlet(AddCardActivity activity) {
        super();
        this.activity = activity;
    }

    public SendMsgServlet(AddCardActivity activity, ProgressDialog dialog, EditText smscode) {
        super();
        this.activity = activity;
        this.dialog = dialog;
        this.smscode = smscode;
    }


    @Override
    protected BindCardEntity doInBackground(SendCardInfo... arg0) {
        Map<String, String> mp = new HashMap<>();
        SendCardInfo sendCardInfo = arg0[0];
        mp.put("saru", Const.SARULRUID);//商户号
        mp.put("phone", sendCardInfo.getPhone());//  手机号
        mp.put("saruBackCard", sendCardInfo.getSaruBackCard());//  卡号
        mp.put("cvn2", sendCardInfo.getCvn2());//  卡背面最后三位数字，信用卡必填
        mp.put("expDate", sendCardInfo.getExpDate().substring(2,4)+sendCardInfo.getExpDate().substring(0,2));//  卡有效期，YYMM格式，如1701，信用卡必填
        mp.put("money", sendCardInfo.getMoney());//  金额


        String res = HttpUtil.doPostforDES(Const.URL + "SendMsgServlet", mp);
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
        switch (res.getResultCode()) {
            case 0:
                activity.ShopOnClick(false);
                activity.orderNum = res.getOrderNum();
                activity.NextSmsCode();
                break;

            default:
                activity.ShopOnClick(true);
                TabToast.makeText(activity, res.getMessage());
                break;
        }


        super.onPostExecute(res);
    }

    public static class SendCardInfo {
        private String phone;//  手机号
        private String saruBackCard;//卡号
        private String cvn2;//卡背面最后三位数字，信用卡必填
        private String expDate;//卡有效期，YYMM格式，如1701，信用卡必填
        private String money;//金额 绑卡默认 0.01

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSaruBackCard() {
            return saruBackCard;
        }

        public void setSaruBackCard(String saruBackCard) {
            this.saruBackCard = saruBackCard;
        }

        public String getCvn2() {
            return cvn2;
        }

        public void setCvn2(String cvn2) {
            this.cvn2 = cvn2;
        }

        public String getExpDate() {
            return expDate;
        }

        public void setExpDate(String expDate) {
            this.expDate = expDate;
        }
    }


}
