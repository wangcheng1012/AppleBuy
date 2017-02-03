package com.dw.applebuy.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.hd.wlj.third.Constants;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.wlj.base.util.UIHelper;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler , Title1Fragment.TitleInterface {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(final BaseResp resp) {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode + resp.errStr);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            UIHelper.loadingClose();

            //code
            String result;
            if (resp.errCode == 0) {
                setContentView(R.layout.activity_rechange_back);

//                result = "微信支付成功";
            } else if (resp.errCode == -2) {
                result = "用户取消";
                UIHelper.toastMessage(this,result);
                finish();
            } else {
                //可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
                result = "微信支付失败";
                UIHelper.toastMessage(this,result);
                finish();
            }//end



//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            AlertDialog alertDialog = builder.create();
//            alertDialog.setCanceledOnTouchOutside(false);
//
////            builder.setIcon(android.R.drawable.ic_dialog_info);
//            alertDialog.setTitle(com.wlj.base.R.string.app_tipr);
//            alertDialog.setMessage("支付是否成功？");
//            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"支付完成",new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    send(resp.errCode);
//                    finish();
//                }
//            });
//             alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"遇到问题",new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    send(resp.errCode);
//                    finish();
//                }
//            });
//            alertDialog.show();

        }
    }

    private void send(int code) {
        Intent intent = new Intent();
//        intent.setAction(PayActivity.WX_PAYRESULT_ACTION);
        intent.putExtra("errCode", code);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("充值成功");
    }
}