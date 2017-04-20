package com.linkpay.Service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Base64;

import com.google.gson.Gson;
import com.linkpay.Activity.MainActivity;
import com.linkpay.Application.Const;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Entity.BaseResult;
import com.linkpay.Entity.UserInfoEntity;
import com.linkpay.Utils.HttpUtil;
import com.linkpay.Utils.TabToast;

import java.util.HashMap;
/**
 * Created by jiangmac
 * on 16/10/20.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:实名认证
 */

public class RealIdCardNextServlet extends AsyncTask<String, IntentDialog, RealIdCardNextServlet.RealIDCardInfo> {
    Activity activity;
    ProgressDialog dialog = null;

    public RealIdCardNextServlet(Activity activity, ProgressDialog dialog) {
        super();
        this.activity = activity;
        this.dialog = dialog;
    }

    @Override
    protected RealIDCardInfo doInBackground(String... realIDCardInfos) {
        HashMap mp = new HashMap<>();

        mp.put("phone", Const.LOGIN_PHONE);//账号（phone）
        mp.put("plId", Const.PIID);//登录时候返回的ID
        mp.put("name", UserInfoEntity.name);//姓名（name）
        mp.put("zhname", UserInfoEntity.name);//姓名（name）
        mp.put("idCard", UserInfoEntity.idcard);//身份证（idcard）
        mp.put("barnd", Const.BRAND);//品牌（barnd）
        mp.put("saruName", UserInfoEntity.saruName);//商户名称（saruName）
        mp.put("country", UserInfoEntity.country);//店铺地址（country）
        mp.put("address", UserInfoEntity.address);//店铺详细地址（address）
        mp.put("bankCardNum", UserInfoEntity.bankCardNum);//卡号（bankCardNum）
        mp.put("bankId", UserInfoEntity.bankId);//开户行总行（bankId）
        mp.put("cityId", UserInfoEntity.cityId);//开户行市（cityId）
        mp.put("bankBranchId", UserInfoEntity.bankBranchId);//支行信息（bankBranchId）
        mp.put("iDCardFront", new String(Base64.encode(UserInfoEntity.iDCardFront, Base64.DEFAULT)));//身份证和银行卡正面（iDCardFront）
        mp.put("iDCardBack", new String(Base64.encode(UserInfoEntity.iDCardBack, Base64.DEFAULT)));//身份证反银行卡反面（iDCardBack）
        mp.put("iDCardHand", new String(Base64.encode(UserInfoEntity.iDCardHand, Base64.DEFAULT)));//手持身份证和银行卡半身照（iDCardHand）

        String res = HttpUtil.doPostforDES(Const.URL + "APP_AuthenticationServlet", mp);
        RealIDCardInfo entity = null;
        if (res != null) {
            try {
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
            TabToast.makeText(activity, res.getMessage());
            activity.startActivity(new Intent(activity, MainActivity.class));
            //修改状态为认证中
            Const.ACCOUNT_TYPE = Const.USERTYPE_ING;
        } else {
            if (res.getMessage() != null) {
                TabToast.makeText(activity, res.getMessage());
            } else {
                TabToast.makeText(activity, "错误！");
            }
        }
        super.onPostExecute(res);
    }

    public static class RealIDCardInfo extends BaseResult {


    }

}