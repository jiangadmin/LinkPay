package com.linkpay.Activity.Home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.Application.MyApplication;
import com.linkpay.R;
import com.linkpay.Utils.ImageUtils;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jiang
 * on 2016/10/20.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:分享
 */
public class ShareActivity extends BaseActivity {
    LinearLayout main;
    ImageView qrcode;
    String ShareURL = "http://fusion.qq.com/cgi-bin/qzapps/unified_jump?appid=42376519&from=mqq&actionFlag=0&params=pname%3Dcom.linkpay%26versioncode%3D1%26channelid%3D%26actionflag%3D0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        setBack(true);
        setTitle("分享");

        initview();
        initeven();
    }

    private void initview() {
        main = (LinearLayout) findViewById(R.id.share_main);
        qrcode = (ImageView) findViewById(R.id.share_img);
    }

    private void initeven() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
                cancel();

            }
        }, 24);
    }

    public void Share(View view) {
        //初始化一个WXWebpageObject对象，填写URL
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = ShareURL;

        //用WXWebpageObject对象初始化一个WXmediaMessage对象，填写标题、描述
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = getResources().getString(R.string.app_name);
        msg.description = "领贝钱包用户的管理后台，可以查看账单和管理自己的店铺。";
        msg.thumbData = null;

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        switch (view.getId()) {
            case R.id.share_wechat:
                //分享到朋友
                req.scene = SendMessageToWX.Req.WXSceneSession;
                MyApplication.WeChat_api.sendReq(req);

                break;
            case R.id.share_wechat_friend:
                //分享到朋友圈
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                MyApplication.WeChat_api.sendReq(req);
                break;
            case R.id.share_sms:
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_HTML_TEXT, ShareURL);
//                shareIntent.setType("text/plain");
//                //设置分享列表的标题，并且每次都显示分享列表
//                startActivity(Intent.createChooser(shareIntent, "分享到"));
                break;
        }

    }

    Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    qrcode.setImageBitmap(ImageUtils.getQRcode(ShareURL));
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Message message = new Message();
                            message.what = 2;
                            handler.sendMessage(message);
                            cancel();
                        }
                    }, 1);
                    break;
                case 2:
                    main.setBackgroundResource(R.drawable.share_bg);
                    break;
            }
            super.handleMessage(msg);
        }

    };
}
