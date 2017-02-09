package com.dw.applebuy.ui.home.shoppingmanage.v;

import com.rxmvp.basemvp.BaseView;
import com.rxmvp.bean.HttpStateResult;

/**
 *
 */
public interface Contract {

    /**
     * 添加优惠
     */
    interface YouHuiAddView extends BaseView {

    }

    /**
     * 相册管理
     */
    interface AlbumView extends BaseView {

    }
    /**
     * 资料管理
     */
    interface DataView extends BaseView {

        void saveBack(HttpStateResult s);
    }
    /**
     * 资料管理
     */
    interface TabLayoutView extends BaseView {


        void submitBack();

        void delBack();
    }


}
