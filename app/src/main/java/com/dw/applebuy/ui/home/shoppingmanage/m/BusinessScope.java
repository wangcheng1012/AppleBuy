package com.dw.applebuy.ui.home.shoppingmanage.m;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * 商家经营范围-分类
 */
public class BusinessScope implements Parcelable{

    public static String[] BusinessWeek = {"周日","周一","周二","周三","周四","周五","周六"};

    public BusinessScope() {
    }

    public BusinessScope(String name,boolean selected) {
        this.name = name;
        this.selected = selected;
    }

    /**
     * id : 1
     * name : 图书音像
     * selected : 0
     */

    private String id;
    private String name;
    @Expose
    private boolean selected;



    protected BusinessScope(Parcel in) {
        id = in.readString();
        name = in.readString();
        selected = in.readByte() != 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeByte((byte) (selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BusinessScope> CREATOR = new Creator<BusinessScope>() {
        @Override
        public BusinessScope createFromParcel(Parcel in) {
            return new BusinessScope(in);
        }

        @Override
        public BusinessScope[] newArray(int size) {
            return new BusinessScope[size];
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

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
