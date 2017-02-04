package com.dw.applebuy.ui.home.shoppingmanage.p;

import android.support.v4.util.ArrayMap;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.ui.home.shoppingmanage.v.Contract;
import com.rxmvp.bean.ResultResponse;
import com.rxmvp.basemvp.BasePresenter;
import com.wlj.base.util.StringUtils;

import java.io.File;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * 添加优惠
 */
public class YouHuiAddPresenter extends BasePresenter<Contract.YouHuiAddView> {

    public void addYouHui(ArrayMap<String, String> arrayMap,String imagePath) {
//        ArrayMap<String, Object> arrayMap = new ArrayMap<>();
//        arrayMap.put("sessionid", AppConfig.getAppConfig().get(AppConfig.CONF_KEY));
//        arrayMap.put("title", title.getText() + "");
//        arrayMap.put("description", intro.getText() + "");
//        arrayMap.put("category_id", type.getTag());//分类ID
//        arrayMap.put("stock", number.getText());//库存
//        arrayMap.put("integral", number.getText());//积分
//        arrayMap.put("end_time", number.getText());//优惠时间
//        //arrayMap.put("file", number.getText());//优惠卷图片
//
//        arrayMap.put("id", number.getText());// 	优惠卷ID(传递则为编辑)
//        arrayMap.put("img_path", number.getText());//回传路径(传递则为编辑)
        ArrayMap<String, RequestBody> bodyArrayMap = new ArrayMap<>();
        //验证
        MultipartBody.Part photo;
        if(StringUtils.isEmpty(imagePath)){
            mView.showMessage("请选择优惠券图片");
            return;
        }else{
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), new File(imagePath));
            photo = MultipartBody.Part.createFormData("file", (System.currentTimeMillis()+"").substring(5)+"i.png", photoRequestBody);

        }
        if(StringUtils.isEmpty(arrayMap.get("category_id")+"")){
            mView.showMessage("请选择优惠类型");
            return;
        }
        if(StringUtils.isEmpty(arrayMap.get("end_time")+"")){
            mView.showMessage("请选择优惠结束时间");
            return;
        }
        if(StringUtils.isEmpty(arrayMap.get("title")+"")){
            mView.showMessage("标题不能为空");
            return;
        }
        if(StringUtils.isEmpty(arrayMap.get("description")+"")){
            mView.showMessage("优惠描述不能为空");
            return;
        }
        if(StringUtils.isEmpty(arrayMap.get("integral")+"")){
            mView.showMessage("兑换价不能为空");
            return;
        }
        if(StringUtils.isEmpty(arrayMap.get("stock")+"")){
            toastMessage("库存不能为空");
            return;
        }//end

        mView.showLoading();

        Set<String> set = arrayMap.keySet();
        for (String s : set) {
            bodyArrayMap.put(s,RequestBody.create(null,arrayMap.get(s)));
        }

        addYouHuiCall(bodyArrayMap,photo);
    }

    private void addYouHuiCall(ArrayMap<String, RequestBody> arrayMap, MultipartBody.Part photo) {
        //观察者
        Subscriber<ResultResponse> subscriber = new Subscriber<ResultResponse>() {
            @Override
            public void onCompleted() {
                if(mView != null) {
                    mView.hideLoading();
                }
            }

            @Override
            public void onError(Throwable e) {
                onErrorShow(e,"提交失败");
            }

            @Override
            public void onNext(ResultResponse stringHttpStateResult) {
                if(mView != null) {
                    toastMessage(stringHttpStateResult.getMessage());
                }

            }

        };//end
        AppHttpMethods.getInstance().addYouHui(subscriber,arrayMap,photo);

    }

}
