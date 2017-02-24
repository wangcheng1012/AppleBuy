package com.dw.applebuy.ui;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.loginreg.LoginActivity;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.wlj.base.ui.BaseFragmentActivity;
import com.wlj.base.util.AppContext;
import com.wlj.base.util.ExecutorServices;
import com.wlj.base.util.UIHelper;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class Loading extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        AcpOptions build = new AcpOptions.Builder().setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE).build();
        Acp.getInstance(this).request(build ,new AcpListener() {
            @Override
            public void onGranted() {
                next();
            }

            @Override
            public void onDenied(List<String> permissions) {
                UIHelper.toastMessage(Loading.this, permissions.toString() + "权限拒绝");
                finish();
            }
        });

    }

    private void next() {
        ExecutorServices.getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Class cla = LoginActivity.class;
                if(AppContext.getAppContext().islogin()){
                    cla = MainActivity.class;
                }

                Intent intent = new Intent(Loading.this,cla );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
