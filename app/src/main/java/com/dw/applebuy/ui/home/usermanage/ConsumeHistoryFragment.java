package com.dw.applebuy.ui.home.usermanage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.base.ui.SWRVFragment;
import com.dw.applebuy.ui.home.usermanage.m.MemberIntegralLog;
import com.dw.applebuy.ui.home.usermanage.m.UserList;
import com.rxmvp.api.HttpResultFunc;
import com.wlj.base.decoration.DividerDecoration;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.StringUtils;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * 消费记录列表
 */
public class ConsumeHistoryFragment extends SWRVFragment {

    public static ConsumeHistoryFragment newInstance() {
        ConsumeHistoryFragment fragment = new ConsumeHistoryFragment();
        Bundle args = new Bundle();
//        args.putInt(INOROUT, inOrOut);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {

        setMyInterface(new SWRVInterface() {
            @Override
            public void onCreateViewExtract(RecyclerView recyclerview, SwipeRefreshLayout swipeRefreshLayout) {
                recyclerview.addItemDecoration(new DividerDecoration(getResources().getDrawable(R.drawable.divider_white_f1f1f1_1), DividerDecoration.HORIZONTAL_LIST));
            }

            @Override
            public SWRVContract.SWRVPresenterAdapter getPresenterAdapter() {
                return getSwrvPresenterAdapter();
            }
        });

        super.initView();
    }

    @NonNull
    private SWRVContract.SWRVPresenterAdapter<MemberIntegralLog> getSwrvPresenterAdapter() {
        return new SWRVContract.SWRVPresenterAdapter<MemberIntegralLog>() {
            @Override
            public int getRecycerviewItemlayoutRes() {

                return R.layout.item_consume_history;
            }

            @Override
            public RecyclerView.LayoutManager getLayoutManager() {
                return new LinearLayoutManager(getContext());
            }

            @Override
            public void convert(ViewHolder viewHolder, MemberIntegralLog item, int position) {
                ImageView icon = viewHolder.getView(R.id.consume_history_icon);
                Glide.with(ConsumeHistoryFragment.this).load(item.getIcon()).into(icon);

                viewHolder.setText(R.id.consume_history_title, item.getTitle());
                viewHolder.setText(R.id.consume_history_time,  item.getTime());
                viewHolder.setText(R.id.consume_history_score, item.getIntegral()+"积分");
            }

            @Override
            public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, MemberIntegralLog item) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, MemberIntegralLog item) {
//                        if(inOrOut == inOrOut_out)
            }

            @Override
            public View getEmptyView() {
                return null;
            }

            @Override
            public Observable<List<MemberIntegralLog>> call(FactoryInters apiService, final int curPageStart) {
                //
                String id = getActivity().getIntent().getStringExtra("id");

                Observable<List<MemberIntegralLog>> scoreList = apiService.getMemberIntegralLog(curPageStart, id).map(new HttpResultFunc<List<MemberIntegralLog>>());
                return scoreList;

            }

            @Override
            public boolean needLoadMore() {
                return true;
            }

        };
    }

}
