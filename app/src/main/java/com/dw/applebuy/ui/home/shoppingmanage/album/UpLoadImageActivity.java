package com.dw.applebuy.ui.home.shoppingmanage.album;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.renzheng.p.InfoUtil;
import com.dw.applebuy.been.ImageBean;
import com.dw.applebuy.ui.home.shoppingmanage.m.UploadCoverImg;
import com.dw.applebuy.ui.home.shoppingmanage.p.UpLoadImagePresenter;
import com.dw.applebuy.ui.home.shoppingmanage.v.Contract;
import com.jph.takephoto.model.TResult;
import com.lling.photopicker.utils.TakePhotoCrop;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.wlj.base.util.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 上传图片（单图和多图）
 */
public class UpLoadImageActivity extends BaseMvpActivity<Contract.UpLoadImageView, UpLoadImagePresenter> implements Title1Fragment.TitleInterface, TakePhotoCrop.CropBack, Contract.UpLoadImageView {

    public final static int album_uploadfirst = 11;
    public final static int album_uploadmore = 12;

    public final static int renheng_BusinessLicense = 21;
    public final static int renheng_card = 22;

    @BindView(R.id.upload_image_image)
    ImageView uploadImageImage;
    @BindView(R.id.upload_image_tip)
    TextView uploadImageTip;
    @BindView(R.id.upload_image_upbt)
    Button upbt;

    private TakePhotoCrop takePhotoCrop;
    private String path;
    private int mRequestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        takePhotoCrop = new TakePhotoCrop(this, this);
        takePhotoCrop.getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        mRequestCode = intent.getIntExtra("requestCode", album_uploadfirst);
        String tip = intent.getStringExtra("tip");

        switch (mRequestCode) {

            case album_uploadfirst:
                upbt.setText("上传图片");
                break;
            case album_uploadmore:
                upbt.setText("上传图片");
                uploadImageTip.setText(tip);
                break;
            case renheng_BusinessLicense:
                upbt.setText("完成");
                uploadImageTip.setText(tip);
                uploadImageTip.setGravity(Gravity.CENTER);
                uploadImageImage.setImageResource(R.drawable.icon_45_businesscard);
                break;
            case renheng_card:
                upbt.setText("完成");
                uploadImageTip.setText(tip);
                uploadImageTip.setGravity(Gravity.CENTER);
                uploadImageImage.setImageResource(R.drawable.icon_46_card);
                break;
        }

        //初始化图片显示
        String path = intent.getStringExtra("path");
        if (!StringUtils.isEmpty(path)) {

            if (StringUtils.isUrl(path)) {
                Glide.with(this).load(path).placeholder(R.drawable.upload_image).into(uploadImageImage);
            } else {
                uploadImageImage.setImageBitmap(BitmapFactory.decodeFile(path));
            }

        }

    }

    @Override
    public UpLoadImagePresenter initPresenter() {
        return new UpLoadImagePresenter();
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

                if (mRequestCode == album_uploadfirst || album_uploadmore == mRequestCode) {
                    data.putExtra("aspectX", 16);
                    data.putExtra("aspectY", 9);
                }
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

    @OnClick({R.id.upload_image_image, R.id.upload_image_upbt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upload_image_image:
                takePhotoCrop.photoPicker();
                break;
            case R.id.upload_image_upbt:

                if (mRequestCode == album_uploadfirst) {
                    // 上传
                    presenter.uploadCoverImg(path);
                } else if(album_uploadmore == mRequestCode) {

                    presenter.uploadDetailsImgs(path);
                } else {

                    Intent intent = new Intent();
                    intent.putExtra("path", path);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public void cropback(TResult result) {
        path = result.getImage().getPath();
        uploadImageImage.setImageBitmap(BitmapFactory.decodeFile(path));
    }

    @Override
    public void uploadCoverBack(UploadCoverImg uploadCoverImg) {
        InfoUtil.infoUpdate = true;
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void uploadDetailsBack(List<ImageBean> list) {
        InfoUtil.infoUpdate = true;
        setResult(RESULT_OK);
        finish();
    }
}
