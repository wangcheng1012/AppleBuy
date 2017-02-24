package com.dw.applebuy.ui.home.renzheng.p;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.been.Info;
import com.dw.applebuy.ui.MainActivity;
import com.dw.applebuy.ui.home.renzheng.RenZhengActivity;
import com.orhanobut.logger.Logger;
import com.rxmvp.http.ServiceFactory;
import com.rxmvp.subscribers.CommonSubscriber;
import com.rxmvp.transformer.DefaultTransformer;
import com.rxmvp.transformer.SchedulerTransformer;
import com.wlj.base.util.AppContext;
import com.wlj.base.util.StringUtils;
import com.wlj.base.util.UIHelper;

import java.io.File;

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

    public void getInfo(Activity activity, InfoBack infoBack) {

        if (infoUpdate) {
            getInfoFromWeb(activity, infoBack);
        } else {
            getInfoFromLocation(infoBack);
        }
    }

    /**
     * 资料view切换
     */
    public void getInfoFromLocation(InfoBack infoBack) {

        Observable.just(infoSerializableName)
                .subscribeOn(Schedulers.io())
                .map(getFunc())
//                .observeOn(Schedulers.io())
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
        ServiceFactory
                .createService(FactoryInters.class)
                .getInfo(null)
                .compose(new DefaultTransformer<Info>())
                .map(new Func1<Info, Info>() {
                    @Override
                    public Info call(Info info) {
                        AppContext.getAppContext().saveObject(info, infoSerializableName);
                        Logger.e("InfoUtil  "+info);
                        return info;
                    }
                })
                .subscribe(new CommonSubscriber<Info>(activity) {
                    @Override
                    public void onNext(Info info) {
//                        AppContext.getAppContext().saveObject(info, infoSerializableName);
                        infoBack.back(info);
                    }
                });

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
                e.printStackTrace();
                UIHelper.closeProgressbar();
                UIHelper.toastMessage(AppContext.getAppContext(), "商家信息获取失败");
            }

            @Override
            public void onNext(Info info) {
                //缓存
                infoBack.back(info);
            }

        };
    }

    /**
     * 清除缓存
     *
     * @param mContext
     */
    public void clean(Context mContext) {
        File data = mContext.getFileStreamPath(infoSerializableName);
        if (data != null) {

            data.delete();
        }
    }

    /**
     *
     */
    public interface InfoBack {
        void back(Info info);
    }


}
