package com.dw.applebuy.ui.home.hexiao.v;

import com.rxmvp.basemvp.BaseView;
import com.rxmvp.bean.HttpResult;

/**
 *
 */
public interface Contract {

    /**
     * 订单
     */
    interface OrderDetail2View extends BaseView {

        void useCouponBack(HttpResult httpResult);
    }



}
