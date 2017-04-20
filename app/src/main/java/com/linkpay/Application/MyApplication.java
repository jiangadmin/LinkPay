package com.linkpay.Application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import com.linkpay.Activity.Login.WelcomeActivity;
import com.linkpay.Utils.LogUtil;
import com.linkpay.Utils.MyProperUtil;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by jiang
 * on 16/10/12.
 * Email: jiangyaoyao@chinarb.cn
 * Phone：18605296932
 * Purpose:
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    // user your appid the key.
    public static String MI_PUSH_APP_ID;
    // user your appid the key.
    public static String MI_PUSH_APP_KEY;

    //微信服务
    public static String WeChat_APP_ID;
    public static IWXAPI WeChat_api;
    public static String WeChat_SECRET;
    //Tencent
    public static String Tencent_APP_ID;
    public static String Tencent_APP_KEY;


    private static MyApplication singleton;
    private static Stack<Activity> activityStack;

    private static AppHandler sHandler = null;
    private static WelcomeActivity sMainActivity = null;


    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        initial();

        //初始化push推送服务
        if (shouldInit()) {
            MiPushClient.registerPush(this, MI_PUSH_APP_ID, MI_PUSH_APP_KEY);
            LogUtil.e(TAG, "初始化push推送服务");
        }

        //        微信
        WeChat_api = WXAPIFactory.createWXAPI(this, WeChat_APP_ID, true);
        WeChat_api.registerApp(WeChat_APP_ID);

        //打开Log
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);

        if (sHandler == null) {
            sHandler = new AppHandler(getApplicationContext());
        }


    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    private void initial() {
        WeChat_APP_ID = MyProperUtil.getProperties(this).get("WeChat_ID").toString();
        LogUtil.i(this.toString(), "初始化微信ID：" + WeChat_APP_ID);

        WeChat_SECRET = MyProperUtil.getProperties(this).get("WeChat_SECRET").toString();
        LogUtil.i(this.toString(), "初始化微信SECRET：" + WeChat_SECRET);

        Const.URL = MyProperUtil.getProperties(this).get("url").toString();
        LogUtil.i(this.toString(), "初始化服务器地址：" + Const.URL);

        Const.IMAGE = MyProperUtil.getProperties(this).get("image").toString();
        LogUtil.i(this.toString(), "初始化广告地址：" + Const.IMAGE);

        Const.HUMAN = MyProperUtil.getProperties(this).get("kefu").toString();
        LogUtil.i(this.toString(), "初始化客服地址：" + Const.HUMAN);

        Const.BRAND = MyProperUtil.getProperties(this).get("barnd").toString();
        LogUtil.i(this.toString(), "初始化品牌号：" + Const.BRAND);

        Const.HTTPDES = new Boolean(MyProperUtil.getProperties(this).get("httpdes").toString());
        LogUtil.i(this.toString(), "初始化通信加密：" + Const.HTTPDES);

        Const.HTTPKEY = MyProperUtil.getProperties(this).get("httpkey").toString();
        LogUtil.i(this.toString(), "初始化通讯秘钥：" + Const.HTTPKEY);

        Const.LOGSHOW = new Boolean(MyProperUtil.getProperties(this).get("logshow").toString());
        LogUtil.i(this.toString(), "初始化打印日志：" + Const.LOGSHOW);

        MI_PUSH_APP_ID = MyProperUtil.getProperties(this).get("MiPush_ID").toString();
        LogUtil.i(this.toString(), "初始化推送ID：" + MI_PUSH_APP_ID);

        MI_PUSH_APP_KEY = MyProperUtil.getProperties(this).get("MiPush_KEY").toString();
        LogUtil.i(this.toString(), "初始化推送KEY：" + MI_PUSH_APP_KEY);

        Tencent_APP_ID = MyProperUtil.getProperties(this).get("Tencent_APP_ID").toString();
        LogUtil.i(this.toString(), "初始化腾讯ID：" + Tencent_APP_ID);

        Tencent_APP_KEY = MyProperUtil.getProperties(this).get("Tencent_APP_KEY").toString();
        LogUtil.i(this.toString(), "初始化腾讯KEY：" + Tencent_APP_KEY);

    }


    /*********************/
    // Returns the application instance
    public static MyApplication getInstance() {
        return singleton;
    }

    /**
     * add Activity 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
        }
    }

    public static AppHandler getHandler() {
        return sHandler;
    }

    public static class AppHandler extends Handler {

        private Context context;

        public AppHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            Map<String, String> s = (Map<String, String>) msg.obj;
            if (sMainActivity != null) {

            }


        }
    }

}
