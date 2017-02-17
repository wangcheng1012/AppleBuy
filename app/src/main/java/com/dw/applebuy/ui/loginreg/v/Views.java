package com.dw.applebuy.ui.loginreg.v;

import com.rxmvp.basemvp.BaseView;
import com.rxmvp.bean.HttpResult;

import java.util.List;


public interface Views {


    interface verifyCode extends BaseView {
        void verifyCodeBack();
    }

    /**
     * 注册
     */
    interface RegisterView extends verifyCode {

        void registeerBack();
    }

    interface LoginView extends BaseView {

        void LoginBack();
    }

    interface ForgetView extends verifyCode {

        void submitBack(HttpResult<List> stringHttpResult);
    }

    interface ChangePhoneView extends verifyCode {

        void changePhoneBack(HttpResult stringHttpStateResult);
    }
}
