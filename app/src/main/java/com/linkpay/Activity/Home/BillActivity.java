package com.linkpay.Activity.Home;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.Adapter.AccountWaterListAdapter;
import com.linkpay.Entity.AccountWearBean;
import com.linkpay.R;
import com.linkpay.Service.AccountWaterDetilsServlet;
import com.linkpay.Service.AccountWaterServlet;
import com.linkpay.Utils.LogUtil;
import com.linkpay.Utils.TabToast;
import com.linkpay.View.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by jiang
 * on 16/8/10
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:资金明细
 * update:
 */
public class BillActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, XListView.IXListViewListener {
    XListView listView;
    FloatingActionButton seach;
    AccountWaterListAdapter adapter;
    ArrayList<AccountWearBean> lists = new ArrayList<>();
    //弹窗中的控件
    TextView startdate, enddate;
    RadioButton quanbu, xiaofei, qingsuan, dongjie, jiedong, shouyi;
    Button exit, sumbit;

    Dialog shaixuan = null;
    Dialog datedialog = null;

    String starttime, endtime;
    String SaleruType = "";

    String nowtype = "";    //当前显示的类型
    String nowstartdate = "";    //当前时间区间起始值
    String nowenddate = "";      //当前时间区间末尾值

    int nowpage = 0;      //起始页码

    private static final String TAG = "BillActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountwater);
        setBack(true);
        setTitle("查看流水");
        initview();
        initeven();
    }

    private void initview() {
        listView = (XListView) findViewById(R.id.accountwater_lv);
        seach = (FloatingActionButton) findViewById(R.id.accountwater_fabtn_seach);


    }

    private void initeven() {
        listView.setPullLoadEnable(true);

        AccountWaterServlet.AccountWater accountWater = new AccountWaterServlet.AccountWater();
        accountWater.setNowPage(nowpage);
        new AccountWaterServlet(this, new ProgressDialog(this).show(this, "请稍后", "加载中...")).execute(accountWater);

        seach.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        listView.setXListViewListener(this);


    }

    /**
     * 更新列表显示
     *
     * @param listbeen 回传的列表信息
     */
    public void updatelistview(ArrayList<AccountWearBean> listbeen) {

        if (nowtype != SaleruType) {
            lists.clear();
            nowtype = SaleruType;
        }

        this.lists.addAll(listbeen);
        adapter = new AccountWaterListAdapter(this, lists);
        listView.setAdapter(adapter);
        if (lists.size() > 20)
            listView.setSelection(lists.size() - 20);
        listView.stopRefresh();

        listView.stopLoadMore();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            显示筛选框
            case R.id.accountwater_fabtn_seach:
                showdialog();
                break;
//            显示时间选择器
            case R.id.dialog_account_startdate:
                DateDialog(v);
                break;
//            显示时间选择器
            case R.id.dialog_account_enddate:
                DateDialog(v);
                break;
            //类型选择：全部
            case R.id.dialog_account_quanbu:
                Raidobuttoninit();
                SaleruType = "";
                quanbu.setChecked(true);
                quanbu.setTextColor(getResources().getColor(R.color.style_color));
                break;
            //类型选择：消费
            case R.id.dialog_account_xiaofei:
                Raidobuttoninit();
                SaleruType = "1";
                xiaofei.setChecked(true);
                xiaofei.setTextColor(getResources().getColor(R.color.style_color));
                break;
            //类型选择：清算
            case R.id.dialog_account_qingsuan:
                Raidobuttoninit();
                SaleruType = "6";
                qingsuan.setChecked(true);
                qingsuan.setTextColor(getResources().getColor(R.color.style_color));
                break;
            case R.id.dialog_account_dongjie:
                Raidobuttoninit();
                SaleruType = "0";
                dongjie.setChecked(true);
                dongjie.setTextColor(getResources().getColor(R.color.style_color));
                break;
            case R.id.dialog_account_jiedong:
                Raidobuttoninit();
                SaleruType = "0";
                jiedong.setChecked(true);
                jiedong.setTextColor(getResources().getColor(R.color.style_color));
                break;
            case R.id.dialog_account_shouyi:
                Raidobuttoninit();
                SaleruType = "0";
                shouyi.setChecked(true);
                shouyi.setTextColor(getResources().getColor(R.color.style_color));
                break;

            case R.id.dialog_account_exit:
                shaixuan.dismiss();
                break;
            case R.id.dialog_account_sumbit:
                shaixuan.dismiss();
                AccountWaterServlet.AccountWater info = new AccountWaterServlet.AccountWater();
                if (starttime != null && endtime != null) {
                    info.setStartTime(starttime);
                    info.setEndTime(endtime);
                }
                if (SaleruType.equals("0")) {
                    TabToast.makeText(this, "暂不支持此类型");
                    return;
                }
                if (!SaleruType.equals("")) {
                    info.setSaleruType(SaleruType);
                }
                new AccountWaterServlet(this, new ProgressDialog(this).show(this, "请稍后", "加载中...")).execute(info);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AccountWearBean wearBean = lists.get(position - 1);
        LogUtil.e("点击的", position + "");
        LogUtil.e("点击后", wearBean.getId() + "");
        AccountWaterDetilsServlet.AccountWaterDetils accountWaterDetils = new AccountWaterDetilsServlet.AccountWaterDetils();
        accountWaterDetils.setId(wearBean.getId());
        accountWaterDetils.setSaleruType(wearBean.getSaleruType());
        new AccountWaterDetilsServlet(this).execute(accountWaterDetils);


    }

    /**
     * 筛选弹框
     */
    void showdialog() {

        shaixuan = new Dialog(this);
        shaixuan.requestWindowFeature(Window.FEATURE_NO_TITLE);
        shaixuan.setContentView(R.layout.dialog_accountwater);
        startdate = (TextView) shaixuan.findViewById(R.id.dialog_account_startdate);
        enddate = (TextView) shaixuan.findViewById(R.id.dialog_account_enddate);

        startdate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        enddate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        quanbu = (RadioButton) shaixuan.findViewById(R.id.dialog_account_quanbu);
        xiaofei = (RadioButton) shaixuan.findViewById(R.id.dialog_account_xiaofei);
        qingsuan = (RadioButton) shaixuan.findViewById(R.id.dialog_account_qingsuan);
        dongjie = (RadioButton) shaixuan.findViewById(R.id.dialog_account_dongjie);
        jiedong = (RadioButton) shaixuan.findViewById(R.id.dialog_account_jiedong);
        shouyi = (RadioButton) shaixuan.findViewById(R.id.dialog_account_shouyi);

        switch (nowtype) {
            case "":
                quanbu.setTextColor(getResources().getColor(R.color.style_color));
                break;
            case "1":
                xiaofei.setTextColor(getResources().getColor(R.color.style_color));
                break;
            case "6":
                qingsuan.setTextColor(getResources().getColor(R.color.style_color));
                break;
        }

        exit = (Button) shaixuan.findViewById(R.id.dialog_account_exit);
        sumbit = (Button) shaixuan.findViewById(R.id.dialog_account_sumbit);

        startdate.setOnClickListener(this);
        enddate.setOnClickListener(this);

        quanbu.setOnClickListener(this);
        xiaofei.setOnClickListener(this);
        qingsuan.setOnClickListener(this);
        dongjie.setOnClickListener(this);
        jiedong.setOnClickListener(this);
        shouyi.setOnClickListener(this);
        exit.setOnClickListener(this);
        sumbit.setOnClickListener(this);

        shaixuan.show();
    }

    //    筛选按钮初始化
    void Raidobuttoninit() {
        quanbu.setTextColor(getResources().getColor(R.color.black));
        xiaofei.setTextColor(getResources().getColor(R.color.black));
        qingsuan.setTextColor(getResources().getColor(R.color.black));
        dongjie.setTextColor(getResources().getColor(R.color.black));
        jiedong.setTextColor(getResources().getColor(R.color.black));
        shouyi.setTextColor(getResources().getColor(R.color.black));

        quanbu.setChecked(false);
        xiaofei.setChecked(false);
        qingsuan.setChecked(false);
        dongjie.setChecked(false);
        jiedong.setChecked(false);
        shouyi.setChecked(false);

    }

    long startdatetime = 0;


    //    日期选择器
    void DateDialog(final View view) {


        datedialog = new Dialog(this);
        datedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        datedialog.setContentView(R.layout.dialog_datepicker);
        final DatePicker datePicker = (DatePicker) datedialog.findViewById(R.id.dialog_datepicker);
        Button submit = (Button) datedialog.findViewById(R.id.dialog_datepicker_sumbit);
        datePicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        if (view == enddate && startdatetime != 0) {
            datePicker.setMinDate(startdatetime);
        }
        datePicker.setMaxDate(new Date().getTime());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view == startdate) {
                    startdatetime = datePicker.getDrawingTime();
                    startdate.setText(datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth());
                    starttime = startdate.getText().toString();
                } else {
                    enddate.setText(datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth());
                    endtime = enddate.getText().toString();
                }
                startdatetime = 0;
                datedialog.dismiss();

            }
        });
        datedialog.show();

    }

    long time = 0;

    //下拉刷新数据
    @Override
    public void onRefresh() {
        LogUtil.e(this.toString(), "time4 :" + time + " curr" + System.currentTimeMillis() + " cha" + (System.currentTimeMillis() - time));
        if (time != 0 && System.currentTimeMillis() - time < 200) {
            TabToast.makeText(this, "刷新过于频繁,请稍后在试");
            return;
        }
        //清空列表 重新加载
        lists.clear();
        AccountWaterServlet.AccountWater accountWater = new AccountWaterServlet.AccountWater();
        nowpage = 0;
        accountWater.setNowPage(nowpage);
        new AccountWaterServlet(this, null).execute(accountWater);

    }

    //上拉加载更多
    @Override
    public void onLoadMore() {
        if (Nextpage.nextpage) {
            LogUtil.e("list最后一个", lists.size() + "");
            AccountWearBean wearBean = lists.get(lists.size() - 1);
            AccountWaterServlet.AccountWater accountWater = new AccountWaterServlet.AccountWater();
            accountWater.setAleruaccountId(wearBean.getId() + "");
            nowpage = nowpage + 1;
            accountWater.setNowPage(nowpage);
            LogUtil.e(this, "当前页：" + nowpage);
            new AccountWaterServlet(this, null).execute(accountWater);
        } else {
            LogUtil.e("拉取数据", "不再拉取");
            listView.stopRefresh();
            listView.stopLoadMore();
            ((TextView) findViewById(R.id.xlistview_footer_hint_textview)).setText("数据加载完毕");
            ((TextView) findViewById(R.id.xlistview_footer_hint_textview)).setText("数据加载完毕");

        }

    }

    /**
     * 控制是否继续加载
     */
    public static class Nextpage {

        public static boolean nextpage = true;

    }
}
