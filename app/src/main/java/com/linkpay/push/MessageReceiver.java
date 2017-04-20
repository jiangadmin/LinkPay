package com.linkpay.push;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.text.TextUtils;

import com.linkpay.Application.KEY_NAME;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.Dialog.PushDialog;
import com.linkpay.Utils.LogUtil;
import com.linkpay.Utils.SqliteUtil;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageReceiver extends PushMessageReceiver {
    private String mRegId;
    private long mResultCode = -1;
    private String mReason;
    private String mCommand;
    private String mMessage;
    private String mTopic;
    private String mAlias;
    private String mUserAccount;
    private String mStartTime;
    private String mEndTime;

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        LogUtil.e("小米推送返回", "onReceivePassThroughMessage");
        LogUtil.e("小米推送返回", message.toString());
        mMessage = message.getContent();
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        LogUtil.e("小米推送，点击标题", message.toString());
        mMessage = message.getContent();

        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }


        PushDialog.mTitle = message.getTitle();
        PushDialog.mMessage = message.getContent();
        PushDialog.mType = Integer.parseInt(message.getExtra().get("PushType"));
        Intent intent = new Intent(context, PushDialog.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);


    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        LogUtil.e("小米推送，接收到：", message.toString());
        mMessage = message.getContent();

        //铃声启动
        if (message.getPassThrough() == 0) {
            MediaPlayer mp = new MediaPlayer();
            try {
                mp.setDataSource(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                mp.prepare();
                mp.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间

        //生成ContentValues对象 //key:列名，value:想插入的值
        ContentValues cv = new ContentValues();
        //往ContentValues对象存放数据，键-值对模式
        cv.put(KEY_NAME.PLID, message.getUserAccount());
        cv.put(KEY_NAME.推送标题, message.getTitle());
        cv.put(KEY_NAME.推送简述, message.getDescription());
        cv.put(KEY_NAME.推送内容, message.getContent());
        cv.put(KEY_NAME.推送时间, formatter.format(curDate));
        cv.put(KEY_NAME.推送类型, message.getExtra().get("PushType"));
        cv.put(KEY_NAME.推送已读, 0);
        //调用insert方法，将数据插入数据库
        SqliteUtil.Insert(context,"push",cv);

    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        LogUtil.e("小米推送返回", "onCommandResult");
        LogUtil.e("小米推送返回", message.toString());
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
            }
        }
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        LogUtil.e("小米推送返回", "onReceiveRegisterResult");
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                LogUtil.e("小米推送", "成功");
                LogUtil.e("小米推送", "command：" + message.getCommand());
                LogUtil.e("小米推送", "resultCode：" + message.getResultCode());
                LogUtil.e("小米推送", "reason：" + message.getReason());
                LogUtil.e("小米推送", "category：" + message.getCategory());
                LogUtil.e("小米推送", "commandArguments：" + message.getCommandArguments());

                MiPushClient.setAlias(context, message.getCommandArguments().toString(), null);
                mRegId = cmdArg1;
            }
        }
    }
}