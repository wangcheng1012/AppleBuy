package com.hd.wlj.third.quicklogin.wx;

import android.content.Context;

import com.hd.wlj.third.quicklogin.qq.UserInfoBack;
import com.hd.wlj.third.Constants;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.wlj.base.util.StringUtils;
import com.wlj.base.util.UIHelper;
import com.wlj.base.web.HttpPost;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 *
 */
public class WXLogin {

    private final IWXAPI api;
    private Context context;

    public WXLogin(Context context) {
        api = WXAPIFactory.createWXAPI(context, Constants.WX_APP_ID);
        this.context = context;
    }

    public void auth() {
        // send oauth request
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "auth";
        api.sendReq(req);
    }

    public void loginBack(BaseResp resp) {

        int errCode = resp.errCode;

        String result = "登录失败";
        switch (errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "登录成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "登录取消";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "用户拒绝授权";
                break;
        }
        UIHelper.toastMessage(context, result);
    }

    /**
     * 获取微信AccessToken
     *
     * @param resp
     */
    public void getAccessToken(SendAuth.Resp resp, UserInfoBack userInfoBack) {

        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {

            String code = resp.code;

            try {
                HttpPost httpGet = new HttpPost(Constants.getAccess_tokenUrl+code);
                String result = httpGet.getResult();
                JSONObject jsonObject = new JSONObject(result);
                String errcode = jsonObject.optString("errcode");
                String unionid = jsonObject.optString("unionid");
                if (StringUtils.isEmpty(errcode) && !StringUtils.isEmpty(unionid)) {
                    String access_token = jsonObject.optString("access_token");
                    String openid = jsonObject.optString("openid");
                    String refresh_token = jsonObject.optString("refresh_token");
                    String expires_in = jsonObject.optString("expires_in");
                    String scope = jsonObject.optString("scope");

                    getUserInfo(access_token, openid, userInfoBack);

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            userInfoBack.fail("失败");
        }
    }

    /**
     * 获取wx用户信息
     *
     * @param access_token
     * @param openid
     * @throws IOException
     */
    private void getUserInfo(String access_token, String openid, UserInfoBack userInfoBack) throws IOException {
        HttpPost httpGet = new HttpPost(Constants.getuserinfo+"?access_token="+access_token+"&openid="+openid );
//        httpGet.addParemeter("access_token", access_token);
//        httpGet.addParemeter("openid", openid);
        String result = httpGet.getResult();
        userInfoBack.back(result);

    }


}
