package com.dw.applebuy.ui.loginreg.p;

import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.been.LoginBack;
import com.dw.applebuy.ui.loginreg.v.Views;
import com.rxmvp.basemvp.BasePresenter;
import com.rxmvp.bean.HttpResult;
import com.rxmvp.http.ServiceFactory;
import com.rxmvp.http.exception.ApiException;
import com.rxmvp.subscribers.RxSubscriber;
import com.rxmvp.transformer.DefaultTransformer;
import com.wlj.base.util.AppConfig;

import rx.Subscriber;

/**
 *
 */
public class LoginPresenter extends BasePresenter<Views.LoginView> {

    /**
     * 登录
     *
     * @param loginPhone
     * @param loginPsw
     */
    public void Login(AutoCompleteTextView loginPhone, EditText loginPsw) {
        String phone = loginPhone.getText() + "";
        String psw = loginPsw.getText() + "";

        if (phone.length() != 11) {
            toastMessage("手机号错误");
            return;
        }
        if (psw.length() < 6 || psw.length() > 15) {
            toastMessage("密码必须为6-15位");
            return;
        }

        mView.showLoading();
        LoginCall2(phone, psw);
    }

    private void LoginCall2(String phone, final String psw) {

        ServiceFactory
                .createService(FactoryInters.class)
                .login(phone, psw)
                .compose(new DefaultTransformer<LoginBack>())
                .subscribe(new RxSubscriber<LoginBack>(mView) {
                    @Override
                    public void onNext(LoginBack loginBack) {
                        // 计时
                        if (mView != null) {

                            String sessionid = loginBack.getSessionid();
                            String mobile = loginBack.getMobile();
                            //缓存
                            AppConfig.getAppConfig().set(AppConfig.CONF_KEY, sessionid);
                            AppConfig.getAppConfig().set(AppConfig.CONF_PHONE, mobile);
                            AppConfig.getAppConfig().set(AppConfig.CONF_PSW, psw);

                            mView.LoginBack();
                        }
                    }

                });


    }

}
