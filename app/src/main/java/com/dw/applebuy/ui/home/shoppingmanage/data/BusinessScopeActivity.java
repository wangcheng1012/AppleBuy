package com.dw.applebuy.ui.home.shoppingmanage.data;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.ui.home.shoppingmanage.m.ProvinceCityArea;
import com.dw.applebuy.ui.home.shoppingmanage.m.ProvinceCityAreaRequest;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.add.YouHuiTypeActivity;
import com.orhanobut.logger.Logger;
import com.rxmvp.api.HttpResultFunc;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.util.MathUtil;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import rx.Observable;

/**
 * 店铺管理 -》资料管理 -》经营范围
 *
 */
public class BusinessScopeActivity extends YouHuiTypeActivity {

    public static final String RESULT_BusinessScope = "BusinessScope";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



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

            }

            @Override
            public View getEmptyView() {
                return null;
            }

            @Override
            public Observable<List<ProvinceCityArea>> call(FactoryInters apiService) {
                String url = "app/common/getProvinces";
                ProvinceCityAreaRequest request = new ProvinceCityAreaRequest();

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
