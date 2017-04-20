package com.linkpay.Activity.Mine;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.R;

/**
 * Created by jiang
 * on 2016/11/4.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:结算卡信息
 */
public class CardIdInfoActivity extends BaseActivity {
    ImageView bank_logo;
    TextView bank_name,cardid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardidinfo);
        setTitle("结算卡");
        setBack(true);
        initview();
        evenview();
    }

    private void initview() {
        bank_logo = (ImageView) findViewById(R.id.cardidinfo_bank_logo);
        bank_name  = (TextView) findViewById(R.id.cardidinfo_bank_name);
        cardid = (TextView) findViewById(R.id.cardidinfo_cardid);
    }

    private void evenview() {

    }
}
