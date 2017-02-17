package com.dw.applebuy.ui.loginreg.p;

import android.support.v4.util.ArrayMap;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.ui.loginreg.v.Views;
import com.rxmvp.basemvp.BasePresenter;
import com.rxmvp.bean.HttpResult;
import com.rxmvp.http.ServiceFactory;
import com.rxmvp.subscribers.RxSubscriber;
import com.rxmvp.transformer.DefaultTransformer;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import rx.Subscriber;

/**
 *
 */
public class RegisterPresenter extends BasePresenter<Views.RegisterView> {


    public RegisterPresenter() {

    }

    /**
     * 获取验证码
     *
     * @param phone
     */
    public void getVerifyCode(String phone) {
        if (phone.isEmpty()) {
            mView.showMessage("请输入手机号码");
            return;
        }
        mView.showLoading();
        getCodeCall(phone);
    }

    /**
     * 注册
     *
     * @param arrayMap
     */
    public void register(ArrayMap<String, String> arrayMap) {
        //验证
        if (arrayMap.get("mobile").length() != 11) {
            toastMessage("手机号错误");
            return;
        }
        if (arrayMap.get("code").isEmpty()) {
            toastMessage("验证码为空");
            return;
        }
        if (arrayMap.get("password").length() < 6 || arrayMap.get("password").length() > 15) {
            toastMessage("密码必须为6-15位");
            return;
        }
        if (!arrayMap.get("password").equals(arrayMap.get("re_password"))) {
            toastMessage("密码与确认密码不相同");
            return;
        }
        mView.showLoading();
        registerCall(arrayMap);
    }

    /**
     * 验证码
     *
     * @param phone
     */
    private void getCodeCall(String phone) {

//        String content = null;
//        try {
//            content = URLDecoder.decode(phone, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        //观察者
//        Subscriber<HttpResult<List>> subscriber = new Subscriber<HttpResult<List>>() {
//            @Override
//            public void onCompleted() {
//                mView.hideLoading();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                onErrorShow(e, "验证码获取失败");
//            }
//
//            @Override
//            public void onNext(HttpResult<List> stringHttpStateResult) {
//                // 计时 ，
//                if (stringHttpStateResult.getStatus() == 1) {
//                    //计时
//                    if (mView != null) {
//                        mView.verifyCodeBack();
//                    }
//                }
//                toastMessage(stringHttpStateResult.getMessage());
//            }
//
//        };//end
//
//        AppHttpMethods.getInstance().getCode(subscriber, content);

        ServiceFactory
                .createService(FactoryInters.class)
                .getRegisterVerifyCode(phone)
                .compose(new DefaultTransformer<String>())
                .subscribe(new RxSubscriber<String>(mView) {
                    @Override
                    public void onNext(String s) {

                        if (mView != null) {
                            mView.registeerBack();
                        }
                    }

                });
    }

    /**
     * 注册call
     *
     * @param arrayMap
     */
    private void registerCall(ArrayMap<String, String> arrayMap) {

        ServiceFactory
                .createService(FactoryInters.class)
                .register(arrayMap)
                .compose(new DefaultTransformer<List>())
                .subscribe(new RxSubscriber<List>(mView) {
                    @Override
                    public void onNext(List list) {
                        if (mView != null) {
                            mView.registeerBack();
                        }
                    }

                });
    }
}
