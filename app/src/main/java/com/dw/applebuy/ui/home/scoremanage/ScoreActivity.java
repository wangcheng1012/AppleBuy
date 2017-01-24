package com.dw.applebuy.ui.home.scoremanage;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.base.ui.SWRVContract;
import com.dw.applebuy.base.ui.SWRVFragment;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.scoremanage.m.ScoreListResult;
import com.google.gson.JsonArray;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.adapter.MyFragmentStatePagerAdapter;
import com.wlj.base.util.GoToHelp;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Func1;

/**
 * 积分管理
 */
public class ScoreActivity extends AppCompatActivity implements Title1Fragment.TitleInterface {


    @BindView(R.id.score_number)
    TextView scoreNumber;
    @BindView(R.id.score_text)
    TextView scoreText;
    @BindView(R.id.score_bar_out)
    ImageView scoreBarOut;
    @BindView(R.id.score_bar_in)
    ImageView scoreBarIn;
    @BindView(R.id.user_usertoalnumber)
    TextView usertoalnumber;
    @BindView(R.id.score_viewpage)
    ViewPager viewpage;

    private View curSelectBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);

        toggleBarSelect(scoreBarOut);

        initViewPage();
    }

    private void initViewPage() {
        final List<Fragment> list = new ArrayList<>();
        list.add(ScoreFragment.newInstance(ScoreFragment.inOrOut_out));
        list.add(ScoreFragment.newInstance(ScoreFragment.inOrOut_in));

        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }
        };

        viewpage.setAdapter(pagerAdapter);

        viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    toggleBarSelect(scoreBarOut);
                }else{
                    toggleBarSelect(scoreBarIn);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("积分管理");
        right.setText("充值");
        right.setVisibility(View.VISIBLE);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GoToHelp.go(ScoreActivity.this, ReChangeScoreActivity.class);
            }
        });
    }

    @OnClick({R.id.score_bar_out, R.id.score_bar_in})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.score_bar_out:
                //赠送
                viewpage.setCurrentItem(0);
                break;
            case R.id.score_bar_in:
                //收入
                viewpage.setCurrentItem(1);
                break;
        }
    }

    /**
     * 切换bar
     */
    private void toggleBarSelect(View iv) {

        if (iv == scoreBarOut) {
            //赠送
            if (curSelectBar == scoreBarOut) return;
            curSelectBar = scoreBarOut;
            scoreBarIn.setImageResource(R.drawable.shape_huatiao_3);
            scoreBarOut.setImageResource(R.drawable.shape_huatiao_6);

        } else {
            //收入
            if (curSelectBar == scoreBarIn) return;
            curSelectBar = scoreBarIn;
            scoreBarIn.setImageResource(R.drawable.shape_huatiao_6);
            scoreBarOut.setImageResource(R.drawable.shape_huatiao_3);
        }

    }

}
