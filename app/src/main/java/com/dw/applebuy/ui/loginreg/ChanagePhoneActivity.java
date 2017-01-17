package com.dw.applebuy.ui.loginreg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.loginreg.p.ChanagePhonePresenter;
import com.dw.applebuy.ui.loginreg.v.Views;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.rxmvp.bean.HttpStateResult;

import java.util.List;

/**
 * 更换手机号码
 */
public class ChanagePhoneActivity extends BaseMvpActivity<Views.ChanagePhoneView,ChanagePhonePresenter> implements Views.ChanagePhoneView,Title1Fragment.TitleInterface  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chanage_phone);
    }

    @Override
    public ChanagePhonePresenter initPresenter() {
        return new ChanagePhonePresenter();
    }

    @Override
    public void submitBack(HttpStateResult<List> stringHttpStateResult) {

    }

    @Override
    public void verifyCodeBack() {

    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("更换手机");
    }
}
