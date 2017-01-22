package com.dw.applebuy.base.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dw.applebuy.R;
import com.rxmvp.basemvp.BaseMvpFragment;
import com.wlj.base.util.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * SwipeRefreshLayout下拉刷新 和RecyclerView加载跟多实现的一个fragment，多个地方都可以调用
 */
public class SWRVFragment<T> extends BaseMvpFragment<SWRVContract.SWRVView,SWRVPresenter<T>> implements SWRVContract.SWRVView {

    @BindView(R.id.classify_list_recycerview)
    RecyclerView recycerview;
    @BindView(R.id.classify_list_swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private SWRVInterface myInterface;

    public SWRVFragment() {
    }

    @Override
    public SWRVPresenter initPresenter() {
        return new SWRVPresenter<T> (getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_classify_list, container, false);
        ButterKnife.bind(this, view);
        initView();
        return  view;
    }

    protected void initView() {

        presenter.setPresenterAdapter(myInterface.getPresenterAdapter());

        presenter.initRecycerview(recycerview, swipeRefreshLayout);

        myInterface.onCreateViewExtract(recycerview, swipeRefreshLayout);
        presenter.onRefresh();

    }

    public SWRVPresenter getPresenter() {
        return presenter;
    }

    public RecyclerView getRecycerview() {
        return recycerview;
    }

    public RecyclerView.Adapter getAdapter() {
        return presenter.getAdapter();
    }

    public void setMyInterface(SWRVInterface<T> myInterface) {
        this.myInterface = myInterface;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        presenter.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        UIHelper.toastMessage(getContext(),message);
    }

    public interface SWRVInterface<T> {

        /**
         * onCreateView的扩展，可以对recyclerview、swipeRefreshLayout做补充,如recyclerview家head
         *
         * @param recyclerview
         * @param swipeRefreshLayout
         */
        void onCreateViewExtract(RecyclerView recyclerview, SwipeRefreshLayout swipeRefreshLayout);

        SWRVContract.SWRVPresenterAdapter<T> getPresenterAdapter();

    }

}
