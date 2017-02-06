package com.dw.applebuy.ui.songjifen.v;

import com.dw.applebuy.ui.songjifen.m.InputPhoneUser;
import com.rxmvp.basemvp.BaseView;

/**
 *
 */
public interface Contracts {

    interface InputPhoneView extends BaseView {


        void verifyPhoneBack(InputPhoneUser inputPhoneUser);
    }

}
