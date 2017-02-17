package com.dw.applebuy.ui.message;

import android.os.Bundle;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.wlj.base.ui.BaseFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageDetailActivity extends BaseFragmentActivity implements Title1Fragment.TitleInterface {

    @BindView(R.id.message_detail_time)
    TextView time;
    @BindView(R.id.message_detail_title)
    TextView title;
    @BindView(R.id.message_detail_content)
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);

        MessageBean messageBean = getIntent().getParcelableExtra("MessageBean");
        time.setText(messageBean.getAddtime());
        title.setText(messageBean.getTitle());
        content.setText(messageBean.getContent());



    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("消息详情");
    }
}
