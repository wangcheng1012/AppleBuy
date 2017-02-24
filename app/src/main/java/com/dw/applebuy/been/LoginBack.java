package com.dw.applebuy.been;

import cn.trinea.android.common.annotation.NotProguard;

/**
 *  登录成功返回
 */
@NotProguard
public class LoginBack {

    /**
     * sessionid : iuMUomf/oYjQTa57AJGseSSBzo+LbyCXtPonB4x+4aVjwh260GiDo7KnUB0Pn2gEXw62FOyuQ0jgCvoDMMKhhjqgdhxp5TVFvMt06as8xNUEC6CZy3ArzxD+6bI+YCGU
     * mobile : 15310315193
     */

    private String sessionid;
    private String mobile;
    /**
     * jpush_code : 58a1838d8c30a49342994234
     */

    private String jpush_code;

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "sessionid:"+sessionid +" mobile:"+mobile;
    }

    public String getJpush_code() {
        return jpush_code;
    }

    public void setJpush_code(String jpush_code) {
        this.jpush_code = jpush_code;
    }
}
