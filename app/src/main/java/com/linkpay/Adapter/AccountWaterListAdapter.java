package com.linkpay.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.linkpay.Entity.AccountWearBean;
import com.linkpay.R;

import java.util.ArrayList;

/**
 * Created by jiang
 * on 16/8/11
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:资金明细列表适配器
 * update:
 */
public class AccountWaterListAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<AccountWearBean> accountWearBeen;

    public AccountWaterListAdapter(Context context, ArrayList<AccountWearBean> accountWearBeen) {
        layoutInflater = LayoutInflater.from(context);
        this.accountWearBeen = accountWearBeen;
    }


    @Override
    public int getCount() {
        return accountWearBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return accountWearBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_acountwater, parent, false);
            holder = new ViewHolder();

            holder.mType = (TextView) convertView.findViewById(R.id.item_type);
            holder.mDate = (TextView) convertView.findViewById(R.id.item_date);
            holder.mMoney = (TextView) convertView.findViewById(R.id.item_money);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AccountWearBean bean = accountWearBeen.get(position);
        holder.mType.setText(bean.getSaleruType() == 1 ? "消费" : "清算");
        holder.mType.setTextColor(bean.getSaleruType() == 1 ? Color.RED : Color.GREEN);
        holder.mDate.setText(bean.getSaleruTime().replace(" ", "\n"));
        holder.mMoney.setText(bean.getSaleruMoney());

        return convertView;
    }

    private class ViewHolder {
        TextView mType;
        TextView mMoney;
        TextView mDate;
    }
}
