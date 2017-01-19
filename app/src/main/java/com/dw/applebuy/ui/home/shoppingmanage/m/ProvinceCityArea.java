package com.dw.applebuy.ui.home.shoppingmanage.m;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 省 市 区都用这 modle
 */
public class ProvinceCityArea implements Parcelable {

    /**
     * id : 340000
     * name : 安徽省
     * pid : 0
     * chesan300 : null
     * first_pinyin : A
     * updatetime : null
     */

    private String id;
    private String name;
    private String pid;
    private Object chesan300;
    private String first_pinyin;
    private Object updatetime;

    protected ProvinceCityArea(Parcel in) {
        id = in.readString();
        name = in.readString();
        pid = in.readString();
        first_pinyin = in.readString();
    }

    public static final Creator<ProvinceCityArea> CREATOR = new Creator<ProvinceCityArea>() {
        @Override
        public ProvinceCityArea createFromParcel(Parcel in) {
            return new ProvinceCityArea(in);
        }

        @Override
        public ProvinceCityArea[] newArray(int size) {
            return new ProvinceCityArea[size];
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Object getChesan300() {
        return chesan300;
    }

    public void setChesan300(Object chesan300) {
        this.chesan300 = chesan300;
    }

    public String getFirst_pinyin() {
        return first_pinyin;
    }

    public void setFirst_pinyin(String first_pinyin) {
        this.first_pinyin = first_pinyin;
    }

    public Object getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Object updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(pid);
        dest.writeString(first_pinyin);
    }
}
