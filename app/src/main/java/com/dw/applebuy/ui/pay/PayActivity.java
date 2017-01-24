package com.dw.applebuy.ui.pay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.wlj.base.util.GoToHelp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * PayActivity支付页面
 */
public class PayActivity extends AppCompatActivity implements Title1Fragment.TitleInterface {

    @BindView(R.id.pay_money)
    TextView payMoney;
    @BindView(R.id.pay_score)
    TextView payScore;
    @BindView(R.id.pay_zhifubao)
    ImageView payZhifubao;
    @BindView(R.id.pay_weixin)
    ImageView payWeixin;
    @BindView(R.id.pay_submit)
    Button paySubmit;

    private ImageView curPayWay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);

        payZhifubao.setSelected(true);
        curPayWay = payZhifubao;
    }

    @OnClick({R.id.pay_zhifubao, R.id.pay_weixin, R.id.pay_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_zhifubao:
                if (curPayWay == payZhifubao) {
                    return;
                }
                payZhifubao.setSelected(true);
                payWeixin.setSelected(false);
                curPayWay = payZhifubao;

                break;
            case R.id.pay_weixin:
                if (curPayWay == payWeixin) {
                    return;
                }
                payZhifubao.setSelected(false);
                payWeixin.setSelected(true);
                curPayWay = payWeixin;
                break;
            case R.id.pay_submit:

                GoToHelp.go(this,RechangeBackActivity.class);
                break;
        }
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("支付");
    }
}
