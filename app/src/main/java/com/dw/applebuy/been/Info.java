package com.dw.applebuy.been;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 *  商家详情
 */
public class Info implements Serializable{

    /**
     * id : 2
     * name : 15086856521
     * category_id : []
     * category : []
     * mobile : 15086856523
     * sales_volume : 0
     * business_hours : {"from":"","to":""}
     * business_week : {"from":"","to":""}
     * province_id : 0
     * latitude : 0
     * longitude : 0
     * addtime : 1483414387
     * city_id :
     * area_id : 0
     * address :
     * cover_img : http://supplier.pingguo24.com/static/img/no_img.jpg
     * imgs : [{"width":300,"height":300,"url":"http://supplier.pingguo24.com/static/img/no_img.jpg"},{"width":300,"height":300,"url":"http://supplier.pingguo24.com/static/img/no_img.jpg"}]
     * score : 0
     * min_integral : 0
     * status : 2
     * authenticate_status : 1
     * province :
     * city :
     * area :
     * all_address :
     * business_time :
     * distance : no
     */

    private String id;
    private String name;
    private String mobile;
    private int sales_volume;
    private BusinessHoursBean business_hours;
    private BusinessWeekBean business_week;
    private int province_id;
    private int latitude;
    private int longitude;
    private String addtime;
    private String city_id;
    private int area_id;
    private String address;
    private String cover_img;
    private String score;
    private int min_integral;
    private String status;
    /**
     * 认证状态(1:未认证  2:认证中  3:已认证  4:认证失败)
     */
    private String authenticate_status;
    private String province;
    private String city;
    private String area;
    private String all_address;
    private String business_time;
    private String distance;
    private List<?> category_id;
    private List<?> category;
    private List<ImgsBean> imgs;

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

    public int getSales_volume() {
        return sales_volume;
    }

    public void setSales_volume(int sales_volume) {
        this.sales_volume = sales_volume;
    }

    public BusinessHoursBean getBusiness_hours() {
        return business_hours;
    }

    public void setBusiness_hours(BusinessHoursBean business_hours) {
        this.business_hours = business_hours;
    }

    public BusinessWeekBean getBusiness_week() {
        return business_week;
    }

    public void setBusiness_week(BusinessWeekBean business_week) {
        this.business_week = business_week;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCover_img() {
        return cover_img;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getMin_integral() {
        return min_integral;
    }

    public void setMin_integral(int min_integral) {
        this.min_integral = min_integral;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthenticate_status() {
        return authenticate_status;
    }

    public void setAuthenticate_status(String authenticate_status) {
        this.authenticate_status = authenticate_status;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAll_address() {
        return all_address;
    }

    public void setAll_address(String all_address) {
        this.all_address = all_address;
    }

    public String getBusiness_time() {
        return business_time;
    }

    public void setBusiness_time(String business_time) {
        this.business_time = business_time;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<?> getCategory_id() {
        return category_id;
    }

    public void setCategory_id(List<?> category_id) {
        this.category_id = category_id;
    }

    public List<?> getCategory() {
        return category;
    }

    public void setCategory(List<?> category) {
        this.category = category;
    }

    public List<ImgsBean> getImgs() {
        return imgs;
    }

    public void setImgs(List<ImgsBean> imgs) {
        this.imgs = imgs;
    }



    public static class BusinessHoursBean implements Serializable{
        /**
         * from :
         * to :
         */

        private String from;
        private String to;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

    }

    public static class BusinessWeekBean implements Serializable {
        /**
         * from :
         * to :
         */

        private String from;
        private String to;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

    }

    public static class ImgsBean implements Serializable{
        /**
         * width : 300
         * height : 300
         * url : http://supplier.pingguo24.com/static/img/no_img.jpg
         */

        private int width;
        private int height;
        private String url;

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

    }
}
