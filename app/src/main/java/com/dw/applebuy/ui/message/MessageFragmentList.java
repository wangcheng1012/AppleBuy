package com.dw.applebuy.ui.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.dw.applebuy.R;
import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.base.ui.SWRVFragment;
import com.dw.applebuy.ui.MainActivity;
import com.dw.applebuy.ui.home.usermanage.UserActivity;
import com.dw.applebuy.util.SlidingButtonView;
import com.rxmvp.api.HttpResultFunc;
import com.rxmvp.basemvp.BaseView;
import com.rxmvp.bean.HttpResult;
import com.rxmvp.http.ServiceFactory;
import com.rxmvp.subscribers.RxSubscriber;
import com.rxmvp.transformer.DefaultTransformer;
import com.wlj.base.decoration.DividerDecoration;
import com.wlj.base.util.DpAndPx;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.UIHelper;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * 消息列表
 */
public class MessageFragmentList extends SWRVFragment {

    public static final String MESSAGE_RECEIVED_ACTION = "com.dw.applebuy.ui.message_ACTION";
    public static final String KEY_MESSAGE = "key_message";
    public static final String KEY_EXTRAS = "key_extras";
    public static boolean isForeground = false;

//    private MainActivity activity;
    private LocalBroadcastManager mLocalBroadcastManager;
    private IntentFilter intentFilter;
    private MessageBroadcast messageBroadcast;
    private MessageListDelegate listDelegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof UserActivity) {

        } else {

            mLocalBroadcastManager = LocalBroadcastManager.getInstance(getContext());
            intentFilter = new IntentFilter();
            intentFilter.addAction(MESSAGE_RECEIVED_ACTION);
            messageBroadcast = new MessageBroadcast();
        }
    }

    @Override
    protected void initView() {
        listDelegate = new MessageListDelegate(this);
        setMyInterface(new SWRVInterface() {
            @Override
            public void onCreateViewExtract(RecyclerView recyclerview, SwipeRefreshLayout swipeRefreshLayout) {
                recyclerview.addItemDecoration(new DividerDecoration(getResources().getDrawable(R.drawable.divider_white_f1f1f1_10), DividerDecoration.HORIZONTAL_LIST));
            }

            @Override
            public SWRVContract.SWRVPresenterAdapter getPresenterAdapter() {
                return getSwrvPresenterAdapter();
            }
        });

        super.initView();
    }

    @NonNull
    private SWRVContract.SWRVPresenterAdapter<MessageBean> getSwrvPresenterAdapter() {
        return new SWRVContract.SWRVPresenterAdapter<MessageBean>() {
            @Override
            public int getRecycerviewItemlayoutRes() {

                return R.layout.item_message_fragment;
            }

            @Override
            public RecyclerView.LayoutManager getLayoutManager() {
                return new LinearLayoutManager(getContext());
            }

            @Override
            public void convert(ViewHolder viewHolder, MessageBean item, int position) {

                listDelegate.silding(viewHolder, item);

                View state = viewHolder.getView(R.id.message_state);
                //
                int is_view = item.getIs_view();
                if (is_view == 1) {
                    state.setVisibility(View.GONE);
                } else {
                    state.setVisibility(View.VISIBLE);
                }

                viewHolder.setText(R.id.message_time, item.getAddtime());
                viewHolder.setText(R.id.message_title, item.getTitle());
                viewHolder.setText(R.id.message_content, item.getContent());

            }


            @Override
            public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, MessageBean item) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, MessageBean item) {

                listDelegate.itemClick(view, item);
            }

            @Override
            public View getEmptyView() {
                return null;
            }

            @Override
            public Observable<List<MessageBean>> call(FactoryInters apiService, final int curPageStart) {

                Observable<List<MessageBean>> observable = apiService.getMessage(curPageStart)
                        .map(new HttpResultFunc<List<MessageBean>>());
                return observable;
            }

            @Override
            public boolean needLoadMore() {
                return true;
            }

        };

    }

    //--------broadcast
    @Override
    public void onResume() {
        super.onResume();
        isForeground = true;
        if (mLocalBroadcastManager != null) {
            mLocalBroadcastManager.registerReceiver(messageBroadcast, intentFilter);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isForeground = false;
        if (mLocalBroadcastManager != null) {
            mLocalBroadcastManager.unregisterReceiver(messageBroadcast);
        }
    }

    private class MessageBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            getPresenter().onRefresh();
        }
    }

}
