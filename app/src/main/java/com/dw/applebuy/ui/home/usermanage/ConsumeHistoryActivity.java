package com.dw.applebuy.ui.home.usermanage;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;

/**
 *  获取会员消费记录[积分兑换记录]
 */
public class ConsumeHistoryActivity extends AppCompatActivity implements Title1Fragment.TitleInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume_history);

//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.user_container,ConsumeHistoryFragment.newInstance());
//        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("消费记录");
    }
}
