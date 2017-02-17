package com.dw.applebuy.ui;

import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.home.HomeFragment;
import com.dw.applebuy.ui.home.renzheng.p.InfoUtil;
import com.dw.applebuy.ui.message.MessageFragment;
import com.dw.applebuy.ui.set.SetFragment;
import com.dw.applebuy.ui.songjifen.JiFenFragment;
import com.dw.applebuy.util.RenZhengHelp;
import com.wlj.base.ui.BaseFragmentActivity;
import com.wlj.base.util.AppContext;
import com.wlj.base.util.AppManager;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends BaseFragmentActivity {

    private FragmentTabHost myTabhost;
    //Tab图片
    private int mImages[] = {R.drawable.tab_apple_selector, R.drawable.tab_jifen_selector, R.drawable.tab_message_selector, R.drawable.tab_set_selector};
    //标记
    private String mFragmentTags[] = {"首页", "送积分", "消息", "设置"};
    //加载的Fragment
    private Class mFragment[] = {HomeFragment.class, JiFenFragment.class, MessageFragment.class, SetFragment.class};
    public String authenticate_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //语句的作用是让手机屏幕保持一种不暗不关闭的效果
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabHost();

        InfoUtil.infoUpdate = true;
//        InfoUtil.getInstall().getInfo(this, new InfoUtil.InfoBack() {
//            @Override
//            public void back(Info info) {
//                  authenticate_status = info.getAuthenticate_status();
//            }
//        });
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
        myTabhost.getTabWidget().getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RenZhengHelp.renzheng_ed.equals(authenticate_status)) {
                    //执行默认点击操作
                    myTabhost.setCurrentTab(1);
                    myTabhost.getTabWidget().requestFocus(View.FOCUS_FORWARD);
                } else {
                    //dialog
//                    UIHelper.dialog(MainActivity.this, "你暂未通过商家认证", null,null);
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("你暂未通过商家认证")
//                            .setContentText("你暂未通过商家认证")
                            .setConfirmText("确认")
                            .show();
                }
            }
        });
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
