package com.dw.applebuy.ui.home.shoppingmanage.data;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.been.Info;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.renzheng.p.InfoUtil;
import com.dw.applebuy.ui.home.shoppingmanage.data.child.BusinessScopeActivity;
import com.dw.applebuy.ui.home.shoppingmanage.data.child.AddBusinessTimeActivity;
import com.dw.applebuy.ui.home.shoppingmanage.data.child.MapActivity;
import com.dw.applebuy.ui.home.shoppingmanage.data.child.RegionActivity;
import com.dw.applebuy.ui.home.shoppingmanage.m.BusinessScope;
import com.dw.applebuy.ui.home.shoppingmanage.m.ProvinceCityArea;
import com.dw.applebuy.ui.home.shoppingmanage.p.DataPresenter;
import com.dw.applebuy.ui.home.shoppingmanage.v.Contract;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.rxmvp.bean.HttpResult;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 资料管理
 */
public class DataActivity extends BaseMvpActivity<Contract.DataView, DataPresenter> implements Title1Fragment.TitleInterface, Contract.DataView {

    @BindView(R.id.data_shopping)
    EditText dataShopping;
    @BindView(R.id.data_phone)
    TextView dataPhone;
    @BindView(R.id.data_region)
    TextView dataRang;
    @BindView(R.id.data_address)
    EditText dataAddress;
    @BindView(R.id.data_businessScope)
    TextView dataBusinessScope;
    @BindView(R.id.data_businessTime)
    TextView dataBusinessTime;

    private int addressrequiestcode = 21;
    private Info info;
    private int businessScopeRequiestcode = 22;
    private int businessTimeRequiestcode = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    public DataPresenter initPresenter() {
        return new DataPresenter();
    }

    private void initView() {

        InfoUtil.getInstall().getInfo(this, new InfoUtil.InfoBack() {
            @Override
            public void back(Info info) {
                DataActivity.this.info = info;
                dataShopping.setText(info.getName());
                dataPhone.setText(info.getMobile());
                dataRang.setText(info.getProvince() + " " + info.getCity() + " " + info.getArea());
                dataAddress.setText(info.getAddress());
                dataBusinessScope.setText((info.getCategory() == null?"":info.getCategory() + "").replace("[", "").replace("]", ""));

                String weekShow = info.getBusiness_weekShow( );
                dataBusinessTime.setText(info.getBusiness_hoursShow() + (StringUtils.isEmpty(weekShow) ? "" : "(" + weekShow + ")"));

            }
        });

    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("资料管理");
    }

    @OnClick({R.id.data_region, R.id.data_address_location, R.id.data_businessScope, R.id.data_businessTime, R.id.data_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.data_region:
                GoToHelp.goResult(this, RegionActivity.class, RegionActivity.call_province);
                break;
            case R.id.data_address_location:
                GoToHelp.goResult(this, MapActivity.class, addressrequiestcode);
                break;
            case R.id.data_businessScope:

                List<String> category_id = info.getCategory_id();

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("businessScopes", (ArrayList<String>) category_id);

                GoToHelp.goResult(this, BusinessScopeActivity.class, businessScopeRequiestcode, bundle);
                break;
            case R.id.data_businessTime:
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("info", info);
                GoToHelp.goResult(this, AddBusinessTimeActivity.class, businessTimeRequiestcode, bundle1);
                break;
            case R.id.data_save:
                ArrayMap<String, Object> arrayMap = new ArrayMap<String, Object>();
                //必填
                arrayMap.put("name", dataShopping.getText() + "");
                arrayMap.put("week", (info.getBusiness_week() + "").replace("[", "").replace("]", "").replace(" ", ""));//周（0到6）代表（周日到周六）多个用逗号隔开
                arrayMap.put("hours_from", info.getBusiness_hours().getFrom());
                arrayMap.put("hours_to", info.getBusiness_hours().getTo());//营业时间 （24小时两个都传0）
                arrayMap.put("category", (info.getCategory_id() + "").replace("[", "").replace("]", "").replace(" ", ""));//商户ID 例如 1,2,3
                //非必填
                arrayMap.put("province", info.getProvince_id());
                arrayMap.put("city", info.getCity_id());
                arrayMap.put("area", info.getArea_id());
                arrayMap.put("address", info.getAddress());
//                arrayMap.put("longitude", info.getAddress());
//                arrayMap.put("latitude", info.getAddress());

                presenter.save(arrayMap);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) return;

        if (RegionActivity.call_province == requestCode) {
            ProvinceCityArea province = data.getParcelableExtra("province");
            ProvinceCityArea city = data.getParcelableExtra("city");
            ProvinceCityArea area = data.getParcelableExtra("area");

            dataRang.setText(province.getName() + " " + city.getName() + " " + area.getName());
        } else if (addressrequiestcode == requestCode) {
            String address = data.getStringExtra("address");
            dataAddress.setText(address);
        } else if (businessScopeRequiestcode == requestCode) {
            //经营范围 选择返回
            ArrayList<BusinessScope> businessScopes = data.getParcelableArrayListExtra("businessScopes");

            List<String> category = new ArrayList<>();
            List<String> category_id = new ArrayList<>();

            for (int i = 0; i < businessScopes.size(); i++) {
                BusinessScope businessScope = businessScopes.get(i);
                boolean selected = businessScope.getSelected();
                if (selected) {
                    String id = businessScope.getId();
                    String name = businessScope.getName();

                    category_id.add(id);
                    category.add(name);
                }

            }
            info.setCategory(category);
            info.setCategory_id(category_id);

            dataBusinessScope.setText((info.getCategory() + "").replace("[", "").replace("]", ""));

        } else if (businessTimeRequiestcode == requestCode) {
            //营业时间
            ArrayList<String> business_week_id = data.getStringArrayListExtra("business_week_id");
            String time = data.getStringExtra("time");

            if (info == null) {
                return;
            }

            if ( business_week_id != null) {
                info.setBusiness_week(business_week_id);
            }
            if (time != null) {
                String[] split = time.split("-");
                Info.BusinessHoursBean business_hours = info.getBusiness_hours();
                business_hours.setFrom(split[0]);
                business_hours.setTo(split[1]);
            }
            String weekShow = info.getBusiness_weekShow( );
            dataBusinessTime.setText(info.getBusiness_hoursShow() + (StringUtils.isEmpty(weekShow) ? "" : "(" + weekShow + ")"));
        }

//        Logger.d(requestCode+"  " + resultCode  +"  "  + data );
    }

    @Override
    public void saveBack(HttpResult s) {
        showMessage(s.getMessage());
        setResult(RESULT_OK);
        finish();
    }
}
