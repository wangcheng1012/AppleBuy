package com.dw.applebuy.ui.message;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wlj on 2017/2/15.
 */

public class MessageBean implements Parcelable{

    /**
     * id : 1
     * title : 通知消息
     * content : 第一个消息的通知信息发送
     * type : 1
     * addtime : 2017-01-03 11:33:07
     * is_view : 1
     */

    private String id;
    private String title;
    private String content;
    private String type;
    private String addtime;
    private int is_view;

    protected MessageBean(Parcel in) {
        id = in.readString();
        title = in.readString();
        content = in.readString();
        type = in.readString();
        addtime = in.readString();
        is_view = in.readInt();
    }

    public static final Creator<MessageBean> CREATOR = new Creator<MessageBean>() {
        @Override
        public MessageBean createFromParcel(Parcel in) {
            return new MessageBean(in);
        }

        @Override
        public MessageBean[] newArray(int size) {
            return new MessageBean[size];
        }
    };

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getIs_view() {
        return is_view;
    }

    public void setIs_view(int is_view) {
        this.is_view = is_view;
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
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(type);
        dest.writeString(addtime);
        dest.writeInt(is_view);
    }
}
