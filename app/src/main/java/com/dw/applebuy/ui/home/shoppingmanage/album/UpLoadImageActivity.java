package com.dw.applebuy.ui.home.shoppingmanage.album;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 上传图片（单图和多图）
 */
public class UpLoadImageActivity extends AppCompatActivity implements Title1Fragment.TitleInterface {

    public final static int album_uploadfirst = 11;
    public final static int album_uploadmore = 12;

    public final static int renheng_BusinessLicense = 21;
    public final static int renheng_card = 22;

    @BindView(R.id.upload_image_image)
    ImageView uploadImageImage;
    @BindView(R.id.upload_image_tip)
    TextView uploadImageTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }

    @Override
    public void setTitle(TextView title, TextView right) {

        title.setText("上传图片");
    }

    @OnClick(R.id.upload_image_upbt)
    public void onClick() {
    }
}
