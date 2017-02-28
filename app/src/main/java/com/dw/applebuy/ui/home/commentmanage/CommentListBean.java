package com.dw.applebuy.ui.home.commentmanage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import cn.trinea.android.common.annotation.NotProguard;

/**
 * 评论管理
 */
@NotProguard
public class CommentListBean {

    /**
     * info : {"nice":1,"good":1,"poor":0,"total":4}
     * data : [{"head_portrait":"http://supplier.pingguo24.com/uploads/consumer/1/head_portrait/mini/1.png","name":"李海平","addtime":"2016-12-22","score":"3","content":"相当可以","imgs":[],"icon":"http://supplier.pingguo24.com/static/img/coupon/jian@2x.png","title":"满30减20","order_time":"2017-01-09","is_reply":1,"reply":"谢谢"}]
     */

    private InfoBean info;
    private List<DataBean> data;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @NotProguard
    public static class InfoBean {

//        nice: 1,  /*好评*/
//        good: 1,/*中评*/
//        poor: 0,/*差评*/
//        total: 4 /* 总评价条数*/

        private int nice;
        private int good;
        private int poor;
        private int total;

        public int getNice() {
            return nice;
        }

        public void setNice(int nice) {
            this.nice = nice;
        }

        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
        }

        public int getPoor() {
            return poor;
        }

        public void setPoor(int poor) {
            this.poor = poor;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    @NotProguard
    public static class DataBean {
        /**
         * head_portrait : http://supplier.pingguo24.com/uploads/consumer/1/head_portrait/mini/1.png
         * name : 李海平
         * addtime : 2016-12-22
         * score : 3
         * content : 相当可以
         * imgs : []
         * icon : http://supplier.pingguo24.com/static/img/coupon/jian@2x.png
         * title : 满30减20
         * order_time : 2017-01-09
         * is_reply : 1
         * reply : 谢谢
         */

        private String id;
        private String head_portrait;
        private String name;
        private String addtime;
        private String score;
        private String content;
        private String icon;
        private String title;
        private String order_time;
        /**
         *  是否回复 1-已回复   0-未回复
         */
        private int is_reply;
        private String reply;
        private List<ImagesBean> imgs;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHead_portrait() {
            return head_portrait;
        }

        public void setHead_portrait(String head_portrait) {
            this.head_portrait = head_portrait;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOrder_time() {
            return order_time;
        }

        public void setOrder_time(String order_time) {
            this.order_time = order_time;
        }

        public int getIs_reply() {
            return is_reply;
        }

        public void setIs_reply(int is_reply) {
            this.is_reply = is_reply;
        }

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public List<ImagesBean> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImagesBean> imgs) {
            this.imgs = imgs;
        }
    }

    @NotProguard
    public static class ImagesBean implements Parcelable{

        //big: "http://supplier.pingguo24.com/static/img/no_img.jpg", /*大图*/
        //min: "http://supplier.pingguo24.com/static/img/no_img.jpg" /*小图*/
        private String big;
        private String min;

        protected ImagesBean(Parcel in) {
            big = in.readString();
            min = in.readString();
        }

        public static final Creator<ImagesBean> CREATOR = new Creator<ImagesBean>() {
            @Override
            public ImagesBean createFromParcel(Parcel in) {
                return new ImagesBean(in);
            }

            @Override
            public ImagesBean[] newArray(int size) {
                return new ImagesBean[size];
            }
        };

        public String getBig() {
            return big;
        }

        public void setBig(String big) {
            this.big = big;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(big);
            dest.writeString(min);
        }

        @Override
        public String toString() {
            return getBig();
        }
    }

}
