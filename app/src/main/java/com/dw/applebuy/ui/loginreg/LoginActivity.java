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
import com.dw.applebuy.ui.loginreg.p.LoginPresenter;
import com.dw.applebuy.ui.loginreg.v.Views;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.wlj.base.util.AppManager;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseMvpActivity<Views.LoginView,LoginPresenter> implements Title1Fragment.TitleInterface,Views.LoginView {

    @BindView(R.id.login_phone)
    AutoCompleteTextView loginPhone;
    @BindView(R.id.login_psw)
    EditText loginPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        if(BuildConfig.DEBUG){
            loginPhone.setText("15310315193");
            loginPsw.setText("123456");
        }
    }

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter( );
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
                bundle.putString("title","找回密码");
                GoToHelp.go(this , ForgetPswActivity.class,bundle);
                break;
            case R.id.login_loginbt:
                presenter.Login(loginPhone,loginPsw);
//                LoginBack();
                break;
            case R.id.login_reg:
                GoToHelp.go(this, RegisterActivity.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().AppExit(this);
    }

    @Override
    public void LoginBack() {
        GoToHelp.go(this, MainActivity.class);
        finish();
    }

    @Override
    public void showMessage(String message) {
        UIHelper.toastMessage(getApplication(),message);
    }

}

