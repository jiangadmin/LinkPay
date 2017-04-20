package com.linkpay.push;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 1、本demo可以直接运行，设置topic和alias。
 * 服务器端使用appsecret即可以向demo发送广播和单点的消息。
 * 2、为了修改本demo为使用你自己的appid，你需要修改几个地方：DemoApplication.java中的APP_ID,
 * APP_KEY，AndroidManifest.xml中的packagename，和权限permission.MIPUSH_RECEIVE的前缀为你的packagename。
 *
 * @author wangkuiwei
 */
public class PushActivity extends Activity {

    public static List<String> logList = new CopyOnWriteArrayList<>();

    public TextView logView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        // 设置别名
//        MiPushClient.setAlias(PushActivity.this, "", null);
//
//        // 撤销别名
//        MiPushClient.unsetAlias(this, "", null);
//
//        // 设置帐号
//        MiPushClient.setUserAccount(this, "", null);
//
//        // 撤销帐号
//        MiPushClient.unsetUserAccount(this, "", null);

        // 设置标签
        MiPushClient.subscribe(this, "", null);

        // 撤销标签
        MiPushClient.unsubscribe(this, "", null);


//        // 设置接收消息时间
//        findViewById(1).setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                new TimeIntervalDialog(PushActivity.this, new TimeIntervalDialog.TimeIntervalInterface() {
//
//                    @Override
//                    public void apply(int startHour, int startMin, int endHour, int endMin) {
//                        MiPushClient.setAcceptTime(PushActivity.this, startHour, startMin, endHour, endMin, null);
//                    }
//
//                    @Override
//                    public void cancel() {
//                        //ignore
//                    }
//
//                })
//                        .show();
//            }
//        });
        // 暂停推送
        MiPushClient.pausePush(this, null);

        //恢复推送
        MiPushClient.resumePush(this, null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLogInfo();
    }

    public void refreshLogInfo() {
        String AllLog = "";
        for (String log : logList) {
            AllLog = AllLog + log + "\n\n";
        }
        logView.setText(AllLog);
    }
}
