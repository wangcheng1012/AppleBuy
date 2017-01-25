package com.dw.applebuy.ui.pay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.been.RechageScoreOrder;
import com.dw.applebuy.ui.Title1Fragment;
import com.google.gson.Gson;
import com.hd.wlj.third.pay.WeiXinPay;
import com.wlj.base.util.GoToHelp;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * PayActivity支付页面
 */
public class PayActivity extends AppCompatActivity implements Title1Fragment.TitleInterface {

    //    public static String WX_PAYRESULT_ACTION = "com.dw.applebuy.ui.pay.WXPayActivity";
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
    private String payStr;
    private RechageScoreOrder rechageScoreOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);

        payZhifubao.setSelected(true);
        curPayWay = payZhifubao;

        /**
         bundle.putParcelable("RechageScoreOrder",rechageScoreOrder);
         bundle.putString("money",moneyView.getText()+"");
         bundle.putString("scoreTotal",scoreTotal.getText()+"");
         */
        Intent intent = getIntent();
        rechageScoreOrder = intent.getParcelableExtra("RechageScoreOrder");
        String money = intent.getStringExtra("money");
        String scoreTotal = intent.getStringExtra("scoreTotal");
        payMoney.setText("¥"+money);
        payScore.setText("充值积分  "+scoreTotal);

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

//                GoToHelp.go(this, RechangeBackActivity.class);
                if (curPayWay == payWeixin) {
                    try {
                        RechageScoreOrder.WxParamBean wx_param = rechageScoreOrder.getWx_param();
                        Gson gson = new Gson();
                        String s = gson.toJson(wx_param);
                        WeiXinPay.pay(getApplicationContext(), new JSONObject(s));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("支付");
    }
}
