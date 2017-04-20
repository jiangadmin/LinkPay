package com.linkpay.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by jiangadmin
 * on 16/4/15
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:跑马灯
 * update:
 */
public class ScrollForeverTextView extends TextView {

    public ScrollForeverTextView(Context context) {
        super(context);
    }

    public ScrollForeverTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollForeverTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

}  