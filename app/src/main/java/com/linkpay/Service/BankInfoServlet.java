package com.linkpay.Service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.linkpay.Adapter.SimpleAdapter;
import com.linkpay.Application.Const;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Entity.BankInfo;
import com.linkpay.Entity.UserInfoEntity;
import com.linkpay.Utils.HttpUtil;
import com.linkpay.Utils.TabToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by jiangmac
 * on 16/04/08.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:银行 省市区 支行 联动
 */

public class BankInfoServlet extends AsyncTask<String, IntentDialog, List<BankInfo>> {
    private Activity activity;
    private int type;                //1:银行  2：省   3：城市   4：区县  5：联行 6:搜索联行
    Spinner spinner;
    private ProgressDialog dialog = null;

    public BankInfoServlet(Activity activity, int type, Spinner spinner, ProgressDialog dialog) {
        super();
        this.activity = activity;
        this.type = type;
        this.spinner = spinner;
        this.dialog = dialog;
    }


    @Override
    protected List<BankInfo> doInBackground(String... params) {
        Map<String, String> mp = new HashMap<>();
        String res = null;
        switch (type) {
//			银行
            case 1:
                res = HttpUtil.doPostforDES(Const.URL + "GetBankServlet", mp);
                break;
//			省
            case 2:
                res = HttpUtil.doPostforDES(Const.URL + "GetProvinceServlet", mp);
                break;
//			市
            case 3:
                mp.put("id", params[0]);//省的ID
                res = HttpUtil.doPostforDES(Const.URL + "GetCityServlet", mp);
                break;
//          区
            case 4:
                mp.put("id", params[0]);//市的id
                res = HttpUtil.doPostforDES(Const.URL + "GetCountryServlet", mp);
                break;
//			搜索联行
            case 5:
                mp.put("bankName", params[0]);//银行的名称
                mp.put("cityName", params[1]);//市的名称
                mp.put("keyword", params[2]);//关键字
                res = HttpUtil.doPostforDES(Const.URL + "GetBankInfoServlet", mp);
                break;
        }

        List<BankInfo> entity = null;
        if (res != null) {
            try {
                entity = new Gson().fromJson(res, new TypeToken<List<BankInfo>>() {
                }.getType());
            } catch (Exception e) {
                entity = new ArrayList<>();
            }


        } else {

        }
        return entity;
    }


    @Override
    protected void onPostExecute(List<BankInfo> res) {
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
        if (res == null && type != 5) {
            TabToast.makeText(activity, "连接服务器失败，请稍后再试");
            activity.finish();
            return;
        }
        SimpleAdapter adapter = null;
        switch (type) {
            case 1:
                UserInfoEntity.bank = res;
                adapter = new SimpleAdapter(activity, UserInfoEntity.bank, 0);
                break;
            case 2:
                UserInfoEntity.province = res;
                adapter = new SimpleAdapter(activity, UserInfoEntity.province, 0);
                break;
            case 3:
                UserInfoEntity.ctiy = res;
                adapter = new SimpleAdapter(activity, UserInfoEntity.ctiy, 0);
                break;

            case 4:
                UserInfoEntity.region = res;
                adapter = new SimpleAdapter(activity, UserInfoEntity.region, 0);
                break;
            case 5:
                if (res.size() == 0) {
                    UserInfoEntity.bankBranch = null;
                    UserInfoEntity.bankBranchId = null;
                    TabToast.makeText(activity, "没有检索下级信息");
//                    wheelView.setVisibility(View.GONE);
//                    editText.setVisibility(View.VISIBLE);
                } else {
                    UserInfoEntity.xbank = res;
                    adapter = new SimpleAdapter(activity, UserInfoEntity.xbank, 1);
//                    wheelView.setVisibility(View.VISIBLE);
//                    editText.setVisibility(View.GONE);
                }
                break;

        }
        try {
            spinner.setAdapter(adapter);
//            wheelView.setViewAdapter(new ArrayWheelAdapter<>(activity,arr));
        } catch (Exception e) {

        }
        super.onPostExecute(res);
    }
}
