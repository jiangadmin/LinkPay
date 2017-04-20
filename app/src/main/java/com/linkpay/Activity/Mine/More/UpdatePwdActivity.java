package com.linkpay.Activity.Mine.More;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.Application.Const;
import com.linkpay.R;
import com.linkpay.Service.UpdatepasswordServlet;
import com.linkpay.Utils.TabToast;

/**
 * Created by jiang
 * on 2016/10/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:修改密码
 */
public class UpdatePwdActivity extends BaseActivity {

    EditText oldpwd,newpwd,repwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepwd);
        setBack(true);
        setTitle("修改密码");
        initview();
    }

    private void initview() {
        oldpwd = (EditText) findViewById(R.id.updatepwd_oldpwd);
        newpwd = (EditText) findViewById(R.id.updatepwd_newpwd);
        repwd = (EditText) findViewById(R.id.updatepwd_repwd);
    }

    public void UpdatePWD(View view){
        String oldpwdtext = oldpwd.getText().toString().trim();
        String newpwdtext1 = newpwd.getText().toString().trim();
        String newpwdtext2 = repwd.getText().toString().trim();
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

        UpdatepasswordServlet.UpdatePasswordInfo updatePasswordInfo = new UpdatepasswordServlet.UpdatePasswordInfo();
        updatePasswordInfo.setPhone(Const.LOGIN_PHONE);
        updatePasswordInfo.setOldpwd(oldpwdtext);
        updatePasswordInfo.setNewpwd(newpwdtext1);
        new UpdatepasswordServlet(this,new ProgressDialog(this).show(this, "请等待...", "正在修改...")).execute();
    }
}
