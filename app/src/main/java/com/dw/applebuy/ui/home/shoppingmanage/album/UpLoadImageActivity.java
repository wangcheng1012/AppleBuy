package com.dw.applebuy.ui.home.shoppingmanage.album;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;

/**
 * 上传图片（单图和多图）
 */
public class UpLoadImageActivity extends AppCompatActivity implements Title1Fragment.TitleInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
    }

    @Override
    public void setTitle(TextView title, TextView right) {

        title.setText("上传图片");
    }
}
