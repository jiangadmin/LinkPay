package com.linkpay.Activity.Login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.Application.KEY_NAME;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.R;
import com.linkpay.Service.LoginServlet;
import com.linkpay.Utils.SharedPreferencesUtil;
import com.linkpay.Utils.TabToast;
import com.linkpay.View.LocusPassWordView;

/**
 * Created by jiangmac
 * on 15/12/21.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:登录手势密码
 */
public class LoginLoucsActivity extends BaseActivity {
    private LocusPassWordView locuspwd;
    private ImageView userimg;
    private TextView phone;
    private Button update;
    private ProgressDialog dialog;
    int errornum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_locus);
        initview();
        locuspwd.setOnCompleteListener(new LocusPassWordView.OnCompleteListener() {
            @Override
            public void onComplete(String password) {
                if (locuspwd.verifyPassword(password)) {
                    dialog = ProgressDialog.show(LoginLoucsActivity.this, "请等待...", "正在为您登陆...");
                    LoginServlet task = new LoginServlet(LoginLoucsActivity.this, dialog, locuspwd);
                    LoginServlet.LoginInfo loginInfo = new LoginServlet.LoginInfo();
                    loginInfo.setPhone(SharedPreferencesUtil.getInstance(LoginLoucsActivity.this).getStringValue(KEY_NAME.手机号));
                    loginInfo.setPwd(SharedPreferencesUtil.getInstance(LoginLoucsActivity.this).getStringValue(KEY_NAME.密码));
                    task.execute(loginInfo);

                } else {
                    errornum++;
                    if (errornum >= 5) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginLoucsActivity.this);
                        builder.setTitle("通知");
                        builder.setMessage("软件被锁定，请稍后再试...");
                        builder.setNegativeButton("使用密码登录", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                locuspwd.clearPassword();
                                locuspwd.resetPassWord("");// 密码置空
                                startActivity(new Intent(LoginLoucsActivity.this, LoginActivity.class));
                            }
                        });
                        builder.show();

                    }
                    locuspwd.clearPassword();
                    TabToast.makeText(LoginLoucsActivity.this, "密码输入错误,请重新输入");
                }
            }
        });
    }

    private void initview() {
        locuspwd = (LocusPassWordView) findViewById(R.id.login_locus_pwd);
        phone = (TextView) findViewById(R.id.login_phone_show);
        update = (Button) findViewById(R.id.login_update);

        phone.setText(SharedPreferencesUtil.getInstance(this).getStringValue("phone"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginLoucsActivity.this, LoginActivity.class));
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        IntentDialog.ExitDialog(this);
    }
}
