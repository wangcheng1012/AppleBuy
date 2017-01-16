package com.dw.applebuy.ui.home.shoppingmanage.youhui.add.p;

import android.graphics.Bitmap;
import android.support.v4.util.ArrayMap;

import com.dw.applebuy.BuildConfig;
import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.add.v.Views;
import com.rxmvp.api.ApiException;
import com.rxmvp.api.GsonConverter.ResultResponse;
import com.rxmvp.basemvp.BasePresenter;
import com.wlj.base.util.StringUtils;

import java.io.ByteArrayOutputStream;

import rx.Subscriber;

/**
 * 添加优惠
 */
public class YouHuiAddPresenter extends BasePresenter<Views.YouHuiAddView> {

    public void addYouHui(ArrayMap<String, Object> arrayMap) {
//        ArrayMap<String, Object> arrayMap = new ArrayMap<>();
//        arrayMap.put("sessionid", AppConfig.getAppConfig().get(AppConfig.CONF_KEY));
//        arrayMap.put("title", title.getText() + "");
//        arrayMap.put("description", intro.getText() + "");
//        arrayMap.put("category_id", type.getTag());//分类ID
//        arrayMap.put("stock", number.getText());//库存
//        arrayMap.put("integral", number.getText());//积分
//        arrayMap.put("end_time", number.getText());//优惠时间
//        arrayMap.put("file", number.getText());//优惠卷图片
//
//        arrayMap.put("id", number.getText());// 	优惠卷ID(传递则为编辑)
//        arrayMap.put("img_path", number.getText());//回传路径(传递则为编辑)

        //验证
        Object file = arrayMap.get("file");
        if(StringUtils.isEmpty(file +"")){
            mView.showMessage("请选择优惠券图片");
            return;
        }else{
            Bitmap   bitmap = (Bitmap)file;

            ByteArrayOutputStream bStream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.PNG, 80, bStream);

            byte[] bytes = bStream.toByteArray();
            arrayMap.put("file",bytes);
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
            mView.showMessage("库存不能为空");
            return;
        }//end

        mView.showLoading();
        addYouHuiCall(arrayMap);

    }

    private void addYouHuiCall(ArrayMap<String, Object> arrayMap) {
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
                    mView.showMessage(stringHttpStateResult.getMessage());
                }

            }

        };//end
        AppHttpMethods.getInstance().addYouHui(subscriber,arrayMap);

    }

    private void onErrorShow(Throwable e,String defMessage) {
        if(mView != null) {
            mView.hideLoading();
            if(e instanceof ApiException){
                mView.showMessage(e.getMessage());
            }else {
                mView.showMessage(defMessage);
            }
        }
        if(BuildConfig.DEBUG){
            e.printStackTrace();
        }
    }
}
