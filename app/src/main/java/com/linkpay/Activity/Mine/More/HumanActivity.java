package com.linkpay.Activity.Mine.More;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.Application.Const;
import com.linkpay.R;
import com.linkpay.Utils.LogUtil;

/**
 * Created by jiang
 * on 2016/10/20.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:客服  环信
 */
public class HumanActivity extends BaseActivity {
    WebView webView;
    ProgressBar pro;
    public ValueCallback<Uri> uploadFile; //保存一个这样的成员变量
    public ValueCallback<Uri[]> uploadFileforaos5;

    public final static int FILECHOOSER_RESULTCODE = 1;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = (WebView) findViewById(R.id.web_view);
        pro = (ProgressBar) findViewById(R.id.web_view_pro);

        /**********/


        webView.setWebChromeClient(new RsenWebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void openFileChooser(ValueCallback<Uri> uploadFile) {
                openFileChooserImpl(uploadFile);
//                KeFuActivity.this.uploadFile = uploadFile;
//                startActivityForResult(new Intent(KeFuActivity.this, SelectPicPopupWindow.class), FILECHOOSER_RESULTCODE);
            }

            @Override
            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType) {
                openFileChooser(uploadFile);
            }

            @Override
            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture) {
                openFileChooser(uploadFile);
            }

            // For Android > 5.0
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, WebChromeClient.FileChooserParams fileChooserParams) {
                openFileChooserImplForAndroid5(uploadMsg);
                return true;
            }
        });


        /**********/


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
//        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setBuiltInZoomControls(true); // 显示放大缩小 controler
//        webSettings.setSupportZoom(true); // 可以缩放
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.e(this.toString(), "跳转" + url);
                view.loadUrl(Const.HUMAN);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pro.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });


        webView.loadUrl(Const.HUMAN);
    }

    private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
        uploadFile = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
    }

    private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
        uploadFileforaos5 = uploadMsg;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "选择图片");

        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == uploadFile)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            uploadFile.onReceiveValue(result);
            uploadFile = null;

        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == uploadFileforaos5)
                return;
            Uri result = (intent == null || resultCode != RESULT_OK) ? null : intent.getData();
            if (result != null) {
                uploadFileforaos5.onReceiveValue(new Uri[]{result});
            } else {
                uploadFileforaos5.onReceiveValue(new Uri[]{});
            }
            uploadFileforaos5 = null;
        }
    }

    public abstract class RsenWebChromeClient extends WebChromeClient {
        /**
         * 兼容3.0以前的版本
         */
        public abstract void openFileChooser(ValueCallback<Uri> uploadFile);

        /**
         * 兼容4.0以前的版本
         */
        public abstract void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType);

        /**
         * 兼容4.0以后的版本
         */
        public abstract void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture);
    }

}
