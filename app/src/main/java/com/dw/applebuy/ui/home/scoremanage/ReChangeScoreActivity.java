package com.dw.applebuy.ui.home.scoremanage;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.scoremanage.p.ReChangeScorePresenter;
import com.dw.applebuy.ui.home.scoremanage.v.Contract;
import com.dw.applebuy.ui.pay.PayActivity;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.wlj.base.util.GoToHelp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 积分充值
 */
public class ReChangeScoreActivity extends BaseMvpActivity<Contract.ReChangeScoreView, ReChangeScorePresenter> implements Contract.ReChangeScoreView,Title1Fragment.TitleInterface {

    @BindView(R.id.rechangescore_score)
    TextView score;
    @BindView(R.id.rechangescore_RecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rechangescore_money)
    TextView money;
    @BindView(R.id.rechangescore_score_total)
    TextView scoreTotal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechange_score);
        ButterKnife.bind(this);

    }

    @Override
    public ReChangeScorePresenter initPresenter() {
        return new ReChangeScorePresenter();
    }

    @OnClick(R.id.rechangescore_submit)
    public void onClick() {
        GoToHelp.go(this,PayActivity.class);

    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("充值");
    }
}
