package com.dw.applebuy.ui.home.usermanage.p;

import android.widget.EditText;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.ui.home.usermanage.m.UserList;
import com.dw.applebuy.ui.home.usermanage.v.Contract;
import com.rxmvp.basemvp.BasePresenter;
import com.rxmvp.bean.HttpResult;
import com.wlj.base.util.StringUtils;

import rx.Subscriber;

/**
 * Created by wlj on 2017/2/15.
 */
public class ModifyUserPresenter extends BasePresenter<Contract.ModifyUserView> {

    public void saveRemark(UserList.ListBean mListBean, EditText modifyuserMark) {

        String text = modifyuserMark.getText() + "";

        if (StringUtils.isEmpty(text)) {
            toastMessage("备注不能为空");
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
                    mView.saveRemarkBack(s);
                }

            }
        };

        mView.showLoading();
        AppHttpMethods.getInstance().saveRemark(sub, mListBean.getMember_id(),text);

    }
}
