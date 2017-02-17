package com.dw.applebuy.ui.home.usermanage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.wlj.base.ui.BaseFragmentActivity;

public class SearchUserActivity extends BaseFragmentActivity implements Title1Fragment.TitleInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("搜索结果");
    }
}
