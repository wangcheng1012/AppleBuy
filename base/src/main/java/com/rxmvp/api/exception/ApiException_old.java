package com.rxmvp.api.exception;

/**
 * API异常处理
 */
public class ApiException_old extends RuntimeException {


    // 异常处理，为速度，不必要设置 getter 和 setter
    public int code;
    public String message;

    public ApiException_old(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ApiException_old(int status, String message) {
        super(message);
        this.code = status;
    }

    public int getStatus() {
        return code;
    }
}