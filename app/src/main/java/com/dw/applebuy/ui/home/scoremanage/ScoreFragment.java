package com.dw.applebuy.ui.home.scoremanage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dw.applebuy.BuildConfig;
import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.base.ui.SWRVFragment;
import com.dw.applebuy.ui.home.scoremanage.m.ScoreListResult;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.showing.m.Coupon;
import com.google.gson.JsonArray;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.decoration.DividerDecoration;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;


 public class ScoreFragment extends SWRVFragment<ScoreListResult.ScoreListBean> {

    private static final String INOROUT = "inOrOut";

    public static final int inOrOut_out = 2;
    public static final int inOrOut_in = 3;

    private int inOrOut;

    public ScoreFragment() {

    }


    public static ScoreFragment newInstance(int inOrOut) {
         ScoreFragment fragment = new ScoreFragment();
         Bundle args = new Bundle();
         args.putInt(INOROUT, inOrOut);
         fragment.setArguments(args);
         return fragment;
     }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            inOrOut = getArguments().getInt(INOROUT, inOrOut_out);
        }
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
    private SWRVContract.SWRVPresenterAdapter<ScoreListResult.ScoreListBean> getSwrvPresenterAdapter() {
        return new SWRVContract.SWRVPresenterAdapter<ScoreListResult.ScoreListBean>() {
            @Override
            public int getRecycerviewItemlayoutRes() {
                if (inOrOut == inOrOut_out) {
                    return R.layout.item_score_out;
                }
                return R.layout.item_score_in;
            }

            @Override
            public RecyclerView.LayoutManager getLayoutManager() {
                return new LinearLayoutManager(getContext());
            }

            @Override
            public void convert(ViewHolder viewHolder, ScoreListResult.ScoreListBean item, int position) {

//                        if(inOrOut == inOrOut_out)
            }

            @Override
            public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, ScoreListResult.ScoreListBean item) {

            }

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, ScoreListResult.ScoreListBean item) {
//                        if(inOrOut == inOrOut_out)
            }

            @Override
            public View getEmptyView() {
                return null;
            }

            @Override
            public Observable<List<ScoreListResult.ScoreListBean>> call(FactoryInters apiService, int curPageStart) {

                JsonArray jsonArray = new JsonArray();
                if (inOrOut == inOrOut_out) {
                    jsonArray.add(1);
                } else {
                    jsonArray.add(2);
                    jsonArray.add(3);
                }

                Observable<HttpStateResult<ScoreListResult>> scoreList = apiService.getScoreList(curPageStart, jsonArray.toString());
                Observable<List<ScoreListResult.ScoreListBean>> map = scoreList.map(new Func1<HttpStateResult<ScoreListResult>, List<ScoreListResult.ScoreListBean>>() {
                    @Override
                    public List call(HttpStateResult<ScoreListResult> httpStateResult) {
                        ScoreListResult data = httpStateResult.getData();

                        ScoreListResult.InfoBean info = data.getInfo();

                        List<ScoreListResult.ScoreListBean> list = data.getList();

                        if (BuildConfig.DEBUG) {
                            if (list == null) {
                                list = new ArrayList<ScoreListResult.ScoreListBean>();
                            }
                            if (list.size() <= 0){

                                for (int length = 12; length > 0; length--) {
                                    list.add(new ScoreListResult.ScoreListBean());
                                }

                            }
                        } else {
                            //正常
                            if (list == null) return null;
                        }


                        return list;
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
}
