package com.dw.applebuy.ui.songjifen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;

/**
 * 赠送成功页面
 */
public class GiveSuccessActivity extends AppCompatActivity implements Title1Fragment.TitleInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_success);
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("赠送成功");
    }
}
