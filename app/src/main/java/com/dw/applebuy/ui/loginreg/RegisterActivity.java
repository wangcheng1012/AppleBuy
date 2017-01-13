package com.dw.applebuy.ui.loginreg;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.base.util.CountDownTimerUtils;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.loginreg.p.RegisterPresenter;
import com.dw.applebuy.ui.loginreg.v.Views;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.UIHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseMvpActivity<Views.RegisterView,RegisterPresenter> implements Title1Fragment.TitleInterface,Views.RegisterView {

    @BindView(R.id.register_phone)
    AutoCompleteTextView registerPhone;
    @BindView(R.id.pushverify)
    Button pushverify;
    @BindView(R.id.register_verify)
    EditText registerVerify;
    @BindView(R.id.register_psw)
    EditText registerPsw;
    @BindView(R.id.register_pswsure)
    EditText registerPswsure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @Override
    public RegisterPresenter initPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public void setTitle(TextView title) {
        title.setText("快速注册");
    }

    @OnClick({R.id.register_xieyi, R.id.register_submit, R.id.register_login,R.id.pushverify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_xieyi:

                break;
            case R.id.register_submit:
                ArrayMap<String, String> arrayMap = new ArrayMap<>();
                arrayMap.put("mobile",registerPhone.getText()+"");
                arrayMap.put("code",registerVerify.getText()+"");
                arrayMap.put("password",registerPsw.getText()+"");
                arrayMap.put("re_password",registerPswsure.getText()+"");
                presenter.register(arrayMap);
                break;
            case R.id.register_login:
                GoToHelp.go(this,LoginActivity.class);
                finish();
                break;
            case R.id.pushverify:
                presenter.getVerifyCode(registerPhone.getText()+"");
                break;
        }
    }

    @Override
    public void showMessage(String message) {
        UIHelper.toastMessage(getApplication(),message);
    }

    @Override
    public void registeerBack(HttpStateResult<List> httpStateResult) {
        if(httpStateResult.getStatus() == 1) {
            finish();
        }
    }

    @Override
    public void verifyCodeBack() {
        //倒计时
        CountDownTimerUtils countDownTimerUtils = new CountDownTimerUtils(pushverify, 60000, 1000);
        countDownTimerUtils.start();
    }

    @Override
    public void showLoading() {
        UIHelper.showProgressbar(this, null);
    }

    @Override
    public void hideLoading() {
        UIHelper.closeProgressbar();
    }
}
