package com.linkpay.Activity.Mine.More;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.R;
import com.linkpay.Utils.TabToast;
import com.linkpay.Utils.ToolUtil;
import com.linkpay.View.LocusPassWordView;

/**
 * Created by jiangmac
 * on 15/12/21.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:手势密码
 */
public class LocusPwdActivity extends BaseActivity {
    LocusPassWordView locuspwd;
    String password;
    Button ok, esc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locuspwd);
        setTitle("绘制登录密码图案");
        setBack(true);
        initview();
        passwordIsEmpty();
    }

    private void initview() {
        ok = (Button) findViewById(R.id.locus_ok);
        esc = (Button) findViewById(R.id.locus_esc);
        locuspwd = (LocusPassWordView) findViewById(R.id.locus_password_view);

        ok.setVisibility(View.VISIBLE);
        esc.setVisibility(View.VISIBLE);

    }

    /**
     * 判断密码是否为空
     */
    private void passwordIsEmpty() {
        if (locuspwd.isPasswordEmpty()) {// 密码为空，设置密码

            ok.setText("继续");
            esc.setText("取消");

            ok.setTextColor(Color.GRAY);
            esc.setTextColor(Color.WHITE);

            ok.setEnabled(false);
            esc.setEnabled(true);

            locuspwd.setOnCompleteListener(new LocusPassWordView.OnCompleteListener() {
                @Override
                public void onComplete(String mPassword) {
                    ok.setEnabled(true);
                    ok.setTextColor(Color.WHITE);
                    password = mPassword;
                    esc.setText("重试");
                    carryOn();
                }
            });

            // 删除前一步设置的隐私密码，结束本页面
            esc.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    locuspwd.clearPassword();
                    locuspwd.resetPassWord("");// 密码置空
                    finish();
                }
            });
        } else {// 密码不为空，不显示设置按钮，只绘制密码
            setTitle("确认已保存的密码图案");
            ok.setVisibility(View.GONE);
            esc.setVisibility(View.GONE);

            locuspwd.setOnCompleteListener(new LocusPassWordView.OnCompleteListener() {// 绘制密码
                @Override
                public void onComplete(String mPassword) {
                    if (locuspwd.verifyPassword(mPassword)) {// 绘制的密码一致，删除密码
                        locuspwd.clearPassword();
                        locuspwd.resetPassWord("");// 密码置空
                        TabToast.makeText(LocusPwdActivity.this, "登录密码已经取消!");
                        finish();
                    } else {// 绘制的密码不一致，提示用户
                        TabToast.makeText(LocusPwdActivity.this, "密码绘制错误!");
                        locuspwd.clearPassword();
                    }
                }
            });
        }
    }

    /**
     * 继续
     */
    private void carryOn() {
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!ToolUtil.isEmpty(password)) {
                    locuspwd.resetPassWord(password);
                    locuspwd.clearPassword();

                    ok.setText("确定");
                    ok.setTextColor(Color.GRAY);
                    ok.setEnabled(false);
                    esc.setText("取消");
                    setTitle("确认登录密码图案");
                    resetPWD();
                } else {
                    locuspwd.clearPassword();
                    TabToast.makeText(LocusPwdActivity.this, "密码不能为空,请输入密码.");

                }
            }
        });

        esc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                locuspwd.clearPassword();
                locuspwd.resetPassWord("");
                esc.setText("取消");
                passwordIsEmpty();
            }
        });
    }

    private void resetPWD() {
        esc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                locuspwd.clearPassword();
                locuspwd.resetPassWord("");
                finish();
            }
        });

        locuspwd.setOnCompleteListener(new LocusPassWordView.OnCompleteListener() {
            @Override
            public void onComplete(String mPassword) {
                if (locuspwd.verifyPassword(mPassword)) {
                    ok.setEnabled(true);
                    ok.setTextColor(Color.WHITE);
                    ok.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            locuspwd.resetPassWord(password);
                            TabToast.makeText(LocusPwdActivity.this,"手势密码设置成功");
                            finish();
                        }
                    });
                } else {
                    TabToast.makeText(LocusPwdActivity.this, "密码不一致！");
                    locuspwd.clearPassword();
                }
            }
        });
    }

}
