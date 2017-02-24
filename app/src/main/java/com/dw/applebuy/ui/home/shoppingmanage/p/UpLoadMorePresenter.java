package com.dw.applebuy.ui.home.shoppingmanage.p;

import android.content.Context;

import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.been.Info;
import com.dw.applebuy.been.ImageBean;
import com.dw.applebuy.ui.home.renzheng.p.InfoUtil;
import com.dw.applebuy.ui.home.shoppingmanage.v.Contract;
import com.rxmvp.basemvp.BasePresenter;
import com.rxmvp.bean.HttpResult;
import com.rxmvp.http.ServiceFactory;
import com.rxmvp.http.exception.ErrorType;
import com.rxmvp.http.exception.ServerException;
import com.rxmvp.subscribers.RxSubscriber;
import com.rxmvp.transformer.DefaultTransformer;
import com.rxmvp.transformer.ErrorTransformer;
import com.rxmvp.transformer.SchedulerTransformer;
import com.wlj.base.util.AppConfig;
import com.wlj.base.util.AppContext;
import com.wlj.base.util.StringUtils;
import com.wlj.base.util.UIHelper;

import java.io.File;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 相册管理
 */
public class UpLoadMorePresenter extends BasePresenter<Contract.UpLoadMoreView> {


    public void del(Context mContext, final ImageBean imageBeen,final Info info ){

        UIHelper.tip(mContext, "确认删除？", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();

                ServiceFactory
                        .createService(FactoryInters.class)
                        .removeDetailsImgs(imageBeen.getUri())
                        .compose(SchedulerTransformer.<HttpResult>getInstance())
                        .map(new Func1<HttpResult, HttpResult>() {
                            @Override
                            public HttpResult call(HttpResult httpResult) {

                                if (httpResult.getStatus() == ErrorType.SUCCESS){
                                    List<ImageBean> imgs = info.getImgs();

                                    imgs.remove(imageBeen);
                                    AppContext.getAppContext().saveObject(info, InfoUtil.infoSerializableName);

                                }else {
                                    throw new ServerException(httpResult.getMessage(), httpResult.getStatus());
                                }

                                return httpResult;
                            }
                        })
                        .subscribe(new RxSubscriber<HttpResult>(mView) {
                            @Override
                            public void onNext(HttpResult httpResult) {
                                mView.delBack(httpResult);

                                toastMessage(httpResult.getMessage());
                            }
                        });


            }
        }, new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });

    }

    public void uploadDetailsImgs(String path, final Info info) {
        MultipartBody.Part photo;

        if (StringUtils.isEmpty(path)) {
            mView.showMessage("请选择图片");
            return;
        } else {
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), new File(path));
            photo = MultipartBody.Part.createFormData("file", (System.currentTimeMillis() + "").substring(5) + "i.png", photoRequestBody);
        }

        RequestBody requestBody = RequestBody.create(null, AppConfig.getAppConfig().get(AppConfig.CONF_KEY));

        ServiceFactory
                .createService(FactoryInters.class)
                .uploadDetailsImgs(requestBody, photo)
                .compose(new DefaultTransformer<List<ImageBean>>())
                .map(new Func1<List<ImageBean>, List<ImageBean>>() {
                    @Override
                    public List<ImageBean> call(List<ImageBean> imageBeen) {

                        List<ImageBean> imgs = info.getImgs();

                        imgs.addAll(imageBeen);
                        AppContext.getAppContext().saveObject(info, InfoUtil.infoSerializableName);
                        return imageBeen;
                    }
                })
                .subscribe(new RxSubscriber<List<ImageBean>>(mView) {
                    @Override
                    public void onNext(List<ImageBean> imageBeens) {
                        toastMessage("上传成功");
                        mView.uploadDetailsBack();
                    }
                });
    }
}
