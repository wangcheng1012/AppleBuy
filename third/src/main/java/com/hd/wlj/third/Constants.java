package com.hd.wlj.third;

import com.wlj.base.web.BaseURL;

public class Constants {

    public static String Code = "code";
    public static final String QQ_APP_ID = " ";
    public static final String QQ_APP_key = " ";
    public static final String SS_APP_KEY = " ";
    public static final String SS_APP_Secret = " ";
    public static final String SS_SCOPE = "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write";
    public static final String WX_APP_KEY = " ";
    public static final String WX_APP_ID = "wx0041540a81a6cfce";//wxb36fcbec4adbd1ee
    public static final String WX_AppSecret = " ";
    public static String WX_PAYID = " ";

    public static String getuserinfo = "https://api.weixin.qq.com/sns/userinfo" ;
    public static String getAccess_tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
            "appid=" + WX_APP_ID +
            "&secret=" + WX_AppSecret +
            "&grant_type=authorization_code"+
            "&code=";

    public static String shoucang;
    public static String shoucang_remove;

    public static String fenxiangtarget = BaseURL.HOST + "share_product.jsp?id=";
    public static String qq_targetUrl = fenxiangtarget;
    public static String SS_REDIRECT_URL = fenxiangtarget;

}