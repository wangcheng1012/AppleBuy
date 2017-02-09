package com.dw.applebuy.ui.home.shoppingmanage.p;

import android.support.annotation.NonNull;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.ui.home.shoppingmanage.v.Contract;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.showing.m.Coupon;
import com.rxmvp.basemvp.BasePresenter;
import com.rxmvp.bean.HttpStateResult;

import rx.Subscriber;

/**
 *
 */
public class TabLayoutPresenter extends BasePresenter<Contract.TabLayoutView> {

    /**
     * 提交草稿
     *
     * @param item
     */
    public void submitShenhe(Coupon item) {
        Subscriber  sub =  new Subscriber<HttpStateResult>() {
            @Override
            public void onCompleted() {

                if (mView != null) {
                    mView.hideLoading();
                }
            }
            @Override
            public void onError(Throwable e) {
                onErrorShow(e,"提交失败");
            }

            @Override
            public void onNext(HttpStateResult httpStateResult) {
                if(mView != null){
                    mView.submitBack();
                }
            }
        };
        mView.showLoading();
        AppHttpMethods.getInstance().submitCoupon(sub, "submitCoupon", item.getId());
    }

    /**
     * 删除
     * @param item
     */
    public void delShenHe(Coupon item) {
        Subscriber  sub =  new Subscriber<HttpStateResult>() {
            @Override
            public void onCompleted() {
                if (mView != null) {
                    mView.hideLoading();
                }
            }
            @Override
            public void onError(Throwable e) {
                onErrorShow(e,"删除失败");
            }
            @Override
            public void onNext(HttpStateResult httpStateResult) {
                if(mView != null){
                    mView.delBack();
                }
            }
        };
        mView.showLoading();
        AppHttpMethods.getInstance().submitCoupon(sub, "deleteCoupon", item.getId());
    }

}
