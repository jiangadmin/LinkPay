package com.linkpay.Activity.Mine.More;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.Application.Const;
import com.linkpay.Application.MyApplication;
import com.linkpay.R;
import com.linkpay.Service.UpdatepasswordServlet;
import com.linkpay.Utils.SharedPreferencesUtil;
import com.linkpay.Utils.TabToast;
import com.linkpay.Utils.ToolUtil;

/**
 * Created by jiangmac
 * on 15/11/18.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:密码管理
 */
public class PasswordActivity extends BaseActivity implements  CompoundButton.OnCheckedChangeListener {
    EditText oldpwd;
    EditText newpwd1;
    EditText newpwd2;
    Button submit;
    private ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        setTitle("密码管理");
        setBack(true);
        initview();


    }

    private void initview() {

        oldpwd = (EditText) findViewById(R.id.password_old);
        newpwd1 = (EditText) findViewById(R.id.password_new1);
        newpwd2 = (EditText) findViewById(R.id.password_new2);
        submit = (Button) findViewById(R.id.password_submit);

//        if (ToolUtil.isEmpty(
//                SharedPreferencesUtil.getInstance(this).getStringValue(
//                        SharedPreferencesUtil.getInstance(this).getStringValue("phone") + "locuspwd"))) {
//            sspwd.setChecked(false);
//        } else {
//            sspwd.setChecked(true);
//        }

    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        if (ToolUtil.isEmpty(SharedPreferencesUtil.getInstance(this).getStringValue(SharedPreferencesUtil.getInstance(this).getStringValue("phone") + "locuspwd"))) {
//            sspwd.setChecked(false);
//        } else {
//            sspwd.setChecked(true);
//        }
//    }


    public void Submit(View view){
        String oldpwdtext = oldpwd.getText().toString().trim();
        String newpwdtext1 = newpwd1.getText().toString().trim();
        String newpwdtext2 = newpwd2.getText().toString().trim();
        if ("".equals(oldpwdtext)) {
            TabToast.makeText(this, "请输入原密码！");
            return;
        }
        if ("".equals(newpwdtext1)) {
            TabToast.makeText(this, "请输入新密码！");
            return;
        }
        if (newpwdtext1.length() < 6) {
            TabToast.makeText(this, "密码过于简单！");
            return;
        }
        if (!newpwdtext1.equals(newpwdtext2)) {
            TabToast.makeText(this, "两次密码不一样！");
            return;
        }

        progressdialog = ProgressDialog.show(this, "请等待...", "正在修改...");
        UpdatepasswordServlet.UpdatePasswordInfo updatePasswordInfo = new UpdatepasswordServlet.UpdatePasswordInfo();
        updatePasswordInfo.setPhone(Const.LOGIN_PHONE);
        updatePasswordInfo.setOldpwd(oldpwdtext);
        updatePasswordInfo.setNewpwd(newpwdtext1);
        new UpdatepasswordServlet(this, progressdialog).execute();
    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (ToolUtil.isEmpty(
                SharedPreferencesUtil.getInstance(this).getStringValue(
                        SharedPreferencesUtil.getInstance(this).getStringValue("phone") + "locuspwd")) == isChecked) {
//            startActivity(new Intent(this, LocusPWDActivity.class));
        }

    }
}
