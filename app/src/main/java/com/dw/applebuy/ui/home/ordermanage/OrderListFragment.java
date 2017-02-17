package com.dw.applebuy.ui.home.ordermanage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.base.ui.SWRVFragment;
import com.dw.applebuy.ui.home.hexiao.OrderDetail2Activity;
import com.dw.applebuy.ui.home.ordermanage.m.CouponOrderList;
import com.rxmvp.api.HttpResultFunc;
import com.rxmvp.bean.HttpResult;
import com.wlj.base.decoration.DividerDecoration;
import com.wlj.base.util.GoToHelp;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import rx.Observable;

/**
 * Created by wlj on 2017/2/13.
 */
public class OrderListFragment extends SWRVFragment {

    private int status;

    public static OrderListFragment newInstance(int status) {

        Bundle args = new Bundle();
        args.putInt("status", status);

        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            status = arguments.getInt("status", 0);
        }
    }

    @Override
    protected void initView() {
        setMyInterface(new SWRVFragment.SWRVInterface() {
            @Override
            public void onCreateViewExtract(RecyclerView recyclerview, SwipeRefreshLayout swipeRefreshLayout) {
                recyclerview.addItemDecoration(new DividerDecoration(getResources().getDrawable(R.drawable.divider_white_f1f1f1_10), DividerDecoration.HORIZONTAL_LIST));
            }

            @Override
            public SWRVContract.SWRVPresenterAdapter getPresenterAdapter() {
                return getMyPresenterAdapter();
            }
        });

        super.initView();
    }

    private SWRVContract.SWRVPresenterAdapter<CouponOrderList> getMyPresenterAdapter() {
        return new SWRVContract.SWRVPresenterAdapter<CouponOrderList>() {

            @Override
            public int getRecycerviewItemlayoutRes() {
                return R.layout.item_order_list;
            }

            @Override
            public RecyclerView.LayoutManager getLayoutManager() {
                return new LinearLayoutManager(getContext());
            }

            @Override
            public void convert(ViewHolder viewHolder, CouponOrderList item, int position) {

                ImageView head = viewHolder.getView(R.id.itemorderlist_head);
                Glide.with(OrderListFragment.this).load(item.getHead_portrait()).into(head);
                viewHolder.setText(R.id.itemorderlist_name, item.getName());
                viewHolder.setText(R.id.itemorderlist_time, item.getAddtime());
                TextView statusshow = viewHolder.getView(R.id.itemorderlist_statusshow);
                statusshow.setText(item.getStatusShow());
                //
                ImageView image = viewHolder.getView(R.id.itemorderlist_image);
                Glide.with(OrderListFragment.this).load(item.getIcon()).into(image);
                viewHolder.setText(R.id.itemorderlist_title, item.getTitle());
                viewHolder.setText(R.id.itemorderlist_address, "有效时间：" + item.getTime() + " 前");
                viewHolder.setText(R.id.itemorderlist_score, item.getIntegral() + " 积分");
//                viewHolder.setText(R.id.itemorderlist_number,"x"+item.getNumber());
//                viewHolder.setText(R.id.itemorderlist_total,String.format("共%s件，共计：%s积分",item.getNumber(), MathUtil.multiply(item.getNumber(),item.getIntegral()).toString()));

                TextView usetime = viewHolder.getView(R.id.itemorderlist_usetime);
//                TextView scan = viewHolder.getView(R.id.itemorderlist_scan);
//                TextView input = viewHolder.getView(R.id.itemorderlist_input);
                if ("1".equals(item.getStatus())) {
                    //未消费
                    statusshow.setTextColor(getResources().getColor(R.color.yellow_FF552E));

                    usetime.setVisibility(View.GONE);
//                    scan.setVisibility(View.VISIBLE);
//                    input.setText("输入验证码");
                } else {
                    statusshow.setTextColor(getResources().getColor(R.color.black3_999999));

                    usetime.setVisibility(View.VISIBLE);
                    usetime.setText(item.getUsetime() + " 消费");
//                    scan.setVisibility(View.GONE);
//                    input.setText("查看详情");
                }

//                scan.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        GoToHelp.go(getActivity(), CodeScanActivity.class);
//                    }
//                });
//                input.setTag(item.getCode());
//                input.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Object code = v.getTag();
//                        Bundle bundle = new Bundle();
//                        bundle.putString(OrderDetailFragment.ITEM, code+"");
//                        GoToHelp.go(getActivity(), OrderDetailActivity.class, bundle);
//                    }
//                });

            }

            @Override
            public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, CouponOrderList item) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, CouponOrderList item) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(OrderDetailFragment.ITEM, item);
                GoToHelp.go(getActivity(), OrderDetail2Activity.class, bundle);
            }

            @Override
            public View getEmptyView() {
                return null;
            }

            @Override
            public Observable<List<CouponOrderList>> call(FactoryInters apiService, int curPageStart) {
                Observable<HttpResult<List<CouponOrderList>>> couponOrder = apiService.getCouponOrder(null, curPageStart, status);
                Observable<List<CouponOrderList>> map;

                map = couponOrder.map(new HttpResultFunc<List<CouponOrderList>>());

                return map;
            }

            @Override
            public boolean needLoadMore() {
                return true;
            }
        };
    }
}
