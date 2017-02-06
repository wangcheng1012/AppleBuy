package com.dw.applebuy.ui.songjifen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.songjifen.m.InputPhoneUser;
import com.dw.applebuy.ui.songjifen.p.InputPhonePresenter;
import com.dw.applebuy.ui.songjifen.v.Contracts;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 输入手机号码
 */
public class InputPhoneActivity extends BaseMvpActivity<Contracts.InputPhoneView, InputPhonePresenter> implements Title1Fragment.TitleInterface, Contracts.InputPhoneView {

    @BindView(R.id.inputphone_phone)
    EditText inputphonePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_phone);
        ButterKnife.bind(this);
    }

    @Override
    public InputPhonePresenter initPresenter() {
        return new InputPhonePresenter();
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("输入手机号码");
    }

    @OnClick(R.id.inputphone_verify)
    public void onClick() {

        presenter.verifyPhone(inputphonePhone);
    }

    @Override
    public void verifyPhoneBack(InputPhoneUser inputPhoneUser) {

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (inputPhoneUser.getStatus() == InputPhoneUser.status_have) {

            extras.putParcelable("InputPhoneUser", inputPhoneUser);
            GoToHelp.go(this, PhoneVerifySuccessActivity.class, extras);
        } else {
            extras.putString("phone",inputphonePhone.getText()+"");
            GoToHelp.go(this, CreartUserActivity.class,extras);
        }
    }
}
