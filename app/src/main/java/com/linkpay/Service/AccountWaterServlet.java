package com.linkpay.Service;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.Gson;
import com.linkpay.Activity.Home.BillActivity;
import com.linkpay.Application.Const;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Entity.AccountWaterListEntity;
import com.linkpay.R;
import com.linkpay.Utils.HttpUtil;
import com.linkpay.Utils.LogUtil;
import com.linkpay.Utils.TabToast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiang
 * on 16/7/28
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:获取商户资金变动流水
 * update:
 */
public class AccountWaterServlet extends AsyncTask<AccountWaterServlet.AccountWater, IntentDialog, AccountWaterListEntity> {
    private static final String TAG = "AccountWaterServlet";
    private BillActivity activity;
    private ProgressDialog dialog = null;
    TextView textView ;

    public AccountWaterServlet(BillActivity activity, ProgressDialog dialog) {
        this.activity = activity;
        this.dialog = dialog;
    }




    @Override
    protected AccountWaterListEntity doInBackground(AccountWater... params) {
        Map<String, String> mp = new HashMap<>();

        AccountWater accountWater = params[0];

        mp.put("plId", Const.PIID);
        mp.put("next", "1");
        mp.put("nowPage", accountWater.getNowPage() + "");
        if (accountWater.getAleruaccountId() != null) {
            mp.put("aleruaccountId", accountWater.getAleruaccountId());
        }
        if (accountWater.getStartTime() != null && accountWater.getEndTime() != null) {
            mp.put("startTime", accountWater.getStartTime());
            mp.put("endTime", accountWater.getEndTime());
        }
        if (accountWater.getSaleruType() != null) {
            mp.put("saleruType", accountWater.getSaleruType());
        }
        String res = HttpUtil.doPostforDES(Const.URL + "APP_AccountWaterServlet", mp);

        AccountWaterListEntity entity = null;
        if (res != null) {
            try {
                entity = new Gson().fromJson(res, AccountWaterListEntity.class);
            } catch (Exception e) {
                entity = new AccountWaterListEntity();
                entity.setResultCode(-11);
                entity.setMessage("报文解析失败!");
            }
        } else {
            entity = new AccountWaterListEntity();
            entity.setResultCode(-12);
            entity.setMessage("服务器走丢了!");
        }
        return entity;

    }

    @Override
    protected void onPostExecute(AccountWaterListEntity res) {
        if (dialog != null) {
            dialog.dismiss();
        }
        if (res.getResultCode() == 0) {
            activity.updatelistview(res.getSaleruList());
            if (res.getSaleruList().size() < 20) {
                textView = (TextView) activity.findViewById(R.id.xlistview_footer_hint_textview);
                textView.setText("数据加载完毕");
                TabToast.makeText(activity, "数据加载完毕！");
                BillActivity.Nextpage.nextpage =false;
            }else {
                BillActivity.Nextpage.nextpage = true;
            }
        } else {

        }
        LogUtil.e(activity,"获取数据条数："+res.getSaleruList().size());
        super.onPostExecute(res);
    }


    /**
     * 筛选条件
     */
    public static class AccountWater {
        public String aleruaccountId;//请求查询流水的id
        public String startTime;//开始时间（检索条件）
        public String endTime;//结束时间（检索条件）
        public String saleruType;//（1表示检索1消费流水
        public int nowPage;//当前页码

        public int getNowPage() {
            return nowPage;
        }

        public void setNowPage(int nowPage) {
            this.nowPage = nowPage;
        }

        public String getAleruaccountId() {
            return aleruaccountId;
        }

        public void setAleruaccountId(String aleruaccountId) {
            this.aleruaccountId = aleruaccountId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getSaleruType() {
            return saleruType;
        }

        public void setSaleruType(String saleruType) {
            this.saleruType = saleruType;
        }
    }
}
