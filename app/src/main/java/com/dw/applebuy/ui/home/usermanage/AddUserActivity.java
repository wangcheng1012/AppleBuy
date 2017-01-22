package com.dw.applebuy.ui.home.usermanage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;

/**
 * 商家添加会员
 */
public class AddUserActivity extends AppCompatActivity implements Title1Fragment.TitleInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("添加会员");
    }
}
