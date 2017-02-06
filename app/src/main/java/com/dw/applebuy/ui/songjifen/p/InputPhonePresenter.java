package com.dw.applebuy.ui.songjifen.p;

import android.widget.EditText;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.ui.set.m.AboutUsModel;
import com.dw.applebuy.ui.set.p.AboutUsPresenter;
import com.dw.applebuy.ui.songjifen.m.InputPhoneUser;
import com.dw.applebuy.ui.songjifen.v.Contracts;
import com.rxmvp.basemvp.BasePresenter;
import com.wlj.base.util.UIHelper;

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
        Subscriber<InputPhoneUser> subscriber = new Subscriber<InputPhoneUser>() {

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
            public void onNext(InputPhoneUser inputPhoneUser) {
                if (mView != null) {
                    mView.verifyPhoneBack(inputPhoneUser);
                }
            }
        };
        mView.showLoading();
        AppHttpMethods.getInstance().checkMember(subscriber,phone);
    }
}
