package com.dw.applebuy.ui.pay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;

/**
 * 充值返回
 */
public class RechangeBackActivity extends AppCompatActivity implements Title1Fragment.TitleInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechange_back);




    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("充值成功");
    }
}
