package com.dw.applebuy.ui;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.base.api.AppHttpMethods;
import com.dw.applebuy.been.Info;
import com.dw.applebuy.ui.home.HomeFragment;
import com.dw.applebuy.ui.home.renzheng.p.InfoUtil;
import com.dw.applebuy.ui.message.MessageFragment;
import com.dw.applebuy.ui.set.SetFragment;
import com.dw.applebuy.ui.songjifen.JiFenFragment;
import com.orhanobut.logger.Logger;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.util.AppConfig;
import com.wlj.base.util.AppContext;
import com.wlj.base.util.AppManager;
import com.wlj.base.util.UIHelper;

import java.io.File;
import java.io.Serializable;

import rx.Subscriber;


public class MainActivity extends AppCompatActivity {

    private FragmentTabHost myTabhost;
    //Tab图片
    private int mImages[] = {R.drawable.tab_apple_selector, R.drawable.tab_jifen_selector, R.drawable.tab_message_selector, R.drawable.tab_set_selector};
    //标记
    private String mFragmentTags[] = {"首页", "送积分", "消息", "设置"};
    //加载的Fragment
    private Class mFragment[] = {HomeFragment.class, JiFenFragment.class, MessageFragment.class, SetFragment.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //语句的作用是让手机屏幕保持一种不暗不关闭的效果
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabHost();

        InfoUtil.infoUpdate = true;
    }


    private void initTabHost() {
        myTabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        myTabhost.setup(this, getSupportFragmentManager(), R.id.container_fragment);
        //去掉分割线
        myTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        for (int i = 0; i < mImages.length; i++) {
            //对Tab按钮添加标记和图片
            TabHost.TabSpec tabSpec = myTabhost.newTabSpec(mFragmentTags[i]).setIndicator(getTabView(i));
            //添加Fragment
            myTabhost.addTab(tabSpec, mFragment[i], null);
            myTabhost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.white);
//            myTabhost.getTabWidget().setCurrentTab(1);
//            myTabhost.getTabWidget().getChildTabViewAt(2).setSelected(true);

        }

    }

    //获取图片资源
    private View getTabView(int index) {
        View view = getLayoutInflater().inflate(R.layout.main_tabbar, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.maintabar_image);
        TextView text = (TextView) view.findViewById(R.id.maintabar_text);
        imageView.setImageResource(mImages[index]);
        text.setText(mFragmentTags[index]);
        return view;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().AppExit(getApplicationContext());
        AppContext.getAppContext().loginOut();
    }



}
