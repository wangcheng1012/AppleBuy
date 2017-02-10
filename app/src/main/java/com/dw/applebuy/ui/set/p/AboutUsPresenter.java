package com.dw.applebuy.ui.set.p;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.ui.set.v.Contracts;
import com.rxmvp.basemvp.BasePresenter;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/1/18.
 */

public class AboutUsPresenter extends BasePresenter<Contracts.AboutUsView> {

    public void getAboutUs() {
        //观察者
        Subscriber<String> subscriber = new Subscriber<String>() {

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
            public void onNext(String aboutUsModel) {
                if (mView != null) {
                    mView.aboutUs(aboutUsModel);
                }
            }
        };
        mView.showLoading();
        AppHttpMethods.getInstance().getAboutUs(subscriber);
    }

}
