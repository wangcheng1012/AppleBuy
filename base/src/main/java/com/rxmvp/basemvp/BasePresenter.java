package com.rxmvp.basemvp;


import com.rxmvp.api.exception.ApiException_old;
import com.rxmvp.http.exception.ErrorType;

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
    public void onErrorShow(Throwable e,String defMessage) {
        e.printStackTrace();
        if(mView != null && mView instanceof BaseView) {
            BaseView mView = (BaseView) this.mView;
            mView.hideLoading();
            if(e instanceof ApiException_old){
                ApiException_old ae = (ApiException_old)e;
                int status = ae.getStatus();
                if(status != ErrorType.RE_LOGIN){
                    mView.showMessage(e.getMessage());
                }
            }else {
                mView.showMessage(defMessage);

            }
        }
    }
}
