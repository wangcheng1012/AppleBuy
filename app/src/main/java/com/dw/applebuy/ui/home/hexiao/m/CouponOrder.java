package com.dw.applebuy.ui.home.hexiao.m;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 *
 */
public class CouponOrder implements Parcelable {


    /**
     * coupon_id : 53
     * id : 103
     * title : 烤全羊优惠券
     * code : 58a17700d87be5115751327660
     * end_time : 截止：2017/06/30
     * integral : 500
     * addtime : 2017/02/13 17:06
     * usetime : 1970/01/01 08:00:00
     * number : 1
     * description : 凭此券到店消费烤全羊套餐，满288元可抵用50元现金
     * member_id : 6
     * status : 1
     * category_id : 2
     * img : [{"width":1280,"height":960,"url":"http://supplier.pingguo24.com/uploads/merchant/6/coupon/53/file20170118180043_1484733605.png"}]
     * cover_img : http://supplier.pingguo24.com/uploads/merchant/6/coverImg/main/cover_img.png?r=347721484
     * merchant : 草原狼烤羊腿
     * score : 0
     * merchant_id : 6
     * name : 泉淅
     * head_portrait : http://supplier.pingguo24.com/uploads/consumer/6/head_portrait/mini/1.png
     * code_src : 58a17700d87be5115751327660
     * comment : 0
     * icon : http://supplier.pingguo24.com/static/img/coupon/youhuiquan@2x.png
     */

    private String coupon_id;
    private String id;
    private String title;
    private String code;
    private String end_time;
    private String integral;
    private String addtime;
    private String usetime;
    private String number;
    private String description;
    private String member_id;
    private String status;
    private String category_id;
    private String cover_img;
    private String merchant;
    private String score;
    private String merchant_id;
    private String name;
    private String head_portrait;
    private String code_src;
    private int comment;
    private String icon;
    private List<ImgBean> img;

    protected CouponOrder(Parcel in) {
        coupon_id = in.readString();
        id = in.readString();
        title = in.readString();
        code = in.readString();
        end_time = in.readString();
        integral = in.readString();
        addtime = in.readString();
        usetime = in.readString();
        number = in.readString();
        description = in.readString();
        member_id = in.readString();
        status = in.readString();
        category_id = in.readString();
        cover_img = in.readString();
        merchant = in.readString();
        score = in.readString();
        merchant_id = in.readString();
        name = in.readString();
        head_portrait = in.readString();
        code_src = in.readString();
        comment = in.readInt();
        icon = in.readString();
        img = in.createTypedArrayList(ImgBean.CREATOR);
    }

    public static final Creator<CouponOrder> CREATOR = new Creator<CouponOrder>() {
        @Override
        public CouponOrder createFromParcel(Parcel in) {
            return new CouponOrder(in);
        }

        @Override
        public CouponOrder[] newArray(int size) {
            return new CouponOrder[size];
        }
    };

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getUsetime() {
        return usetime;
    }

    public void setUsetime(String usetime) {
        this.usetime = usetime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_portrait() {
        return head_portrait;
    }

    public void setHead_portrait(String head_portrait) {
        this.head_portrait = head_portrait;
    }

    public String getCode_src() {
        return code_src;
    }

    public void setCode_src(String code_src) {
        this.code_src = code_src;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<ImgBean> getImg() {
        return img;
    }

    public void setImg(List<ImgBean> img) {
        this.img = img;
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
        dest.writeString(coupon_id);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(code);
        dest.writeString(end_time);
        dest.writeString(integral);
        dest.writeString(addtime);
        dest.writeString(usetime);
        dest.writeString(number);
        dest.writeString(description);
        dest.writeString(member_id);
        dest.writeString(status);
        dest.writeString(category_id);
        dest.writeString(cover_img);
        dest.writeString(merchant);
        dest.writeString(score);
        dest.writeString(merchant_id);
        dest.writeString(name);
        dest.writeString(head_portrait);
        dest.writeString(code_src);
        dest.writeInt(comment);
        dest.writeString(icon);
        dest.writeTypedList(img);
    }

    public static class ImgBean implements Parcelable {
        /**
         * width : 1280
         * height : 960
         * url : http://supplier.pingguo24.com/uploads/merchant/6/coupon/53/file20170118180043_1484733605.png
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
            dest.writeInt(width);
            dest.writeInt(height);
            dest.writeString(url);
        }
    }
}
