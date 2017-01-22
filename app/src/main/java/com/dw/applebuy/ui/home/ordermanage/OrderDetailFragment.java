package com.dw.applebuy.ui.home.ordermanage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.base.ui.SWRVFragment;
import com.dw.applebuy.been.Comment;
import com.dw.applebuy.ui.home.ordermanage.m.CouponOrder;
import com.rxmvp.api.HttpResultFunc;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.decoration.DividerDecoration;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * 订单详情
 */
public class OrderDetailFragment extends SWRVFragment<Comment> {

    public static final String ARG_ITEM_ID = "item_id";
    private int code ;
    private int status = 0;

    public OrderDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            code = getArguments().getInt(ARG_ITEM_ID);
        }
    }

    @Override
    protected void initView() {
        setMyInterface(new SWRVInterface<Comment>() {
            @Override
            public void onCreateViewExtract(RecyclerView recyclerview, SwipeRefreshLayout swipeRefreshLayout) {

                recyclerview.addItemDecoration( new DividerDecoration(getResources().getDrawable(R.drawable.divider_white_f1f1f1_1),DividerDecoration.HORIZONTAL_LIST)) ;

                addHead(recyclerview);
            }

            @Override
            public SWRVContract.SWRVPresenterAdapter<Comment> getPresenterAdapter() {
                return getOrderDetailPresenterAdapter();
            }
        });

        super.initView();
    }

    private void addHead(RecyclerView recyclerview) {

        View header = LayoutInflater.from(getContext()).inflate(R.layout.part_orderdetail_head, null);
        RecyclerView.Adapter loadMoreWrapper =  getAdapter();

        HeaderAndFooterWrapper headerAdapter = new HeaderAndFooterWrapper(loadMoreWrapper);
        headerAdapter.addHeaderView(header);
        recyclerview.setAdapter(headerAdapter);
        headerAdapter.notifyDataSetChanged();
        setAdapter(headerAdapter);
        //
    }

    private SWRVContract.SWRVPresenterAdapter<Comment> getOrderDetailPresenterAdapter() {
        return new SWRVContract.SWRVPresenterAdapter<Comment>() {
            @Override
            public int getRecycerviewItemlayoutRes() {
                return R.id.item_order_detail;
            }

            @Override
            public RecyclerView.LayoutManager getLayoutManager() {
                return new LinearLayoutManager(getContext());
            }

            @Override
            public void convert(ViewHolder viewHolder, Comment item, int position) {

            }

            @Override
            public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, Comment item) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, Comment item) {

            }

            @Override
            public View getEmptyView() {
                return null;
            }

            @Override
            public Observable<List<Comment>> call(FactoryInters apiService, int curPageStart) {
                Observable<HttpStateResult<List<CouponOrder>>> couponOrder = apiService.getCouponOrder(code, 0, status);

                Observable<List<CouponOrder>> map = couponOrder.map(new HttpResultFunc<List<CouponOrder>>());

                return null;
            }

            @Override
            public boolean needLoadMore() {
                return true;
            }
        };
    }

}
