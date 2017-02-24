package com.dw.applebuy.ui.home.renzheng.p;

import android.support.v4.util.ArrayMap;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.ui.home.renzheng.v.Contract;
import com.rxmvp.basemvp.BasePresenter;
import com.rxmvp.bean.HttpResult;
import com.wlj.base.util.StringUtils;

import java.io.File;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 *
 */
public class RenZhengPresenter extends BasePresenter<Contract.RenZhengView> {

    public void submit(ArrayMap<String, Object> arrayMap, String business_license, String certificate) {


        ArrayMap<String, RequestBody> bodyArrayMap = new ArrayMap<>();
        //验证


//        arrayMap.put("name",name.getText()+"");
//        arrayMap.put("register_code",register_code.getText()+"");
//        arrayMap.put("legal_person",legal_person.getText()+"");
//        arrayMap.put("legal_person_code",legal_person_code.getText()+"");
//        arrayMap.put("email",email.getText()+"");
//        arrayMap.put("type", type);//	1:企业 2：个体
        if(StringUtils.isEmpty(arrayMap.get("name")+"")){
            mView.showMessage("营业执照名称不能为空");
            return;
        }
        if(StringUtils.isEmpty(arrayMap.get("register_code")+"")){
            mView.showMessage("营业执照注册号不能为空");
            return;
        }
        if(StringUtils.isEmpty(arrayMap.get("legal_person")+"")){
            mView.showMessage("法人姓名不能为空");
            return;
        }
        if(StringUtils.isEmpty(arrayMap.get("legal_person_code")+"")){
            mView.showMessage("法人身份证号不能为空");
            return;
        }
        if(StringUtils.isEmpty(arrayMap.get("email")+"")){
            mView.showMessage("邮箱不能为空");
            return;
        }
            //
        MultipartBody.Part photo;
        if(StringUtils.isEmpty(business_license)){
            mView.showMessage("请选择营业执照图片");
            return;
        }else{
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), new File(business_license));
            photo = MultipartBody.Part.createFormData("business_license", (System.currentTimeMillis()+"").substring(5)+"i.png", photoRequestBody);

        }
        MultipartBody.Part photo2;
        if(StringUtils.isEmpty(certificate)){
            mView.showMessage("请选择身份证照片");
            return;
        }else{
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), new File(certificate));
            photo2 = MultipartBody.Part.createFormData("certificate", (System.currentTimeMillis()+"").substring(5)+"h.png", photoRequestBody);
        }
        //end

        mView.showLoading();

        Set<String> set = arrayMap.keySet();
        for (String s : set) {
            bodyArrayMap.put(s,RequestBody.create(null,arrayMap.get(s)+""));
        }

        submitCall(bodyArrayMap,photo,photo2);
    }

    private void submitCall(ArrayMap<String, RequestBody> arrayMap, MultipartBody.Part photo, MultipartBody.Part photo2) {

        //观察者
        Subscriber<HttpResult> subscriber = new Subscriber<HttpResult>() {
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
            public void onNext(HttpResult stringHttpStateResult) {
                if(mView != null) {
                    toastMessage(stringHttpStateResult.getMessage());
                    mView.submitBack();
                }
                InfoUtil.infoUpdate =true;
            }

        };//end

        AppHttpMethods.getInstance().applyAuthentication(subscriber,arrayMap,photo,photo2);
    }
}
