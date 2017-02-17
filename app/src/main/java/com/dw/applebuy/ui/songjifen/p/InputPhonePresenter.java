package com.dw.applebuy.ui.songjifen.p;

import android.widget.EditText;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.ui.songjifen.m.VerifyUser;
import com.dw.applebuy.ui.songjifen.v.Contracts;
import com.rxmvp.basemvp.BasePresenter;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/1/18.
 */

public class InputPhonePresenter extends BasePresenter<Contracts.InputPhoneView> {


    public void verifyPhone(EditText inputphonePhone) {

        String phone = inputphonePhone.getText() + "";
        if(phone.length() != 11){
            mView.showMessage("手机号码格式错误");
            return;
        }

        //观察者
        Subscriber<VerifyUser> subscriber = new Subscriber<VerifyUser>() {

            @Override
            public void onCompleted() {
                if (mView != null) {
                    mView.hideLoading();
                }
            }
            @Override
            public void onError(Throwable e) {
                onErrorShow(e, "获取失败");
            }

            @Override
            public void onNext(VerifyUser inputPhoneUser) {
                if (mView != null) {
                    mView.verifyPhoneBack(inputPhoneUser);
                }
            }
        };
        mView.showLoading();
        AppHttpMethods.getInstance().checkMember(subscriber,phone);
    }
}
