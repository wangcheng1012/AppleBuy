package com.dw.applebuy.ui.loginreg.v;

import com.rxmvp.basemvp.BaseView;
import com.rxmvp.bean.HttpStateResult;

import java.util.List;


public interface Views {

    /**
     * 注册
     */
    interface RegisterView extends BaseView {

        void registeerBack(HttpStateResult<List> httpStateResult);

        void verifyCodeBack();
    }

    interface LoginView extends BaseView {
        void LoginBack();
    }

    interface ForgetView extends BaseView {
        void submitBack(HttpStateResult<List> stringHttpStateResult);
        void verifyCodeBack();
    }
}
