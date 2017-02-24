package com.dw.applebuy.ui.songjifen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.base.util.CountDownTimerUtils;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.songjifen.p.CreatUserPresenter;
import com.dw.applebuy.ui.songjifen.v.Contracts;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建用户
 */
public class CreatUserActivity extends BaseMvpActivity<Contracts.CreatUserView,CreatUserPresenter> implements Title1Fragment.TitleInterface,Contracts.CreatUserView {

    @BindView(R.id.creart_user_phone)
    EditText phone;
    @BindView(R.id.creart_user_name)
    EditText name;
    @BindView(R.id.creart_user_verify)
    EditText verify;
    @BindView(R.id.pushverify)
    Button pushverify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creart_user);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public CreatUserPresenter initPresenter() {
        return new CreatUserPresenter();
    }

    private void initView() {
        Intent intent = getIntent();
        String phoneStr = intent.getStringExtra("phone");
        phone.setText(phoneStr);
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("创建用户");
    }

    @OnClick({R.id.pushverify, R.id.creart_user_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pushverify:
                presenter.pushverify(phone);
                break;
            case R.id.creart_user_sure:

                if (StringUtils.isEmpty( verify.getText() + "")) {
                    showMessage("验证码不能为空");
                    return;
                }
                Intent intent = getIntent();
                ArrayMap<String, Object> arrayMap = new ArrayMap<>();
                arrayMap.put("integral", intent.getIntExtra("jifen",0));
                arrayMap.put("mobile", phone.getText()+"");
                arrayMap.put("name", name.getText()+"");
                arrayMap.put("code", verify.getText()+"");
//                arrayMap.put("id", null );
                presenter.giveIntegral(arrayMap);

                break;
        }
    }

    @Override
    public void verifyCodeBack() {
        //倒计时
        CountDownTimerUtils countDownTimerUtils = new CountDownTimerUtils(pushverify, 60000, 1000);
        countDownTimerUtils.start();
    }

    @Override
    public void giveIntegralBack() {
        GoToHelp.go(this,GiveSuccessActivity.class,getIntent().getExtras());
    }
}
