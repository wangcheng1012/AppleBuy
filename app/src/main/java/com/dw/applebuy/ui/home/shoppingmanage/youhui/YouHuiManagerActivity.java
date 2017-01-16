package com.dw.applebuy.ui.home.shoppingmanage.youhui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.add.YouHuiAddActivity;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.showing.TabLayoutFragment;
import com.wlj.base.util.GoToHelp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YouHuiManagerActivity extends AppCompatActivity {

    @BindView(R.id.youhuimanager_RadioGroup)
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhuimanager);
        ButterKnife.bind(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment fragment = TabLayoutFragment.newInstance(TabLayoutFragment.SHOEING);
                switch (checkedId) {
                    case R.id.youhuimanager_showing://展示中
                        fragment = TabLayoutFragment.newInstance(TabLayoutFragment.SHOEING);
                        break;
                    case R.id.youhuimanager_xiajia://下架
                        fragment = TabLayoutFragment.newInstance(TabLayoutFragment.XIAJIA);
                        break;
                    case R.id.youhuimanager_kind://分类
                        fragment = TabLayoutFragment.newInstance(TabLayoutFragment.XIAJIA);
                        break;
                }
                transaction.replace(R.id.titletab_content, fragment);
                transaction.commit();
            }
        });

        ((RadioButton) (radioGroup.getChildAt(0))).setChecked(true);
    }

    @OnClick({R.id.youfei_back, R.id.youfei_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.youfei_back:
                finish();
                break;
            case R.id.youfei_add:
                GoToHelp.go(this,YouHuiAddActivity.class);
                break;

        }
    }

}
