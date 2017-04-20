package com.linkpay.Activity.Mine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.Activity.Mine.More.AboutActivity;
import com.linkpay.Activity.Mine.More.KeFuActivity;
import com.linkpay.Activity.Mine.More.LocusPwdActivity;
import com.linkpay.Activity.Mine.More.PasswordActivity;
import com.linkpay.R;
import com.linkpay.Service.UpdateService;

/**
 * Created by jiang
 * on 2016/10/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:设置
 */
public class MoreActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        setBack(true);
        setTitle("更多");

        initview();

    }

    private void initview() {

    }

    /**
     * 账户安全
     *
     * @param view
     */
    public void Password(View view) {
        startActivity(new Intent(this, PasswordActivity.class));
    }

    /**
     * 手势密码
     *
     * @param view
     */
    public void Loucs(View view) {
        startActivity(new Intent(this, LocusPwdActivity.class));
    }

    /**
     * 检查更新
     *
     * @param view
     */
    public void Update(View view) {
        new UpdateService(this, new ProgressDialog(this).show(this, "", "检查更新...")).execute();
    }

    /**
     * 联系客服
     *
     * @param view
     */
    public void Human(View view) {
//        startActivity(new Intent(this, HumanActivity.class));
        startActivity(new Intent(this, KeFuActivity.class));
    }


    /**
     * 关于
     *
     * @param view
     */

    public void About(View view) {
        startActivity(new Intent(this, AboutActivity.class));
    }
}
