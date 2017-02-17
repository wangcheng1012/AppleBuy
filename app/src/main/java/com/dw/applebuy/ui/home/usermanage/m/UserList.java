package com.dw.applebuy.ui.home.usermanage.m;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by wlj on 2017/2/14.
 */

public class UserList {

    /**
     * info : {"month_num":0,"member_sum":2}
     * list : [{"merchant_id":"1","member_id":"1","addtime":"2016-12-22 11:07","name":"李海平","name_remark":"","mobile":"15086856523","head_portrait":"http://supplier.pingguo24.com/uploads/consumer/1/head_portrait/mini/1.png","consume_sum":"10"}]
     */
    private InfoBean info;
    private List<ListBean> list;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class InfoBean {
        /**
         * month_num : 0
         * member_sum : 2
         */

        private int month_num;
        private int member_sum;

        public int getMonth_num() {
            return month_num;
        }

        public void setMonth_num(int month_num) {
            this.month_num = month_num;
        }

        public int getMember_sum() {
            return member_sum;
        }

        public void setMember_sum(int member_sum) {
            this.member_sum = member_sum;
        }
    }

    public static class ListBean implements Parcelable {
        /**
         * merchant_id : 1
         * member_id : 1
         * addtime : 2016-12-22 11:07
         * name : 李海平
         * name_remark :
         * mobile : 15086856523
         * head_portrait : http://supplier.pingguo24.com/uploads/consumer/1/head_portrait/mini/1.png
         * consume_sum : 10
         */

        private String merchant_id;
        private String member_id;
        private String addtime;
        private String name;
        private String name_remark;
        private String mobile;
        private String head_portrait;
        private String consume_sum;

        protected ListBean(Parcel in) {
            merchant_id = in.readString();
            member_id = in.readString();
            addtime = in.readString();
            name = in.readString();
            name_remark = in.readString();
            mobile = in.readString();
            head_portrait = in.readString();
            consume_sum = in.readString();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel in) {
                return new ListBean(in);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };

        public String getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(String merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

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

        public String getHead_portrait() {
            return head_portrait;
        }

        public void setHead_portrait(String head_portrait) {
            this.head_portrait = head_portrait;
        }

        public String getConsume_sum() {
            return consume_sum;
        }

        public void setConsume_sum(String consume_sum) {
            this.consume_sum = consume_sum;
        }

        /**
         * Describe the kinds of special objects contained in this Parcelable's
         * marshalled representation.
         *
         * @return a bitmask indicating the set of special object types marshalled
         * by the Parcelable.
         */
        @Override
        public int describeContents() {
            return 0;
        }

        /**
         * Flatten this object in to a Parcel.
         *
         * @param dest  The Parcel in which the object should be written.
         * @param flags Additional flags about how the object should be written.
         *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
         */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(merchant_id);
            dest.writeString(member_id);
            dest.writeString(addtime);
            dest.writeString(name);
            dest.writeString(name_remark);
            dest.writeString(mobile);
            dest.writeString(head_portrait);
            dest.writeString(consume_sum);
        }
    }
}
