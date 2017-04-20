package com.linkpay.Activity.Mine.More;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.Application.Const;
import com.linkpay.Application.MyApplication;
import com.linkpay.R;
import com.linkpay.Service.LoginServlet;
import com.linkpay.Utils.LogUtil;
import com.linkpay.Utils.TabToast;
import com.tencent.mm.sdk.modelbiz.JumpToBizProfile;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by jiang
 * on 2016/10/26.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:客服 微信公众号
 */
public class KeFuActivity extends BaseActivity {
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kefu);
        setBack(true);
        setTitle("联系客服");
        linearLayout = (LinearLayout) findViewById(R.id.kefu_main);
        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                towechat();
                return false;

            }
        });
    }

    public void towechat(){
        if (MyApplication.WeChat_api.isWXAppInstalled()) {
//            复制 APP 名到粘贴板
                    ((ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE)).setText(getResources().getString(R.string.app_name));
//                        启动微信
            MyApplication.WeChat_api.openWXApp();
//            JumpToBizProfile.Req req = new JumpToBizProfile.Req();
//            req.toUserName = "gh_646632cea093"; //公众号原始ID
//            req.profileType = JumpToBizProfile.JUMP_TO_HARD_WARE_BIZ_PROFILE;
//            req.extMsg = "extMsg";
//            api.sendReq(req);
//
//            LogUtil.e(this,"APP_ID:"+MyApplication.WeChat_APP_ID);
//            LogUtil.e(this,"原始ID:"+req.toUserName);

        }else{
            TabToast.makeText(this,"未安装微信");
        }
    }
}
