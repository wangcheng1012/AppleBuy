package com.dw.applebuy.ui.home.shoppingmanage.data;

import android.content.Intent;
import android.os.Bundle;
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
import com.dw.applebuy.ui.home.shoppingmanage.m.YouhuiQuanType;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.add.YouHuiTypeActivity;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.ui.BaseFragmentActivity;
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


    protected SWRVContract.SWRVPresenterAdapter<YouhuiQuanType> getMyPresenterAdapter() {
        return new SWRVContract.SWRVPresenterAdapter<YouhuiQuanType>() {
            @Override
            public int getRecycerviewItemlayoutRes() {
                return R.layout.item_youhuiquan;
            }

            @Override
            public RecyclerView.LayoutManager getLayoutManager() {
                return new LinearLayoutManager(getApplicationContext());
            }

            @Override
            public void convert(ViewHolder viewHolder, YouhuiQuanType item, int position) {
                viewHolder.setText(R.id.item_youhuiquan,item.getName());
            }

            @Override
            public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, YouhuiQuanType item) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, YouhuiQuanType item) {
                Intent intent = new Intent();
                intent.putExtra(RESULT_Region,  item);
                setResult(RESULT_OK,intent);
                finish();
            }

            @Override
            public View getEmptyView() {
                return null;
            }

            @Override
            public Observable<List<YouhuiQuanType>> call(FactoryInters apiService) {
                Observable<HttpStateResult<List<YouhuiQuanType>>> couponCategory = apiService.getCouponCategory(/*AppConfig.getAppConfig().get(AppConfig.CONF_KEY)*/);
                Observable<List<YouhuiQuanType>> observable = couponCategory.map(new Func1<HttpStateResult<List<YouhuiQuanType>>, List<YouhuiQuanType>>() {
                    @Override
                    public List<YouhuiQuanType> call(HttpStateResult<List<YouhuiQuanType>> listHttpStateResult) {
                        List<YouhuiQuanType> data = listHttpStateResult.getData();
                        return data;
                    }
                });
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

}
