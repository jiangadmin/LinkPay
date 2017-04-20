package com.linkpay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.linkpay.Entity.BankInfo;
import com.linkpay.R;

import java.util.List;


public class SimpleAdapter extends BaseAdapter {
    private List<BankInfo> list;
    private Context mContext;
    private int type;  //0:name  1:branch

    public SimpleAdapter(Context pContext, List<BankInfo> list, int type) {
        this.mContext = pContext;
        this.list = list;
        this.type = type;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
        convertView = _LayoutInflater.inflate(R.layout.item_spiner, null);
        if (convertView != null) {
            TextView textView = (TextView) convertView.findViewById(R.id.date);
            if (type == 0) {
                textView.setText(list.get(position).getName());
            } else if (type == 1) {
                if (list.get(position).getBranch().indexOf("公司") != -1) {
                    textView.setText(list.get(position).getBranch().subSequence(list.get(position).getBranch().indexOf("公司") + 2, list.get(position).getBranch().length()));
                } else {
                    textView.setText(list.get(position).getBranch());
                }
            } else if (type == 2) {
                textView.setText(list.get(position).getBankName());
            }

        }
        return convertView;
    }
}
