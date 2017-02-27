package com.dw.applebuy.ui.home.commentmanage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.wlj.base.ui.BaseFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 评论管理
 */
public class CommentActivity extends BaseFragmentActivity implements Title1Fragment.TitleInterface {

    @BindView(R.id.commont_total)
    TextView total;
    @BindView(R.id.commont_good)
    TextView good;
    @BindView(R.id.commont_bad)
    TextView bad;
    @BindView(R.id.comment_content)
    FrameLayout content;
    private CommentFragment commentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        commentFragment = CommentFragment.newInstance(0);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.comment_content, commentFragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("评论管理");
    }

    public void changShow(final CommentListBean.InfoBean info) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                total.setText(getSpannableString("总评价\n" + info.getTotal()));
                good.setText(getSpannableString("好价\n" + info.getNice()));
                bad.setText(getSpannableString("差价\n" + info.getPoor()));
            }
        });

    }

    @NonNull
    private SpannableString getSpannableString(String total) {
        SpannableString builder = new SpannableString(total);
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.yellow_FF552E));
        builder.setSpan(span, total.indexOf("\n"), total.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new RelativeSizeSpan(2f), total.indexOf("\n"), total.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    @OnClick({R.id.commont_total, R.id.commont_good, R.id.commont_bad})
    public void onClick(View view) {
        if(commentFragment == null )return;

        switch (view.getId()) {
            case R.id.commont_total:
                commentFragment.onRefresh(0);
                break;
            case R.id.commont_good:
                commentFragment.onRefresh(1);
                break;
            case R.id.commont_bad:
                commentFragment.onRefresh(2);
                break;
        }
    }
}
