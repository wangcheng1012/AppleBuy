package com.dw.applebuy.ui.home.scoremanage;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.wlj.base.util.GoToHelp;

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
    TextView userUsertoalnumber;
    @BindView(R.id.score_viewpage)
    ViewPager viewpage;

    private  ImageView curSelectBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);

        toggleBarSelect();
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("积分管理");
        right.setText("充值");
        right.setVisibility(View.VISIBLE);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GoToHelp.go(ScoreActivity.this, ScoreActivity.class);
            }
        });
    }

    @OnClick({R.id.score_bar_out, R.id.score_bar_in})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.score_bar_out:
                toggleBarSelect();
                break;
            case R.id.score_bar_in:
                toggleBarSelect();
                break;
        }
    }

    /**
     * 切换bar
     */
   private void toggleBarSelect(){

       if(curSelectBar == scoreBarOut){
           curSelectBar = scoreBarIn;
           scoreBarIn.setSelected(true);
           scoreBarOut.setSelected(false);
       }else{
           curSelectBar = scoreBarOut;
           scoreBarIn.setSelected(false);
           scoreBarOut.setSelected(true);
       }
   }
}
