package com.dw.applebuy.base.ui;

import android.support.v7.widget.RecyclerView;

import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.been.ResultData;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.showing.m.Coupon;
import com.rxmvp.api.RetrofitBase;
import com.rxmvp.basemvp.BaseView;
import com.wlj.base.bean.Base;
import com.wlj.base.web.asyn.BaseAsyncModle;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by wlj on 2016/11/7.
 */

public class SWRVContract {

    public interface SWRVView extends BaseView{
    }

    public interface SWRVPresenterAdapter<T> {

        int getRecycerviewItemlayoutRes();

        RecyclerView.LayoutManager getLayoutManager();

        void convert(ViewHolder viewHolder, T item, int position);

        void onItemLongClick(android.view.View view, RecyclerView.ViewHolder holder, int position, T item);

        void onItemClick(android.view.View view, RecyclerView.ViewHolder holder, int position, T item);

        /**
         * 当数据为空时显示的view
         *
         * @return
         */
        android.view.View getEmptyView();

        Observable< List<T>>  call(FactoryInters apiService, int curPageStart);

        /**
         * 是否需要加载更多
         * @return
         */
        boolean needLoadMore();
    }


}