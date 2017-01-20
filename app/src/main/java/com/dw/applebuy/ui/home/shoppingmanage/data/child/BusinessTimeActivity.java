package com.dw.applebuy.ui.home.shoppingmanage.data.child;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.util.ChooseStartEndTimeDialogFragment;
import com.dw.applebuy.util.DayDialogFragment;
import com.wlj.base.ui.BaseFragmentActivity;
import com.wlj.base.util.GoToHelp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 营业时间
 */
public class BusinessTimeActivity extends BaseFragmentActivity implements Title1Fragment.TitleInterface,ChooseStartEndTimeDialogFragment.TimeChoosed {

    @BindView(R.id.businesstime_day)
    TextView businesstimeDay;
    @BindView(R.id.businesstime_time)
    TextView businesstimeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_time);
        ButterKnife.bind(this);
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("添加营业时间");
    }

    @OnClick({R.id.businesstime_day, R.id.businesstime_time, R.id.data_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.businesstime_day:
                GoToHelp.go(this,ChooseWeekActivity.class);
                break;
            case R.id.businesstime_time:
                ChooseStartEndTimeDialogFragment dialogFragment = new ChooseStartEndTimeDialogFragment();
                dialogFragment.show(getSupportFragmentManager() ,getClass().getSimpleName());
                break;
            case R.id.data_save:



                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    @Override
    public void choosedBack(String time) {
        businesstimeTime.setText(time);
    }
}
