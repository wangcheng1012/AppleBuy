package com.dw.applebuy.ui.home.renzheng.p;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.been.Info;
import com.dw.applebuy.ui.MainActivity;
import com.dw.applebuy.ui.home.renzheng.RenZhengActivity;
import com.orhanobut.logger.Logger;
import com.wlj.base.util.AppContext;
import com.wlj.base.util.StringUtils;
import com.wlj.base.util.UIHelper;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 *
 */
public class InfoUtil {

    public final static String infoSerializableName = "info";
    public static Boolean infoUpdate = true;

    private static InfoUtil mInfoUtil;

    public static InfoUtil getInstall() {
        if (mInfoUtil == null) {
            mInfoUtil = new InfoUtil();
        }
        return mInfoUtil;
    }

    public void getInfo(Activity activity, InfoBack infoBack){

        if(infoUpdate){
            getInfoFromWeb(activity,infoBack);
        }else{
            getInfoFromLocation(infoBack);
        }
    }

    /**
     * 资料view切换
     */
    public void getInfoFromLocation(InfoBack infoBack) {

        Observable.just(infoSerializableName)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(getFunc())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getInfoSubscriber(infoBack));
//                .subscribe(getAction1());
    }

    @NonNull
    private Func1<String, Info> getFunc() {
        return new Func1<String, Info>() {
            @Override
            public Info call(String s) {
//                Logger.i("getFunc  "+ s);
                return (Info) AppContext.getAppContext().readObject(s);
            }
        };
    }

    public void destroy() {
        mInfoUtil = null;
    }


    public void getInfoFromWeb(Activity activity, final InfoBack infoBack) {
        infoUpdate = false;
        Subscriber<Info> subscriber = getInfoSubscriber(infoBack);

        UIHelper.showProgressbar(activity, null);
        AppHttpMethods.getInstance().getInfo(subscriber);

    }

    @NonNull
    private Subscriber<Info> getInfoSubscriber(final InfoBack infoBack) {
        //观察者
        return new Subscriber<Info>() {
            @Override
            public void onCompleted() {
                UIHelper.closeProgressbar();
            }

            @Override
            public void onError(Throwable e) {
                UIHelper.closeProgressbar();
                UIHelper.toastMessage(AppContext.getAppContext(), "商家信息获取失败");
            }

            @Override
            public void onNext(Info info) {
                //缓存
                AppContext.getAppContext().saveObject(info, infoSerializableName);
                infoBack.back(info);
            }

        };
    }

    /**
     *
     */
    public interface InfoBack {
        void back(Info info);
    }


}
