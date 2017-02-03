package com.dw.applebuy.ui.home.renzheng;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.shoppingmanage.album.UpLoadImageActivity;
import com.wlj.base.util.GoToHelp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 资质认证
 */
public class RenZhengActivity extends AppCompatActivity implements Title1Fragment.TitleInterface {

    @BindView(R.id.renzheng_name)
    TextView renzhengName;
    @BindView(R.id.renzheng_addr)
    TextView renzhengAddr;
    @BindView(R.id.renheng_type_faren)
    TextView renhengTypeFaren;
    @BindView(R.id.renheng_type_business)
    TextView renhengTypeBusiness;
    @BindView(R.id.renheng_BusinessLicense_name)
    EditText renhengBusinessLicenseName;
    @BindView(R.id.renheng_BusinessLicense_number)
    EditText renhengBusinessLicenseNumber;
    @BindView(R.id.renheng_username)
    EditText renhengUsername;
    @BindView(R.id.renheng_cardnumber)
    EditText renhengCardnumber;
    @BindView(R.id.renheng_email)
    EditText renhengEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zheng);
        ButterKnife.bind(this);

        renhengTypeFaren.setSelected(true);
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("资质认证");
    }

    @OnClick({R.id.renheng_type_faren, R.id.renheng_type_business, R.id.renheng_BusinessLicense, R.id.renheng_card, R.id.renheng_submitbt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.renheng_type_faren:
                renhengTypeFaren.setSelected(true);
                renhengTypeBusiness.setSelected(false);
                break;
            case R.id.renheng_type_business:
                renhengTypeFaren.setSelected(false);
                renhengTypeBusiness.setSelected(true);
                break;
            case R.id.renheng_BusinessLicense:
                Bundle bundle = new Bundle();
                bundle.putString("tip","请上传清晰、真实的营业执照照片");
                bundle.putInt("requestCode",UpLoadImageActivity.renheng_BusinessLicense);
                GoToHelp.goResult(this,UpLoadImageActivity.class,UpLoadImageActivity.renheng_BusinessLicense,bundle);
                break;
            case R.id.renheng_card:
                Bundle bundle2 = new Bundle();
                bundle2.putString("tip","手持证件人面部无遮挡，五官清晰可见\n身份证各项信息及头像均清晰可见，无遮挡");
                bundle2.putInt("requestCode",UpLoadImageActivity.renheng_card);
                GoToHelp.goResult(this,UpLoadImageActivity.class,UpLoadImageActivity.renheng_card,bundle2);
                break;
            case R.id.renheng_submitbt:

                break;
        }
    }
}
