package com.dw.applebuy.ui.home.hexiao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.hexiao.m.CouponOrder;
import com.dw.applebuy.ui.home.hexiao.p.OrderDetail2Presenter;
import com.dw.applebuy.ui.home.hexiao.v.Contract;
import com.dw.applebuy.ui.home.ordermanage.OrderDetailFragment;
import com.dw.applebuy.ui.home.ordermanage.m.CouponOrderList;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.rxmvp.bean.HttpResult;
import com.wlj.base.util.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 订单详情
 */
public class OrderDetail2Activity extends BaseMvpActivity<Contract.OrderDetail2View, OrderDetail2Presenter> implements Title1Fragment.TitleInterface, Contract.OrderDetail2View {

    @BindView(R.id.order_detail2_name)
    TextView name;
    @BindView(R.id.order_detail2_code)
    TextView code;
    @BindView(R.id.order_detail2_endtime)
    TextView endtime;
    @BindView(R.id.order_detail2_score)
    TextView score;
    @BindView(R.id.order_detail2_addtime)
    TextView addtime;
    @BindView(R.id.order_detail2_bt)
    Button bt;
    @BindView(R.id.itemorderlist_name)
    TextView itemorderlistName;
    @BindView(R.id.itemorderlist_time)
    TextView itemorderlistTime;
    @BindView(R.id.itemorderlist_statusshow)
    TextView itemorderlistStatusshow;
    @BindView(R.id.itemorderlist_head)
    ImageView itemorderlistHead;
    @BindView(R.id.order_detail2_userinfolayout)
    LinearLayout orderDetail2Userinfolayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail2);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    public OrderDetail2Presenter initPresenter() {
        return new OrderDetail2Presenter();
    }

    private void initView() {
        Intent intent = getIntent();
        CouponOrder couponOrder = intent.getParcelableExtra("CouponOrder");
        CouponOrderList item = intent.getParcelableExtra(OrderDetailFragment.ITEM);
        if (couponOrder != null) {


            name.setText(couponOrder.getTitle());
            code.setText(couponOrder.getCode());
            endtime.setText(couponOrder.getEnd_time());
            score.setText(couponOrder.getIntegral() + "积分");
            addtime.setText(couponOrder.getAddtime());

        } else if (item != null) {

            orderDetail2Userinfolayout.setVisibility(View.VISIBLE);

            Glide.with(this).load(item.getHead_portrait()).bitmapTransform(new CropCircleTransformation(this)).into(itemorderlistHead);
            itemorderlistName.setText(item.getName());
            itemorderlistTime.setText(item.getAddtime());
            itemorderlistStatusshow.setText(item.getStatusShow());

            if ("1".equals(item.getStatus())) {
                //未消费
                itemorderlistStatusshow.setTextColor(getResources().getColor(R.color.yellow_FF552E));

            }else{
                itemorderlistStatusshow.setTextColor(getResources().getColor(R.color.black3_999999));
            }

            name.setText(item.getTitle());
            code.setText(item.getCode());
            endtime.setText(item.getTime());
            score.setText(item.getIntegral() + "积分");
            addtime.setText(item.getAddtime());

            bt.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.order_detail2_bt)
    public void onClick() {

        presenter.hexiao(code.getText() + "");
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("订单详情");
    }

    @Override
    public void useCouponBack(HttpResult httpResult) {

        UIHelper.tip(this, httpResult.getMessage(), new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                finish();
            }
        });
    }
}
