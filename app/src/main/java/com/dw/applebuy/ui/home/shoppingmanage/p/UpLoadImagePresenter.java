package com.dw.applebuy.ui.home.shoppingmanage.p;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.ui.home.shoppingmanage.m.ImageBean;
import com.dw.applebuy.ui.home.shoppingmanage.m.UploadCoverImg;
import com.dw.applebuy.ui.home.shoppingmanage.v.Contract;
import com.rxmvp.basemvp.BasePresenter;
import com.wlj.base.util.AppConfig;
import com.wlj.base.util.StringUtils;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by wlj on 2017/2/14.
 */
public class UpLoadImagePresenter extends BasePresenter<Contract.UpLoadImageView> {




    /**
     * 上传首图
     * @param coverimg
     */
    public void uploadCoverImg(String coverimg) {

        MultipartBody.Part photo;

        if (StringUtils.isEmpty(coverimg)) {
            mView.showMessage("请选择图片");
            return;
        } else {
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), new File(coverimg));
            photo = MultipartBody.Part.createFormData("cover_img", (System.currentTimeMillis() + "").substring(5) + "i.png", photoRequestBody);
        }

        //观察者
        Subscriber<UploadCoverImg> subscriber = new Subscriber<UploadCoverImg>() {
            @Override
            public void onCompleted() {
                if (mView != null) {
                    mView.hideLoading();
                }
            }

            @Override
            public void onError(Throwable e) {
                onErrorShow(e, "提交失败");
            }

            @Override
            public void onNext(UploadCoverImg uploadCoverImg ) {
                if (mView != null) {
                    mView.uploadCoverBack(uploadCoverImg);
                }
            }

        };//end

        mView.showLoading();
        RequestBody requestBody = RequestBody.create(null, AppConfig.getAppConfig().get(AppConfig.CONF_KEY));
        AppHttpMethods.getInstance().uploadCoverImg(subscriber,photo, requestBody);

    }

    /**
     * 上传详情图片
     * @param coverimg
     */
    public void uploadDetailsImgs(String coverimg) {

        MultipartBody.Part photo;

        if (StringUtils.isEmpty(coverimg)) {
            mView.showMessage("请选择图片");
            return;
        } else {
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), new File(coverimg));
            photo = MultipartBody.Part.createFormData("file", (System.currentTimeMillis() + "").substring(5) + "i.png", photoRequestBody);
        }

        //观察者
        Subscriber<List<ImageBean>> subscriber = new Subscriber<List<ImageBean>>() {
            @Override
            public void onCompleted() {
                if (mView != null) {
                    mView.hideLoading();
                }
            }

            @Override
            public void onError(Throwable e) {
                onErrorShow(e, "提交失败");
            }

            @Override
            public void onNext(List<ImageBean> list ) {
                if (mView != null) {
                    mView.uploadDetailsBack(list);
                }
            }

        };//end

        mView.showLoading();
        RequestBody requestBody = RequestBody.create(null, AppConfig.getAppConfig().get(AppConfig.CONF_KEY));
        AppHttpMethods.getInstance().uploadDetailsImgs(subscriber,photo,requestBody);
    }

}
