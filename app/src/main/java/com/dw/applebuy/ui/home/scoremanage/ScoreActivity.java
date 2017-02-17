package com.dw.applebuy.ui.home.scoremanage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.scoremanage.m.ScoreListResult;
import com.wlj.base.util.GoToHelp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
                if (position == 0) {
                    toggleBarSelect(scoreBarOut);
                } else {
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

    /**
     *
     * @param score
     */
    private void setUsertoalnumber(int score) {
        usertoalnumber.setVisibility(View.VISIBLE);
        String scoreStr = "共赠送积分 " + score;
        SpannableStringBuilder builder = new SpannableStringBuilder(scoreStr);

        ForegroundColorSpan yellowSpan = new ForegroundColorSpan(getResources().getColor(R.color.yellow_FF552E));

        builder.setSpan(yellowSpan, 5, scoreStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        usertoalnumber.setText(builder);

    }

    public void changeShow(ScoreListResult.InfoBean  info,int inOrOut){

        if(info == null )return;

        if(ScoreFragment.inOrOut_out == inOrOut) {
            scoreNumber.setText(info.getMonth_total()+"");
            scoreText.setText("本月赠送积分");
            setUsertoalnumber(info.getTotal());

        }else{
            scoreNumber.setText(info.getBalance()+"");
            scoreText.setText("账户剩余积分");
            usertoalnumber.setVisibility(View.GONE);
        }
    }


}
