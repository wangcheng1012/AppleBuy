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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.dw.applebuy.BuildConfig;
import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.base.ui.SWRVFragment;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.add.YouHuiAddActivity;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.showing.m.Coupon;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.decoration.DividerDecoration;
import com.wlj.base.util.DpAndPx;
import com.wlj.base.util.GoToHelp;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Arrays;
import java.util.List;

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
    private int sort_type;
    private int requestcode_itemClick = 2;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        child.setDividerPadding(DpAndPx.dpToPx(getContext(), 15));
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
    }

    /**
     * viewpage的itemFragment
     * @param position
     * @return
     */
    @NonNull
    private SWRVFragment<Coupon> getItemFragment(int position) {
        //1-添加时间 2-销量 3-销量
        switch (position) {
            case 0://添加时间
                sort_type = 1;
                break;
            case 1://销量
                sort_type = 2;
                break;
            case 2://库存
                sort_type = 3;
                break;
        }

        SWRVFragment<Coupon> swrvFragment = new SWRVFragment<>();
        swrvFragment.setMyInterface(new SWRVFragment.SWRVInterface<Coupon>() {
            @Override
            public void onCreateViewExtract(RecyclerView recyclerview, SwipeRefreshLayout swipeRefreshLayout) {

                recyclerview.addItemDecoration( new DividerDecoration(getResources().getDrawable(R.drawable.divider_white_f1f1f1_10),DividerDecoration.HORIZONTAL_LIST)) ;
            }

            @Override
            public SWRVContract.SWRVPresenterAdapter<Coupon> getPresenterAdapter() {
                return getSwrvPresenterAdapter();
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

                if(item == null ) return;
//                {
//                    id: "5",
//                        category_id: "1", /*分类*/
//                        stock: "200",/*库存*/
//                        integral: "60",/*积分*/
//                        title: "全场6折",/*优惠卷名称*/
//                        sales_volume: "3",/*销量*/
//                        end_time: "有效期截止：2017/01/09",/*有效期*/
//                        icon: "http://supplier.pingguo24.com/static/img/coupon/zhekouquan@2x.png" /*图标*/
//                }
                ImageView view = viewHolder.getView(R.id.showing_image);
                Glide.with(TabLayoutFragment.this).load(item.getIcon()).into(view);

                viewHolder.setText(R.id.showing_name,item.getTitle());
                viewHolder.setText(R.id.showing_number,"销量 "+item.getSales_volume() + "    收藏 0 " + "    库存 " + item.getStock());//销量
                viewHolder.setText(R.id.showing_time,"时间 "+ item.getEnd_time());
                viewHolder.setText(R.id.showing_scors,item.getIntegral() +"积分");//积分
            }

            @Override
            public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, Coupon item) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, Coupon item) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("Coupon",item);

                GoToHelp.goResult(getActivity(), YouHuiAddActivity.class, requestcode_itemClick,bundle);
            }

            @Override
            public View getEmptyView() {
                return null;
            }

            @Override
            public Observable<List<Coupon>> call(FactoryInters apiService) {

                Observable<HttpStateResult<Coupon[]>> coupon = apiService.getCoupon( 1, sort_type, mTab);
                //变换
                Observable<List<Coupon>> observable = coupon.map(new Func1<HttpStateResult<Coupon[]>, List<Coupon>>() {

                    @Override
                    public List<Coupon> call(HttpStateResult<Coupon[]> httpStateResult) {

                        Coupon[] data = httpStateResult.getData();

                        if (BuildConfig.DEBUG) {
                            if (data == null) {
                                data = new Coupon[12];
                            }
                            if (data.length <= 0){

                                for (int length = 12; length > 0; length--) {
                                    data[length-1] = (new Coupon());
                                }

                            }
                        } else {
                            //正常
                            if (data == null) return null;
                        }
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

}
