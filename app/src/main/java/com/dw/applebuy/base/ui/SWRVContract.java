package com.dw.applebuy.base.ui;

import android.support.v7.widget.RecyclerView;

import com.dw.applebuy.base.api.FactoryInters;
import com.rxmvp.basemvp.BaseView;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

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