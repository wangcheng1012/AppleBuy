package com.dw.applebuy.ui.loginreg;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.base.util.CountDownTimerUtils;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.loginreg.p.ChangePhonePresenter;
import com.dw.applebuy.ui.loginreg.v.Views;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.rxmvp.bean.ResultResponse;
import com.wlj.base.util.AppConfig;
import com.wlj.base.util.GoToHelp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 更换手机号码
 */
public class ChangePhoneActivity extends BaseMvpActivity<Views.ChangePhoneView, ChangePhonePresenter> implements Views.ChangePhoneView, Title1Fragment.TitleInterface {

    @BindView(R.id.change_phone)
    TextView changePhone;
    @BindView(R.id.change_newphone)
    AutoCompleteTextView changeNewphone;
    @BindView(R.id.change_verify)
    EditText changeVerify;
    @BindView(R.id.pushverify)
    Button pushverify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chanage_phone);
        ButterKnife.bind(this);

        String phone = AppConfig.getAppConfig().get(AppConfig.CONF_PHONE);
        changePhone.setText("您当前手机号为"+phone);
    }

    @Override
    public ChangePhonePresenter initPresenter() {
        return new ChangePhonePresenter();
    }

    @Override
    public void changePhoneBack(ResultResponse stringHttpStateResult) {
        if(stringHttpStateResult.getStatus() == 1) {
            finish();
        }
    }

    @Override
    public void verifyCodeBack() {
        new CountDownTimerUtils(pushverify,60000,1000).start();
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("更换手机");
    }

    @OnClick({R.id.pushverify, R.id.change_submitbt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pushverify:
                presenter.getVerifyCode(changeNewphone.getText()+"");
                break;
            case R.id.change_submitbt:
                presenter.changePhone(changeNewphone.getText()+"",changeVerify.getText()+"" );
                break;
        }
    }
}
