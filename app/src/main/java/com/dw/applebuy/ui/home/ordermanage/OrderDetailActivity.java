package com.dw.applebuy.ui.home.ordermanage;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.wlj.base.ui.BaseFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseFragmentActivity {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_rightTv)
    TextView titleRightTv;

    @BindView(R.id.order_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.order_detail_container)
    NestedScrollView orderDetailContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);
        initTitle();

        if (savedInstanceState == null) {


            OrderDetailFragment fragment = new OrderDetailFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.order_detail_container, fragment)
                    .commit();
        }
    }

    private void initTitle() {

        toolbar.setTitle("");
        titleName.setText(getTitle());
        titleBack.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.title_back)
    public void onClick() {
        finish();
    }
}
