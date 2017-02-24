package com.dw.applebuy.been;

import com.wlj.base.util.MathUtil;
import com.wlj.base.util.StringUtils;

import java.io.Serializable;
import java.util.List;

import cn.trinea.android.common.annotation.NotProguard;

import static com.dw.applebuy.ui.home.shoppingmanage.m.BusinessScope.BusinessWeek;

/**
 * 商家详情
 */
@NotProguard
public class Info implements Serializable {

    /**
     * id : 2
     * name : 15086856521
     * category_id : []
     * category : []
     * mobile : 15086856523
     * sales_volume : 0
     * business_hours : {"from":"","to":""}
     * business_week : []
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
     * contact_tel: "400-2334-6666",
     * message_new: 1
     */

    private String id;
    private String name;
    private String mobile;
    private int sales_volume;
    private BusinessHoursBean business_hours;
    private List<String> business_week;
    private int province_id;
    private double latitude;
    private double longitude;
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
    private List<String> category_id;
    private List<String> category;
    private List<ImageBean> imgs;
    private String contact_tel;
    private int message_new;

    public List<String> getBusiness_week() {
        return business_week;
    }

    /**
     * [1,2]->星期一，星期二
     *
     * @return
     */
    public String getBusiness_weekShow() {

        if (business_week == null) {
            return "";
        }
        if (business_week.size() == 7) {
            return "每天";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < business_week.size(); i++) {
            int week = MathUtil.parseInteger(business_week.get(i));
            stringBuilder.append(BusinessWeek[week] + ",");
        }
        return stringBuilder.deleteCharAt(-1 + stringBuilder.length()).toString();
    }

    public String getBusiness_hoursShow() {
        if (business_hours == null) return "";
        String from = business_hours.getFrom();
        String to = business_hours.getTo();

        if (StringUtils.isEmpty(from) && StringUtils.isEmpty(to)) {
            return "";
        }
        if ("0".equals(from) && "0".equals(to)) {
            return "全天24小时";
        }
        return from + "-" + to;
    }


//    public String getBusiness_weekStr() {
//
//        if (business_week == null) {
//            return "";
//        }
//
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < business_week.size(); i++) {
//            stringBuilder.append(BusinessWeek[i]+",");
//        }
//        return stringBuilder.deleteCharAt(-1 + stringBuilder.length()).toString();
//    }


    public void setBusiness_week(List<String> business_week) {
        this.business_week = business_week;
    }

    public String getContact_tel() {
        return contact_tel;
    }

    public void setContact_tel(String contact_tel) {
        this.contact_tel = contact_tel;
    }

    public int getMessage_new() {
        return message_new;
    }

    public void setMessage_new(int message_new) {
        this.message_new = message_new;
    }

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

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
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

    public List<String> getCategory_id() {
        return category_id;
    }

    public void setCategory_id(List<String> category_id) {
        this.category_id = category_id;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<ImageBean> getImgs() {
        return imgs;
    }

    public void setImgs(List<ImageBean> imgs) {
        this.imgs = imgs;
    }


    public static class BusinessHoursBean implements Serializable {
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

//    public static class ImgsBean implements Serializable {
//        /**
//         * width : 300
//         * height : 300
//         * url : http://supplier.pingguo24.com/static/img/no_img.jpg
//         */
//
//        private int width;
//        private int height;
//        private String url;
//        /**
//         * uri : uploads/merchant/69/details/main/file1487061420.png
//         */
//
//        private String uri;
//
//        public int getWidth() {
//            return width;
//        }
//
//        public void setWidth(int width) {
//            this.width = width;
//        }
//
//        public int getHeight() {
//            return height;
//        }
//
//        public void setHeight(int height) {
//            this.height = height;
//        }
//
//        public String getUrl() {
//            return url;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//
//
//        @Override
//        public String toString() {
//            return getUrl();
//        }
//
//        public String getUri() {
//            return uri;
//        }
//
//        public void setUri(String uri) {
//            this.uri = uri;
//        }
//    }
}
