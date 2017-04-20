package com.linkpay.Activity.Home;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.Application.Const;
import com.linkpay.Application.MyApplication;
import com.linkpay.R;
import com.linkpay.Utils.ImageUtils;
import com.linkpay.Utils.LogUtil;
import com.linkpay.Utils.TabToast;

import java.io.File;
import java.util.List;

/**
 * Created by jiang
 * on 2016/10/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:我的台码
 */
public class MyQRCodeActivity extends BaseActivity {
    ImageView qrcode;
    TextView procsn;
    LinearLayout qrcodeimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myqrcode);
        setBack(true);
        setTitle("我的台码");

        initview();
        setScreenBrightness(255);
    }

    private void initview() {
        qrcode = (ImageView) findViewById(R.id.myqrcode_img);
        procsn = (TextView) findViewById(R.id.myqrcode_procsn);
        qrcode.setImageBitmap(ImageUtils.getQRcode(Const.PROC_SN_HTTP));
        procsn.setText("No." + Const.PROC_SN);
        qrcodeimg = (LinearLayout) findViewById(R.id.qrcode_main);


    }

    public void FromImage(View view) {

        qrcodeimg.setDrawingCacheEnabled(true);
        Bitmap bmp = qrcodeimg.getDrawingCache();
        // 拷贝图片，否则在setDrawingCacheEnabled(false)以后该图片会被释放掉
        bmp = bmp.createBitmap(bmp);
        qrcodeimg.setDrawingCacheEnabled(false);
        if (bmp != null) {
            if (ImageUtils.setimgage(bmp, "qrcodeimg")) {
                String imagePath = Environment.getExternalStorageDirectory() + File.separator + "qrcodeimg.png";
//                //由文件得到uri
                Uri imageUri = Uri.fromFile(new File(imagePath));
                switch (view.getId()) {
                    case R.id.fromwechat:
                        if (MyApplication.WeChat_api.isWXAppInstalled()) {
                            initShareIntent("com.tencent.mm", imageUri);
                        } else {
                            TabToast.makeText(this, "还未安装微信");
                        }
                        break;
                    case R.id.fromalipay:
                        initShareIntent("com.alipay.mobile", imageUri);
                        break;
                }

            } else {
                TabToast.makeText(this, "不可用");
            }
        } else {
            TabToast.makeText(this, "不可用");
        }
    }

    /**
     * 保存当前的屏幕亮度值，并使之生效
     */

    public void setScreenBrightness(int paramInt) {
        Window localWindow = getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        float f = paramInt / 255.0F;
        localLayoutParams.screenBrightness = f;
        localWindow.setAttributes(localLayoutParams);
    }

    private void initShareIntent(String type, Uri imageUri) {

        boolean found = false;

        Intent share = new Intent(android.content.Intent.ACTION_SEND);

        share.setType("image/jpeg");

        //gets the list of intents that can be loaded.

        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(share, 0);

        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) || info.activityInfo.name.toLowerCase().contains(type)) {
                    LogUtil.e("分享列表", info.activityInfo.packageName + "---" + info.activityInfo.name);
                    share.putExtra(Intent.EXTRA_STREAM, imageUri);
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                }
            }
            if (!found) return;
            startActivity(Intent.createChooser(share, "发送到"));

        }

    }
}

