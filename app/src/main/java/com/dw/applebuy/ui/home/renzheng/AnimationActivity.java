package com.dw.applebuy.ui.home.renzheng;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.wlj.base.util.ExecutorServices;
import com.wlj.base.util.GoToHelp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimationActivity extends AppCompatActivity {

    @BindView(R.id.animation_bg)
    ImageView animationBg;
    @BindView(R.id.animation_x)
    TextView animationX;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.animation_comment)
    ImageView animationComment;
    @BindView(R.id.animation_shopping)
    ImageView animationShopping;
    @BindView(R.id.animation_v)
    ImageView animationV;
    @BindView(R.id.animation_button)
    Button animationButton;
    @BindView(R.id.animation_consume)
    ImageView animationConsume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);

        initView();
    }
    int startOffset = 100;
    private void initView() {
//        animation(animationV ,animationComment,animationShopping,animationConsume,textView3,textView4,animationButton);
        Animation animation = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.scale_sf);
        animationV.startAnimation(animation);
        animationV.setVisibility(View.VISIBLE);

        startOffset += 350;
        ScaleAnimation animation_consume = new ScaleAnimation(0, 1.2f, 0, 1.2f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        animation_consume.setDuration(350);
        animation_consume.setInterpolator(new AnticipateOvershootInterpolator());
        animation_consume.setStartOffset(startOffset);
        animationConsume.startAnimation(animation_consume);
        animationConsume.setVisibility(View.VISIBLE);

        startOffset += 200;
        ScaleAnimation animation_shopping = new ScaleAnimation(0, 1.2f, 0, 1.2f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        animation_shopping.setDuration(350);
        animation_shopping.setInterpolator(new AnticipateOvershootInterpolator());
        animation_shopping.setStartOffset(startOffset);
        animationShopping.startAnimation(animation_shopping);
        animationShopping.setVisibility(View.VISIBLE);

        startOffset += 100;
        ScaleAnimation animation_comment = new ScaleAnimation(0, 1.2f, 0, 1.2f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        animation_comment.setDuration(350);
        animation_comment.setInterpolator(new AnticipateOvershootInterpolator());
        animation_comment.setStartOffset(startOffset);
        animationComment.startAnimation(animation_comment);
        animationComment.setVisibility(View.VISIBLE);

        startOffset += 200;
        AlphaAnimation animation_textView3 = new AlphaAnimation(0.0f,1.0f);
        animation_textView3.setDuration(350);
        animation_textView3.setInterpolator(new AnticipateOvershootInterpolator());
        animation_textView3.setStartOffset(startOffset);
        textView3.startAnimation(animation_textView3);
        textView3.setVisibility(View.VISIBLE);

        AlphaAnimation animation_textView4 = new AlphaAnimation(0.0f,1.0f);
        animation_textView4.setDuration(350);
        animation_textView4.setInterpolator(new AnticipateOvershootInterpolator());
        animation_textView4.setStartOffset(startOffset);
        textView4.startAnimation(animation_textView4);
        textView4.setVisibility(View.VISIBLE);

        AlphaAnimation animation_animationButton = new AlphaAnimation(0.0f,1.0f);
        animation_animationButton.setDuration(350);
        animation_animationButton.setInterpolator(new AnticipateOvershootInterpolator());
        animation_animationButton.setStartOffset(startOffset);
        animationButton.startAnimation(animation_animationButton);
        animationButton.setVisibility(View.VISIBLE);

    }
//
//    private void animation(View... views){
//        if(views == null )return;
//        animation( new ArrayList<View>(Arrays.asList(views)));
//    }
//
//    private void animation(final List<View> views) {
//        if(views.size() <= 0)return;
//        Animation animation = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.scale_sf);
//        views.get(0).startAnimation(animation);
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
////                s.p
//            }
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                View remove = views.remove(0);
//                remove.setVisibility(View.VISIBLE);
//                animation(views);
//            }
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//    }


    @OnClick({R.id.animation_x, R.id.animation_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.animation_button:
            GoToHelp.go(this, RenZhengActivity.class);
//                break;
            case R.id.animation_x:
                finish();
                break;
        }
    }
}
