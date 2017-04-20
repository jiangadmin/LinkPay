package com.linkpay.Activity.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.Application.KEY_NAME;
import com.linkpay.Application.MyApplication;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.R;
import com.linkpay.Service.LoginServlet;
import com.linkpay.Utils.LogUtil;
import com.linkpay.Utils.SharedPreferencesUtil;
import com.linkpay.Utils.TabToast;
import com.linkpay.Utils.ToolUtil;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jiangadmin
 * on 15/10/13.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:登录
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    EditText phone, pwd;                   //账号

    ProgressDialog progressdialog = null;      //内容提示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        initview();

    }


    public void initview() {
        phone = (EditText) findViewById(R.id.login_phone);                  //账号
        pwd = (EditText) findViewById(R.id.login_pwd);                       //密码


        //焦点自动到密码框
        if (SharedPreferencesUtil.getInstance(this).getStringValue(KEY_NAME.手机号) != null) {
            phone.setText(SharedPreferencesUtil.getInstance(this).getStringValue(KEY_NAME.手机号));
            pwd.requestFocus();
            //让软键盘延时弹出，以更好的加载Activity
            new Timer().schedule(new TimerTask() {

                public void run() {
                    InputMethodManager inputManager = (InputMethodManager) pwd.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(pwd, 0);
                }

            }, 500);
        }


        //手机号输入到11位后焦点自动到密码框
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (start == 11) {
                    pwd.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start == 11) {
                    pwd.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (phone.getText().length() == 11) {
                    pwd.requestFocus();
                }
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) pwd.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }




    /**
     * 登录
     */
    public void login_submit(View view) {
        //手机号正则表达式验证
        if (!ToolUtil.isMobileNO(phone.getText().toString())) {
            TabToast.makeText(this, "账号有误");
            return;
        }
        //密码常规验证
        if (pwd.getText().toString().length() < 6) {
            TabToast.makeText(this, "密码不正确");
            return;
        }
        //发送登录请求
        progressdialog = ProgressDialog.show(LoginActivity.this, "请等待...", "正在为您登陆...");
        LoginServlet.LoginInfo info = new LoginServlet.LoginInfo();
        info.setPhone(phone.getText().toString());
        info.setPwd(pwd.getText().toString());

        new LoginServlet(this, progressdialog).execute(info);

    }

    /**
     * 忘记密码
     */
    public void login_forgetpwd(View view) {
        startActivity(new Intent(this, ForgetPwdActivity.class));

    }

    /**
     * 注册
     */
    public void login_register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));

    }


    /**
     * 微信快捷登录
     */
    public void Wechat_login(View view) {
        if (MyApplication.WeChat_api.isWXAppInstalled()) {
            progressdialog = ProgressDialog.show(LoginActivity.this, "请等待...", "正在启动微信...");
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            MyApplication.WeChat_api.sendReq(req);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("检测到您未安装微信，是否现在安装一个？");
            builder.setNegativeButton("取消", null);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        Uri uri = Uri.parse("market://details?id=" + "com.tencent.mm");//改处写APP的包名即可
                        Intent intentwx = new Intent(Intent.ACTION_VIEW, uri);
                        intentwx.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentwx);
                    } catch (Exception e) {
                        TabToast.makeText(LoginActivity.this, "您没有安装应用市场");
                    }
                }
            });
            builder.show();

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (progressdialog != null)
            progressdialog.dismiss();
    }

    /**
     * Tencent 登录
     */
    public void Tencent_login(View view) {
        Tencent mTencent = Tencent.createInstance(MyApplication.Tencent_APP_ID, this.getApplicationContext());
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    LogUtil.e(TAG, "onComplete" + o);
                }

                @Override
                public void onError(UiError uiError) {
                    LogUtil.e(TAG, "onError" + uiError);
                }

                @Override
                public void onCancel() {
                    LogUtil.e(TAG, "onCancel");
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        IntentDialog.ExitDialog(this);
    }

    public static FingerprintManager getFingerprintManager(Context context) {
        FingerprintManager fingerprintManager = null;
        try {
            fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        } catch (Throwable e) {
            LogUtil.e(TAG, "have not class FingerprintManager");
        }
        return fingerprintManager;
    }
}
