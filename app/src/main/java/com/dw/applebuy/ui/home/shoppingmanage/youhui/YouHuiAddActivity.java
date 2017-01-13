package com.dw.applebuy.ui.home.shoppingmanage.youhui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.wlj.base.util.GoToHelp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加优惠券
 */
public class YouHuiAddActivity extends AppCompatActivity implements Title1Fragment.TitleInterface {

    @BindView(R.id.youhuiadd_type)
    TextView youhuiaddType;
    @BindView(R.id.youhuiadd_time)
    TextView youhuiaddTime;
    @BindView(R.id.youhuiadd_title)
    EditText youhuiaddTitle;
    @BindView(R.id.youhuiadd_intro)
    EditText youhuiaddIntro;
    @BindView(R.id.youhuiadd_price)
    EditText youhuiaddPrice;
    @BindView(R.id.youhuiadd_number)
    EditText youhuiaddNumber;
    @BindView(R.id.youhuiadd_complate)
    Button youhuiaddComplate;
    @BindView(R.id.activity_you_hui_add)
    LinearLayout activityYouHuiAdd;
    private final int requestCode = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_hui_add);
        ButterKnife.bind(this);

    }

    @Override
    public void setTitle(TextView title) {
        title.setText("新增优惠");
    }

    @OnClick({R.id.youhuiadd_type, R.id.youhuiadd_time, R.id.youhuiadd_complate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.youhuiadd_type:
                GoToHelp.goResult(this,YouHuiTypeActivity.class,requestCode);
                break;
            case R.id.youhuiadd_time:
                break;
            case R.id.youhuiadd_complate:
                break;
        }
    }
}
