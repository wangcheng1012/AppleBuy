package com.dw.applebuy.ui.home.scoremanage.m;

import java.util.List;

import cn.trinea.android.common.annotation.NotProguard;

/**
 *  积分列表
 */
@NotProguard
public class ScoreListResult {

    /**
     * info : {}
     * list : []
     */
    private InfoBean info;
    private List<ScoreListBean> list;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<ScoreListBean> getList() {
        return list;
    }

    public void setList(List<ScoreListBean> list) {
        this.list = list;
    }

    @NotProguard
    public static class InfoBean {

        /**
         * total : 7100
         * month_total : 7100
         * balance : 0
         */

        private int total;
        private int month_total;
        private int balance;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getMonth_total() {
            return month_total;
        }

        public void setMonth_total(int month_total) {
            this.month_total = month_total;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }
    }


    /**
     * 积分管理的  积分列表
     */
    @NotProguard
    public static class ScoreListBean {
//        public static final String type_赠送会员 = "1";
//        public static final String type_兑换 = "2";
        /**
         * 充值
         */
        public static final String type_rechage = "3";
        /**
         *
         */
        public static final String type_xiaofei = "4";

        /**
         * name : 张三
         * name_remark : PZX
         * head_portrait : http://localhost/pgg/static/img/no_img.jpg
         * mobile : 15736107188
         * time : 2017-01-13 15:02
         * integral : 100
         * title : 全场消费减50
         * type : 2
         */

        private String name;
        private String name_remark;
        private String head_portrait;
        private String mobile;
        private String time;
        private String integral;
        private String title;
        private String type;

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

        public String getHead_portrait() {
            return head_portrait;
        }

        public void setHead_portrait(String head_portrait) {
            this.head_portrait = head_portrait;
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
    }
}
