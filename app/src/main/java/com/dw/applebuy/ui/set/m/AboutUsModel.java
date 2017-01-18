package com.dw.applebuy.ui.set.m;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class AboutUsModel implements Parcelable {

    /**
     * contact_tel : 400-2334-6666
     * about_us : aaaa
     */

    private String contact_tel;
    private String merchant_about_us;

    protected AboutUsModel(Parcel in) {
        contact_tel = in.readString();
        merchant_about_us = in.readString();
    }

    public static final Creator<AboutUsModel> CREATOR = new Creator<AboutUsModel>() {
        @Override
        public AboutUsModel createFromParcel(Parcel in) {
            return new AboutUsModel(in);
        }

        @Override
        public AboutUsModel[] newArray(int size) {
            return new AboutUsModel[size];
        }
    };

    public String getContact_tel() {
        return contact_tel;
    }

    public void setContact_tel(String contact_tel) {
        this.contact_tel = contact_tel;
    }

    public String getMerchant_about_us() {
        return merchant_about_us;
    }

    public void setMerchant_about_us(String merchant_about_us) {
        this.merchant_about_us = merchant_about_us;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contact_tel);
        dest.writeString(merchant_about_us);
    }
}
