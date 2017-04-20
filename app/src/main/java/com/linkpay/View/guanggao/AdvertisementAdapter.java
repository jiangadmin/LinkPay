package com.linkpay.View.guanggao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.linkpay.R;

import org.json.JSONArray;

import java.util.List;

/**
 * 广告轮播adapter
 *
 * @author dong
 * @data 2015年3月8日下午3:46:35
 * @contance dong854163@163.com
 */
public class AdvertisementAdapter extends PagerAdapter {

    private Context context;
    private List<View> views;
    JSONArray advertiseArray;

    public AdvertisementAdapter() {
        super();
    }

    public AdvertisementAdapter(Context context, List<View> views, JSONArray advertiseArray) {
        this.context = context;
        this.views = views;
        this.advertiseArray = advertiseArray;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(views.get(position), 0);
        final int POSITION = position;
        View view = views.get(position);
        try {
            String head_img = advertiseArray.optJSONObject(position).optString("head_img");
            ImageView ivAdvertise = (ImageView) view.findViewById(R.id.ivAdvertise);
            // 加载网络图片
            ImageLoaderUtil.getImage(context, ivAdvertise, head_img, 0, 0, 0, 0);
            // item的点击监听
            ivAdvertise.setOnClickListener(new OnClickListener() {
                @SuppressLint("ShowToast")
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "第" + POSITION + "个广告图片被点击", 0).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

}
