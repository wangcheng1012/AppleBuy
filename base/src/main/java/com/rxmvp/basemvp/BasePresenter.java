package com.rxmvp.basemvp;


import com.rxmvp.api.ApiException;

public abstract class BasePresenter<T> {
    public T mView;

    public void attach(T mView){
        this.mView = mView;
    }

    public void dettach(){
        mView = null;
    }

}
