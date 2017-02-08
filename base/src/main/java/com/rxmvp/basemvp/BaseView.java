package com.rxmvp.basemvp;


import com.rxmvp.bean.HttpStateResult;

public interface BaseView {
    void showLoading();
    void hideLoading();
    void showMessage(String message);

}
