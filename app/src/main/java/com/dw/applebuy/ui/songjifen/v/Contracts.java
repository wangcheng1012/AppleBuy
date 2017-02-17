package com.dw.applebuy.ui.songjifen.v;

import com.dw.applebuy.ui.songjifen.m.VerifyUser;
import com.rxmvp.basemvp.BaseView;

/**
 *
 */
public interface Contracts {

    interface InputPhoneView extends BaseView {


        void verifyPhoneBack(VerifyUser verifyUser);
    }
    interface CreatUserView extends BaseView {


        void verifyCodeBack();

        void giveIntegralBack();
    }

}
