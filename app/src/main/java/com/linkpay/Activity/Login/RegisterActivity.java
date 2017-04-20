package com.linkpay.Activity.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.R;
import com.linkpay.Service.GetSMSCodeServlet;
import com.linkpay.Service.Register_Servlet;
import com.linkpay.Utils.TabToast;
import com.linkpay.Utils.ToolUtil;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jiang
 * on 16/10/18.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:注册
 */
public class RegisterActivity extends BaseActivity {
    private static final String TAG = "RegisterActivity";
    EditText phone, code, pwd1, pwd2, procsn;
    TextView codebtn;
    Button sumbit;

    TimeCount time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("注册");
        setBack(true);
        initview();
        evenview();
        time = new TimeCount(120000, 1000);

    }

    private void initview() {
        phone = (EditText) findViewById(R.id.register_phone);
        code = (EditText) findViewById(R.id.register_code);
        pwd1 = (EditText) findViewById(R.id.register_pwd);
        pwd2 = (EditText) findViewById(R.id.register_repwd);
        procsn = (EditText) findViewById(R.id.register_procsn);

        codebtn = (TextView) findViewById(R.id.register_code_btn);
        sumbit = (Button) findViewById(R.id.register_sumbit);


    }


    private void evenview() {
        if (!ToolUtil.isMobileNO(phone.getText().toString().trim())) {

            new Timer().schedule(new TimerTask() {

                public void run() {
                    InputMethodManager inputManager = (InputMethodManager) phone.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.showSoftInput(phone, 0);
                }

            }, 200);
        }
    }

    /**
     * 获取短信验证码
     *
     * @param view
     */
    public void GetSMSCode(View view) {
        //手机号正则验证
        if (ToolUtil.isMobileNO(phone.getText().toString())) {
            //准备请求数据
            GetSMSCodeServlet.SMSCodeInfo smsCodeInfo = new GetSMSCodeServlet.SMSCodeInfo();
            smsCodeInfo.setPhone(phone.getText().toString());
            smsCodeInfo.setType("1");
            //发送获取短信验证码请求
            new GetSMSCodeServlet(this).execute(smsCodeInfo);
            //开始计时
            time.start();

        } else {
            TabToast.makeText(this, "手机号不正确！");
        }
    }

    /**
     * 扫描得到台码
     *
     * @param view
     */
    public void GetQRCode(View view) {
        //启动扫码页面
        startActivityForResult(new Intent(this, CaptureActivity.class), 1);
    }


    /**
     * 注册
     *
     * @param view
     */
    public void Register(View view) {
        //手机号正则表达式验证
        if (!ToolUtil.isMobileNO(phone.getText().toString().trim())) {
            TabToast.makeText(this, "请输入正确的手机号码");
            return;
        }
        //短信验证码常规验证
        if (code.getText().toString().length() < 3) {
            TabToast.makeText(this, "请输入正确的验证码");
            return;
        }
        //密码正则表达式沿着
        if (!ToolUtil.isNormal(pwd1.getText().toString()) && pwd1.getText().toString().trim().length() < 6) {
            TabToast.makeText(this, "密码必须包含字母和数字");
            return;
        }
        if (!pwd2.getText().toString().equals(pwd1.getText().toString())) {
            TabToast.makeText(this, "两次密码必须相同");
            return;
        }
        if (procsn.getText().toString().length() < 10) {
            TabToast.makeText(this, "请输入正确的验证码");
            return;
        }
        //准备请求数据
        Register_Servlet.Registerinfo registerinfo = new Register_Servlet.Registerinfo();
        registerinfo.setPhone(phone.getText().toString());
        registerinfo.setCode(code.getText().toString());
        registerinfo.setPwd(pwd1.getText().toString());
        registerinfo.setProcsn(procsn.getText().toString());
        //发出注册请求
        new Register_Servlet(this, new ProgressDialog(this).show(this, null, "正在注册...")).execute(registerinfo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //扫码页回来
            if (requestCode == 1) {
                Bundle bundle = data.getExtras();
                String sntext = bundle.getString("result");
                //判断是否是台码
                if (sntext.lastIndexOf("procSn=") != -1) {
                    String sn = sntext.substring(sntext.lastIndexOf("procSn=") + 7, sntext.length());
                    //二次判断
                    if (sn.length() == 10) {
                        //台码填入输入框
                        procsn.setText(sn);
                    }
                }else {
                    TabToast.makeText(this,"请扫正确的台码");
                }
            }
        }
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
            codebtn.setText("重新验证");
            codebtn.setClickable(true);
            codebtn.setTextColor(getResources().getColor(R.color.white));
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            codebtn.setClickable(false);
            codebtn.setText(millisUntilFinished / 1000 + "秒后重新获取");
            codebtn.setTextColor(Color.GRAY);
        }
    }
}
