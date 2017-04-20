package com.linkpay.Activity.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.Entity.UserInfoEntity;
import com.linkpay.R;
import com.linkpay.Service.BankInfoServlet;
import com.linkpay.Utils.TabToast;
import com.linkpay.Utils.ToolUtil;

/**
 * Created by jiang
 * on 2016/10/21.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:商户信息
 */
public class RealCardIDActivity extends BaseActivity {
    Spinner sp_bank, sp_province, sp_ctiy, sp_xbank;

    EditText cardid, workey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realidcardnext);
        setBack(true);
        setTitle("结算信息");
        initview();
        initlistener();

    }

    private void initview() {
        cardid = (EditText) findViewById(R.id.realidcardnext_cardid);
        workey = (EditText) findViewById(R.id.realidcardnext_workey);
        sp_bank = (Spinner) findViewById(R.id.spinner_bank);
        sp_province = (Spinner) findViewById(R.id.spinner_province);
        sp_ctiy = (Spinner) findViewById(R.id.spinner_city);
        sp_xbank = (Spinner) findViewById(R.id.spinner_xbank);
    }

    public void WorKey(View view) {
        new BankInfoServlet(RealCardIDActivity.this, 5, sp_xbank, null).execute(UserInfoEntity.bankName, UserInfoEntity.cityName, workey.getText().toString().trim());
    }

    public void submit(View view) {
        if (ToolUtil.isEmpty(cardid.getText().toString())) {
            TabToast.makeText(this, "结算卡卡号还没有填");
        } else {
            UserInfoEntity.bankCardNum = cardid.getText().toString();
            startActivity(new Intent(this, SetPhotoActivity.class));
        }
    }

    private void initlistener() {
        new BankInfoServlet(this, 1, sp_bank, null).execute();
        new BankInfoServlet(this, 2, sp_province, null).execute();

        sp_bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserInfoEntity.bankId = UserInfoEntity.bank.get(position).getId() + "";
                UserInfoEntity.bankName = UserInfoEntity.bank.get(position).getName();
                if (UserInfoEntity.cityName != null) {
                    new BankInfoServlet(RealCardIDActivity.this, 5, sp_xbank, null).execute(UserInfoEntity.bankName, UserInfoEntity.cityName, workey.getText().toString().trim());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sp_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int provide = UserInfoEntity.province.get(position).getId();
                new BankInfoServlet(RealCardIDActivity.this, 3, sp_ctiy, null).execute(provide + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_ctiy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserInfoEntity.cityId = UserInfoEntity.ctiy.get(position).getId() + "";
                UserInfoEntity.cityName = UserInfoEntity.ctiy.get(position).getName();
                new BankInfoServlet(RealCardIDActivity.this, 5, sp_xbank, null).execute(UserInfoEntity.bankName, UserInfoEntity.cityName, workey.getText().toString().trim());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sp_xbank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserInfoEntity.bankBranchId = UserInfoEntity.xbank.get(position).getLinkno();
                UserInfoEntity.bankBranch = UserInfoEntity.xbank.get(position).getBranch();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
