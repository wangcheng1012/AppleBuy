package com.dw.applebuy.ui.songjifen.p;

import android.support.v4.util.ArrayMap;
import android.widget.EditText;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.ui.songjifen.v.Contracts;
import com.rxmvp.basemvp.BasePresenter;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.util.MathUtil;
import com.wlj.base.util.StringUtils;

import rx.Subscriber;

/**
 *
 */
public class CreatUserPresenter extends BasePresenter<Contracts.CreatUserView> {

    public void pushverify(EditText phoneView) {
        String phone = phoneView.getText() + "";
        if (phone.length() != 11) {
            toastMessage("手机号码格式错误");
            return;
        }
        mView.showLoading();
        //观察者
        Subscriber<HttpStateResult<String>> subscriber = new Subscriber<HttpStateResult<String>>() {
            @Override
            public void onCompleted() {
                if (mView != null) {
                    mView.hideLoading();
                }
            }

            @Override
            public void onError(Throwable e) {
                onErrorShow(e, "验证码发送失败");
            }

            @Override
            public void onNext(HttpStateResult<String> stringHttpStateResult) {
                // 计时 ，
                if (stringHttpStateResult.getStatus() == 0) {
                    //计时
                    if (mView != null) {
                        mView.verifyCodeBack();
                    }
                }
                toastMessage(stringHttpStateResult.getMessage());
            }

        };//end

        AppHttpMethods.getInstance().giveIntegralVerifyCode(subscriber, phone);
    }

    public void giveIntegral(ArrayMap<String, Object> arrayMap) {
//        arrayMap.put("integral", intent.getIntExtra("jifen",0));
//        arrayMap.put("mobile", phone.getText()+"");
//        arrayMap.put("name", name.getText()+"");
//        arrayMap.put("code", verify.getText()+"");

        if (MathUtil.parseInteger(arrayMap.get("integral") + "") == 0) {
            mView.showMessage("积分不能为0");
            return;
        }
        if ((arrayMap.get("mobile") + "").length() != 11) {
            mView.showMessage("手机号错误");
            return;
        }
        if (StringUtils.isEmpty(arrayMap.get("name") + "")) {
            mView.showMessage("用户姓名不能为空");
            return;
        }

//        if (StringUtils.isEmpty(arrayMap.get("code") + "")) {
//            mView.showMessage("验证码不能为空");
//            return;
//        }

        mView.showLoading();
        //观察者
        Subscriber<HttpStateResult> subscriber = new Subscriber<HttpStateResult>() {
            @Override
            public void onCompleted() {
                if (mView != null) {
                    mView.hideLoading();
                }
            }

            @Override
            public void onError(Throwable e) {
                onErrorShow(e, "赠送积分失败");
            }

            @Override
            public void onNext(HttpStateResult stringHttpStateResult) {

                if (mView != null) {
                    mView.giveIntegralBack();
                }

            }

        };//end

        AppHttpMethods.getInstance().giveIntegral(subscriber, arrayMap);
    }
}
