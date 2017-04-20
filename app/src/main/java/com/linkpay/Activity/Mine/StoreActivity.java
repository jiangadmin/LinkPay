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
 * Purpose:店铺信息
 */
public class StoreActivity extends BaseActivity {
    EditText store, address;
    Spinner sp_province, sp_ctiy, sp_region;
    String regionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        setBack(true);
        setTitle("店铺信息");
        initview();
        initlistener();
    }

    private void initview() {
        store = (EditText) findViewById(R.id.store_name);
        address = (EditText) findViewById(R.id.store_address);
        sp_province = (Spinner) findViewById(R.id.store_province);
        sp_ctiy = (Spinner) findViewById(R.id.store_ctiy);
        sp_region = (Spinner) findViewById(R.id.store_region);

    }

    public void Next(View view) {
        if (ToolUtil.isEmpty(store.getText().toString())) {
            TabToast.makeText(this, "还没有店铺名称");
        } else {
            if (ToolUtil.isEmpty(address.getText().toString())) {
                TabToast.makeText(this, "还没有填详细地址");
            } else {
                UserInfoEntity.saruName = store.getText().toString();
                UserInfoEntity.country =regionID;
                UserInfoEntity.address = address.getText().toString();
                startActivity(new Intent(this, RealCardIDActivity.class));
            }
        }

    }

    private void initlistener() {
        new BankInfoServlet(this, 2, sp_province, null).execute();

        sp_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int provide = UserInfoEntity.province.get(position).getId();
                new BankInfoServlet(StoreActivity.this, 3, sp_ctiy, null).execute(provide + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_ctiy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int provide = UserInfoEntity.ctiy.get(position).getId();
                new BankInfoServlet(StoreActivity.this, 4, sp_region, null).execute(provide + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sp_region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                regionID = String.valueOf(UserInfoEntity.region.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
