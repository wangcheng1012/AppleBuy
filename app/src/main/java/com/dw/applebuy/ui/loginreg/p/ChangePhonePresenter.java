package com.dw.applebuy.ui.loginreg.p;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.ui.loginreg.v.Views;
import com.rxmvp.basemvp.BasePresenter;
import com.rxmvp.bean.HttpStateResult;
import com.rxmvp.bean.ResultResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import rx.Subscriber;

/**
 * 更换手机号码
 */
public class ChangePhonePresenter extends BasePresenter<Views.ChangePhoneView> {


    public ChangePhonePresenter( ) {
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
     * 更换手机
     * @param phone
     * @param verifyCode
     */
    public void changePhone(String phone, String verifyCode) {
        //验证
        if(phone.length() != 11){
           toastMessage("手机号错误");
            return;
        }
        if(verifyCode.isEmpty()){
           toastMessage("验证码为空");
            return;
        }

        mView.showLoading();
        changePhoneCall(phone,verifyCode);
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

        AppHttpMethods.getInstance().getChangeMobileVerifyCode(subscriber ,content);
    }

    /**
     *  call
     * @param phone
     * @param verifyCode
     */
    private void changePhoneCall(String phone, String verifyCode) {
        //观察者
        Subscriber<ResultResponse> subscriber = new Subscriber<ResultResponse>() {
            @Override
            public void onCompleted() {
                if(mView != null) {
                    mView.hideLoading();
                }
            }

            @Override
            public void onError(Throwable e) {
                onErrorShow(e,"更换手机失败");
            }

            @Override
            public void onNext(ResultResponse  resultResponse) {
               toastMessage(resultResponse.getMessage());
                if(mView != null) {
                    mView.changePhoneBack(resultResponse);
                }
            }

        };//end
        AppHttpMethods.getInstance().changePhone(subscriber,phone,verifyCode);
    }
}
