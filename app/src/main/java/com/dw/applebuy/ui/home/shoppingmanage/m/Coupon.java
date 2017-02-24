package com.dw.applebuy.ui.home.shoppingmanage.m;

import android.os.Parcel;
import android.os.Parcelable;

import com.wlj.base.util.StringUtils;

import java.util.List;

import cn.trinea.android.common.annotation.NotProguard;

/**
 * 优惠券
 */
@NotProguard
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
    /**
     * cover_img : http://supplier.pingguo24.com/uploads/merchant/3/coverImg/main/cover_img.png?r=985230969
     * merchant : 原子弹霸王餐
     * category_name : 满减劵
     * mobile : 15683124781
     * img : [{"width":3000,"height":2002,"url":"http://supplier.pingguo24.com/uploads/merchant/3/coupon/46/file20170113114613_1484279142.png"}]
     * merchant_id : 3
     * description : SSSSSSSSSSS
     * status : 2
     * audit_status : 2
     * sales_volume : 0
     * db_img : ["uploads/merchant/3/coupon/46/file20170113114613_1484279142.png"]
     * show_end_time : 截止2017/01/20
     */

    private String cover_img;
    private String merchant;
    private String category_name;
    private String mobile;
    private String merchant_id;
    private String description;
    private String status;
    private String audit_status;
//    @SerializedName("sales_volume")
//    private int sales_volumeX;
    private String show_end_time;
    private List<ImgBean> img;
    private List<String> db_img;


    protected Coupon(Parcel in) {
        id = in.readString();
        category_id = in.readString();
        stock = in.readString();
        integral = in.readString();
        title = in.readString();
        sales_volume = in.readString();
        end_time = in.readString();
        icon = in.readString();
        cover_img = in.readString();
        merchant = in.readString();
        category_name = in.readString();
        mobile = in.readString();
        merchant_id = in.readString();
        description = in.readString();
        status = in.readString();
        audit_status = in.readString();
//        sales_volumeX = in.readInt();
        show_end_time = in.readString();
        img = in.createTypedArrayList(ImgBean.CREATOR);
        db_img = in.createStringArrayList();
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
        dest.writeString(cover_img);
        dest.writeString(merchant);
        dest.writeString(category_name);
        dest.writeString(mobile);
        dest.writeString(merchant_id);
        dest.writeString(description);
        dest.writeString(status);
        dest.writeString(audit_status);
//        dest.writeInt(sales_volumeX);
        dest.writeString(show_end_time);
        dest.writeTypedList(img);
        dest.writeStringList(db_img);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getCover_img() {
        return cover_img;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public String getShow_end_time() {
        return show_end_time;
    }

    public void setShow_end_time(String show_end_time) {
        this.show_end_time = show_end_time;
    }

    public List<ImgBean> getImg() {
        return img;
    }
    public String getImgFromIndex(int index) {
        String url =  getCover_img();
        List<Coupon.ImgBean> imgs =  getImg();
        if(imgs != null && imgs.size()-1 >= index){
            url = imgs.get(index).getUrl();
        }
        return url;
    }
    public void setImg(List<ImgBean> img) {
        this.img = img;
    }

    public List<String> getDb_img() {
        return db_img;
    }

    public void setDb_img(List<String> db_img) {
        this.db_img = db_img;
    }

    public String getDb_imgFromIndex(int index) {
        if(db_img != null && db_img.size()-1 >= index){
            String s = db_img.get(index);
            return s;
        }
        return null;
    }

    /**
     * 下架时的end_time参数处理
     * @return
     */
    public String getShelvesEnd_time() {
        if(!StringUtils.isEmpty(end_time)){
            String[] split = end_time.split("：");
            String t = split[1];

            if(!StringUtils.isEmpty(t)){
                String replace = t.replace("/", "-");
                return replace;
            }
        }
        return "";
    }

//    public int getSales_volumeX() {
//        return sales_volumeX;
//    }
//
//    public void setSales_volumeX(int sales_volumeX) {
//        this.sales_volumeX = sales_volumeX;
//    }

    @NotProguard
    public static class ImgBean implements Parcelable {
        /**
         * width : 3000
         * height : 2002
         * url : http://supplier.pingguo24.com/uploads/merchant/3/coupon/46/file20170113114613_1484279142.png
         */

        private int width;
        private int height;
        private String url;

        protected ImgBean(Parcel in) {
            width = in.readInt();
            height = in.readInt();
            url = in.readString();
        }

        public static final Creator<ImgBean> CREATOR = new Creator<ImgBean>() {
            @Override
            public ImgBean createFromParcel(Parcel in) {
                return new ImgBean(in);
            }

            @Override
            public ImgBean[] newArray(int size) {
                return new ImgBean[size];
            }
        };

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(width);
            dest.writeInt(height);
            dest.writeString(url);
        }
    }
}
