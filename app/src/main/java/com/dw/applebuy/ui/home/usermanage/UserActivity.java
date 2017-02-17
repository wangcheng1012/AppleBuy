package com.dw.applebuy.ui.home.usermanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.usermanage.m.UserList;
import com.wlj.base.ui.BaseFragmentActivity;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.StringUtils;
import com.wlj.base.util.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 会员管理
 */
public class UserActivity extends BaseFragmentActivity implements Title1Fragment.TitleInterface{

    @BindView(R.id.user_addnumber)
    TextView userAddnumber;
    @BindView(R.id.user_seachtext)
    EditText userSeachtext;
    @BindView(R.id.user_usertoalnumber)
    TextView usetoalnumber;
    @BindView(R.id.user_container)
    FrameLayout userContainer;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        userFragment = UserFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.user_container,userFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @OnClick(R.id.user_seach)
    public void onClick() {
        String search = userSeachtext.getText() + "";
        if (StringUtils.isEmpty(search)) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("search", search);
        GoToHelp.go(this,SearchUserActivity.class,bundle);
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

    public void changeShow(UserList.InfoBean info) {

        userAddnumber.setText("+"+info.getMonth_num());

        String format = String.format("共有 %s 个会员", info.getMember_sum());
        SpannableStringBuilder  builder = new SpannableStringBuilder(format);

        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.yellow_FF552E));
        builder.setSpan(span,3,format.indexOf(" 个会员"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        usetoalnumber.setText(builder);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        userFragment.onActivityResult(requestCode,resultCode,data);
    }
}
