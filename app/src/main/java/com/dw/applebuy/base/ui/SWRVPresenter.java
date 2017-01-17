package com.dw.applebuy.base.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.dw.applebuy.base.api.AppHttpMethods;
import com.rxmvp.api.RetrofitBase;
import com.rxmvp.basemvp.BasePresenter;
import com.wlj.base.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 *
 */
public class SWRVPresenter<T> extends BasePresenter<SWRVContract.SWRVView> {

    private Context mContext;
    private RecyclerView recycerview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<T> datas;
    //    private AsyncCall asyncCall;
    private TextView loadmoretext;

    private SWRVContract.SWRVPresenterAdapter presenterAdapter;
    //    private BaseAsyncModle modle;
    private RecyclerView.Adapter adapter;

    /**
     * 页数 加载完
     */
    private boolean isComplate;
    /**
     * 请求时的page
     */
    private int curPageStart = -1;
    /**
     * 回调后的page
     */
    private int curPageEnd = -1;
    //curPageStart != curPageEnd 相当于加载中

    public SWRVPresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void initRecycerview(RecyclerView recycerview, SwipeRefreshLayout swipeRefreshLayout) {

        this.recycerview = recycerview;
        this.swipeRefreshLayout = swipeRefreshLayout;

        if (presenterAdapter == null) {
            throw new RuntimeException("presenterAdapter 不能为空");
        }
        int itemlayout = presenterAdapter.getRecycerviewItemlayoutRes();

        if (itemlayout == 0) {
            throw new RuntimeException("recycerview的item layout 不能为空");
        }

        RecyclerView.LayoutManager layoutManager = presenterAdapter.getLayoutManager();

        if (layoutManager == null) {
            throw new RuntimeException("layoutManager 不能为空");
        }

        recycerview.setLayoutManager(layoutManager);

        recycerview(itemlayout);

        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout() {

        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SWRVPresenter.this.onRefresh();
            }
        });
    }

    private void recycerview(int layout) {

        datas = new ArrayList<>();
        CommonAdapter<T> commonAdapter = new CommonAdapter<T>(mContext, layout, datas) {

            @Override
            protected void convert(ViewHolder viewHolder, T item, int position) {
                if (mView == null) return;
                viewHolder.getConvertView().setTag(R.id.tag_first, item);

                presenterAdapter.convert(viewHolder, item, position);
            }
        };

        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                T tag = (T) view.getTag(R.id.tag_first);

                if (tag != null) {

                    presenterAdapter.onItemClick(view, holder, position, tag);
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                T tag = (T) view.getTag(R.id.tag_first);

                if (tag != null) {
                    presenterAdapter.onItemLongClick(view, holder, position, tag);
                }
                return false;
            }
        });

        //空 EmptyWrapper
        View emptyView = presenterAdapter.getEmptyView();
        if (emptyView == null) {
            TextView empty = new TextView(mContext);
            empty.setText("点击刷新");
            empty.setTextColor(Color.BLACK);
            empty.setGravity(Gravity.CENTER);
            empty.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT));
            emptyView = empty;
        }
        EmptyWrapper emptyWrapper = new EmptyWrapper(commonAdapter);
        emptyWrapper.setEmptyView(emptyView);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        adapter = emptyWrapper;

        if (presenterAdapter.needLoadMore()) {
            //loadMoreWrapper
            loadmoretext = new TextView(mContext);
            // loadmoretext.setText(" ");
            loadmoretext.setTextColor(Color.BLACK);
            loadmoretext.setGravity(Gravity.CENTER);
            loadmoretext.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

            LoadMoreWrapper loadMoreWrapper = new LoadMoreWrapper(emptyWrapper, recycerview);
            loadMoreWrapper.setLoadMoreView(loadmoretext);
            loadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    if (!isComplate()) {
                        loadMore();
                    }
                }
            });
            adapter = loadMoreWrapper;
        }
        recycerview.setAdapter(adapter);
    }//Recycerview  end

    /**
     * 网络请求完 且 数据加载完
     *
     * @return
     */
    private boolean isComplate() {
        //curPageStart != curPageEnd 相当于加载中
        if (curPageStart == curPageEnd && isComplate) {
            //不加载更多
            return true;
        }
        return false;
    }

    private void loadMore() {
        //说明请求未完成，但要排除刷新
        if (curPageEnd != curPageStart) return;
        callWeb(curPageEnd + 1);
    }

    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        isComplate = false;
        callWeb(1);
    }

    private synchronized void callWeb(int page) {

        curPageStart = page;
        //观察者
        Subscriber<List<T>> subscriber = new Subscriber<List<T>>() {

            @Override
            public void onCompleted() {
                if (mView != null) {
                    mView.hideLoading();
                }
                curPageEnd = curPageStart;
                RefreshingClose();
            }

            @Override
            public void onError(Throwable e) {
                RefreshingClose();

                onErrorShow(e,"获取数据失败");
            }

            @Override
            public void onNext(List<T> list) {
                if (list == null)return;
                //以此判定为刷新 就清除原油数据
                if (curPageEnd <= 1) {
                    datas.clear();
                }
                //是否加载完成
                if (list.size() <= 0) {
                    isComplate = true;
                }

                datas.addAll(list);
                adapter.notifyDataSetChanged();
            }
        };//end

        AppHttpMethods appHttpMethods = AppHttpMethods.getInstance();
        RetrofitBase retrofitBase = appHttpMethods.getRetrofitBase();
        Observable<List<T>> observable = presenterAdapter.call(appHttpMethods.getApiService());
        retrofitBase.toSubscribe(observable, subscriber);
    }


//
//
//    private void loadData(int page) {
//
//        modle = presenterAdapter.getBaseAsyncModle();
//
//        if (modle == null) {
//            throw new RuntimeException("网路请求modle 不能为空");
//        }
//
//        modle.setPage(page);
//        asyncCall = modle.Request(presenterAdapter.getRequestType());
//        asyncCall.setShowToast(false);
//
//        loadBack();
//    }
//
//    private void loadBack() {
//        asyncCall.setOnAsyncBackListener(new AsyncCall.OnAsyncBackListener() {
//            @Override
//            public void OnAsyncBack(List<Base> paramList, Base paramBase, int requesttype) {
//                //以此判定为刷新 就清除原油数据
//                if (asyncCall.getPageIndex() <= 1) {
//                    datas.clear();
//                }
//                List<Base> list = presenterAdapter.handleBackData(paramList, paramBase, requesttype);
//
//                if (list == null) {
//                    throw new RuntimeException("list==null");
//                }
//                datas.addAll(list);
//                adapter.notifyDataSetChanged();
//                RefreshingClose();
//
////                if(asyncCall.getPageIndex() == 1 && paramList.size() == 0){
////                    //数据为空
////                    loadmoretext.setText("数据为空");
////                }
//
//                if (asyncCall.isComplate() && datas.size() != 0) {
//                    //加载完
////                    loadmoretext.setText("已经到底了");
//                }
//            }
//
//            @Override
//            public void fail(Exception paramException) {
//
//                if ((paramException instanceof SocketTimeoutException)) {
//                    UIHelper.toastMessage(AppContext.getAppContext(), " 链接超时");
//                } else {
//                    UIHelper.toastMessage(AppContext.getAppContext(), paramException.getMessage());
//                }
//                RefreshingClose();
////                loadmoretext.setText("异常");
//            }
//        });
//    }

    public void RefreshingClose() {

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

//    public void onRefresh(BaseAsyncModle modle) {
//        this.modle = modle;
//        onRefresh();
//    }

    public SWRVContract.SWRVPresenterAdapter getPresenterAdapter() {
        return presenterAdapter;
    }

    public void setPresenterAdapter(SWRVContract.SWRVPresenterAdapter presenterAdapter) {
        this.presenterAdapter = presenterAdapter;
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

}