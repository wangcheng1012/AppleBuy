package com.dw.applebuy.ui.set.v;

import com.dw.applebuy.ui.set.m.AboutUsModel;
import com.rxmvp.basemvp.BaseView;

/**
 *
 */
public interface Contracts {

    interface AboutUsView extends BaseView {

        void aboutUs(AboutUsModel aboutUsModel, boolean aboutUsClick);
    }

}
