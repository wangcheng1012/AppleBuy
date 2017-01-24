package com.dw.applebuy.ui.songjifen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.wlj.base.util.GoToHelp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InputPhoneActivity extends AppCompatActivity implements Title1Fragment.TitleInterface {

    @BindView(R.id.inputphone_phone)
    EditText inputphonePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_phone);
        ButterKnife.bind(this);
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("输入手机号码");
    }

    @OnClick(R.id.inputphone_verify)
    public void onClick() {
        GoToHelp.go(this,PhoneVerifySuccessActivity.class);
//          GoToHelp.go(this,CreartUserActivity.class);
    }
}
