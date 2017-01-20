package com.dw.applebuy.ui.home.ordermanage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.base.ui.SWRVFragment;
import com.dw.applebuy.ui.home.ordermanage.m.CouponOrder;
import com.dw.applebuy.ui.home.ordermanage.p.OrderListPresenter;
import com.dw.applebuy.ui.home.ordermanage.v.Contract;
import com.rxmvp.api.HttpResultFunc;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.util.DpAndPx;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;


public class OrderListActivity extends BaseMvpActivity<Contract.OrderListView, OrderListPresenter> implements Contract.OrderListView {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_rightTv)
    TextView titleRightTv;
    @BindView(R.id.order_toolbar)
    Toolbar toolbar;
    @BindView(R.id.order_tablayout)
    TabLayout tablayout;
    @BindView(R.id.order_viewpager)
    ViewPager viewpage;

    String[] tabtitles = {"全部","未消费","已消费","已完成"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        initTitle();

        tablayoutViewPager();

//        View recyclerView = findViewById(R.id.item_list);
//        assert recyclerView != null;

    }

    private void tablayoutViewPager() {

        splitLine(tablayout);
        //Adapter
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return tabtitles.length;
            }

            @Override
            public Fragment getItem(int position) {
                SWRVFragment  swrvFragment = getItemFragment(position);
                return swrvFragment;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabtitles[position];
            }
        };
        viewpage.setAdapter(pagerAdapter);
        tablayout.setupWithViewPager(viewpage);
    }

    private SWRVFragment getItemFragment(int position) {
        SWRVFragment mSWRVFragment = new SWRVFragment();
        mSWRVFragment.setMyInterface(new SWRVFragment.SWRVInterface() {
            @Override
            public void onCreateViewExtract(RecyclerView recyclerview, SwipeRefreshLayout swipeRefreshLayout) {

            }

            @Override
            public SWRVContract.SWRVPresenterAdapter getPresenterAdapter() {
                return getMyPresenterAdapter();
            }
        });

        return mSWRVFragment;
    }

    private SWRVContract.SWRVPresenterAdapter getMyPresenterAdapter() {
        return new SWRVContract.SWRVPresenterAdapter<CouponOrder>() {

            @Override
            public int getRecycerviewItemlayoutRes() {
                return R.layout.item_order_list;
            }

            @Override
            public RecyclerView.LayoutManager getLayoutManager() {
                return new LinearLayoutManager(getApplicationContext());
            }

            @Override
            public void convert(ViewHolder viewHolder, CouponOrder item, int position) {

            }

            @Override
            public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, CouponOrder item) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, CouponOrder item) {

            }

            @Override
            public View getEmptyView() {
                return null;
            }

            @Override
            public Observable<List<CouponOrder>> call(FactoryInters apiService, int curPageStart) {
                Observable<HttpStateResult<List<CouponOrder>>> couponOrder = apiService.getCouponOrder(0, curPageStart, 0);
                Observable<List<CouponOrder>> map = couponOrder.map(new HttpResultFunc<List<CouponOrder>>());
                return map;
            }

            @Override
            public boolean needLoadMore() {
                return true;
            }
        };
    }

    /**
     * 分割线
     * @param tablayout
     */
    private  void splitLine(TabLayout tablayout) {
        //分割线
        LinearLayout child = (LinearLayout) tablayout.getChildAt(0);
        child.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        child.setDividerDrawable( getResources().getDrawable(R.drawable.shape_divider));
        child.setDividerPadding(DpAndPx.dpToPx(getApplicationContext(), 15));
    }

    private void initTitle() {
        //        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        titleName.setText(getTitle());
        titleBack.setVisibility(View.VISIBLE);
    }

    @Override
    public OrderListPresenter initPresenter() {
        return new OrderListPresenter();
    }

    @OnClick(R.id.title_back)
    public void onClick() {
        finish();

    }


}
