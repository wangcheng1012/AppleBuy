package com.dw.applebuy.ui.message;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dw.applebuy.R;
import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.base.ui.SWRVFragment;
import com.dw.applebuy.ui.MainActivity;
import com.dw.applebuy.ui.home.usermanage.UserActivity;
import com.rxmvp.api.HttpResultFunc;
import com.rxmvp.basemvp.BaseView;
import com.wlj.base.decoration.DividerDecoration;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.UIHelper;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * 消息列表
 */
public class MessageFragmentList extends SWRVFragment {

    private MainActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof UserActivity) {
            activity = (MainActivity) getActivity();
        }
    }

    @Override
    protected void initView() {

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

                View state = viewHolder.getView(R.id.message_state);

                int is_view = item.getIs_view();
                if(is_view == 1){
                    state.setVisibility(View.GONE);
                }else{
                    state.setVisibility(View.VISIBLE);
                }

                viewHolder.setText(R.id.message_time,item.getAddtime());
                viewHolder.setText(R.id.message_title,item.getTitle());
                viewHolder.setText(R.id.message_content,item.getContent());

            }

            @Override
            public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, MessageBean item) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, MessageBean item) {

                Subscriber<MessageBean> sub = new Subscriber<MessageBean>() {
                    @Override
                    public void onCompleted() {
                        UIHelper.closeProgressbar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        presenter.onErrorShow(e,"获取失败");
                        UIHelper.closeProgressbar();
                    }

                    @Override
                    public void onNext(MessageBean messageBean) {

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("MessageBean",messageBean);
                        GoToHelp.go(getActivity(),MessageDetailActivity.class,bundle);
                    }
                };
                UIHelper.showProgressbar(getActivity(),null);
                AppHttpMethods.getInstance().viewMessage(sub,item.getId());
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

}
