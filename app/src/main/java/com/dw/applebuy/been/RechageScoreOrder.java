package com.dw.applebuy.been;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 *  充值积分是获取订单的返回接口
 */
public class RechageScoreOrder implements Parcelable {


    /**
     * pay_order : {"merchant_id":"1","trans_code":"T5881ce063cda268868233934099251","package_id":"6","amount":"950.00","integral":"100000","status":1,"addtime":1484901894,"callback":"http://supplier.pingguo24.com/index.php/merchant/app/integral/zfbCallback"}
     * wx_param : {"appid":"wx0041540a81a6cfce","partnerid":"1428284002","prepayid":"wx201701201645407434fc38220839321123","package":"Sign=WXPay","noncestr":"0xh4kfrpxhhdghiktjzkpqustkdd62om","timestamp":1484901899,"paysign":"89B4BDF4AB477177DAE14F0953FDF0C9"}
     */

    private PayOrderBean pay_order;
    private WxParamBean wx_param;


    protected RechageScoreOrder(Parcel in) {
        pay_order = in.readParcelable(PayOrderBean.class.getClassLoader());
        wx_param = in.readParcelable(WxParamBean.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(pay_order, flags);
        dest.writeParcelable(wx_param, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RechageScoreOrder> CREATOR = new Creator<RechageScoreOrder>() {
        @Override
        public RechageScoreOrder createFromParcel(Parcel in) {
            return new RechageScoreOrder(in);
        }

        @Override
        public RechageScoreOrder[] newArray(int size) {
            return new RechageScoreOrder[size];
        }
    };

    public PayOrderBean getPay_order() {
        return pay_order;
    }

    public void setPay_order(PayOrderBean pay_order) {
        this.pay_order = pay_order;
    }

    public WxParamBean getWx_param() {
        return wx_param;
    }

    public void setWx_param(WxParamBean wx_param) {
        this.wx_param = wx_param;
    }


    public static class PayOrderBean implements Parcelable {
        /**
         * merchant_id : 1
         * trans_code : T5881ce063cda268868233934099251
         * package_id : 6
         * amount : 950.00
         * integral : 100000
         * status : 1
         * addtime : 1484901894
         * callback : http://supplier.pingguo24.com/index.php/merchant/app/integral/zfbCallback
         */

        private String merchant_id;
        private String trans_code;
        private String package_id;
        private String amount;
        private String integral;
        private int status;
        private int addtime;
        private String callback;

        protected PayOrderBean(Parcel in) {
            merchant_id = in.readString();
            trans_code = in.readString();
            package_id = in.readString();
            amount = in.readString();
            integral = in.readString();
            status = in.readInt();
            addtime = in.readInt();
            callback = in.readString();
        }

        public static final Creator<PayOrderBean> CREATOR = new Creator<PayOrderBean>() {
            @Override
            public PayOrderBean createFromParcel(Parcel in) {
                return new PayOrderBean(in);
            }

            @Override
            public PayOrderBean[] newArray(int size) {
                return new PayOrderBean[size];
            }
        };

        public String getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(String merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getTrans_code() {
            return trans_code;
        }

        public void setTrans_code(String trans_code) {
            this.trans_code = trans_code;
        }

        public String getPackage_id() {
            return package_id;
        }

        public void setPackage_id(String package_id) {
            this.package_id = package_id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public String getCallback() {
            return callback;
        }

        public void setCallback(String callback) {
            this.callback = callback;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(merchant_id);
            dest.writeString(trans_code);
            dest.writeString(package_id);
            dest.writeString(amount);
            dest.writeString(integral);
            dest.writeInt(status);
            dest.writeInt(addtime);
            dest.writeString(callback);
        }
    }

    public static class WxParamBean implements Parcelable {
        /**
         * appid : wx0041540a81a6cfce
         * partnerid : 1428284002
         * prepayid : wx201701201645407434fc38220839321123
         * package : Sign=WXPay
         * noncestr : 0xh4kfrpxhhdghiktjzkpqustkdd62om
         * timestamp : 1484901899
         * paysign : 89B4BDF4AB477177DAE14F0953FDF0C9
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private int timestamp;
        private String paysign;

        protected WxParamBean(Parcel in) {
            appid = in.readString();
            partnerid = in.readString();
            prepayid = in.readString();
            packageX = in.readString();
            noncestr = in.readString();
            timestamp = in.readInt();
            paysign = in.readString();
        }

        public static final Creator<WxParamBean> CREATOR = new Creator<WxParamBean>() {
            @Override
            public WxParamBean createFromParcel(Parcel in) {
                return new WxParamBean(in);
            }

            @Override
            public WxParamBean[] newArray(int size) {
                return new WxParamBean[size];
            }
        };

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getPaysign() {
            return paysign;
        }

        public void setPaysign(String paysign) {
            this.paysign = paysign;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(appid);
            dest.writeString(partnerid);
            dest.writeString(prepayid);
            dest.writeString(packageX);
            dest.writeString(noncestr);
            dest.writeInt(timestamp);
            dest.writeString(paysign);
        }
    }
}
