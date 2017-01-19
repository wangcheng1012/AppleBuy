package com.dw.applebuy.ui.home.shoppingmanage.data;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.base.ui.SWRVFragment;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.shoppingmanage.m.ProvinceCityArea;
import com.dw.applebuy.ui.home.shoppingmanage.m.ProvinceCityAreaRequest;
import com.dw.applebuy.ui.home.shoppingmanage.m.YouhuiQuanType;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.add.YouHuiTypeActivity;
import com.orhanobut.logger.Logger;
import com.rxmvp.api.HttpResultFunc;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.ui.BaseFragmentActivity;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.MathUtil;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;

/**
 * 店铺管理 -》资料管理 -》选择区域
 */
public class RegionActivity extends YouHuiTypeActivity {

    public static final String RESULT_Region = "Region";

    public static final int  call_province = 1;
    private static final int  call_city = 2;
    private static final int  call_area = 3;

    private int  curCall = call_province;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

          intent = getIntent();
          curCall = intent.getIntExtra("curCall", call_province);

    }

    protected SWRVContract.SWRVPresenterAdapter<ProvinceCityArea> getMyPresenterAdapter() {
        return new SWRVContract.SWRVPresenterAdapter<ProvinceCityArea>() {
            @Override
            public int getRecycerviewItemlayoutRes() {
                return R.layout.item_youhuiquan;
            }

            @Override
            public RecyclerView.LayoutManager getLayoutManager() {
                return new LinearLayoutManager(getApplicationContext());
            }

            @Override
            public void convert(ViewHolder viewHolder, ProvinceCityArea item, int position) {
                viewHolder.setText(R.id.item_youhuiquan,item.getName());
            }

            @Override
            public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, ProvinceCityArea item) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, ProvinceCityArea item) {

//                Intent intent = getIntent();
//                int curCall = intent.getIntExtra("curCall", call_province);
                switch (curCall){
                    case call_province:
                        Intent intent1 = new Intent();
                        intent1.putExtra("province",item);
                        intent1.putExtra("curCall", call_city);
                        intent1.setClass(getApplicationContext(),RegionActivity.class);
                        startActivityForResult(intent1,call_city);
//                        swrvFragment.getPresenter().onRefresh();
                        break;
                    case call_city:
                        Intent intent2 = new Intent();
                        intent2.putExtra("city",item);
                        intent2.putExtras(intent);
                        intent2.putExtra("curCall", call_area);
                        intent2.setClass(getApplicationContext(),RegionActivity.class);
                        startActivityForResult(intent2,call_area);
//                        swrvFragment.getPresenter().onRefresh();
                        break;
                    case call_area:

//                        Intent intent3 = new Intent();
//                        intent.putExtras(intent);
                        intent.setClass(getApplicationContext(),DataActivity.class);
                        intent.putExtra("area",item);
                        intent.putExtra(RESULT_Region,  item);
                        setResult(RESULT_OK,intent);
                        finish();
                        break;
                }

            }

            @Override
            public View getEmptyView() {
                return null;
            }

            @Override
            public Observable<List<ProvinceCityArea>> call(FactoryInters apiService) {
                String url = "app/common/getProvinces";
                ProvinceCityAreaRequest request = new ProvinceCityAreaRequest();
//                Intent intent = getIntent();
//                int curCall = intent.getIntExtra("curCall", call_province);

                switch (curCall){
                    case call_province:
//                        url = "app/common/getProvinces";
//                        request = new ProvinceCityAreaRequest();
                        break;
                    case call_city:
                        url = "app/common/getCitiesByProvinceId";
                        ProvinceCityArea province = intent.getParcelableExtra("province");
                        request.setProvince_id(MathUtil.parseInteger(province.getId()));
                        break;
                    case call_area:
                        url = "app/common/getAreaByCityId";
                        ProvinceCityArea city = intent.getParcelableExtra("city");
                        request.setCity_id(MathUtil.parseInteger(city.getId()));
                        break;
                }

                Observable<HttpStateResult<List<ProvinceCityArea>>> couponCategory = apiService.getProvinceCityArea(url, request.getProvince_id(),request.getCity_id());

                Observable<List<ProvinceCityArea>> observable = couponCategory.map(new HttpResultFunc<List<ProvinceCityArea>>() );
                return observable;
            }

            @Override
            public boolean needLoadMore() {
                return false;
            }
        };
    }

    @Override
    public void setTitle(TextView title, TextView right) {


        title.setText("优惠类型");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Logger.i(requestCode+"  " + resultCode  +"  "  + data );
        setResult(RESULT_OK,data);
        finish();

    }
}
