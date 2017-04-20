package com.linkpay.Activity.Home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.Entity.SendPayEntity;
import com.linkpay.R;
import com.linkpay.Service.BankCardPayServlet;
import com.linkpay.Service.SendPayMsgServlet;
import com.linkpay.Utils.TabToast;

/**
 * Created by jiang
 * on 2017/1/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:交易
 */
public class CardPayActivty extends BaseActivity implements TextWatcher {
    EditText money, smscode;
    TextView smscodebtn;
    String phone;
    String cardnum;
    ProgressDialog progressDialog;
    SendPayEntity info;

    public static String orderNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardpay);
        setTitle("支付信息");
        setBack(true);

        phone = getIntent().getStringExtra("手机号");
        cardnum = getIntent().getStringExtra("银行卡号");

        money = (EditText) findViewById(R.id.cardpay_money);
        smscode = (EditText) findViewById(R.id.cardpay_code);
        smscodebtn = (TextView) findViewById(R.id.cardpay_smscode_btn);

        money.addTextChangedListener(this);

    }


    public void 获取交易验证码(View view) {
        if ((money.getText().length() == 0) || (Float.parseFloat(money.getText().toString()) == 0)) {
            money.requestFocus();
            TabToast.makeText(this, "请输入正确的金额");
        } else {
            money.setEnabled(false);
            progressDialog = new ProgressDialog(this).show(this, "请稍后", "正在获取短信验证码");

            info = new SendPayEntity();
            info.setMoney(money.getText().toString());
            info.setPhone(phone);
            info.setCardnum(cardnum);

            new SendPayMsgServlet(this, progressDialog, money).execute(info);
        }
    }

    public void 确认交易(View view) {
        if (smscode.getText().toString() == "" || smscode.getText().toString().length() < 4) {
            TabToast.makeText(this, "请填写正确的验证码");
            smscode.requestFocus();
        } else if (orderNum == null || orderNum == "") {
            TabToast.makeText(this, "请重新获取的验证码");
        } else {
            progressDialog = new ProgressDialog(this).show(this, "请稍后", "正在发送交易");

            info = new SendPayEntity();
            info.setPhone(phone);
            info.setSmscode(smscode.getText().toString());
            info.setOrderNum(orderNum);

            new BankCardPayServlet(this, progressDialog).execute(info);
        }

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


}
