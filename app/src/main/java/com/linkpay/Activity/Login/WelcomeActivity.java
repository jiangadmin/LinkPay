package com.linkpay.Activity.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.R;
import com.linkpay.Service.DemoServlet;
import com.linkpay.Service.UpdateService;

/**
 * Created by jiang
 * on 16/10/12.
 * Email: jiangyaoyao@chinarb.cnø
 * Phone：18605296932
 * Purpose:欢迎页
 */
public class WelcomeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//        测试
        new DemoServlet().execute();
        //检查更新
        new UpdateService(this).execute();

    }

}
