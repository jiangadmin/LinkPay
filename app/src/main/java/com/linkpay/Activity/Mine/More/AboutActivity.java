package com.linkpay.Activity.Mine.More;

import android.os.Bundle;
import android.view.View;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.R;
import com.linkpay.Utils.TabToast;

/**
 * Created by jiang
 * on 2016/10/20.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:关于
 */
public class AboutActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abuot);
        setBack(true);
        setTitle("关于");
    }

    public void 去评分(View view){
        TabToast.makeText(this,"前方施工");
    }
    public void 功能介绍(View view){
        TabToast.makeText(this,"前方施工");
    }
    public void 投诉(View view){
        TabToast.makeText(this,"前方施工");
    }
}
