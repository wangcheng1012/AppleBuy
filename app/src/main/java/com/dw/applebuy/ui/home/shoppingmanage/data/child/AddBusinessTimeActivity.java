package com.dw.applebuy.ui.home.shoppingmanage.data.child;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.been.Info;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.shoppingmanage.m.BusinessScope;
import com.dw.applebuy.util.ChooseStartEndTimeDialogFragment;
import com.wlj.base.ui.BaseFragmentActivity;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加营业时间
 */
public class AddBusinessTimeActivity extends BaseFragmentActivity implements Title1Fragment.TitleInterface, ChooseStartEndTimeDialogFragment.TimeChoosed {

    @BindView(R.id.businesstime_day)
    TextView businesstimeDay;
    @BindView(R.id.businesstime_time)
    TextView businesstimeTime;
    private Info info;
    private int businesstimeDay_RequsetCode = 31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_time);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        info = (Info) getIntent().getSerializableExtra("info");
        businesstimeDay.setText(info.getBusiness_weekShow( ));
        businesstimeTime.setText(info.getBusiness_hoursShow());

        businesstimeDay.setTag(info.getBusiness_week());
        Info.BusinessHoursBean hours = info.getBusiness_hours();
        businesstimeTime.setTag(hours.getFrom()+"-"+hours.getTo());

    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("添加营业时间");
    }

    @OnClick({R.id.businesstime_day, R.id.businesstime_time, R.id.data_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.businesstime_day:
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("business_week_id", (ArrayList<String>) businesstimeDay.getTag());
                GoToHelp.goResult(this, ChooseWeekActivity.class, businesstimeDay_RequsetCode, bundle);

                break;
            case R.id.businesstime_time:
                ChooseStartEndTimeDialogFragment dialogFragment = new ChooseStartEndTimeDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), getClass().getSimpleName());
                break;
            case R.id.data_save:

                if(businesstimeDay.getTag()== null){
                    UIHelper.toastMessage(this,"请选择每周营业日");
                    return;
                }
                if(businesstimeTime.getTag()== null){
                    UIHelper.toastMessage(this,"请选择营业时间");
                    return;
                }

                Intent intent = new Intent();
                intent.putStringArrayListExtra("business_week_id", (ArrayList<String>)businesstimeDay.getTag() );
                intent.putExtra("time",  businesstimeTime.getTag()+"");
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        if (requestCode == businesstimeDay_RequsetCode && data != null) {
            ArrayList<String> business_week_id =  data.getStringArrayListExtra("business_week_id");

            info.setBusiness_week(business_week_id);
            businesstimeDay.setText(info.getBusiness_weekShow());
            businesstimeDay.setTag(business_week_id);
        }

    }

    @Override
    public void choosedBack(String time) {
        if("00:00-00:00".equals(time)){
            businesstimeTime.setText("全天24小时");
            businesstimeTime.setTag("0-0");
        }else {
            businesstimeTime.setText(time);
            businesstimeTime.setTag(time);
        }
    }
}
