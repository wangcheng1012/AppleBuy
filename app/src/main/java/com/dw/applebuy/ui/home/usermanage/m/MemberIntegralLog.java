package com.dw.applebuy.ui.home.usermanage.m;

import cn.trinea.android.common.annotation.NotProguard;

/**
 * Created by wlj on 2017/2/15.
 * 会员消费记录[积分兑换记录]
 */
@NotProguard
public class MemberIntegralLog {

    /**
     * name : 彭老师开车
     * name_remark : PZX
     * mobile : 15723131464
     * time : 2017-01-19 13:46
     * integral : 100
     * title : AAAAAA
     * type : 2
     */

    private String name;
    private String name_remark;
    private String mobile;
    private String time;
    private String integral;
    private String title;
    private String type;
    private String icon;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_remark() {
        return name_remark;
    }

    public void setName_remark(String name_remark) {
        this.name_remark = name_remark;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
