package com.dw.applebuy.ui.home.shoppingmanage.youhui.showing;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.base.ui.SWRVFragment;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.add.YouHuiAddActivity;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.showing.m.Coupon;
import com.wlj.base.util.GoToHelp;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 优惠券管理列表的item 代理
 */
public class CouponListItemDelegate {
    private final TabLayoutFragment tabLayoutFragment;

    public CouponListItemDelegate(TabLayoutFragment tabLayoutFragment) {
        this.tabLayoutFragment = tabLayoutFragment;
    }

    void caoGaoinit(ViewHolder viewHolder, Coupon item) {

        TextView view1 = viewHolder.getView(R.id.showing_image_textview1);
        TextView view2 = viewHolder.getView(R.id.showing_image_textview2);

        Drawable drawable = tabLayoutFragment.getResources().getDrawable(R.drawable.icon_daishenhe);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        view1.setCompoundDrawables(drawable, null, null, null);
        view1.setTextColor(tabLayoutFragment.getResources().getColor(R.color.blue_24B9EB));
        view1.setText("提交审核");

        Drawable drawable2 = tabLayoutFragment.getResources().getDrawable(R.drawable.icon_23_del);
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
        view2.setCompoundDrawables(drawable2, null, null, null);
        view2.setTextColor(tabLayoutFragment.getResources().getColor(R.color.black3_999999));
        view2.setText("删除");

        caoGaoOnClick(item, view1, view2);
    }

    private void caoGaoOnClick(final Coupon item, TextView view1, TextView view2) {
        //提交审核
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabLayoutFragment.getPresenter().submitShenhe(item);
            }
        });
        //删除
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(tabLayoutFragment.getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("确认删除优惠券 \n \"" + item.getTitle() + "\"")
                        .setConfirmText("确认")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                tabLayoutFragment.getPresenter().delShenHe(item);
                            }
                        });
                sweetAlertDialog.setCanceledOnTouchOutside(true);
                sweetAlertDialog.show();
                sweetAlertDialog.getmTitleTextView().setGravity(Gravity.CENTER);
            }
        });
    }

    void itemOnClick(Coupon item) {
        tabLayoutFragment.getPresenter().getCouponInfo(item);
    }

    void couponBack(Coupon coupon) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Coupon", coupon);
        GoToHelp.goResult(tabLayoutFragment.getActivity(), YouHuiAddActivity.class, tabLayoutFragment.modifyrCode, bundle);
    }

    //展示中
    public void showing(ViewHolder viewHolder, final Coupon item) {

        TextView view2 = viewHolder.getView(R.id.showing_image_textview2);
        Drawable drawable2 = tabLayoutFragment.getResources().getDrawable(R.drawable.icon_22_xiajia);
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
        view2.setCompoundDrawables(drawable2, null, null, null);
        view2.setTextColor(tabLayoutFragment.getResources().getColor(R.color.blue_24B9EB));
        view2.setText("下架");

        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(tabLayoutFragment.getActivity())
                        .setTitleText("提示")
                        .setConfirmText("确认")
                        .setCancelText("取消")
                        .setContentText("是否下架优惠券\n\"" + item.getTitle() + "\"")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                tabLayoutFragment.getPresenter().offShelfCoupon(item);
                            }
                        });
                sweetAlertDialog.setCanceledOnTouchOutside(true);
                sweetAlertDialog.show();
                sweetAlertDialog.getmTitleTextView().setGravity(Gravity.CENTER);
            }
        });
    }

    public void xiajia(ViewHolder viewHolder, final Coupon item) {

        TextView view2 = viewHolder.getView(R.id.showing_image_textview2);
        Drawable drawable2 = tabLayoutFragment.getResources().getDrawable(R.drawable.icon_22_shangjia);
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
        view2.setCompoundDrawables(drawable2, null, null, null);
        view2.setTextColor(tabLayoutFragment.getResources().getColor(R.color.blue_24B9EB));
        view2.setText("上架");

        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(tabLayoutFragment.getActivity())
                        .setTitleText("提示")
                        .setConfirmText("确认")
                        .setCancelText("取消")
                        .setContentText("是否上架优惠券\n\"" + item.getTitle() + "\"")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                tabLayoutFragment.getPresenter().shelvesCoupon(item);
                            }
                        });
                sweetAlertDialog.setCanceledOnTouchOutside(true);
                sweetAlertDialog.show();
                sweetAlertDialog.getmTitleTextView().setGravity(Gravity.CENTER);

            }

        });
    }
}