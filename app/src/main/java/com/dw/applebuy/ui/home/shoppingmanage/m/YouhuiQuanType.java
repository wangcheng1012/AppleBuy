package com.dw.applebuy.ui.home.shoppingmanage.m;

import android.os.Parcel;
import android.os.Parcelable;

import cn.trinea.android.common.annotation.NotProguard;

/**
 * 优惠券item
 */
@NotProguard
public class YouhuiQuanType implements Parcelable {

    private String id;
    private String name;

    protected YouhuiQuanType(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<YouhuiQuanType> CREATOR = new Creator<YouhuiQuanType>() {
        @Override
        public YouhuiQuanType createFromParcel(Parcel in) {
            return new YouhuiQuanType(in);
        }

        @Override
        public YouhuiQuanType[] newArray(int size) {
            return new YouhuiQuanType[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }
}