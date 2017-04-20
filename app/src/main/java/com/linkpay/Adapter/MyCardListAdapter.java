package com.linkpay.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkpay.Entity.CardEntity;
import com.linkpay.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiang
 * on 17/1/17
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:已绑定卡列表适配器
 * update:
 */
public class MyCardListAdapter extends BaseAdapter {
    Context context;

    LayoutInflater layoutInflater;
    ArrayList<CardEntity.ResultListBean> listBeen;

    public MyCardListAdapter(Context context, ArrayList<CardEntity.ResultListBean> listBeen) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.listBeen = listBeen;
    }


    @Override
    public int getCount() {
        return listBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return listBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_mycard, parent, false);
            holder = new ViewHolder();

            holder.mBankLogo = (ImageView) convertView.findViewById(R.id.mycard_banklogo);
            holder.mBankName = (TextView) convertView.findViewById(R.id.mycard_bankname);
            holder.mCardNum = (TextView) convertView.findViewById(R.id.mycard_cardnum);
            holder.mCardType = (TextView) convertView.findViewById(R.id.mycard_cardtype);
            holder.mCardName = (TextView) convertView.findViewById(R.id.mycard_cardname);
            holder.mCardbg = (RelativeLayout) convertView.findViewById(R.id.mycard_bg);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        CardEntity.ResultListBean bean = listBeen.get(position);
        holder.mCardType.setText(bean.getSabcState() == 1 ? "" : "正在绑定");
        holder.mBankName.setText(bean.getSabcBankName());
        holder.mCardName.setText(bean.getSabcBankCardType());
        holder.mCardNum.setText(bean.getSabcBankCard().substring(bean.getSabcBankCard().length() - 4));
        holder.mBankLogo.setImageDrawable(getBankInfo(bean.getSabcBankName(),0));
        holder.mCardbg.setBackground(getBankInfo(bean.getSabcBankName(),1));

        return convertView;
    }

    private class ViewHolder {
        ImageView mBankLogo;
        TextView mBankName;
        TextView mCardNum;
        TextView mCardType;
        TextView mCardName;
        RelativeLayout mCardbg;
    }

    private Drawable getBankInfo(String strBankname,int logoORbg) {

        int strBankIco, strBankCor;

        String strRet = "";

        if (strBankname == null)
            strBankname = "";
        if (strBankname.indexOf("工商银行") > -1 || strBankname.indexOf("工行") > -1) {

            strBankIco = R.drawable.ic_bank_gongshang;
            strBankCor = R.drawable.bank_bg_red;
        } else if (strBankname.indexOf("农业银行") > -1) {
            strBankIco = R.drawable.ic_bank_nongye;
            strBankCor = R.drawable.bank_bg_green;
        } else if (strBankname.indexOf("中国银行") > -1) {
            strBankIco = R.drawable.ic_bank_zhongguo;
            strBankCor = R.drawable.bank_bg_red;
        } else if (strBankname.indexOf("建设银行") > -1) {
            strBankIco = R.drawable.ic_bank_jianse;
            strBankCor = R.drawable.bank_bg_blue;
        } else if (strBankname.indexOf("交通银行") > -1) {
            strBankIco = R.drawable.ic_bank_jiaotong;
            strBankCor = R.drawable.bank_bg_blue;
        } else if (strBankname.indexOf("平安银行") > -1) {
            strBankIco = R.drawable.ic_bank_pinan;
            strBankCor = R.drawable.bank_bg_red;
        } else if (strBankname.indexOf("邮政") > -1
                || strBankname.indexOf("邮储") > -1) {
            strBankIco = R.drawable.ic_bank_youchu;
            strBankCor = R.drawable.bank_bg_green;
        } else if (strBankname.indexOf("招商银行") > -1) {
            strBankIco = R.drawable.ic_bank_zhaoshang;
            strBankCor = R.drawable.bank_bg_red;
        } else if (strBankname.indexOf("民生银行") > -1) {
            strBankIco = R.drawable.ic_bank_mingshen;
            strBankCor = R.drawable.bank_bg_blue;
        } else if (strBankname.indexOf("光大银行") > -1) {
            strBankIco = R.drawable.ic_bank_guangda;
            strBankCor = R.drawable.bank_bg_blue;
        } else if (strBankname.indexOf("华夏银行") > -1) {
            strBankIco = R.drawable.ic_bank_huaxia;
            strBankCor = R.drawable.bank_bg_red;
        } else if (strBankname.indexOf("江苏银行") > -1) {
            strBankIco = R.drawable.ic_bank_jiangsu;
            strBankCor = R.drawable.bank_bg_blue;
        } else if (strBankname.indexOf("南京银行") > -1) {
            strBankIco = R.drawable.ic_bank_nanjing;
            strBankCor = R.drawable.bank_bg_red;
        } else if (strBankname.indexOf("浦发银行") > -1
                || strBankname.indexOf("浦东发展银行") > -1) {
            strBankIco = R.drawable.ic_bank_pufa;
            strBankCor = R.drawable.bank_bg_blue;
        } else if (strBankname.indexOf("兴业银行") > -1) {
            strBankIco = R.drawable.ic_bank_xinye;
            strBankCor = R.drawable.bank_bg_blue;
        } else if (strBankname.indexOf("广发银行") > -1) {
            strBankIco = R.drawable.ic_bank_zhongguoguangfa;
            strBankCor = R.drawable.bank_bg_red;
        } else if (strBankname.indexOf("中信银行") > -1) {
            strBankIco = R.drawable.ic_bank_zhongxin;
            strBankCor = R.drawable.bank_bg_red;
        } else if (strBankname.indexOf("中信自x助银行") > -1) {
            strBankIco = R.drawable.ic_bank_zhongxin;
            strBankCor = R.drawable.bank_bg_red;
        } else { // Other
            strBankIco = R.drawable.ic_logo_icon;
            strBankCor = R.drawable.bank_bg_blue; // 默认 蓝色
        }

        if (logoORbg==0){
            return context.getResources().getDrawable(strBankIco);
        }else {
            return context.getResources().getDrawable(strBankCor);
        }

    }
}
