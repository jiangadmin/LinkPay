package com.linkpay.Activity.Mine;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.R;
import com.linkpay.Service.RealIdCardServlet;
import com.linkpay.Utils.TabToast;
import com.linkpay.Utils.ToolUtil;


/**
 * Created by jiang
 * on 16/4/5
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:实名认证
 * update:
 */
public class RealIdCardActivity extends BaseActivity {
    EditText name, idcard;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setContentView(R.layout.activity_realidcard);
        setTitle("实名认证");
        setBack(true);
        initview();

    }

    private void initview() {
        name = (EditText) findViewById(R.id.realidcard_name);
        idcard = (EditText) findViewById(R.id.realidcard_idcard);
        submit = (Button) findViewById(R.id.realidcard_sumbit);

    }


    public void submit(View view) {
        if (isnull()) {
            RealIdCardServlet.RealIDCardInfo info = new RealIdCardServlet.RealIDCardInfo();
            info.setName(name.getText().toString());
            info.setIdCard(idcard.getText().toString());
            new RealIdCardServlet(this, new ProgressDialog(this).show(this, "请稍后", "正在核对...")).execute(info);
        }
    }

    public boolean isnull() {
        if (name.getText().toString().replaceAll(" ", "").length() < 2) {
            TabToast.makeText(this, "填写您的姓名");
            return false;
        }
        if (!ToolUtil.IsIDcard(idcard.getText().toString())) {
            TabToast.makeText(this, "填写正确的身份证号");
            return false;
        }
        return true;
    }
}
