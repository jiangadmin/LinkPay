package com.linkpay.Activity.Login;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.R;
import com.linkpay.Service.ForgetPWDServlet;
import com.linkpay.Service.GetSMSCodeServlet;
import com.linkpay.Utils.TabToast;
import com.linkpay.Utils.ToolUtil;

/**
 * Created by 垚垚
 * on 15/7/15.
 * Email: jiangyaoyao@chinarb.cn
 * Phone：18661201018
 * Purpose: 忘记密码
 */
public class ForgetPwdActivity extends BaseActivity {
    EditText phone, code, pwd1, pwd2;

    TextView getsmscode;

    ProgressDialog dialog;
    TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgetpwd);

        setTitle("重置密码");
        setBack(true);
        initview();
        time = new TimeCount(120000, 1000);
    }


    //初始化控件
    private void initview() {
        phone = (EditText) findViewById(R.id.forgetpwd_phone);             //手机号
        code = (EditText) findViewById(R.id.forgetpwd_code);               //验证码
        pwd1 = (EditText) findViewById(R.id.forgetpwd_pwd);               //新密码
        pwd2 = (EditText) findViewById(R.id.forgetpwd_repwd);               //确认密码

        getsmscode  = (TextView) findViewById(R.id.forgetpwd_code_btn);

    }


    //获取验证码
    public void GetSMSCode(View view) {
        if (!ToolUtil.isMobileNO(phone.getText().toString().trim())) {
            TabToast.makeText(this, "请输入正确的手机号码");
            return;
        }

        GetSMSCodeServlet.SMSCodeInfo info = new GetSMSCodeServlet.SMSCodeInfo();
        info.setPhone(phone.getText().toString().trim());
        info.setType("3");//1:注册  3：改密码
        new GetSMSCodeServlet(this).execute(info);
        time.start();//开始计时
    }

    //提交事件
    public void ForGetPWD(View view) {
        if (!ToolUtil.isMobileNO(phone.getText().toString().trim())) {
            TabToast.makeText(this, "请输入正确的手机号码");
            return;
        }
        if (code.getText().toString().length() < 3) {
            TabToast.makeText(this, "请输入正确的验证码");
            return;
        }
        if (!ToolUtil.isNormal(pwd1.getText().toString()) && pwd1.getText().toString().trim().length() < 6) {
            TabToast.makeText(this, "你输入的密码不合法");
            return;
        }
        if (!pwd2.getText().toString().equals(pwd1.getText().toString())) {
            TabToast.makeText(this, "你输入的密码不合法");
            return;
        }
        //准备数据
        ForgetPWDServlet.wjpwdInfo info = new ForgetPWDServlet.wjpwdInfo();
        info.setPhone(phone.getText().toString().trim());
        info.setCode(code.getText().toString().trim());
        info.setPassword(pwd1.getText().toString().trim());
        //启动弹框等待
        dialog = ProgressDialog.show(this, "请等待...", "正在操作...");
        //发送重置密码请求
        new ForgetPWDServlet(this, dialog).execute(info);
    }

    /**
     * 计时器
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {

            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            getsmscode.setText("重新验证");
            getsmscode.setClickable(true);
            getsmscode.setTextColor(getResources().getColor(R.color.white));
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            getsmscode.setClickable(false);
            getsmscode.setText(millisUntilFinished / 1000 + "秒后重新获取");
            getsmscode.setTextColor(Color.GRAY);
        }
    }
}
