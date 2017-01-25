package com.dw.applebuy.ui.home.scoremanage;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.been.RechageScoreOrder;
import com.dw.applebuy.been.ScorePackage;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.scoremanage.p.ReChangeScorePresenter;
import com.dw.applebuy.ui.home.scoremanage.v.Contract;
import com.dw.applebuy.ui.pay.PayActivity;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.wlj.base.decoration.MarginDecoration;
import com.wlj.base.util.DpAndPx;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.MathUtil;
import com.wlj.base.util.StringUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 积分充值
 */
public class ReChangeScoreActivity extends BaseMvpActivity<Contract.ReChangeScoreView, ReChangeScorePresenter> implements Contract.ReChangeScoreView, Title1Fragment.TitleInterface {

    @BindView(R.id.rechangescore_score)
    TextView inputScoreView;
    @BindView(R.id.rechangescore_RecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rechangescore_money)
    TextView moneyView;
    @BindView(R.id.rechangescore_score_total)
    TextView scoreTotal;
    /**
     * 套餐数据
     */
    private ArrayList<ScorePackage.PackageBean> mData;
    /**
     * 当前选中的套餐View（选中状态用）
     */
    private View curView;

    //这两参数是否为空来判断是选的 套餐 还是 自定义的积分
    private Integer packageBeanId;
    private Integer inputScore;
    private String score;
    private String money;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechange_score);
        ButterKnife.bind(this);

        initRecyclerView();
        //获取套餐
        presenter.getScorePackage();
        //
        setMoney("0.0");
        setScore("0.0");
        //手动输入
        inputScore();
    }

    /**
     * 手动输入 积分
     */
    private void inputScore() {
        inputScoreView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s + "")) {
                    return;
                }
                presenter.jisuan(s + "");

                try {
                    inputScore = Integer.valueOf(s+"");

                    if(curView != null) {
                        packageBeanId = null;
                        curView.setSelected(false);
                        curView = null;
                    }
                } catch (NumberFormatException e) {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 设置金额
     *
     * @param money
     */
    private void setMoney(String money) {
        this.money = money;
        //支付金额：%s元
        String string = getResources().getString(R.string.rechangescore_money);
        String format = String.format(string, money);

        SpannableStringBuilder spannableString = new SpannableStringBuilder(format);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.yellow_FF552E));

        spannableString.setSpan(redSpan, 5, format.indexOf("元"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        moneyView.setText(spannableString);
    }

    /**
     * 设置积分
     *
     * @param score
     */
    private void setScore(String score) {
        this.score = score;
        //支付金额：s%元
        String string = getResources().getString(R.string.rechangescore_score);
        String format = String.format(string, score);

        SpannableStringBuilder spannableString = new SpannableStringBuilder(format);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.yellow_FF552E));

        spannableString.setSpan(redSpan, 5, format.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        scoreTotal.setText(spannableString);
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new MarginDecoration(DpAndPx.dpToPx(getApplicationContext(), 5)));
        mData = new ArrayList();

        CommonAdapter<ScorePackage.PackageBean> adapter = new CommonAdapter<ScorePackage.PackageBean>(getApplicationContext(), R.layout.item_scorepackage, mData) {
            @Override
            protected void convert(ViewHolder holder, ScorePackage.PackageBean scorePackage, int position) {
                holder.setText(R.id.scorepackage_score, scorePackage.getIntegral());
                holder.setText(R.id.scorepackage_description, scorePackage.getDescription());
            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (curView != null) {
                    curView.setSelected(false);
                }
                curView = view;
                curView.setSelected(true);

                ScorePackage.PackageBean packageBean = mData.get(position);

                try {
                    packageBeanId = Integer.valueOf(packageBean.getId());
                    inputScore = null;
                    inputScoreView.setText("");
                } catch (NumberFormatException e) {
                }

                setMoney(packageBean.getPrice());
                setScore(packageBean.getIntegral());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public ReChangeScorePresenter initPresenter() {
        return new ReChangeScorePresenter();
    }

    @OnClick(R.id.rechangescore_submit)
    public void onClick() {

        presenter.sureRechage(packageBeanId, inputScore);

    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("充值");
    }

    @Override
    public void scorePackageBack(List<ScorePackage.PackageBean> scorePackages) {
        mData.addAll(scorePackages);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void jisuanBack(BigDecimal divide, String s) {
        setMoney(divide.toString());
        setScore(s);
    }

    @Override
    public void sureRechageBack(RechageScoreOrder rechageScoreOrder) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("RechageScoreOrder",rechageScoreOrder);
        bundle.putString("money",money);
        bundle.putString("scoreTotal",score);
        GoToHelp.go(this, PayActivity.class,bundle);
    }
}
