package com.dw.applebuy.ui.home.commentmanage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;

public class CommentActivity extends AppCompatActivity implements Title1Fragment.TitleInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);



    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("评论管理");
    }
}
