package com.dw.applebuy.ui.home.shoppingmanage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.shoppingmanage.album.AlbumActivity;
import com.dw.applebuy.ui.home.shoppingmanage.data.DataActivity;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.YouHuiManagerActivity;
import com.wlj.base.ui.BaseFragmentActivity;
import com.wlj.base.util.GoToHelp;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingManagerActivity extends BaseFragmentActivity implements Title1Fragment.TitleInterface {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_manager);
        ButterKnife.bind(this);
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("门店管理");
    }

    @OnClick({R.id.shoppingmanager_youhuimanager, R.id.shoppingmanager_phontomanager, R.id.shoppingmanager_ziliaomanager})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shoppingmanager_youhuimanager:
//                优惠管理
                GoToHelp.go(this,YouHuiManagerActivity.class);
                break;
            case R.id.shoppingmanager_phontomanager:
//                相册管理
                GoToHelp.go(this,AlbumActivity.class);
                break;
            case R.id.shoppingmanager_ziliaomanager:
//                资料管理
                GoToHelp.go(this,DataActivity.class);
                break;
        }
    }
}
