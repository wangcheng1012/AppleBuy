package com.dw.applebuy.ui.home.shoppingmanage.m;

import android.os.Parcel;
import android.os.Parcelable;

import com.wlj.base.util.AppConfig;

/**
 * 省 市 区都用这 modle
 */
public class ProvinceCityAreaRequest implements Parcelable {

    private String  sessionid = AppConfig.getAppConfig().get(AppConfig.CONF_KEY);

    private int province_id;

    private int city_id;

    public ProvinceCityAreaRequest() {
    }

    protected ProvinceCityAreaRequest(Parcel in) {
        sessionid = in.readString();
        province_id = in.readInt();
        city_id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sessionid);
        dest.writeInt(province_id);
        dest.writeInt(city_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProvinceCityAreaRequest> CREATOR = new Creator<ProvinceCityAreaRequest>() {
        @Override
        public ProvinceCityAreaRequest createFromParcel(Parcel in) {
            return new ProvinceCityAreaRequest(in);
        }

        @Override
        public ProvinceCityAreaRequest[] newArray(int size) {
            return new ProvinceCityAreaRequest[size];
        }
    };

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }


    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
}
