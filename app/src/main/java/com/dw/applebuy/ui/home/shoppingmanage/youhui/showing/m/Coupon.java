package com.dw.applebuy.ui.home.shoppingmanage.youhui.showing.m;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class Coupon implements Parcelable {

    /**
     * id : 4
     * category_id : 3
     * stock : 500
     * integral : 100
     * title : 全场消费减50
     * sales_volume : 1
     * end_time : 有效期截止：2017/01/09
     * icon : http://supplier.pingguo24.com/static/img/coupon/dikouquan@2x.png
     */

    private String id;
    private String category_id;
    private String stock;
    private String integral;
    private String title;
    private String sales_volume;
    private String end_time;
    private String icon;

    public Coupon( ) {
    }

    protected Coupon(Parcel in) {
        id = in.readString();
        category_id = in.readString();
        stock = in.readString();
        integral = in.readString();
        title = in.readString();
        sales_volume = in.readString();
        end_time = in.readString();
        icon = in.readString();
    }

    public static final Creator<Coupon> CREATOR = new Creator<Coupon>() {
        @Override
        public Coupon createFromParcel(Parcel in) {
            return new Coupon(in);
        }

        @Override
        public Coupon[] newArray(int size) {
            return new Coupon[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
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

    public String getSales_volume() {
        return sales_volume;
    }

    public void setSales_volume(String sales_volume) {
        this.sales_volume = sales_volume;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(category_id);
        dest.writeString(stock);
        dest.writeString(integral);
        dest.writeString(title);
        dest.writeString(sales_volume);
        dest.writeString(end_time);
        dest.writeString(icon);
    }
}
