package com.rxmvp.bean;

/*
Http服务返回一个固定格式的数据
{
    "status": 1,
    "message": "已发送",
    "data": []
}
 */
public class HttpResult<T> {
    private int status;
    private String message;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}