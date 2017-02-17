package com.dw.applebuy.ui.home.shoppingmanage.p;

import android.support.v4.util.ArrayMap;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.ui.home.renzheng.p.InfoUtil;
import com.dw.applebuy.ui.home.shoppingmanage.v.Contract;
import com.rxmvp.basemvp.BasePresenter;
import com.rxmvp.bean.HttpResult;
import com.wlj.base.util.StringUtils;

import rx.Subscriber;

/**
 *
 */
public class DataPresenter extends BasePresenter<Contract.DataView> {


    public void save(ArrayMap<String, Object> arrayMap) {
//        //必填
//        arrayMap.put("name",dataShopping.getText()+"");
//        arrayMap.put("week", info.getBusiness_week().toString());//周（0到6）代表（周日到周六）多个用逗号隔开
//        arrayMap.put("hours_from",info.getBusiness_hours().getFrom() );
//        arrayMap.put("hours_to",info.getBusiness_hours().getTo());//营业时间 （24小时两个都传0）
//        arrayMap.put("category",info.getCategory_id().toString());//商户ID 例如 1,2,3
//        //非必填
//        arrayMap.put("province",info.getProvince_id());
//        arrayMap.put("city",info.getCity_id());
//        arrayMap.put("area",info.getArea_id());
//        arrayMap.put("address",info.getAddress());
//        arrayMap.put("longitude",info.getAddress());
//        arrayMap.put("latitude",info.getAddress());

        if (StringUtils.isEmpty(arrayMap.get("name") + "")) {
            toastMessage("店铺名称不能为空");
            return;
        }
        if (StringUtils.isEmpty(arrayMap.get("week") + "")) {
            toastMessage("营业时间不能为空");
            return;
        }
        if (StringUtils.isEmpty(arrayMap.get("hours_from") + "")) {
            toastMessage("营业时间不能为空");
            return;
        }
        if (StringUtils.isEmpty(arrayMap.get("hours_to") + "")) {
            toastMessage("营业时间不能为空");
            return;
        }
        if (StringUtils.isEmpty(arrayMap.get("category") + "")) {
            toastMessage("经营范围不能为空");
            return;
        }

        Subscriber<HttpResult> sub = new Subscriber<HttpResult>() {
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
            public void onNext(HttpResult s) {
                if (mView != null) {
                    InfoUtil.infoUpdate = true;
                    mView.saveBack(s);
                }

            }
        };

        mView.showLoading();
        AppHttpMethods.getInstance().edit(sub, arrayMap);

    }
}
