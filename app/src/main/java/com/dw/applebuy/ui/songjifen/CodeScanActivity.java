package com.dw.applebuy.ui.songjifen;

import android.content.Intent;
import android.os.Bundle;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.ui.home.hexiao.OrderDetail2Activity;
import com.dw.applebuy.ui.home.hexiao.m.CouponOrder;
import com.dw.applebuy.ui.songjifen.m.VerifyUser;
import com.example.qr_codescan.MipcaActivityCapture;
import com.rxmvp.api.HttpResultFunc;
import com.rxmvp.api.RetrofitBase;
import com.rxmvp.http.ServiceFactory;
import com.rxmvp.http.exception.ApiException;
import com.rxmvp.subscribers.CommonSubscriber;
import com.rxmvp.subscribers.RxSubscriber;
import com.rxmvp.transformer.DefaultTransformer;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.UIHelper;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observable;
import rx.Subscriber;

/**
 * 二维码 扫描
 */
public class CodeScanActivity extends MipcaActivityCapture {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        super.initTitle();
    }

    /**
     * 子类调用，
     *
     * @param resultString
     * @return true则调用setResult
     */
    @Override
    protected boolean onResultBefore(final String resultString) {

        UIHelper.showProgressbar(this,null);
        ServiceFactory
                .createService(FactoryInters.class)
                .checkMemberSao(resultString)
                .compose(new DefaultTransformer<VerifyUser>())
                .subscribe(new CommonSubscriber<VerifyUser>(this) {
                    @Override
                    public void onNext(VerifyUser verifyUser) {

                        UIHelper.closeProgressbar();

                        Intent intent = getIntent();
                        Bundle extras = intent.getExtras();
                        if (verifyUser.getStatus() == VerifyUser.status_have) {

                            extras.putParcelable("VerifyUser", verifyUser);
                            GoToHelp.go(CodeScanActivity.this, PhoneVerifySuccessActivity.class, extras);
                        } else {
                            extras.putString("phone",resultString);
                            GoToHelp.go(CodeScanActivity.this, CreatUserActivity.class,extras);
                        }
                        finish();
                    }

                    @Override
                    protected void onError(ApiException ex) {
//                        super.onError(ex);
                        UIHelper.closeProgressbar();

                        UIHelper.tip(CodeScanActivity.this, ex.getMessage(), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                finish();
                            }
                        },null);

                    }
                });

        return false;
    }

}
