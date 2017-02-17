package com.dw.applebuy.ui.home.shoppingmanage.youhui.add;

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
import com.rxmvp.bean.HttpResult;
import com.wlj.base.ui.BaseFragmentActivity;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;

/**
 * 优惠添加-》选择优惠类型
 */
public class YouHuiTypeActivity extends BaseFragmentActivity implements Title1Fragment.TitleInterface {

    public static final String RESULT_YouhuiQuanType = "YouhuiQuanType";
    @BindView(R.id.youhui_content)
    FrameLayout youhuiContent;
    protected SWRVFragment swrvFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_hui_type);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        swrvFragment = new SWRVFragment();
        swrvFragment.setMyInterface(new SWRVFragment.SWRVInterface() {
            @Override
            public void onCreateViewExtract(RecyclerView recyclerview, SwipeRefreshLayout swipeRefreshLayout) {
                YouHuiTypeActivity.this.onCreateViewExtract(recyclerview,swipeRefreshLayout);
            }

            @Override
            public SWRVContract.SWRVPresenterAdapter getPresenterAdapter() {
                return getMyPresenterAdapter();
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.youhui_content, swrvFragment);
        transaction.commitAllowingStateLoss();
    }

    protected void onCreateViewExtract(RecyclerView recyclerview, SwipeRefreshLayout swipeRefreshLayout) {

    }
    protected SWRVContract.SWRVPresenterAdapter getMyPresenterAdapter() {
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
                viewHolder.setText(R.id.item_youhuiquan, item.getName());
            }

            @Override
            public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, YouhuiQuanType item) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, YouhuiQuanType item) {
                Intent intent = new Intent();
                intent.putExtra(RESULT_YouhuiQuanType, item);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public View getEmptyView() {
                return null;
            }

            @Override
            public Observable<List<YouhuiQuanType>> call(FactoryInters apiService, int curPageStart) {
                Observable<HttpResult<List<YouhuiQuanType>>> couponCategory = apiService.getCouponCategory(/*AppConfig.getAppConfig().get(AppConfig.CONF_KEY)*/);
                Observable<List<YouhuiQuanType>> observable = couponCategory.map(new Func1<HttpResult<List<YouhuiQuanType>>, List<YouhuiQuanType>>() {
                    @Override
                    public List<YouhuiQuanType> call(HttpResult<List<YouhuiQuanType>> listHttpStateResult) {
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
