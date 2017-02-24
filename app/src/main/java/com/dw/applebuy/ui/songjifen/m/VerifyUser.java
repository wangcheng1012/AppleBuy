package com.dw.applebuy.ui.songjifen.m;

import android.os.Parcel;
import android.os.Parcelable;

import cn.trinea.android.common.annotation.NotProguard;

/**
 *  验证phone 返回的user信息
 */
@NotProguard
public class VerifyUser implements Parcelable{

    /**
     * 有此用户
     */
    public final static int status_have = 1;
//    public final static int status_no = 0;

    /**
     * id : 18
     * name : 15310315193
     * mobile : 15310315193
     * status : 1
     */
    private String id;
    private String name;
    private String mobile;
    private int status;

    protected VerifyUser(Parcel in) {
        id = in.readString();
        name = in.readString();
        mobile = in.readString();
        status = in.readInt();
    }

    public static final Creator<VerifyUser> CREATOR = new Creator<VerifyUser>() {
        @Override
        public VerifyUser createFromParcel(Parcel in) {
            return new VerifyUser(in);
        }

        @Override
        public VerifyUser[] newArray(int size) {
            return new VerifyUser[size];
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(mobile);
        dest.writeInt(status);
    }
}
