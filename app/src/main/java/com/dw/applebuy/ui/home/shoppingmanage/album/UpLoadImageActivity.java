package com.dw.applebuy.ui.home.shoppingmanage.album;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.jph.takephoto.model.TResult;
import com.lling.photopicker.utils.TakePhotoCrop;
import com.wlj.base.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 上传图片（单图和多图）
 */
public class UpLoadImageActivity extends AppCompatActivity implements Title1Fragment.TitleInterface, TakePhotoCrop.CropBack {

    public final static int album_uploadfirst = 11;
    public final static int album_uploadmore = 12;

    public final static int renheng_BusinessLicense = 21;
    public final static int renheng_card = 22;

    @BindView(R.id.upload_image_image)
    ImageView uploadImageImage;
    @BindView(R.id.upload_image_tip)
    TextView uploadImageTip;

    private TakePhotoCrop takePhotoCrop;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        takePhotoCrop = new TakePhotoCrop(this, this);
        takePhotoCrop.getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        int requestCode = intent.getIntExtra("requestCode", album_uploadfirst);
        String tip = intent.getStringExtra("tip");

        switch (requestCode) {

            case album_uploadfirst:
                break;
            case album_uploadmore:
                uploadImageTip.setText(tip);
                break;
            case renheng_BusinessLicense:
                uploadImageTip.setText(tip);
                uploadImageTip.setGravity(Gravity.CENTER);
                uploadImageImage.setImageResource(R.drawable.icon_45_businesscard);
                break;
            case renheng_card:
                uploadImageTip.setText(tip);
                uploadImageTip.setGravity(Gravity.CENTER);
                uploadImageImage.setImageResource(R.drawable.icon_46_card);
                break;
        }

        //初始化图片显示
        String path = intent.getStringExtra("path");
        if(!StringUtils.isEmpty(path)){
            uploadImageImage.setImageBitmap(BitmapFactory.decodeFile(path));
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        takePhotoCrop.getTakePhoto().onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        takePhotoCrop.getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TakePhotoCrop.IMAGE) {
                takePhotoCrop.onCrop(data);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        takePhotoCrop.onRequestPermissionsResult_(requestCode, permissions, grantResults);
    }

    @Override
    public void setTitle(TextView title, TextView right) {

        title.setText("上传图片");
    }

    @OnClick(R.id.upload_image_upbt)
    public void onClick() {
        takePhotoCrop.photoPicker();
    }

    @Override
    public void cropback(TResult result) {
        path = result.getImage().getPath();
        uploadImageImage.setImageBitmap(BitmapFactory.decodeFile(path));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("path",path);
        setResult(RESULT_OK,intent);
        super.onBackPressed();

    }
}
