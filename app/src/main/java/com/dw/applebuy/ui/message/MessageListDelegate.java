package com.dw.applebuy.ui.message;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.util.SlidingButtonView;
import com.rxmvp.http.ServiceFactory;
import com.rxmvp.subscribers.RxSubscriber;
import com.rxmvp.transformer.DefaultTransformer;
import com.wlj.base.util.DpAndPx;
import com.wlj.base.util.GoToHelp;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by wlj on 2017/2/24.
 */

public class MessageListDelegate {


    private MessageFragmentList mMessageFragmentList;
    public SlidingButtonView mMenu;

    public MessageListDelegate(MessageFragmentList  mMessageFragmentList ) {

        this.mMessageFragmentList = mMessageFragmentList;
    }

//    左滑
    public void silding(ViewHolder viewHolder, final MessageBean item) {

        //layout_content
        View layout_content = viewHolder.getView(R.id.layout_content);
        WindowManager wm = (WindowManager) mMessageFragmentList.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        layout_content.getLayoutParams().width = outMetrics.widthPixels - DpAndPx.dpToPx(mMessageFragmentList.getContext(), 30);

        //SlidingButtonView
        SlidingButtonView slidingButtonView = viewHolder.getView(R.id.SlidingButtonView);
        slidingButtonView.setSlidingButtonListener(new SlidingButtonView.IonSlidingButtonListener() {

            @Override
            public void onMenuIsOpen(View view) {
                mMenu = (SlidingButtonView) view;
            }

            @Override
            public void onDownOrMove(SlidingButtonView slidingButtonView) {
                if (menuIsOpen()) {
                    if (mMenu != slidingButtonView) {
                        closeMenu();
                    }
                }
            }

            @Override
            public void onClick(View conent) {
                itemClick(conent, item);
            }
        });
        //del
        final View tv_delete = viewHolder.getView(R.id.tv_delete);
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delete(item);
            }
        });

    }



    //-------silde
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        return false;
    }

    /**
     * 获取消息详情
     *
     * @param view
     * @param item
     */
    public void itemClick(final View view, final MessageBean item) {
        ServiceFactory
                .createService(FactoryInters.class)
                .viewMessage(item.getId())
                .compose(new DefaultTransformer<MessageBean>())
                .subscribe(new RxSubscriber<MessageBean>(mMessageFragmentList) {
                    @Override
                    public void onNext(MessageBean messageBean) {
                        //隐藏 小红点
                        item.setIs_view(1);
                        view.findViewById(R.id.message_state).setVisibility(View.GONE);
                        //跳转
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("MessageBean", messageBean);
                        GoToHelp.go(mMessageFragmentList.getActivity(), MessageDetailActivity.class, bundle);
                    }
                });

    }

    /**
     * 删除消息
     * @param item
     */
    private void delete(final MessageBean item) {

        ServiceFactory
                .createService(FactoryInters.class)
                .hideMessage(item.getId())
                .compose(new DefaultTransformer<String>())
                .subscribe(new RxSubscriber<String>(mMessageFragmentList) {
                    @Override
                    public void onNext(String messageBean) {
                        closeMenu();
                        mMessageFragmentList.getPresenter().remove(item);
                    }
                });

    }
}
