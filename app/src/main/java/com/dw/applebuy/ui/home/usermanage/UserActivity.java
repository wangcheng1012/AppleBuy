package com.dw.applebuy.ui.home.usermanage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 会员管理
 */
public class UserActivity extends AppCompatActivity implements Title1Fragment.TitleInterface{

    @BindView(R.id.user_addnumber)
    TextView userAddnumber;
    @BindView(R.id.user_seachtext)
    EditText userSeachtext;
    @BindView(R.id.user_usertoalnumber)
    TextView usetoalnumber;
    @BindView(R.id.user_container)
    FrameLayout userContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.user_seach)
    public void onClick() {

        GoToHelp.go(UserActivity.this, ModifyUserActivity.class);
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("会员管理");
//        right.setVisibility(View.VISIBLE);
//        right.setText("添加");
//        right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                GoToHelp.go(UserActivity.this,AddUserActivity.class);
//                UIHelper.toastMessage(getApplication(),"添加");
//            }
//        });
    }
}
