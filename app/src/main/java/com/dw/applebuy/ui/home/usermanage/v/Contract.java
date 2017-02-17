package com.dw.applebuy.ui.home.usermanage.v;

import com.rxmvp.basemvp.BaseView;
import com.rxmvp.bean.HttpResult;

public interface Contract {
    /**
     * 添加优惠
     */
    interface ModifyUserView extends BaseView {

        void saveRemarkBack(HttpResult s);
    }

}
