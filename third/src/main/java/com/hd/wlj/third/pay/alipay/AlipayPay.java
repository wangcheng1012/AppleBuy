package com.hd.wlj.third.pay.alipay;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.wlj.base.util.MathUtil;
import com.wlj.base.util.UIHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;


/**
 *  重要说明:
 *
 *  这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 *  真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 *  防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
 */
public class AlipayPay  {

    // 商户PID
    protected static final String PARTNER = "2088711640682342";
    // 商户收款账号R
    protected static final String SELLER = "pay@pingguo24.com";
    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2088711640682342";
    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = " ";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = " ";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String RSA2_PRIVATE = "";
    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANmpvs732xAmH4Wg61vQ2PuZ98JoPeEElnJfpI4wvmtGzFN0HmzyrfhurHWcB5/oyqqKPD2AJ4I1Sh0Qa7tSN7Zj+S1kpy+/8N/+p6xEPJe7/C7F9/ogAzVtiov7dYsWllESZkyLzuLOQXjiVDwtk/iE9rpd5f1w3psY6WzHTOvxAgMBAAECgYEA11n7n/hYNBUT8Pkyg6gRVnWSTTUnq98HHugD/m65p5fnhtYHWFGO0JJnbQFQBeWjxrF4QnzuGgSxbCoX/MCuvqDEgYzU2pb2hnHYxS+C/wyKVrrd13iLODln+xzg1zG6gUXaU/hRk1NMSoz7T/IqPGbeDd++DrA/vk1X6/V4r5UCQQD9kaOi2W6oZ5useoQ3S8R2OfYXSohhlr2VQ3TfM1oLUPxYCa5gY7AfTkGyxuivoEi+1euGwWYogafWPz45xUcbAkEA27/6iqij6YAsOWKjNgUq8Dic01hrlE1szKpcF6zLYbRI8wbagpZNDq18ByzYovDstXQVLUyPXI1UDFd6ue+N4wJBALMYt9xg+PDHZUP4sQQhZt0eaTvgL93Fy5fuslCoA5R0mhdQlzKLlq01599u8JCDhH4NuDJQzWgU08fWmYvSVDsCQBJbqG+bBQ6FwawNnHrv1ZV59UgGs/4Qtc0nUkg8DEWeTRXYdbrBGbDnN1xWF8cM/4ltKDO/mJ5vxBy/1iXgaGcCQQDRAPF6jnuN3dcBYJHXsxZusoKIlwhZcxmSnIsBRKoids9cE2XEopdc3DoT8ypqitr/tQdpuHVfRgxLLN5E3F9w";

    protected static final int SDK_PAY_FLAG = 1;
    protected static final int SDK_AUTH_FLAG = 2;
    private Activity activity;

    public AlipayPay(Activity mContext){
        this.activity = mContext;
    }

    @SuppressLint("HandlerLeak")
    protected Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {

                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult(msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        UIHelper.toastMessage(activity,  "支付成功");
                        activity.setResult(Activity.RESULT_OK);
                        activity.finish();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            UIHelper.toastMessage(activity,  "支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            UIHelper.toastMessage(activity,  payResult.getMemo());
//						Toast.makeText(PayDemoActivity.this,
//								payResult.getMemo(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(activity,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(activity,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    public void pay(AlipayBean ab) {
        ab.setBody("");
        new PayV1(activity).pay(mHandler,ab);
    }


    /**
     * 支付宝支付业务
     *
     * @param ab
     */
    public void payV2(AlipayBean ab) {

        final String payOrder = getPayOrder(ab);
        if (payOrder == null) return;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(payOrder, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Nullable
    private String getPayOrder(AlipayBean ab) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                           return;
                        }
                    }).show();
            return null;
        }
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);

        ab.setApp_id(APPID);
        ab.setSign_type( rsa2 ? "RSA2" : "RSA");
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(ab);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        return orderInfo;
    }

    /**
     * 支付宝账户授权业务
     */
    public void authV2() {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            dialoginterface.dismiss();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(activity);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     *
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(activity);
        String version = payTask.getVersion();
        Toast.makeText(activity, version, Toast.LENGTH_SHORT).show();
    }

//    /**
//     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
//     *
//     * @param v
//     */
//    public void h5Pay(View v) {
//        Intent intent = new Intent(this, H5PayDemoActivity.class);
//        Bundle extras = new Bundle();
//        /**
//         * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
//         * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
//         * 商户可以根据自己的需求来实现
//         */
//        String url = "http://m.taobao.com";
//        // url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
//        extras.putString("url", url);
//        intent.putExtras(extras);
//        startActivity(intent);
//    }


}
