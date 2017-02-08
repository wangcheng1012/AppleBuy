package com.rxmvp.bean;

/**
 * GsonConverter 用来只取status和message
 *
 * <p></p>
 Http服务返回一个固定格式的数据
 {
 "status": 1,
 "message": "已发送",
 "data": []
 }
 */
public class ResultResponse  {

    private int status;
    private String message;

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

  }
