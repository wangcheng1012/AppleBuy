package com.dw.applebuy.ui.home.shoppingmanage.p;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.ui.home.shoppingmanage.v.Contract;
import com.dw.applebuy.ui.home.shoppingmanage.m.Coupon;
import com.rxmvp.basemvp.BasePresenter;
import com.rxmvp.bean.HttpResult;

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
        Subscriber  sub =  new Subscriber<HttpResult>() {
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
            public void onNext(HttpResult httpStateResult) {
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
        Subscriber  sub =  new Subscriber<HttpResult>() {
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
            public void onNext(HttpResult httpStateResult) {
                if(mView != null){
                    mView.delBack();
                }
            }
        };
        mView.showLoading();
        AppHttpMethods.getInstance().submitCoupon(sub, "deleteCoupon", item.getId());
    }

    /**
     * 获取优惠券详情
     * @param item
     */
    public void getCouponInfo(Coupon item) {
        Subscriber  sub =  new Subscriber<Coupon>() {
            @Override
            public void onCompleted() {
                if (mView != null) {
                    mView.hideLoading();
                }
            }
            @Override
            public void onError(Throwable e) {
                onErrorShow(e,"获取失败");
            }
            @Override
            public void onNext(Coupon coupon) {
                if(mView != null){
                    mView.couponBack(coupon);
                }
            }
        };
        mView.showLoading();
        AppHttpMethods.getInstance().getCouponInfo(sub,item.getId());
    }

    /**
     * 下架优惠券
     * @param item
     */
    public void offShelfCoupon(Coupon item) {
        Subscriber<HttpResult>  sub =  new Subscriber<HttpResult>() {
            @Override
            public void onCompleted() {
                if (mView != null) {
                    mView.hideLoading();
                }
            }
            @Override
            public void onError(Throwable e) {
                onErrorShow(e,"下架失败");
            }
            @Override
            public void onNext(HttpResult hsr) {
                if(mView != null){
                    mView.offShelfCouponBack(hsr);
                }
            }
        };
        mView.showLoading();
        AppHttpMethods.getInstance().offShelfCoupon(sub,item.getId());
    }

    /**
     * 上架优惠券
     * @param item
     */
    public void shelvesCoupon(Coupon item) {
        Subscriber<HttpResult>  sub =  new Subscriber<HttpResult>() {
            @Override
            public void onCompleted() {
                if (mView != null) {
                    mView.hideLoading();
                }
            }
            @Override
            public void onError(Throwable e) {
                onErrorShow(e,"下架失败");
            }
            @Override
            public void onNext(HttpResult hsr) {
                if(mView != null){
                    mView.shelvesCouponBack(hsr);
                }
            }
        };
        mView.showLoading();
        AppHttpMethods.getInstance().shelvesCoupon(sub,item.getId(),item.getShelvesEnd_time());
    }
}
