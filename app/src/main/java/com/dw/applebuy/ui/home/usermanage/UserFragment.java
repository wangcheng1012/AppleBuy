package com.dw.applebuy.ui.home.usermanage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
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
import com.dw.applebuy.ui.home.scoremanage.ScoreFragment;
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
 * 积分管理
 */
public class UserFragment extends SWRVFragment {

    private UserActivity activity;
    private final int ModifyRequestCode = 9;

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
//        args.putInt(INOROUT, inOrOut);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof UserActivity) {
            activity = (UserActivity) getActivity();
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
    private SWRVContract.SWRVPresenterAdapter<UserList.ListBean> getSwrvPresenterAdapter() {
        return new SWRVContract.SWRVPresenterAdapter<UserList.ListBean>() {
            @Override
            public int getRecycerviewItemlayoutRes() {

                return R.layout.item_user_fragment;
            }

            @Override
            public RecyclerView.LayoutManager getLayoutManager() {
                return new LinearLayoutManager(getContext());
            }

            @Override
            public void convert(ViewHolder viewHolder, UserList.ListBean item, int position) {
                ImageView head = viewHolder.getView(R.id.user_head);
                Glide.with(UserFragment.this).load(item.getHead_portrait()).into(head);

                String name_remark = item.getName_remark();
                if (StringUtils.isEmpty(name_remark)) {
                    viewHolder.setText(R.id.user_name, item.getName());
                } else {
                    viewHolder.setText(R.id.user_name, name_remark + "(" + item.getName() + ")");
                }

                viewHolder.setText(R.id.user_phone, item.getMobile());
                viewHolder.setText(R.id.user_intime, "入会时间：" + item.getAddtime());
                viewHolder.setText(R.id.user_count, "消费次数：" + item.getConsume_sum() + "次");

                View modify = viewHolder.getView(R.id.user_modify);
                modify.setTag(item);
                modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("ListBean", (Parcelable) v.getTag());
                        GoToHelp.goResult(getActivity(), ModifyUserActivity.class, ModifyRequestCode, bundle);
                    }
                });
                View view = viewHolder.getView(R.id.user_jilu);
                view.setTag(item.getMember_id());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Object tag = v.getTag();

                        Bundle bundle = new Bundle();
                        bundle.putString("id", tag+"");
                        GoToHelp.go(getActivity(), ConsumeHistoryActivity.class, bundle);
                    }
                });

            }

            @Override
            public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, UserList.ListBean item) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, UserList.ListBean item) {
//                        if(inOrOut == inOrOut_out)
            }

            @Override
            public View getEmptyView() {
                return null;
            }

            @Override
            public Observable<List<UserList.ListBean>> call(FactoryInters apiService, final int curPageStart) {

                //
                String search = getActivity().getIntent().getStringExtra("search");
                if(StringUtils.isEmpty(search)){
                    search = null;
                }
                Observable<UserList> scoreList = apiService.getMctMemberList(curPageStart, search).map(new HttpResultFunc<UserList>());
                final Observable<List<UserList.ListBean>> map = scoreList.map(new Func1<UserList, List<UserList.ListBean>>() {
                    @Override
                    public List<UserList.ListBean> call(UserList userList) {
                        final UserList.InfoBean info = userList.getInfo();

                        if(activity != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    activity.changeShow(info);
                                }
                            });

                        }
                        return userList.getList();
                    }
                });
                return map;

            }

            @Override
            public boolean needLoadMore() {
                return true;
            }

        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ModifyRequestCode && resultCode == Activity.RESULT_OK) {
            getPresenter().onRefresh();
        }
    }
}
