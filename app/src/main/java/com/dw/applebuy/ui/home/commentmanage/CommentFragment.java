package com.dw.applebuy.ui.home.commentmanage;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.base.ui.SWRVFragment;
import com.dw.applebuy.dialogfragment.CommentImage;
import com.rxmvp.bean.HttpResult;
import com.rxmvp.http.ServiceFactory;
import com.rxmvp.subscribers.RxSubscriber;
import com.rxmvp.transformer.DefaultTransformer;
import com.wlj.base.decoration.DividerDecoration;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.MathUtil;
import com.wlj.base.util.SnackbarUtil;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Observable;
import rx.functions.Func1;

/**
 *
 */
public class CommentFragment extends SWRVFragment {

    private int type;
    private CommentActivity activity;

    public static CommentFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);

        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (CommentActivity) getActivity();
        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getInt("type", 0);
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

                return new SWRVContract.SWRVPresenterAdapter<CommentListBean.DataBean>() {
                    @Override
                    public int getRecycerviewItemlayoutRes() {
                        return R.layout.item_commont;
                    }

                    @Override
                    public RecyclerView.LayoutManager getLayoutManager() {
                        return new LinearLayoutManager(getContext());
                    }

                    @Override
                    public void convert(ViewHolder viewHolder, CommentListBean.DataBean item, int position) {
                        convert_(viewHolder, item, position);
                    }

                    @Override
                    public void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, CommentListBean.DataBean item) {

                    }

                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, CommentListBean.DataBean item) {

                    }

                    @Override
                    public View getEmptyView() {
                        return null;
                    }

                    @Override
                    public Observable<List<CommentListBean.DataBean>> call(FactoryInters apiService, int curPageStart) {
                        return apiService.getMerchantComment(curPageStart, type)
                                .map(new Func1<HttpResult<CommentListBean>, List<CommentListBean.DataBean>>() {
                                    @Override
                                    public List<CommentListBean.DataBean> call(HttpResult<CommentListBean> result) {

                                        CommentListBean data = result.getData();
                                        List<CommentListBean.DataBean> data1 = data.getData();
                                        CommentListBean.InfoBean info = data.getInfo();

                                        if (getUserVisibleHint()) {
                                            activity.changShow(info);
                                        }
                                        return data1;
                                    }
                                });
                    }

                    @Override
                    public boolean needLoadMore() {
                        return true;
                    }
                };
            }
        });
        super.initView();
    }

    private void convert_(ViewHolder viewHolder, final CommentListBean.DataBean item, int position) {

        ImageView head = viewHolder.getView(R.id.item_commont_head);
        viewHolder.setText(R.id.item_commont_name, item.getName());
        viewHolder.setText(R.id.item_commont_time, item.getAddtime());
        RatingBar ratingBar2 = viewHolder.getView(R.id.item_commont_ratingBar2);
        viewHolder.setText(R.id.item_commont_content, item.getContent());
        RecyclerView recyclerView = viewHolder.getView(R.id.item_commont_images);
        convert_images(recyclerView, item.getImgs());
        ImageView quan = viewHolder.getView(R.id.item_commont_image_quan);
        viewHolder.setText(R.id.item_commont_name_quan, item.getTitle());
        viewHolder.setText(R.id.item_commont_time_quan, item.getOrder_time());
        final View reply = viewHolder.getView(R.id.item_commont_reply);
        TextView commontReply = viewHolder.getView(R.id.item_commont_commontReply);
        commontReply.setText(item.getReply());

        Glide.with(this).load(item.getHead_portrait()).bitmapTransform(new CropCircleTransformation(getContext())).into(head);
        ratingBar2.setRating(MathUtil.parseFloat(item.getScore()));
        Glide.with(this).load(item.getIcon()).into(quan);
        if (item.getIs_reply() == 1) {
            //1-已回复   0-未回复
            reply.setVisibility(View.GONE);
            commontReply.setVisibility(View.VISIBLE);
        } else {
            reply.setVisibility(View.VISIBLE);
            commontReply.setVisibility(View.GONE);
        }
        //回复 点击
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = new EditText(getContext());
                SnackbarUtil.SnackbarAddView(v, editText, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        replyCall(MathUtil.parseInteger(item.getId()), v.getTag() + "");
                    }
                });

            }
        });

    }

    /**
     * 商家回复评论
     *
     * @param id    评论ID
     * @param reply 回复内容
     */
    private void replyCall(int id, String reply) {
        ServiceFactory
                .createService(FactoryInters.class)
                .saveReply(id, reply)
                .compose(new DefaultTransformer<String>())
                .subscribe(new RxSubscriber<String>(CommentFragment.this) {
                    @Override
                    public void onNext(String s) {
                        onRefresh(type);
                    }
                });
    }

    /**
     * 评论图片
     * @param recyclerView
     * @param imgs
     */
    private void convert_images(RecyclerView recyclerView, final List<CommentListBean.ImagesBean> imgs) {
        if (imgs == null) {
            return;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ImageAdapter imageAdapter = new ImageAdapter(getContext(), imgs);
        recyclerView.setAdapter( imageAdapter);
        imageAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                CommentImage commentImage = CommentImage.newInstance(imgs,position);
                commentImage.show(getChildFragmentManager(),"pinglun");

            }
        });
        //这样加要不得， 每刷新一次会add一次
//        recyclerView.addItemDecoration(new DividerDecoration(getResources().getDrawable(R.drawable.divider_white_ffffff_10 ), DividerDecoration.HORIZONTAL_LIST));
//        recyclerView.addItemDecoration(new DividerDecoration(getResources().getDrawable(R.drawable.divider_white_ffffff_10), DividerDecoration.VERTICAL_LIST));
    }

    public void onRefresh(int i) {
        type = i;
        getPresenter().onRefresh();

    }


}
