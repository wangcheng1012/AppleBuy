package com.lling.photopicker.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TException;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.lling.photopicker.PhotoPickerActivity;
import com.lling.photopicker.R;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.orhanobut.logger.Logger;
import com.wlj.base.util.AppConfig;
import com.wlj.base.util.UIHelper;
import com.wlj.base.util.img.ImageFileCache;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * 裁剪
 */
public class TakePhotoCrop implements InvokeListener, TakePhoto.TakeResultListener {


    /**
     * 注意   这个要加载 调用页面,和在onCreate onSaveInstanceState onActivityResult 加对应的方法
     */

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        //以下代码为处理Android6.0、7.0动态权限所需
//        takePhotoCrop.onRequestPermissionsResult_(requestCode, permissions, grantResults);
//    }

    public final static int IMAGE = 223;
    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    private Activity mActivity;
    private CropBack mCropBack;

    public TakePhotoCrop(Activity mActivity, CropBack mCropBack) {
        this.mActivity = mActivity;
        this.mCropBack = mCropBack;
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(mActivity), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void takeSuccess(TResult result) {

        mCropBack.cropback(result);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Logger.i("takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        Logger.i(mActivity.getString(R.string.msg_operation_canceled));
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(mActivity, this));
        }
        return takePhoto;
    }

    /**
     * 裁剪
     *
     * @param data
     */
    public void onCrop(final Intent data) {
        //提示框
        new SweetAlertDialog(mActivity)
                .setTitleText("提示")
                .setContentText("是否裁剪？")
                .setConfirmText("确认")
                .setCancelText("取消")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();

                        CropOptions cropOptions = new CropOptions.Builder()
//                        .setAspectX(1).setAspectY(1)
                                .setWithOwnCrop(false).create();
                        try {

                            File file = new File(ImageFileCache.getCropCachePath());
                            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                            Uri imageUri = Uri.fromFile(file);

                            ArrayList<String> uristr = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT_URLSTR);

                            takePhoto.onCrop( Uri.parse(uristr.get(0)), imageUri, cropOptions);

                        } catch (TException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();

                        ArrayList<String> result = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                        mCropBack.cropback(TResult.of(TImage.of(result.get(0))));
                    }
                })
                .show();
    }

    public void onRequestPermissionsResult_(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(mActivity, type, invokeParam, this);
    }

    /**
     * 照片选择
     */
    public void photoPicker() {

        Acp.getInstance(mActivity).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.READ_EXTERNAL_STORAGE
//                                , Manifest.permission.READ_PHONE_STATE
                        ).build()
                , new AcpListener() {
                    @Override
                    public void onGranted() {
                        //选择 图片
                        Intent intent = new Intent(mActivity, PhotoPickerActivity.class);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
                        intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM);
                        mActivity.startActivityForResult(intent, IMAGE);
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        UIHelper.toastMessage(mActivity, permissions.toString() + "权限拒绝");
                    }
                });
    }

    public void removeCropCache() {
        ImageFileCache.removeCropCache();
    }

    /**
     * 没执行        takePhoto.onActivityResult(requestCode,resultCode,data); 妈蛋什么情况
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        takePhoto.onActivityResult(requestCode,resultCode,data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == TakePhotoCrop.IMAGE) {
                 onCrop(data);
            }

        }
    }

    public interface CropBack {
        /**
         * 裁剪返回
         *
         * @param result
         */
        void cropback(TResult result);
    }


}
