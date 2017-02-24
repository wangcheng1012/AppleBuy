package com.dw.applebuy.ui.home.renzheng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.been.Info;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.renzheng.p.InfoUtil;
import com.dw.applebuy.ui.home.renzheng.p.RenZhengPresenter;
import com.dw.applebuy.ui.home.renzheng.v.Contract;
import com.dw.applebuy.ui.home.shoppingmanage.album.UpLoadImageActivity;
import com.dw.applebuy.ui.home.shoppingmanage.data.DataActivity;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.wlj.base.util.AppConfig;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dw.applebuy.ui.home.shoppingmanage.album.UpLoadImageActivity.renheng_BusinessLicense;
import static com.dw.applebuy.ui.home.shoppingmanage.album.UpLoadImageActivity.renheng_card;

/**
 * 资质认证
 */
public class RenZhengActivity extends BaseMvpActivity<Contract.RenZhengView, RenZhengPresenter> implements Title1Fragment.TitleInterface, Contract.RenZhengView {

    @BindView(R.id.renzheng_data_textview)
    TextView renzhengTextView;
    @BindView(R.id.renzheng_data_layoutview)
    RelativeLayout renzhengLayoutView;
    @BindView(R.id.renzheng_name)
    TextView renzhengName;
    @BindView(R.id.renzheng_addr)
    TextView renzhengAddr;
    @BindView(R.id.renheng_type_faren)
    TextView renhengTypeFaren;
    @BindView(R.id.renheng_type_business)
    TextView renhengTypeBusiness;
    @BindView(R.id.renheng_BusinessLicense_name)
    EditText name;
    @BindView(R.id.renheng_BusinessLicense_number)
    EditText register_code;
    @BindView(R.id.renheng_username)
    EditText legal_person;
    @BindView(R.id.renheng_cardnumber)
    EditText legal_person_code;
    @BindView(R.id.renheng_email)
    EditText email;

    @BindView(R.id.renheng_BusinessLicense)
    TextView renhengBusinessLicense;
    @BindView(R.id.renheng_card)
    TextView renhengCard;

    private Info info;
    private int DataRequestCode = 1122;
    /**
     * 营业执照照片 路径
     */
    private String business_license;
    /**
     * 身份证照片 路径
     */
    private String certificate;

    private int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zheng);
        ButterKnife.bind(this);

        renhengTypeFaren.setSelected(true);
        initView();
    }

    private void initView() {
        switchDataView();
    }

    private void switchDataView() {
        //资料view切换
        InfoUtil.getInstall().getInfo(this, new InfoUtil.InfoBack() {
            @Override
            public void back(Info info) {

                RenZhengActivity.this.info = info;
                String hours = info.getBusiness_hoursShow();

                if (StringUtils.isEmpty(hours)) {

                    renzhengTextView.setVisibility(View.VISIBLE);
                    renzhengLayoutView.setVisibility(View.GONE);
                } else {

                    renzhengTextView.setVisibility(View.GONE);
                    renzhengLayoutView.setVisibility(View.VISIBLE);

                    renzhengName.setText(info.getName() + "");
                    renzhengAddr.setText(info.getAll_address() + "");

                }
            }
        });
    }


    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("资质认证");
    }

    @OnClick({R.id.renheng_type_faren, R.id.renheng_type_business, R.id.renheng_BusinessLicense, R.id.renheng_card, R.id.renheng_submitbt, R.id.renzheng_data_textview, R.id.renzheng_data_layoutview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.renheng_type_faren:
                renhengTypeFaren.setSelected(true);
                renhengTypeBusiness.setSelected(false);
                type = 1;
                break;
            case R.id.renheng_type_business:
                renhengTypeFaren.setSelected(false);
                renhengTypeBusiness.setSelected(true);
                type = 2;
                break;
            case R.id.renheng_BusinessLicense:
                Bundle bundle = new Bundle();
                bundle.putString("tip", "请上传清晰、真实的营业执照照片");
                bundle.putInt("requestCode", renheng_BusinessLicense);
                bundle.putString("path",business_license);
                GoToHelp.goResult(this, UpLoadImageActivity.class, renheng_BusinessLicense, bundle);
                break;
            case R.id.renheng_card:
                Bundle bundle2 = new Bundle();
                bundle2.putString("tip", "手持证件人面部无遮挡，五官清晰可见\n身份证各项信息及头像均清晰可见，无遮挡");
                bundle2.putInt("requestCode", renheng_card);
                bundle2.putString("path",certificate);
                GoToHelp.goResult(this, UpLoadImageActivity.class, renheng_card, bundle2);
                break;
            case R.id.renheng_submitbt:

                ArrayMap<String, Object> arrayMap = new ArrayMap<>();
                arrayMap.put("sessionid", AppConfig.getAppConfig().get(AppConfig.CONF_KEY));
                arrayMap.put("name", name.getText() + "");
                arrayMap.put("register_code", register_code.getText() + "");
                arrayMap.put("legal_person", legal_person.getText() + "");
                arrayMap.put("legal_person_code", legal_person_code.getText() + "");
                arrayMap.put("email", email.getText() + "");
                arrayMap.put("type", type);//	1:企业 2：个体

                presenter.submit(arrayMap, business_license, certificate);

                break;
            case R.id.renzheng_data_textview:
                //
            case R.id.renzheng_data_layoutview:
                //
                GoToHelp.goResult(this, DataActivity.class, DataRequestCode);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;

        if (requestCode == DataRequestCode) {
            initView();
        } else if (requestCode == renheng_BusinessLicense) {
            //选择营业执照 照片
            business_license = data.getStringExtra("path");
            if(!StringUtils.isEmpty(business_license)) {
                renhengBusinessLicense.setText("已上传");
            }
        } else if (requestCode == renheng_card) {
            //选择身份证 照片
            certificate = data.getStringExtra("path");
            if(!StringUtils.isEmpty(certificate)) {
                renhengCard.setText("已上传");
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        InfoUtil.getInstall().destroy();
    }

    @Override
    public RenZhengPresenter initPresenter() {
        return new RenZhengPresenter();
    }

    @Override
    public void submitBack() {
        finish();
    }
}
