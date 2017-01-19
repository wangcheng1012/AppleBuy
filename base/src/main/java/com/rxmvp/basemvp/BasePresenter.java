package com.rxmvp.basemvp;


import com.rxmvp.api.ApiException;
import com.wlj.base.BuildConfig;

public abstract class BasePresenter<T> {
    public T mView;

    public void attach(T mView){
        this.mView = mView;
    }

    public void dettach(){
        mView = null;
    }

    /**
     * toast 文本消息
     * @param message
     */
    protected void toastMessage(String message){
        if(mView != null && mView instanceof BaseView) {
            ((BaseView) mView).showMessage(message);
        }
    }

    /**
     * 显示错误回调
     * @param e
     * @param defMessage
     */
    protected void onErrorShow(Throwable e,String defMessage) {

        if(BuildConfig.DEBUG){
            e.printStackTrace();
        }
        if(mView != null && mView instanceof BaseView) {
            BaseView mView = (BaseView) this.mView;
            mView.hideLoading();
            if(e instanceof ApiException){
                mView.showMessage(e.getMessage());
            }else {
                mView.showMessage(defMessage);
            }
        }
    }
}
