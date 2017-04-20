package com.linkpay.Activity.Home;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.R;
import com.linkpay.Service.BindCardServlet;
import com.linkpay.Service.SendMsgServlet;
import com.linkpay.Utils.TabToast;
import com.linkpay.Utils.ToolUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jiang
 * on 2017/1/16.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:添加信用卡
 */
public class AddCardActivity extends BaseActivity implements TextWatcher {
    private static EditText phone, cardnum, cvn2, expdate, money, smscode;
    TextView smscodebtn;
    Button sumbit;
    ProgressDialog progressDialog;
    TimeCount time;
    public static String orderNum;  //订单号

    private static final String TAG = "AddCardActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcard);
        setTitle("支付信息");
        setBack(true);
        initview();
        time = new TimeCount(120000, 1000);

    }

    private void initview() {
        phone = (EditText) findViewById(R.id.addcard_phone);
        cardnum = (EditText) findViewById(R.id.addcard_cardnum);
        cvn2 = (EditText) findViewById(R.id.addcard_cvn2);
        expdate = (EditText) findViewById(R.id.addcard_expdate);
        money = (EditText) findViewById(R.id.addcard_money);
        smscode = (EditText) findViewById(R.id.addcard_code);
        smscodebtn = (TextView) findViewById(R.id.addcard_smscode_btn);
        sumbit = (Button) findViewById(R.id.addcard_sumbit);

        money.addTextChangedListener(this);


    }

    public void 发起绑定(View view) {
        if (ISNULL()) {
            time.start();//开始计时
//            关闭软键盘
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            }
            ShopOnClick(false);

            SendMsgServlet.SendCardInfo info = new SendMsgServlet.SendCardInfo();

            info.setPhone(phone.getText().toString());
            info.setSaruBackCard(cardnum.getText().toString());
            info.setExpDate(expdate.getText().toString());
            info.setCvn2(cvn2.getText().toString());
            info.setMoney(money.getText().toString());
            progressDialog = new ProgressDialog(AddCardActivity.this).show(AddCardActivity.this, "请稍后", "正在获取短信验证码");
            new SendMsgServlet(AddCardActivity.this, progressDialog).execute(info);


        }
    }

    public void 确认绑定(View view) {
        if (ISNULL()) {
            if (ToolUtil.isEmpty(smscode.getText().toString())) {
                smscode.requestFocus();
                TabToast.makeText(this, "请输入正确的短信验证码");
            } else {
                progressDialog = new ProgressDialog(AddCardActivity.this).show(AddCardActivity.this, "请稍后", "正在交易...");
                BindCardServlet.BingcardFrom from = new BindCardServlet.BingcardFrom();
                from.setPhone(phone.getText().toString());
                from.setCode(smscode.getText().toString());
                from.setOrderNum(orderNum);

                new BindCardServlet(this, progressDialog).execute(from);
            }
        }
    }

    public boolean ISNULL() {
        if (cardnum.getText().length() < 12 || cardnum.getText().length() > 21) {
            cardnum.requestFocus();
            TabToast.makeText(this, "请输入正确的银行卡号");
            return false;
        } else if (expdate.getText().toString().length() < 4) {
            expdate.requestFocus();
            TabToast.makeText(this, "请输入正确的卡有效期");
        } else if (cvn2.getText().toString().length() < 3) {
            cvn2.requestFocus();
            TabToast.makeText(this, "请输入正确的CVN2");
        } else if (!ToolUtil.isMobileNO(phone.getText().toString())) {
            phone.requestFocus();
            TabToast.makeText(this, "请输入正确的手机号");
            return false;
        } else if (ToolUtil.isEmpty(money.getText().toString()) || (Float.parseFloat(money.getText().toString()) == 0)) {
            money.requestFocus();
            TabToast.makeText(this, "请输入正确的金额");
            return false;
        }

        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (i == 0 && charSequence.toString().equals(".")) {
            money.setText("0.");
        }

        if (money.getText().toString().indexOf(".") != -1 && money.getText().toString().indexOf(".") == money.getText().toString().length() - 4) {
            money.setText(money.getText().toString().substring(0, money.getText().toString().length() - 1));
        }

        if (i != 0 && Float.parseFloat(money.getText().toString()) > 20000F) {
            money.setText(money.getText().toString().substring(0, money.getText().toString().length() - 1));
        }

        if (money.getText().toString().indexOf(".") != -1 && money.getText().toString().indexOf(".") == (money.getText().toString().length() - 3) && Float.parseFloat(money.getText().toString()) == 0) {
            money.setText(money.getText().toString().substring(0, money.getText().toString().length() - 1));
            TabToast.makeText(this, "金额不能为 0 ");
        }

    }



    @Override
    public void afterTextChanged(Editable editable) {
        money.setSelection(money.getText().toString().length());
    }
    public static void ShopOnClick(Boolean aBoolean) {
        phone.setEnabled(aBoolean);
        cardnum.setEnabled(aBoolean);
        cvn2.setEnabled(aBoolean);
        expdate.setEnabled(aBoolean);
        money.setEnabled(aBoolean);

    }

    public static void NextSmsCode(){
        smscode.requestFocus();
//        弹出软键盘
        new Timer().schedule(new TimerTask() {

            public void run() {
                InputMethodManager inputManager = (InputMethodManager) smscode.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(smscode, 0);
            }

        }, 200);

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
            smscodebtn.setText("获取验证码");
            smscodebtn.setClickable(true);
            smscodebtn.setTextColor(Color.WHITE);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            smscodebtn.setClickable(false);
            smscodebtn.setText(millisUntilFinished / 1000 + "s");
            smscodebtn.setTextColor(Color.BLACK);
        }
    }
}
