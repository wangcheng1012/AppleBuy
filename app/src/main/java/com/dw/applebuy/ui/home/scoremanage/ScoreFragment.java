package com.dw.applebuy.ui.home.scoremanage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.base.ui.SWRVFragment;
import com.dw.applebuy.ui.home.scoremanage.m.ScoreListResult;
import com.google.gson.JsonArray;
import com.rxmvp.bean.HttpResult;
import com.wlj.base.decoration.DividerDecoration;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;


public class ScoreFragment extends SWRVFragment<ScoreListResult.ScoreListBean> {

    private static final String INOROUT = "inOrOut";

    public static final int inOrOut_out = 2;
    public static final int inOrOut_in = 3;

    private int inOrOut;
    private ScoreActivity activity;
    private ScoreListResult.InfoBean info;

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
        activity = (ScoreActivity) getActivity();
        if (getArguments() != null) {
            inOrOut = getArguments().getInt(INOROUT, inOrOut_out);
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

                if (inOrOut == inOrOut_out) {
                    ImageView head = viewHolder.getView(R.id.score_out_head);
                    Glide.with(ScoreFragment.this).load(item.getHead_portrait()).into(head);
                    viewHolder.setText(R.id.score_out_name, item.getName());
                    viewHolder.setText(R.id.score_out_phone, item.getMobile());
                    viewHolder.setText(R.id.score_out_time, item.getTime());

                    TextView score = viewHolder.getView(R.id.score_out_score);
                    String integral = "赠送积分：" + item.getIntegral();
                    SpannableStringBuilder builder = new SpannableStringBuilder(integral);
                    ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.yellow_FF552E));
                    builder.setSpan(span, 5, integral.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    score.setText(builder);

                } else {
                    viewHolder.setText(R.id.scorein_time, item.getTime());
                    viewHolder.setText(R.id.scorein_title, item.getTitle());
                    viewHolder.setText(R.id.scorein_score, item.getIntegral());

                    String type = item.getType();
                    TextView name_phone = viewHolder.getView(R.id.scorein_name_phone);
                    TextView typeshow = viewHolder.getView(R.id.scorein_typeshow);

                    if (ScoreListResult.ScoreListBean.type_rechage.equals(type) ) {
                        name_phone.setVisibility(View.GONE);
                        typeshow.setText("充值收入");
                    } else if ( ScoreListResult.ScoreListBean.type_xiaofei.equals(type)){
                        name_phone.setVisibility(View.GONE);
                        typeshow.setText("充值收入");
                    }else {
                        name_phone.setVisibility(View.VISIBLE);
                        name_phone.setText(item.getName() + " " + item.getMobile());
                        typeshow.setText("消费收入");
                    }

                }

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
            public Observable<List<ScoreListResult.ScoreListBean>> call(FactoryInters apiService, final int curPageStart) {

                JsonArray jsonArray = new JsonArray();
                if (inOrOut == inOrOut_out) {
                    jsonArray.add(1);
                } else {
                    jsonArray.add(2);
                    jsonArray.add(3);
                    jsonArray.add(4);
                }

                Observable<HttpResult<ScoreListResult>> scoreList = apiService.getScoreList(curPageStart, jsonArray.toString());
                final Observable<List<ScoreListResult.ScoreListBean>> map = scoreList.map(new Func1<HttpResult<ScoreListResult>, List<ScoreListResult.ScoreListBean>>() {
                    @Override
                    public List call(HttpResult<ScoreListResult> httpStateResult) {
                        ScoreListResult data = httpStateResult.getData();


                        if (curPageStart == 1) {
                            info = data.getInfo();
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setUserVisibleHint(getUserVisibleHint());
                                }
                            });
                        }
                        List<ScoreListResult.ScoreListBean> list = data.getList();

//                        if (BuildConfig.DEBUG) {
//                            if (list == null) {
//                                list = new ArrayList<ScoreListResult.ScoreListBean>();
//                            }
//                            if (list.size() <= 0) {
//
//                                for (int length = 12; length > 0; length--) {
//                                    list.add(new ScoreListResult.ScoreListBean());
//                                }
//
//                            }
//                        } else {
                        //正常
                        if (list == null) return null;
//                        }


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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            activity.changeShow(info, inOrOut);
        }
    }

}
