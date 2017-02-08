package com.dw.applebuy.ui.loginreg.p;

import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.dw.applebuy.BuildConfig;
import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.been.LoginBack;
import com.dw.applebuy.ui.loginreg.v.Views;
import com.google.gson.internal.LinkedTreeMap;
import com.rxmvp.api.ApiException;
import com.rxmvp.basemvp.BasePresenter;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.util.AppConfig;
import com.wlj.base.util.UIHelper;

import java.util.List;

import rx.Subscriber;

/**
 *
 */
public class LoginPresenter extends BasePresenter<Views.LoginView> {

    /**
     * 登录
     * @param loginPhone
     * @param loginPsw
     */
    public void Login(AutoCompleteTextView loginPhone, EditText loginPsw) {
        String phone = loginPhone.getText() + "";
        String psw = loginPsw.getText() + "";

        if(phone.length() != 11){
            toastMessage("手机号错误");
            return;
        }
        if(psw.length() < 6 || psw.length() > 15){
            toastMessage("密码必须为6-15位");
            return;
        }

        mView.showLoading();
        LoginCall(phone,psw);
    }

    private void LoginCall(String phone, final String psw) {
        //观察者
        Subscriber<HttpStateResult<LoginBack>> subscriber = new Subscriber<HttpStateResult<LoginBack>>() {
            @Override
            public void onCompleted() {
                if(mView != null) {
                    mView.hideLoading();
                }
            }

            @Override
            public void onError(Throwable e) {
                onErrorShow(e,"登录失败");
            }

            @Override
            public void onNext(HttpStateResult<LoginBack> stringHttpStateResult) {
                toastMessage(stringHttpStateResult.getMessage());
                // 计时
                if(stringHttpStateResult.getStatus() == 1 && mView != null){

                    LoginBack data =  stringHttpStateResult.getData();
                    String sessionid = data.getSessionid();
                    String mobile = data.getMobile();
                    AppConfig.getAppConfig().set(AppConfig.CONF_KEY,sessionid);
                    AppConfig.getAppConfig().set(AppConfig.CONF_PHONE,mobile);
                    AppConfig.getAppConfig().set(AppConfig.CONF_PSW,psw);

                    mView.LoginBack();
                }
            }

        };//end
        AppHttpMethods.getInstance().Login(subscriber,phone,psw);
    }

}
