package com.linkpay.Activity;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkpay.Activity.Home.BillActivity;
import com.linkpay.Activity.Home.MyQRCodeActivity;
import com.linkpay.Activity.Home.MyCardListActivity;
import com.linkpay.Activity.Home.NoticeActivity;
import com.linkpay.Activity.Home.ShareActivity;
import com.linkpay.Activity.Mine.CardIdInfoActivity;
import com.linkpay.Activity.Mine.MoreActivity;
import com.linkpay.Activity.Mine.RealIdCardActivity;
import com.linkpay.Application.Const;
import com.linkpay.Application.MyApplication;
import com.linkpay.Dialog.IntentDialog;
import com.linkpay.R;
import com.linkpay.Utils.LogUtil;
import com.linkpay.Utils.TabToast;
import com.linkpay.Utils.ToolUtil;
import com.linkpay.View.guanggao.Advertisements;
import com.linkpay.View.guanggao.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity {

    LinearLayout top_view, nav_top, bill, qrcode, share, guanggao;

    LayoutInflater inflater;

    ImageView mine, information;

    DrawerLayout drawer;

    TextView phone, name, idcard_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initview();
        RequestManager.init(this);
        inflater = LayoutInflater.from(this);
        initimg();
    }

    private void initview() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        top_view = (LinearLayout) findViewById(R.id.home_top);
        nav_top = (LinearLayout) findViewById(R.id.home_nav_top);
        //保持沉浸式状态
        top_view.setPadding(0, ToolUtil.getStatusHeight(this), 0, 0);
        nav_top.setPadding(0, ToolUtil.getStatusHeight(this), 0, 0);

        mine = (ImageView) findViewById(R.id.home_top_mine);
        information = (ImageView) findViewById(R.id.home_top_information);

        bill = (LinearLayout) findViewById(R.id.home_bill);
        qrcode = (LinearLayout) findViewById(R.id.home_qrcode);

        share = (LinearLayout) findViewById(R.id.home_share);

        phone = (TextView) findViewById(R.id.home_nav_phone);
        name = (TextView) findViewById(R.id.home_nav_name);

        idcard_type = (TextView) findViewById(R.id.home_nav_idcard_type);
        guanggao = (LinearLayout) findViewById(R.id.home_guanggao_info);


//        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
//        MDStatusBarCompat.setDrawerToolbarTabLayout(this, coordinatorLayout);
    }


    @Override
    protected void onResume() {
        super.onResume();
        phone.setText(Const.LOGIN_PHONE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (Const.ACCOUNT_TYPE) {
            case Const.USERTYPE_NULL:
                builder.setTitle("认证");
                builder.setMessage("进入微信搜索公众号： " + getResources().getString(R.string.app_name)+"\n关注后，并注册成功，方可使用");
                builder.setPositiveButton("去关注", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        复制 APP 名到粘贴板
                       ((ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE)).setText(getResources().getString(R.string.app_name));
//                        启动微信
                        MyApplication.WeChat_api.openWXApp();
//                        退出程序
                        MyApplication.getInstance().finishAllActivity();
                    }
                });
                builder.setCancelable(false);
                builder.show();
                break;
            case Const.USERTYPE_NO:
                idcard_type.setText("未认证");
                name.setText("未认证");
                builder.setTitle("认证");
                builder.setMessage("当前还没有进行认证，无法进行更多的操作。建议立即认证");
                builder.setNegativeButton("明再说", null);
                builder.setPositiveButton("认证", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(MainActivity.this, RealIdCardActivity.class));
                    }
                });
                builder.show();
                break;
            case Const.USERTYPE_ERROR1:
            case Const.USERTYPE_ERROR2:
                idcard_type.setText("认证错误");
                name.setText("认证错误");
                builder.setTitle("认证异常");
                builder.setMessage(Const.USERTYPE_ERROR_MESSAGE);
                builder.setNegativeButton("没看见", null);
                builder.setPositiveButton("再来一次", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(MainActivity.this, RealIdCardActivity.class));
                    }
                });
                builder.show();
                break;
            case Const.USERTYPE_OK:
                idcard_type.setText("已认证");
                name.setText(Const.NAME);
                break;

            case Const.USERTYPE_ING:
                idcard_type.setText("认证中");
                name.setText("认证中");
                break;

        }
    }

    private void initimg() {

        // 添加图片的Url地址
        JSONArray advertiseArray = new JSONArray();
        try {
            advertiseArray.put(new JSONObject().put("head_img", Const.URL + "bg/" + Const.BRAND + "/1.png"));
            advertiseArray.put(new JSONObject().put("head_img", Const.URL + "bg/" + Const.BRAND + "/2.png"));
            advertiseArray.put(new JSONObject().put("head_img", Const.URL + "bg/" + Const.BRAND + "/3.png"));
//            advertiseArray.put(new JSONObject().put("head_img", Const.IMAGE + "bg/" + Const.BRAND + "/4.jpg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        guanggao.addView(new Advertisements(this, true, inflater, 3000).initView(advertiseArray));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            IntentDialog.ExitDialog(this);
        }
    }


    public void Mine(View view) {
        drawer.openDrawer(GravityCompat.START);
    }


    public void 通知(View view) {
//        TabToast.makeText(this,"年后开放");
        startActivity(new Intent(this, NoticeActivity.class));
    }

    /**
     * 账单查询
     *
     * @param view
     */
    public void Bill(View view) {
        if (getUserType())
            startActivity(new Intent(this, BillActivity.class));
//        switch (Const.ACCOUNT_TYPE) {
//            case Const.USERTYPE_NO:
//            case Const.USERTYPE_ERROR1:
//            case Const.USERTYPE_ERROR2:
//                TabToast.makeText(this, "当前还没有完成实名认证");
//                break;
//            case Const.USERTYPE_ING:
//                TabToast.makeText(this, "实名认证进行中...");
//                break;
//            case Const.USERTYPE_OK:
//                startActivity(new Intent(this, BillActivity.class));
//                break;
//            default:
//                TabToast.makeText(this, "状态有误（code:" + Const.ACCOUNT_TYPE + "）");
//                break;
//        }

    }

    /**
     * 我的台码
     *
     * @param view
     */
    public void MyQRCode(View view) {
        if (getUserType())
            startActivity(new Intent(this, MyQRCodeActivity.class));
//        switch (Const.ACCOUNT_TYPE) {
//            case Const.USERTYPE_NO:
//            case Const.USERTYPE_ERROR1:
//            case Const.USERTYPE_ERROR2:
//                TabToast.makeText(this, "当前还没有完成实名认证");
//                break;
//            case Const.USERTYPE_ING:
//                TabToast.makeText(this, "认证中");
//                break;
//            case Const.USERTYPE_OK:
//                startActivity(new Intent(this, MyQRCodeActivity.class));
//                break;
//            default:
//                TabToast.makeText(this, "状态有误（code:" + Const.ACCOUNT_TYPE + "）");
//                break;
//        }
    }

    /**
     * 推荐好友
     *
     * @param view
     */
    public void 推荐好友(View view) {
        startActivity(new Intent(this, ShareActivity.class));
    }


    /**
     * 无卡支付
     */
    public void 无卡支付(View view){
        if (getUserType())
            startActivity(new Intent(this, MyCardListActivity.class));

//        switch (Const.ACCOUNT_TYPE) {
//            case Const.USERTYPE_NO:
//            case Const.USERTYPE_ERROR1:
//            case Const.USERTYPE_ERROR2:
//                TabToast.makeText(this, "当前还没有完成实名认证");
//                break;
//            case Const.USERTYPE_ING:
//                TabToast.makeText(this, "实名认证进行中...");
//                break;
//            case Const.USERTYPE_OK:
//                startActivity(new Intent(this, MyCardListActivity.class));
//                break;
//            default:
//                TabToast.makeText(this, "状态有误（code:" + Const.ACCOUNT_TYPE + "）");
//                break;
//        }

    }
    /**
     * 实名认证
     *
     * @param view
     */
    public void IDCard(View view) {
        drawer.closeDrawer(GravityCompat.START);

        switch (Const.ACCOUNT_TYPE) {
            case Const.USERTYPE_NO:
            case Const.USERTYPE_ERROR1:
            case Const.USERTYPE_ERROR2:
                startActivity(new Intent(this, RealIdCardActivity.class));
                break;
            case Const.USERTYPE_ING:
                TabToast.makeText(this, "认证中");
                break;
            case Const.USERTYPE_OK:
                TabToast.makeText(this, "已完成认证");
                break;
            default:
                TabToast.makeText(this, "状态有误（code:" + Const.ACCOUNT_TYPE + "）");
                break;
        }

    }

    /**
     * 结算卡信息
     *
     * @param view
     */
    public void CardIDInfo(View view) {
        drawer.closeDrawer(GravityCompat.START);
        switch (Const.ACCOUNT_TYPE) {
            case Const.USERTYPE_NO:
            case Const.USERTYPE_ERROR1:
            case Const.USERTYPE_ERROR2:
                TabToast.makeText(this, "当前还没有完成实名认证");
                break;
            case Const.USERTYPE_ING:
                TabToast.makeText(this, "认证中");
                break;
            case Const.USERTYPE_OK:
                TabToast.makeText(this,"开发中...");
//                startActivity(new Intent(this, CardIdInfoActivity.class));
                break;
            default:
                TabToast.makeText(this, "状态有误（code:" + Const.ACCOUNT_TYPE + "）");
                break;
        }
    }

    public boolean getUserType(){
        switch (Const.ACCOUNT_TYPE) {
            case Const.USERTYPE_NO:
            case Const.USERTYPE_ERROR1:
            case Const.USERTYPE_ERROR2:
                TabToast.makeText(this, "当前还没有完成实名认证");
                return false;

            case Const.USERTYPE_ING:
                TabToast.makeText(this, "认证中");
                return false;
            case Const.USERTYPE_OK:
                return true;
            default:
                TabToast.makeText(this, "状态有误（code:" + Const.ACCOUNT_TYPE + "）");
                return false;
        }
    }

    /**
     * 设置
     *
     * @param view
     */
    public void Setting(View view) {
        drawer.closeDrawer(GravityCompat.START);
        startActivity(new Intent(this, MoreActivity.class));

    }

    /**
     * 退出
     *
     * @param view
     */
    public void Exit(View view) {
        drawer.closeDrawer(GravityCompat.START);
        IntentDialog.ExitDialog(this);
    }
}
