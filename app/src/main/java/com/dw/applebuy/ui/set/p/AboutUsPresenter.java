package com.dw.applebuy.ui.set.p;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.ui.set.m.AboutUsModel;
import com.dw.applebuy.ui.set.v.Contracts;
import com.rxmvp.basemvp.BasePresenter;
import com.wlj.base.util.GoToHelp;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/1/18.
 */

public class AboutUsPresenter extends BasePresenter<Contracts.AboutUsView> {
    private AboutUsModel aboutUsModel;
    private boolean aboutUsClick;

    public void getAboutUs() {
        //观察者
        Subscriber<AboutUsModel> subscriber = new Subscriber<AboutUsModel>() {

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
            public void onNext(AboutUsModel aboutUsModel) {
                if (mView != null) {
                    AboutUsPresenter.this.aboutUsModel = aboutUsModel;
                    mView.aboutUs(aboutUsModel, aboutUsClick);
                    aboutUsClick = false;
                }
            }
        };
        AppHttpMethods.getInstance().getAboutUs(subscriber);
    }

    public void aboutUsClick() {

        if (aboutUsModel == null) {
            mView.showLoading();
            aboutUsClick = true;
            getAboutUs();
        } else {
            mView.aboutUs(aboutUsModel, true);
        }

    }
}
