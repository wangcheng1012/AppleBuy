package com.dw.applebuy.ui.loginreg.p;

import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.dw.applebuy.BuildConfig;
import com.dw.applebuy.base.api.AppHttpMethods;
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

    private Context mContext;

    public LoginPresenter(Context context) {
        mContext = context;
    }

    /**
     * 登录
     * @param loginPhone
     * @param loginPsw
     */
    public void Login(AutoCompleteTextView loginPhone, EditText loginPsw) {
        String phone = loginPhone.getText() + "";
        String psw = loginPsw.getText() + "";

        if(phone.length() != 11){
            UIHelper.toastMessage(mContext,"手机号错误");
            return;
        }
        if(psw.length() < 6 || psw.length() > 15){
            UIHelper.toastMessage(mContext,"密码必须为6-15位");
            return;
        }

        mView.showLoading();
        LoginCall(phone,psw);
    }

    private void LoginCall(String phone, String psw) {
        //观察者
        Subscriber<HttpStateResult<Object>> subscriber = new Subscriber<HttpStateResult<Object>>() {
            @Override
            public void onCompleted() {
                if(mView != null) {
                    mView.hideLoading();
                }
            }

            @Override
            public void onError(Throwable e) {
                onErrorShow(e);
            }

            @Override
            public void onNext(HttpStateResult<Object> stringHttpStateResult) {
                UIHelper.toastMessage(mContext,stringHttpStateResult.getMessage());
                // 计时
                if(stringHttpStateResult.getStatus() == 1 && mView != null){

                    LinkedTreeMap data = (LinkedTreeMap)stringHttpStateResult.getData();
                    String sessionid = data.get("sessionid")+"";
                    String mobile = data.get("mobile")+"";
                    AppConfig.getAppConfig().set(AppConfig.CONF_KEY,sessionid);
                    AppConfig.getAppConfig().set(AppConfig.CONF_PHONE,mobile);

                    mView.LoginBack();
                }
            }

        };//end
        AppHttpMethods.getInstance().Login(subscriber,phone,psw);
    }

    private void onErrorShow(Throwable e) {
        if(mView != null) {
            mView.hideLoading();
            if(e instanceof ApiException){
                mView.showMessage(e.getMessage());
            }else {
                mView.showMessage("登录失败");
            }
        }
        if(BuildConfig.DEBUG){
            e.printStackTrace();
        }
    }
}
