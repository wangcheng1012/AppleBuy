package com.dw.applebuy.ui.home.shoppingmanage.data;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.shoppingmanage.data.child.BusinessScopeActivity;
import com.dw.applebuy.ui.home.shoppingmanage.data.child.BusinessTimeActivity;
import com.dw.applebuy.ui.home.shoppingmanage.data.child.ChooseWeekActivity;
import com.dw.applebuy.ui.home.shoppingmanage.data.child.MapActivity;
import com.dw.applebuy.ui.home.shoppingmanage.data.child.RegionActivity;
import com.dw.applebuy.ui.home.shoppingmanage.m.ProvinceCityArea;
import com.wlj.base.ui.BaseFragmentActivity;
import com.wlj.base.util.GoToHelp;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 资料管理
 */
public class DataActivity extends BaseFragmentActivity implements Title1Fragment.TitleInterface {

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
    private int addressrequiestcode = 21;

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
                GoToHelp.goResult(this,RegionActivity.class,RegionActivity.call_province);
                break;
            case R.id.data_address:
                GoToHelp.goResult(this,MapActivity.class,addressrequiestcode);
                break;
            case R.id.data_businessScope:

                ArrayList<Integer> businessScopes = new ArrayList<>();
                businessScopes.add(1);
                businessScopes.add(2);
                businessScopes.add(3);
                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList("businessScopes",businessScopes);

                GoToHelp.goResult(this,BusinessScopeActivity.class,addressrequiestcode,bundle);
                break;
            case R.id.data_businessTime:
                GoToHelp.goResult(this,BusinessTimeActivity.class,addressrequiestcode);
                break;
            case R.id.data_save:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==  RESULT_OK && data != null){

            if(RegionActivity.call_province == requestCode) {
                ProvinceCityArea province = data.getParcelableExtra("province");
                ProvinceCityArea city = data.getParcelableExtra("city");
                ProvinceCityArea area = data.getParcelableExtra("area");
                dataRang.setText(province.getName() + " " + city.getName() + " " + area.getName());
            }else if (addressrequiestcode == requestCode){
                String address = data.getStringExtra("address");
                dataAddress.setText(address);
            }
        }
//        Logger.d(requestCode+"  " + resultCode  +"  "  + data );
    }
}
