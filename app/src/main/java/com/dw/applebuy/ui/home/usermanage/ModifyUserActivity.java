package com.dw.applebuy.ui.home.usermanage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;

public class ModifyUserActivity extends AppCompatActivity implements Title1Fragment.TitleInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user);



    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("编辑会员");
    }
}
