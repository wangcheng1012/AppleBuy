package com.dw.applebuy.ui.home.shoppingmanage.youhui.showing;


import android.app.Activity;
import android.content.Intent;
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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.base.ui.SWRVFragment;
import com.dw.applebuy.ui.home.shoppingmanage.p.TabLayoutPresenter;
import com.dw.applebuy.ui.home.shoppingmanage.v.Contract;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.YouHuiManagerActivity;
import com.dw.applebuy.ui.home.shoppingmanage.m.Coupon;
import com.dw.applebuy.util.OtherTool;
import com.rxmvp.basemvp.BaseMvpFragment;
import com.rxmvp.bean.HttpResult;
import com.wlj.base.decoration.DividerDecoration;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;

/**
 *
 */
public class TabLayoutFragment extends BaseMvpFragment<Contract.TabLayoutView, TabLayoutPresenter> implements Contract.TabLayoutView {

    private static final String TAB = "tab";
    private static final String BACK = "addback";
    public static int SHOWING = 2;
    public static int XIAJIA = 3;
    public final static int modifyrCode = 2;

    /**
     * 审核中
     */
    public static int SHENHE = 4;
    /**
     * caoGaoinit，这个状态是 审核中的一个状态
     */
    public static int CAOGAO = 1;
    private final CouponListItemDelegate couponListItemDelegate = new CouponListItemDelegate(this);

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpage)
    ViewPager viewpage;

    private String[] tabtitle1 = {"添加时间", "销量", "库存"};
    private String[] tabtitles2 = {"草稿箱", "审核中"};
    private String[] tabtitles;

    private int mTab;
    private int mBack;
    //    private int sort_type = 1;
    private SWRVFragment<Coupon> cur;

    public TabLayoutFragment() {
        // Required empty public constructor
    }

    public static TabLayoutFragment newInstance(int tab, int back) {
        TabLayoutFragment fragment = new TabLayoutFragment();
        Bundle args = new Bundle();
        args.putInt(TAB, tab);
//        args.putInt(BACK, back);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTab = getArguments().getInt(TAB, SHOWING);
//            mBack = getArguments().getInt(BACK, 0);
            if (mTab == SHENHE || mTab == CAOGAO) {
                tabtitles = tabtitles2;
            } else {
                tabtitles = tabtitle1;
            }
        }
    }

    @Override
    public TabLayoutPresenter initPresenter() {
        return new TabLayoutPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tablayout, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

        OtherTool.splitLine(getContext(),tablayout);
        //Adapter
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return tabtitles.length;
            }

            @Override
            public Fragment getItem(int position) {
                SWRVFragment<Coupon> swrvFragment = getItemFragment(position);
                return swrvFragment;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabtitles[position];
            }
        };
        viewpage.setAdapter(pagerAdapter);
        tablayout.setupWithViewPager(viewpage);

//        viewpage.setCurrentItem(mBack);
    }

    /**
     * viewpage的itemFragment
     *
     * @param position
     * @return
     */
    @NonNull
    private SWRVFragment<Coupon> getItemFragment(final int position) {

        final SWRVFragment<Coupon> swrvFragment = new SWRVFragment<>();

        swrvFragment.setUserVisibleHint(new SWRVFragment.UserVisibleHint() {
            @Override
            public void isVisibleToUser(boolean isVisibleToUser) {
                if(isVisibleToUser){
                    cur = swrvFragment;
                }
            }
        });

        swrvFragment.setMyInterface(new SWRVFragment.SWRVInterface<Coupon>() {
            @Override
            public void onCreateViewExtract(RecyclerView recyclerview, SwipeRefreshLayout swipeRefreshLayout) {
                recyclerview.addItemDecoration(new DividerDecoration(getResources().getDrawable(R.drawable.divider_white_f1f1f1_10), DividerDecoration.HORIZONTAL_LIST));
            }

            @Override
            public SWRVContract.SWRVPresenterAdapter<Coupon> getPresenterAdapter() {
                return getSwrvPresenterAdapter(position);
            }
        });
        return swrvFragment;
    }

    /**
     * SwrvPresenterAdapter
     *
     * @return
     */
    @NonNull
    private SWRVContract.SWRVPresenterAdapter<Coupon> getSwrvPresenterAdapter(final int position) {
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
            public void convert(ViewHolder viewHolder, final Coupon item, int p) {

                if (item == null) return;
                ImageView view = viewHolder.getView(R.id.showing_image);
                Glide.with(TabLayoutFragment.this).load(item.getIcon()).into(view);

                viewHolder.setText(R.id.showing_name, item.getTitle());
                viewHolder.setText(R.id.showing_number, "库存 " + item.getStock());//销量
                viewHolder.setText(R.id.showing_time, item.getEnd_time());
                viewHolder.setText(R.id.showing_scors, item.getIntegral() + "积分");//积分

                if (mTab == SHENHE) {
                    viewHolder.setText(R.id.showing_number, "库存 " + item.getStock());//销量

                    if (position == 0) {
                        //草稿
                        couponListItemDelegate.caoGaoinit(viewHolder, item);
                    } else {
                        //审核中
                        viewHolder.getView(R.id.showing_image_layout).setVisibility(View.GONE);
                        viewHolder.getView(R.id.showing_image_line).setVisibility(View.GONE);
                    }
                } else if (mTab == SHOWING) {

                    viewHolder.setText(R.id.showing_number, "销量 " + item.getSales_volume() + "   库存 " + item.getStock());//销量
                    couponListItemDelegate.showing(viewHolder, item);
                } else if (mTab == XIAJIA) {

                    viewHolder.setText(R.id.showing_number, "销量 " + item.getSales_volume() + "   库存 " + item.getStock());//销量
                    couponListItemDelegate.xiajia(viewHolder, item);
                }

            }

            @Override
            public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, Coupon item) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int p, Coupon item) {

                if (mTab == SHENHE && getSort_type(position) == 1) {
                    couponListItemDelegate.itemOnClick(item);
                }

            }

            @Override
            public View getEmptyView() {
                return null;
            }

            @Override
            public Observable<List<Coupon>> call(FactoryInters apiService, int curPageStart) {

                int status = mTab;
                int sort = getSort_type(position);
                if (mTab == SHENHE) {
                    if (sort == 1) {
                        status = CAOGAO;
                    } else {
                        sort = 1;
                    }
                }

                Observable<HttpResult<Coupon[]>> coupon = apiService.getCoupon(curPageStart, sort, status);
                //变换
                Observable<List<Coupon>> observable = coupon.map(new Func1<HttpResult<Coupon[]>, List<Coupon>>() {

                    @Override
                    public List<Coupon> call(HttpResult<Coupon[]> httpStateResult) {

                        Coupon[] data = httpStateResult.getData();

//                        if (BuildConfig.DEBUG) {
//                            if (data == null) {
//                                data = new Coupon[12];
//                            }
//                            if (data.length <= 0){
//
//                                for (int length = 12; length > 0; length--) {
//                                    data[length-1] = (new Coupon());
//                                }
//
//                            }
//                        } else {
                        //正常
                        if (data == null) return null;
//                        }
                        return Arrays.asList(data);
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

    /**
     * @param position
     * @return
     */
    private int getSort_type(int position) {
        //1-添加时间 2-销量 3-销量
        switch (position) {
            case 0://添加时间
                return 1;
            case 1://销量
                return 2;
            case 2://库存
                return 3;
        }
        return 0;
    }

    @Override
    public void submitBack() {
        cur.getPresenter().onRefresh();
    }

    @Override
    public void delBack() {
        cur.getPresenter().onRefresh();
    }

    @Override
    public void couponBack(Coupon coupon) {
        couponListItemDelegate.couponBack(coupon);
    }

    @Override
    public void offShelfCouponBack(HttpResult hsr) {
        cur.getPresenter().onRefresh();
    }

    @Override
    public void shelvesCouponBack(HttpResult hsr) {
        cur.getPresenter().onRefresh();
    }


    public TabLayoutPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) return;

        if (modifyrCode == requestCode || requestCode == YouHuiManagerActivity.addrCode) {

            viewpage.setCurrentItem(0);

            if (cur == null) return;

            cur.getPresenter().onRefresh();
        }

    }
}
