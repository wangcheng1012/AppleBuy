package com.dw.applebuy.ui.home.ordermanage.m;

/**
 * Created by Administrator on 2017/1/20.
 */

public class CouponOrder {


//
//     {
//     title: "优惠卷标题",                              /*优惠卷名称*/
//    mct_name: "店铺名称",        /*店铺名称*/
//    address: "重庆市重庆市正街96号沙坪坝区", /*地址*/
//    code: "co5866096f53ec6Array",    /*订单编号*/
//    integral: "40",   /*积分*/
//    number: "2",   /*数量*/
//    name: "15221781148", /*用户名称*/
//    head_portrait: "http://localhost/pgg/static/img/no_img.jpg",/*用户头像*/
//    addtime: "2016/12/30 15:14",/*领取优惠卷时间*/
//    usetime: "2016/12/30 15:16",/*使用时间*/
//    time: "2017/12/28",/*到期时间*/
//    icon: "http://localhost/pgg/uploads/merchant/2/coupon/1/img_1483941960.png", /*优惠卷其中一张图片*/
//    status: "1"  /*状态 (1:未消费  2:已消费 3:已评论)*/
//    }


    private String title;
    private String mct_name;
    private String address;
    private String code;
    private String integral;
    private String number;
    private String name;
    private String head_portrait;
    private String addtime;
    private String usetime;
    private String time;
    private String icon;
    private String status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMct_name() {
        return mct_name;
    }

    public void setMct_name(String mct_name) {
        this.mct_name = mct_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_portrait() {
        return head_portrait;
    }

    public void setHead_portrait(String head_portrait) {
        this.head_portrait = head_portrait;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getUsetime() {
        return usetime;
    }

    public void setUsetime(String usetime) {
        this.usetime = usetime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
