package com.dw.applebuy.ui.home.hexiao;

import android.os.Bundle;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.ui.home.hexiao.m.CouponOrder;
import com.example.qr_codescan.MipcaActivityCapture;
import com.rxmvp.api.HttpResultFunc;
import com.rxmvp.api.RetrofitBase;
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
//        findViewById(R.id.button_back)
//        findViewById(R.id.button_function)
//        findViewById(R.id.relativeLayout_layout)
    }

    /**
     * 子类调用，
     *
     * @param resultString
     * @return true则调用setResult
     */
    @Override
    protected boolean onResultBefore(String resultString) {

        //网络访问
        Subscriber<CouponOrder> sub = new Subscriber<CouponOrder>() {
            @Override
            public void onCompleted() {
                UIHelper.closeProgressbar();
            }

            @Override
            public void onError(Throwable e) {
                UIHelper.closeProgressbar();
                e.printStackTrace();
//                UIHelper.toastMessage(getApplicationContext(), e.getMessage());

                new SweetAlertDialog(CodeScanActivity.this)
                        .setContentText(e.getMessage())
                        .setTitleText("提示")
                        .setConfirmText("确认")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                finish();
                            }
                        })
                        .show();

            }

            @Override
            public void onNext(CouponOrder httpStateResult) {
                //页面跳转
                Bundle bundle = new Bundle();
                bundle.putParcelable("CouponOrder", httpStateResult);
                GoToHelp.go(CodeScanActivity.this, OrderDetail2Activity.class, bundle);
                finish();

            }
        };

        UIHelper.showProgressbar(this, null);

        AppHttpMethods appHttpMethods = AppHttpMethods.getInstance();
        RetrofitBase retrofitBase = appHttpMethods.getRetrofitBase();
        Observable<CouponOrder> couponOrder = appHttpMethods.getApiService().getCouponOrder(resultString)
                .map(new HttpResultFunc<CouponOrder>());
        retrofitBase.toSubscribe(couponOrder, sub);

        return false;
    }

}
