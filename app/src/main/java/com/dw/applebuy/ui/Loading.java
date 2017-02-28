package com.dw.applebuy.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.dw.applebuy.R;
import com.dw.applebuy.base.api.FactoryInters;
import com.dw.applebuy.been.UpdateBean;
import com.dw.applebuy.ui.loginreg.LoginActivity;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.orhanobut.logger.Logger;
import com.rxmvp.http.ServiceFactory;
import com.rxmvp.http.exception.ApiException;
import com.rxmvp.http.exception.ErrorType;
import com.rxmvp.subscribers.CommonSubscriber;
import com.rxmvp.transformer.DefaultTransformer;
import com.wlj.base.ui.BaseFragmentActivity;
import com.wlj.base.update.DownLoadManager;
import com.wlj.base.util.AppContext;
import com.wlj.base.util.ExecutorServices;
import com.wlj.base.util.Log;
import com.wlj.base.util.UIHelper;

import java.io.File;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Loading extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        AcpOptions build = new AcpOptions.Builder().setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).build();
        Acp.getInstance(this).request(build, new AcpListener() {
            @Override
            public void onGranted() {
                check();
            }

            @Override
            public void onDenied(List<String> permissions) {
                UIHelper.toastMessage(Loading.this, permissions.toString() + "权限拒绝");
                finish();
            }
        });

    }

    private void skipLogin() {
        Class cla = LoginActivity.class;
        if (AppContext.getAppContext().islogin()) {
            cla = MainActivity.class;
        }

        Intent intent = new Intent(Loading.this, cla);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    /**
     * 检查版本
     */
    private void check() {
        ServiceFactory
                .createService(FactoryInters.class)
                .automaticUpdate(2)
                .compose(new DefaultTransformer<UpdateBean>())
                .subscribe(new CommonSubscriber<UpdateBean>(this) {
                    @Override
                    public void onNext(UpdateBean updateBean) {
//                        updateBean.setVersion("1");
//                        updateBean.setDownload_url("http://qiniu-app.pgyer.com/6f8516a5952ec867888eb2b2a50779df.apk");
                        complate(updateBean);
                    }

                    @Override
                    protected void onError(ApiException ex) {
                        if (ex.code == ErrorType.HTTP_ERROR) {
                            super.onError(ex);
                            finish();
                        } else {
                            skipLogin();
                        }
                    }
                });
    }

    /**
     * 版本比较
     *
     * @param updateBean
     */
    private void complate(final UpdateBean updateBean) {
        String version_local = getVersionName();
        if (version_local != null) {
            String build_code = updateBean.getVersion();
            if (build_code == null) {
                Logger.e("服务器版本号空");
            }
            int build_code_ = updateBean.codeHandle(build_code);
            int version_local_ = updateBean.codeHandle(version_local);
            Logger.i("build_code_: %s ,version_local_: %s ", build_code_, version_local_);
            if (build_code_ > version_local_) {

                updateTip(updateBean);
            } else {
                ExecutorServices.getExecutorService().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        skipLogin();
                    }
                });

            }
        }
    }

    private void updateTip(final UpdateBean updateBean) {
        new SweetAlertDialog(Loading.this)
                .setTitleText(updateBean.getTitle())
                .setContentText(updateBean.getMessage())
                .setConfirmText("立即更新")
                .setCancelText("稍后再说")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dowanLoad(updateBean.getDownload_url());
                        sweetAlertDialog.dismiss();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
//                        throw new RuntimeException("取消更新");
                        skipLogin();
                    }
                }).show();
    }

    /**
     * 下载apk
     *
     * @param download_url
     */
    private void dowanLoad(final String download_url) {
        // 进度条对话框
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setCancelable(false);
        // 更新时候的启动按钮
        pd.setButton(DialogInterface.BUTTON_POSITIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DownLoadManager.setCancelUpdate(true);
            }
        });
        pd.setMessage("正在下载...");
        pd.show();

        Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                File file = null;
                try {
                    file = DownLoadManager.getFileFromServer(download_url, pd);
                } catch (Exception e) {
                    if (subscriber.isUnsubscribed()) {
                        subscriber.unsubscribe();
                    }
                    throw new RuntimeException("安装包下载失败", e);
                }
                subscriber.onNext(file);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<File>() {
                    @Override
                    public void onCompleted() {
                        pd.dismiss(); // 结束掉进度条对话框
                    }

                    @Override
                    public void onError(Throwable e) {
                        pd.dismiss(); // 结束掉进度条对话框
                        UIHelper.toastMessage(Loading.this, e.getMessage());
                        skipLogin();
                    }

                    @Override
                    public void onNext(File file) {
                        installApk(file);
                    }
                });
    }

    // 安装apk
    public void installApk(File file) {
        // System.out.println("开始安装");
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        // 执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
        finish();
    }

    /**
     * 获取当前程序的版本号
     */
    public String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = AppContext.getAppContext().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(AppContext.getAppContext().getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(e, "获取本地版本号异常");

        }
        return null;
    }
}
