package com.dw.applebuy.ui.set;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUsActivity extends AppCompatActivity implements Title1Fragment.TitleInterface {

    @BindView(R.id.aboutus_version)
    TextView aboutusVersion;
    @BindView(R.id.aboutus_content)
    TextView aboutusContent;
    @BindView(R.id.aboutus_phone)
    TextView aboutusPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String aboutUs = intent.getStringExtra("AboutUs");
        aboutusContent.setText(Html.fromHtml(aboutUs));

        aboutusPhone.setText(String.format(getString(R.string.about_phone),23423+""));

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            aboutusVersion.setText(String.format(getString(R.string.about_version), packageInfo.versionCode+""));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("关于苹果购");
    }
}
