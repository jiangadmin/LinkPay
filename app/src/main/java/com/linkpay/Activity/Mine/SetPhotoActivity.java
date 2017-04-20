package com.linkpay.Activity.Mine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.linkpay.Activity.BaseActivity;
import com.linkpay.Entity.UserInfoEntity;
import com.linkpay.R;
import com.linkpay.Service.RealIdCardNextServlet;
import com.linkpay.Utils.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;


/**
 * Created by jiang
 * on 2016/10/24.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:照片提交
 */
public class SetPhotoActivity extends BaseActivity {

    private String mFilePath;

    ImageView imageView1, imageView2, imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setphoto);
        mFilePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/Linkpay";
        initview();


    }

    private void initview() {
        imageView1 = (ImageView) findViewById(R.id.setphoto_img_1);
        imageView2 = (ImageView) findViewById(R.id.setphoto_img_2);
        imageView3 = (ImageView) findViewById(R.id.setphoto_img_3);


    }

    private byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        return baos.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    imageView1.setImageBitmap(ImageUtils.get240pimage(mFilePath + "img_1.jpg"));
                    UserInfoEntity.iDCardFront = bitmap2Bytes(ImageUtils.get720Pimage(mFilePath + "img_1.jpg"));
                    break;
                case 2:
                    imageView2.setImageBitmap(ImageUtils.get240pimage(mFilePath + "img_2.jpg"));
                    UserInfoEntity.iDCardBack = bitmap2Bytes(ImageUtils.get720Pimage(mFilePath + "img_2.jpg"));
                    break;
                case 3:
                    imageView3.setImageBitmap(ImageUtils.get240pimage(mFilePath + "img_3.jpg"));
                    UserInfoEntity.iDCardHand = bitmap2Bytes(ImageUtils.get720Pimage(mFilePath + "img_3.jpg"));
                    break;
            }
        }
    }

    /**
     * 身份证银行卡正面
     *
     * @param view
     */
    public void Image1(View view) {

        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mFilePath + "img_1.jpg"))), 1);

    }

    /**
     * 身份证银行卡反面
     *
     * @param view
     */
    public void Image2(View view) {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mFilePath + "img_2.jpg"))), 2);

    }

    /**
     * 手持半身照
     *
     * @param view
     */
    public void Image3(View view) {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mFilePath + "img_3.jpg"))), 3);

    }

    /**
     * 提交
     *
     * @param view
     */
    public void Submit(View view) {
        if (UserInfoEntity.Isnoll()) {
            new RealIdCardNextServlet(this, new ProgressDialog(this).show(this, "请稍后", "正在上传资料...")).execute();
        }
    }
}
