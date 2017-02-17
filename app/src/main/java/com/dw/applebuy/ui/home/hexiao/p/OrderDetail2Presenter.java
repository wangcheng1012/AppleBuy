package com.dw.applebuy.ui.home.hexiao.p;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.ui.home.hexiao.v.Contract;
import com.rxmvp.basemvp.BasePresenter;
import com.rxmvp.bean.HttpResult;

import rx.Subscriber;

/**
 *  订单
 */
public class OrderDetail2Presenter extends BasePresenter<Contract.OrderDetail2View> {

    public void hexiao(String code) {

        Subscriber<HttpResult> sub = new Subscriber<HttpResult>() {
            @Override
            public void onCompleted() {
                if(mView != null){
                    mView.hideLoading();
                }
            }

            @Override
            public void onError(Throwable e) {
                onErrorShow(e,"核销失败");
//                if(BuildConfig.DEBUG){
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onNext(HttpResult httpStateResult) {
                mView.useCouponBack(httpStateResult);
            }
        };
        mView.showLoading();
        AppHttpMethods.getInstance().useCoupon(sub,code);
    }
}
