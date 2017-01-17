package com.dw.applebuy.ui.loginreg.p;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.dw.applebuy.BuildConfig;
import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.ui.loginreg.v.Views;
import com.rxmvp.api.ApiException;
import com.rxmvp.basemvp.BasePresenter;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.util.UIHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import rx.Subscriber;

/**
 * 更换手机号码
 */
public class ChanagePhonePresenter extends BasePresenter<Views.ChanagePhoneView> {


    public ChanagePhonePresenter( ) {
    }

    /**
     * 获取验证码
     * @param phone
     */
    public void getVerifyCode(String phone) {
        if (phone.isEmpty()) {
           toastMessage( "请输入手机号码");
            return;
        }
        mView.showLoading();

        getCodeCall(phone);
    }

    /**
     * chanagephone
     * @param arrayMap
     */
    public void submit(ArrayMap<String, String> arrayMap) {
        //验证
        if(arrayMap.get("mobile").length() != 11){
           toastMessage("手机号错误");
            return;
        }
        if(arrayMap.get("code").isEmpty()){
           toastMessage("验证码为空");
            return;
        }
        if(arrayMap.get("password").length() < 6 || arrayMap.get("password").length() > 15){
           toastMessage("密码必须为6-15位");
            return;
        }
        if(!arrayMap.get("password") .equals(arrayMap.get("re_password"))){
           toastMessage("密码与确认密码不相同");
            return;
        }
        mView.showLoading();
        submitCall(arrayMap);
    }

    /**
     * 验证码
     * @param phone
     */
    private void getCodeCall(String phone)  {

        String content = null;
        try {
            content = URLDecoder.decode(phone, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //观察者
        Subscriber<HttpStateResult<List>> subscriber = new Subscriber<HttpStateResult<List>>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                onErrorShow(e,"验证码发送失败");
            }

            @Override
            public void onNext(HttpStateResult<List> stringHttpStateResult) {
                // 计时 ，
                if(stringHttpStateResult.getStatus() == 1){
                    //计时
                    if(mView != null) {
                        mView.verifyCodeBack();
                    }
                }
               toastMessage(stringHttpStateResult.getMessage());
            }

        };//end

        AppHttpMethods.getInstance().getForgetPasswordVerifyCode(subscriber ,content);
    }

    /**
     * 注册call
     * @param arrayMap
     */
    private void submitCall(ArrayMap<String, String> arrayMap) {
        //观察者
        Subscriber<HttpStateResult<List>> subscriber = new Subscriber<HttpStateResult<List>>() {
            @Override
            public void onCompleted() {
                if(mView != null) {
                    mView.hideLoading();
                }
            }

            @Override
            public void onError(Throwable e) {
                onErrorShow(e,"注册失败");
            }

            @Override
            public void onNext(HttpStateResult<List> stringHttpStateResult) {
               toastMessage(stringHttpStateResult.getMessage());
                if(mView != null) {
                    mView.submitBack(stringHttpStateResult);
                }
            }

        };//end
        AppHttpMethods.getInstance().forgetPassword(subscriber,arrayMap);
    }
}
