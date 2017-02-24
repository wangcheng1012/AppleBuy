package com.dw.applebuy.ui.songjifen;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 赠送成功页面
 * 提交审核成功
 */
public class GiveSuccessActivity extends AppCompatActivity implements Title1Fragment.TitleInterface {

    @BindView(R.id.give_image)
    ImageView giveImage;
    @BindView(R.id.give_name)
    TextView giveName;
    @BindView(R.id.give_intro)
    TextView giveIntro;

    public static String FROM_RENZHENG = "FROM_RENZHENG";
    private String from;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_success);
        ButterKnife.bind(this);

        if (FROM_RENZHENG.equals(from)) {
            giveImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_47_uploadsuccess));
            giveName.setText("提交成功 等待审核");
            giveName.setTextColor(getResources().getColor(R.color.yellow_FF552E));
            giveName.setTextSize(25f);

            giveIntro.setText("审核结果将在48小时内以短信的形式通知你，\n请注意查收！");
            giveIntro.setGravity(Gravity.CENTER);
        } else {

            giveIntro.setText(score + "积分");
        }
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        score = intent.getIntExtra("jifen", 0);
        if (FROM_RENZHENG.equals(from)) {
            title.setText("提交成功");
        } else {
            title.setText("赠送成功");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
