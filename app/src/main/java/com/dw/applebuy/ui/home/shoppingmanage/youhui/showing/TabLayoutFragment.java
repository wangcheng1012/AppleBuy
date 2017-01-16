package com.dw.applebuy.ui.home.shoppingmanage.youhui.showing;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dw.applebuy.BuildConfig;
import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.base.ui.SWRVFragment;
import com.dw.applebuy.been.ResultData;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.showing.m.Coupon;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.bean.Base;
import com.wlj.base.util.AppConfig;
import com.wlj.base.util.DpAndPx;
import com.wlj.base.web.asyn.BaseAsyncModle;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;

/**
 */
public class TabLayoutFragment extends Fragment {

    private static final String TAB = "tab";
    public static int SHOEING = 2;
    public static int XIAJIA = 3;

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpage)
    ViewPager viewpage;
    private String[] tabtitles = {"添加时间", "销量", "库存"};

    private int mTab;

    public TabLayoutFragment() {
        // Required empty public constructor
    }

    public static TabLayoutFragment newInstance(int tab) {
        TabLayoutFragment fragment = new TabLayoutFragment();
        Bundle args = new Bundle();
        args.putInt(TAB, tab);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTab = getArguments().getInt(TAB, SHOEING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tablayout, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

        //分割线
        LinearLayout child = (LinearLayout) tablayout.getChildAt(0);
        child.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        child.setDividerDrawable(getActivity().getResources().getDrawable(R.drawable.shape_divider));
        child.setDividerPadding(DpAndPx.dpToPx(getContext(),15));
        //Adapter
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return tabtitles.length;
            }

            @Override
            public Fragment getItem(int position) {
                SWRVFragment<Coupon> swrvFragment = getItemFragment(position);
                return  swrvFragment;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabtitles[position];
            }
        };
        viewpage.setAdapter(pagerAdapter);
        tablayout.setupWithViewPager(viewpage);
    }

    @NonNull
    private SWRVFragment<Coupon> getItemFragment(int position) {
        switch (position) {
            case 0://添加时间
                break;
            case 1://销量
                break;
            case 2://库存
                break;
        }

        SWRVFragment<Coupon> swrvFragment = new SWRVFragment<>();
        swrvFragment.setMyInterface(new SWRVFragment.SWRVInterface<Coupon>() {
            @Override
            public void onCreateViewExtract(RecyclerView recyclerview, SwipeRefreshLayout swipeRefreshLayout) {
            }
            @Override
            public SWRVContract.SWRVPresenterAdapter<Coupon> getPresenterAdapter() {
                return getSwrvPresenterAdapter();
            }
        });
        return swrvFragment;
    }

    /**
     *  SwrvPresenterAdapter
     * @return
     */
    @NonNull
    private SWRVContract.SWRVPresenterAdapter<Coupon> getSwrvPresenterAdapter() {
        return new SWRVContract.SWRVPresenterAdapter<Coupon>() {

            @Override
            public int getRecycerviewItemlayoutRes() {
                return R.layout.item_youhui_showing;
            }

            @Override
            public RecyclerView.LayoutManager getLayoutManager() {
                return new LinearLayoutManager(getContext());
            }

            @Override
            public void convert(ViewHolder viewHolder, Coupon item, int position) {

            }

            @Override
            public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, Coupon item) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, Coupon item) {

            }

            @Override
            public View getEmptyView() {
                return null;
            }

            @Override
            public Observable< List<Coupon>> call(FactoryInters apiService) {

                Observable<HttpStateResult<ResultData<Coupon>>> coupon = apiService.getCoupon(AppConfig.getAppConfig().get(AppConfig.CONF_KEY),1, 1,mTab);
                //变换
                Observable< List<Coupon>> observable = coupon.map(new Func1<HttpStateResult<ResultData<Coupon>>, List<Coupon>>() {
                    @Override
                    public List<Coupon> call(HttpStateResult<ResultData<Coupon>> resultDataHttpStateResult) {
                        ResultData<Coupon> data = resultDataHttpStateResult.getData();

                        if(data == null )return  null;

                        List<Coupon> list = data.getList();

                        if(BuildConfig.DEBUG && list.isEmpty()){
                            list.add(new Coupon());
                            list.add(new Coupon());
                            list.add(new Coupon());
                            list.add(new Coupon());
                            list.add(new Coupon());
                        }
                        return list;
                    }
                });
                return observable;
            }

            @Override
            public boolean needLoadMore() {
                return true;
            }
        };
    }

}
