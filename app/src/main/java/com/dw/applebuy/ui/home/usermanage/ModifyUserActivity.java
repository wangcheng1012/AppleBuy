package com.dw.applebuy.ui.home.usermanage;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.usermanage.m.UserList;
import com.dw.applebuy.ui.home.usermanage.p.ModifyUserPresenter;
import com.dw.applebuy.ui.home.usermanage.v.Contract;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.rxmvp.bean.HttpResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyUserActivity extends BaseMvpActivity<Contract.ModifyUserView, ModifyUserPresenter> implements Title1Fragment.TitleInterface, Contract.ModifyUserView {

    @BindView(R.id.modifyuser_name)
    AutoCompleteTextView modifyuserName;
    @BindView(R.id.modifyuser_phone)
    AutoCompleteTextView modifyuserPhone;
    @BindView(R.id.modifyuser_mark)
    EditText modifyuserMark;

    UserList.ListBean mListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user);
        ButterKnife.bind(this);

        initView();

    }

    @Override
    public ModifyUserPresenter initPresenter() {
        return new ModifyUserPresenter();
    }

    private void initView() {
        mListBean = getIntent().getParcelableExtra("ListBean");
        modifyuserName.setText(mListBean.getName());
        modifyuserPhone.setText(mListBean.getMobile());
        modifyuserMark.setText(mListBean.getName_remark());
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("编辑会员");
    }

    @OnClick(R.id.modifyuser_submit)
    public void onClick() {
        presenter.saveRemark(mListBean, modifyuserMark);
    }

    @Override
    public void saveRemarkBack(HttpResult s) {
        setResult(RESULT_OK);
        finish();
    }
}
