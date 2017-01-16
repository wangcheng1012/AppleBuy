package com.dw.applebuy.ui.home.shoppingmanage.youhui.add;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.wlj.base.ui.BaseFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 优惠时间
 */
public class YouHuiTimeActivity extends BaseFragmentActivity {

    @BindView(R.id.youhuitime_day)
    TextView youhuitimeDay;
    @BindView(R.id.youhuitime_time)
    TextView youhuitimeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhui_time);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.youhuitime_day, R.id.youhuitime_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.youhuitime_day:
                break;
            case R.id.youhuitime_time:
                break;
        }
    }
}
