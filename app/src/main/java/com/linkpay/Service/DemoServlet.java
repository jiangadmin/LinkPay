package com.linkpay.Service;

import android.os.AsyncTask;

import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Utils.HttpUtil;
import com.linkpay.Utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JiangAdmin
 * on 2017/2/15.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:测试请求
 */
public class DemoServlet extends AsyncTask<String, IntentDialog, String> {
    private static final String TAG = "DemoServlet";

    @Override
    protected String doInBackground(String... params) {
        Map<String, String> mp = new HashMap<>();
//        {"imei":"869804029961831",
// "password":"3BB1CE8770990392B7BC77155E33F928",
// "reqUrl":"userLogin",
// "sign":"D5890A13BF60F65F97EABF0C5246096D",
// "timestamp":"1487121439938",
// "username":"15951824319"}
        mp.put("reqUrl", "userLogin");
        mp.put("imei", "869804029961831");
        mp.put("password", "3BB1CE8770990392B7BC77155E33F928");
        mp.put("sign", "D5890A13BF60F65F97EABF0C5246096D");
        mp.put("timestamp", "1487121439938");
        mp.put("username", "15951824319");

        String res = HttpUtil.doPostforDES("https://www.mengdouwang.cn/md_csp_openapi/actionDispatcher.do", mp);
        return res;
    }

    @Override
    protected void onPostExecute(String s) {
        LogUtil.e(TAG, "请求返回>");
        LogUtil.e(TAG, s);
        super.onPostExecute(s);
    }
}
