package com.dw.applebuy.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.loginreg.LoginActivity;
import com.hd.wlj.third.quicklogin.qq.UserInfoBack;
import com.hd.wlj.third.quicklogin.wx.WXLogin;
import com.hd.wlj.third.share.Constants;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.wlj.base.util.ExecutorServices;
import com.wlj.base.util.GoToHelp;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.entry);

        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID, false);
        api.registerApp(Constants.WX_APP_ID);

        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {

    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(final BaseResp resp) {

        if (resp instanceof SendAuth.Resp) {
            Login(resp);
        } else {
            share(resp);
        }
        finish();

    }

    /**
     * 分享回掉
     *
     * @param resp
     */
    private void share(BaseResp resp) {
        int result = 0;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.weibosdk_demo_toast_share_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.weibosdk_demo_toast_share_canceled;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                result = "errcode_deny";
                break;
            default:
                result = R.string.weibosdk_demo_toast_share_failed;
                break;
        }
        Toast.makeText(this, getString(result), Toast.LENGTH_LONG).show();
    }

    /**
     * 登录回掉处理
     *
     * @param resp
     */
    private void Login(final BaseResp resp) {

        final WXLogin wxLogin = new WXLogin(getApplicationContext());
        wxLogin.loginBack(resp);
        //
        ExecutorServices.getExecutorService().execute(new Runnable() {
            @Override
            public void run() {

                wxLogin.getAccessToken((SendAuth.Resp) resp, new UserInfoBack() {
                    @Override
                    public void back(Object object) {

                        Bundle bundle = new Bundle();
                        bundle.putString("userInfoJsonStr", object + "");

                        GoToHelp.go(WXEntryActivity.this, LoginActivity.class, bundle);
                    }

                    @Override
                    public void fail(Object object) {

                    }
                });

            }
        });


    }

}