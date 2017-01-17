package com.dw.applebuy.ui.loginreg;

import android.content.Intent;
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
import com.dw.applebuy.ui.loginreg.p.ForgetPresenter;
import com.dw.applebuy.ui.loginreg.v.Views;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.util.StringUtils;
import com.wlj.base.util.UIHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ForgetPswActivity extends BaseMvpActivity<Views.ForgetView, ForgetPresenter> implements Title1Fragment.TitleInterface, Views.ForgetView {

    @BindView(R.id.forget_phone)
    AutoCompleteTextView forgetPhone;
    @BindView(R.id.forget_verify)
    EditText forgetVerify;
    @BindView(R.id.pushverify)
    Button pushverify;
    @BindView(R.id.forget_psw)
    EditText forgetPsw;
    @BindView(R.id.forget_pswsure)
    EditText forgetPswsure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psw);
        ButterKnife.bind(this);
    }

    @Override
    public ForgetPresenter initPresenter() {
        return new ForgetPresenter( );
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        Intent intent = getIntent();
        String title1 = intent.getStringExtra("title");
        if(!StringUtils.isEmpty(title1)){
            //
            title.setText(title1);
        }else{
            title.setText("找回密码");
        }

    }


    @Override
    public void submitBack(HttpStateResult<List> httpStateResult) {
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

    @OnClick({R.id.pushverify, R.id.forget_submitbt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pushverify:
                presenter.getVerifyCode(forgetPhone.getText()+"");
                break;
            case R.id.forget_submitbt:
                ArrayMap<String, String> arrayMap = new ArrayMap<>();
                arrayMap.put("mobile",forgetPhone.getText()+"");
                arrayMap.put("code",forgetVerify.getText()+"");
                arrayMap.put("password",forgetPsw.getText()+"");
                arrayMap.put("re_password",forgetPswsure.getText()+"");
                presenter.submit(arrayMap);
                break;
        }
    }

    @Override
    public void showMessage(String message) {
        UIHelper.toastMessage(getApplication(),message);
    }
}
