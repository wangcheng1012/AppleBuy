package com.dw.applebuy.ui.home.shoppingmanage.v;

import com.dw.applebuy.been.ImageBean;
import com.dw.applebuy.ui.home.shoppingmanage.m.UploadCoverImg;
import com.dw.applebuy.ui.home.shoppingmanage.m.Coupon;
import com.rxmvp.basemvp.BaseView;
import com.rxmvp.bean.HttpResult;

import java.util.List;

/**
 *
 */
public interface Contract {

    /**
     * 添加优惠
     */
    interface YouHuiAddView extends BaseView {

        void callBack();
    }

    /**
     * 相册管理
     */
    interface UpLoadMoreView extends BaseView {

        void delBack(HttpResult httpResult);

        void uploadDetailsBack();
    }

    /**
     * 资料管理
     */
    interface DataView extends BaseView {

        void saveBack(HttpResult s);
    }

    /**
     * 资料管理
     */
    interface TabLayoutView extends BaseView {


        void submitBack();

        void delBack();

        /**
         * 优惠券 详情返回
         *
         * @param coupon
         */
        void couponBack(Coupon coupon);

        void offShelfCouponBack(HttpResult hsr);

        void shelvesCouponBack(HttpResult hsr);

    }

    interface UpLoadImageView extends BaseView {


        void uploadCoverBack(UploadCoverImg uploadCoverImg);

        void uploadDetailsBack(List<ImageBean> list);
    }

}
