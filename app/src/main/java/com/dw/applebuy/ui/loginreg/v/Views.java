package com.dw.applebuy.ui.loginreg.v;

import com.rxmvp.basemvp.BaseView;
import com.rxmvp.bean.HttpStateResult;
import com.rxmvp.bean.ResultResponse;

import java.util.List;


public interface Views {


    interface verifyCode extends BaseView {
        void verifyCodeBack();
    }

    /**
     * 注册
     */
    interface RegisterView extends verifyCode {

        void registeerBack(HttpStateResult<List> httpStateResult);
    }

    interface LoginView extends BaseView {

        void LoginBack();
    }

    interface ForgetView extends verifyCode {

        void submitBack(HttpStateResult<List> stringHttpStateResult);
    }

    interface ChangePhoneView extends verifyCode {

        void changePhoneBack(ResultResponse stringHttpStateResult);
    }
}
