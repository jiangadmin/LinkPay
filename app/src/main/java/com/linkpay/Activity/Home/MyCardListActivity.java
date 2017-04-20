package com.linkpay.Activity.Home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.Adapter.MyCardListAdapter;
import com.linkpay.Entity.CardEntity;
import com.linkpay.R;
import com.linkpay.Service.BankCardInfoServlet;
import com.linkpay.Utils.TabToast;
import com.linkpay.View.MycardAddFooter;

import java.util.ArrayList;

/**
 * Created by jiang
 * on 2017/1/16.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose: 无卡支付
 */
public class MyCardListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    CardEntity cardEntity;
    ListView cardlist;
    MyCardListAdapter adapter;
    ArrayList<CardEntity.ResultListBean> lists = new ArrayList<>();

    MycardAddFooter mycardAddFooter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycardlist);
        setTitle("已绑定的信用卡");
        setBack(true);
        mycardAddFooter = new MycardAddFooter(this);
        cardlist = (ListView) findViewById(R.id.mycard_list);
        cardlist.addFooterView(mycardAddFooter);
        cardlist.setOnItemClickListener(this);


    }

    @Override
    protected void onResume() {
        progressDialog = new ProgressDialog(this).show(this,"请稍后","获取信息中...");
        new BankCardInfoServlet(this,progressDialog).execute();
        cardEntity = new CardEntity();
        super.onResume();
    }

    /**
     * 更新列表显示
     *
     * @param listBeen 回传的列表信息
     */
    public void updatelistview(ArrayList<CardEntity.ResultListBean> listBeen) {
        lists = null;
        lists = listBeen;
        adapter = new MyCardListAdapter(this, lists);
        cardlist.setAdapter(adapter);

    }

    public void 添加信用卡(View view) {
        startActivity(new Intent(this, AddCardActivity.class));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (lists.get(i).getSabcState() != 1) {
            TabToast.makeText(this, "当前还不能使用");
        } else {
            startActivity(new Intent(this, CardPayActivty.class).putExtra("手机号", lists.get(i).getSabcPhoneNum()).putExtra("银行卡号", lists.get(i).getSabcBankCard()));
        }
    }
}
