package com.dw.applebuy.ui.home.shoppingmanage.data;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 资料管理
 */
public class DataActivity extends AppCompatActivity implements Title1Fragment.TitleInterface {

    @BindView(R.id.data_shopping)
    TextView dataShopping;
    @BindView(R.id.data_phone)
    TextView dataPhone;
    @BindView(R.id.data_region)
    TextView dataRang;
    @BindView(R.id.data_address)
    TextView dataAddress;
    @BindView(R.id.data_businessScope)
    TextView dataBusinessScope;
    @BindView(R.id.data_businessTime)
    TextView dataBusinessTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        ButterKnife.bind(this);
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("资料管理");
    }

    @OnClick({R.id.data_region, R.id.data_address, R.id.data_businessScope, R.id.data_businessTime, R.id.data_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.data_region:

                break;
            case R.id.data_address:
                break;
            case R.id.data_businessScope:
                break;
            case R.id.data_businessTime:
                break;
            case R.id.data_save:
                break;
        }
    }
}
