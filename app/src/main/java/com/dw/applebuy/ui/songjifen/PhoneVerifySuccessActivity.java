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

/**
 *
 * 手机验证成功
 */
public class PhoneVerifySuccessActivity extends AppCompatActivity implements Title1Fragment.TitleInterface {

    @BindView(R.id.verifysuccess_phone)
    EditText verifysuccessPhone;
    @BindView(R.id.verifysuccess_name)
    EditText verifysuccessName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneverify_success);
        ButterKnife.bind(this);
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("验证成功");
    }

    @OnClick(R.id.verifysuccess_sure)
    public void onClick() {
        GoToHelp.go(this,GiveSuccessActivity.class);
    }
}
