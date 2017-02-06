package com.dw.applebuy.ui.songjifen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.wlj.base.util.GoToHelp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建用户
 */
public class CreartUserActivity extends AppCompatActivity implements Title1Fragment.TitleInterface {

    @BindView(R.id.creart_user_phone)
    EditText phone;
    @BindView(R.id.creart_user_name)
    EditText name;
    @BindView(R.id.creart_user_verify)
    EditText verify;
    @BindView(R.id.pushverify)
    Button pushverify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creart_user);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        String phoneStr = intent.getStringExtra("phone");
        phone.setText(phoneStr);
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("创建用户");
    }

    @OnClick({R.id.pushverify, R.id.creart_user_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pushverify:

                break;
            case R.id.creart_user_sure:

                GoToHelp.go(this,PhoneVerifySuccessActivity.class);
                break;
        }
    }
}
