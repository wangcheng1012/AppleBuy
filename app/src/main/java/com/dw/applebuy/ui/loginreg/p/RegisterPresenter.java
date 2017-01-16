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
 *
 */
public class RegisterPresenter extends BasePresenter<Views.RegisterView> {

    private Context mContext;

    public RegisterPresenter(Context mContext) {

        this.mContext = mContext;
    }

    private void onErrorShow(Throwable e,String defMessage) {
        if(mView != null) {
            mView.hideLoading();
            if(e instanceof ApiException){
                mView.showMessage(e.getMessage());
            }else {
                mView.showMessage(defMessage);
            }
        }
        if(BuildConfig.DEBUG){
            e.printStackTrace();
        }
    }
    /**
     * 获取验证码
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
     * @param arrayMap
     */
    public void register(ArrayMap<String, String> arrayMap) {
        //验证
        if(arrayMap.get("mobile").length() != 11){
            UIHelper.toastMessage(mContext,"手机号错误");
            return;
        }
        if(arrayMap.get("code").isEmpty()){
            UIHelper.toastMessage(mContext,"验证码为空");
            return;
        }
        if(arrayMap.get("password").length() < 6 || arrayMap.get("password").length() > 15){
            UIHelper.toastMessage(mContext,"密码必须为6-15位");
            return;
        }
        if(!arrayMap.get("password") .equals(arrayMap.get("re_password"))){
            UIHelper.toastMessage(mContext,"密码与确认密码不相同");
            return;
        }
        mView.showLoading();
        registerCall(arrayMap);
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
                onErrorShow(e,"验证码获取失败");
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
                UIHelper.toastMessage(mContext,stringHttpStateResult.getMessage());
            }

        };//end

        AppHttpMethods.getInstance().getCode(subscriber ,content);
    }

    /**
     * 注册call
     * @param arrayMap
     */
    private void registerCall(ArrayMap<String, String> arrayMap) {
        //观察者
        Subscriber<HttpStateResult<List>> subscriber = new Subscriber<HttpStateResult<List>>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                onErrorShow(e,"注册失败");
            }

            @Override
            public void onNext(HttpStateResult<List> stringHttpStateResult) {
                UIHelper.toastMessage(mContext,stringHttpStateResult.getMessage());
                if(mView != null) {
                    mView.registeerBack(stringHttpStateResult);
                }
            }

        };//end
        AppHttpMethods.getInstance().register(subscriber,arrayMap);
    }
}
