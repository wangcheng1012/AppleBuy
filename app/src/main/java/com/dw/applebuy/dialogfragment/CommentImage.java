package com.dw.applebuy.dialogfragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dw.applebuy.ui.home.commentmanage.CommentListBean;
import com.wlj.base.util.DpAndPx;
import com.wlj.base.widget.AutoScrollViewPager;
import com.wlj.base.widget.SwitchViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论图片
 */
public class CommentImage extends AppCompatDialogFragment {

    public static CommentImage newInstance(List<CommentListBean.ImagesBean> imgs, int position) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("imgs", new ArrayList<CommentListBean.ImagesBean>(imgs));
        args.putInt("position",position);
        CommentImage fragment = new CommentImage();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ArrayList<CommentListBean.ImagesBean> imgs = getArguments().getParcelableArrayList("imgs");
        int position = getArguments().getInt("position", 0);
        SwitchViewPager<CommentListBean.ImagesBean> viewPager = new SwitchViewPager<CommentListBean.ImagesBean>(getContext(), imgs){
            @Override
            protected void addSwitchPage(AutoScrollViewPager autoviewPager) {
                super.addSwitchPage(autoviewPager);
                autoviewPager.stopAutoScroll();
            }
        };
        viewPager.setScrollLayoutParams( new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) );
        viewPager.setOnItemPagerViewClickListener(new SwitchViewPager.PageritemClickListener() {
            @Override
            public void ItemClickListener(ImageView imageView1) {
                dismiss();
            }
        });
        View createview = viewPager.createview();
        viewPager.getAutoScrollViewPager().setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % imgs.size() + position);
        return  new AlertDialog.Builder(getActivity())
                .setView(createview)
                .create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
