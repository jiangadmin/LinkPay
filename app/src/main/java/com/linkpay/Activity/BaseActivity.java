package com.linkpay.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkpay.Application.MyApplication;
import com.linkpay.R;
import com.linkpay.Utils.ToolUtil;

/**
 * Created by jiangmac
 * on 15/11/17.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:公共
 */
public class BaseActivity extends AppCompatActivity {
    public static Button BTNMENU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //把状态栏设置为透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Activity到堆栈
        MyApplication.getInstance().addActivity(this);
        setContentView(R.layout.view_title_bar);

    }


    //标题
    public void setTitle(String title) {
        RelativeLayout titlebar = (RelativeLayout) findViewById(R.id.title_bar_tob);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) titlebar.getLayoutParams();
        //获取状态栏高度 加上 要设置的标题栏高度 等于 标题栏实际高度
        layoutParams.height = ToolUtil.getStatusHeight(this) + ToolUtil.dp2px(this, 42);
        titlebar.setLayoutParams(layoutParams);

        TextView tv = (TextView) findViewById(R.id.title_bar_text);
        tv.setText(title);

    }

    //返回
    public void setBack(boolean isvisible) {

        ImageButton backBtn = (ImageButton) findViewById(R.id.title_bar_back);
        if (isvisible) {
            backBtn.setVisibility(View.VISIBLE);
        } else {

            backBtn.setVisibility(View.GONE);
        }
        backBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                finish();

            }
        });

    }

    //菜单
    public void setMenu(String menuname) {

        BTNMENU = (Button) findViewById(R.id.title_bar_menu);
        if (menuname != null) {
            BTNMENU.setText(menuname);
            BTNMENU.setVisibility(View.VISIBLE);
        } else {

            BTNMENU.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束Activity&从栈中移除该Activity
        finishActivity(1);
    }


}
