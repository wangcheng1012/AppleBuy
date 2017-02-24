package com.dw.applebuy.ui.loginreg;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.dw.applebuy.BuildConfig;
import com.dw.applebuy.R;
import com.dw.applebuy.ui.MainActivity;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.renzheng.p.InfoUtil;
import com.dw.applebuy.ui.loginreg.p.LoginPresenter;
import com.dw.applebuy.ui.loginreg.v.Views;
import com.rxmvp.api.interceptor.LoginInterceptor;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.wlj.base.util.AppConfig;
import com.wlj.base.util.AppContext;
import com.wlj.base.util.AppManager;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.StringUtils;
import com.wlj.base.util.UIHelper;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class LoginActivity extends BaseMvpActivity<Views.LoginView, LoginPresenter> implements Title1Fragment.TitleInterface, Views.LoginView {

    @BindView(R.id.login_phone)
    AutoCompleteTextView loginPhone;
    @BindView(R.id.login_psw)
    EditText loginPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        loginPhone.setText(AppConfig.getAppConfig().get(AppConfig.CONF_PHONE));
        loginPsw.setText(AppConfig.getAppConfig().get(AppConfig.CONF_PSW));

        boolean reLogin = getIntent().getBooleanExtra(LoginInterceptor.interceptorLogin, false);
//        String error_code = getIntent().getStringExtra(LoginInterceptor.error_code);

        if (reLogin) {

            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("提示")
                    .setContentText("你的账号在其他地方登录,如不是本人操作请及时修改密码")
                    .setConfirmText("确认")
                    .show();

        }
    }

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("登录");
    }

    @OnClick({R.id.login_forgetpsw, R.id.login_loginbt, R.id.login_reg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_forgetpsw:
                Bundle bundle = new Bundle();
                bundle.putString("title", "找回密码");
                GoToHelp.go(this, ForgetPswActivity.class, bundle);
                break;
            case R.id.login_loginbt:
                presenter.Login(loginPhone, loginPsw);
//                LoginBack();
                break;
            case R.id.login_reg:
                GoToHelp.go(this, RegisterActivity.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AppManager.getAppManager().AppExit(this);
    }

    @Override
    public void LoginBack() {
        InfoUtil.getInstall().clean(this);
        GoToHelp.go(this, MainActivity.class);
//        finish();
    }

    @Override
    public void showMessage(String message) {
        UIHelper.toastMessage(getApplication(), message);
    }

}

