package com.dw.applebuy.ui.songjifen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.songjifen.m.InputPhoneUser;
import com.dw.applebuy.ui.songjifen.p.CreatUserPresenter;
import com.dw.applebuy.ui.songjifen.v.Contracts;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.wlj.base.util.GoToHelp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 手机验证成功
 */
public class PhoneVerifySuccessActivity extends BaseMvpActivity<Contracts.CreatUserView,CreatUserPresenter> implements Title1Fragment.TitleInterface,Contracts.CreatUserView {

    @BindView(R.id.verifysuccess_phone)
    EditText phoneView;
    @BindView(R.id.verifysuccess_name)
    EditText nameView;
    private InputPhoneUser inputPhoneUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneverify_success);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public CreatUserPresenter initPresenter() {
        return new CreatUserPresenter();
    }

    private void initView() {
        Intent intent = getIntent();
        inputPhoneUser = intent.getParcelableExtra("InputPhoneUser");
        phoneView.setText(inputPhoneUser.getMobile());
        nameView.setText(inputPhoneUser.getName());
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("验证成功");
    }

    @OnClick(R.id.verifysuccess_sure)
    public void onClick() {
        Intent intent = getIntent();
        ArrayMap<String, Object> arrayMap = new ArrayMap<>();
        arrayMap.put("integral", intent.getIntExtra("jifen",0));
        arrayMap.put("mobile", phoneView.getText()+"");
        arrayMap.put("name", nameView.getText()+"");
//        arrayMap.put("code", verify.getText()+"");
        arrayMap.put("id", inputPhoneUser.getId() );

        presenter.giveIntegral(arrayMap);
    }

    @Override
    public void verifyCodeBack() {
    }

    @Override
    public void giveIntegralBack() {
        GoToHelp.go(this, GiveSuccessActivity.class);
    }
}
