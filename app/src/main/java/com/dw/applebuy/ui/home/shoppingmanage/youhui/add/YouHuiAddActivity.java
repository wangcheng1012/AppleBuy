package com.dw.applebuy.ui.home.shoppingmanage.youhui.add;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.shoppingmanage.m.YouhuiQuanType;
import com.dw.applebuy.ui.home.shoppingmanage.p.YouHuiAddPresenter;
import com.dw.applebuy.ui.home.shoppingmanage.v.Contract;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.showing.m.Coupon;
import com.dw.applebuy.util.DayDialogFragment;
import com.jph.takephoto.model.TResult;
import com.lling.photopicker.PhotoPickerActivity;
import com.lling.photopicker.utils.TakePhotoCrop;
import com.orhanobut.logger.Logger;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.wlj.base.util.AppConfig;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.MathUtil;
import com.wlj.base.util.StringUtils;
import com.wlj.base.util.img.ImageFileCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 添加优惠券
 */
public class YouHuiAddActivity extends BaseMvpActivity<Contract.YouHuiAddView, YouHuiAddPresenter> implements Title1Fragment.TitleInterface, DayDialogFragment.TimeChoosed, Contract.YouHuiAddView, TakePhotoCrop.CropBack {

    @BindView(R.id.youhuiadd_image)
    ImageView imageView;
    @BindView(R.id.youhuiadd_type)
    TextView type;
    @BindView(R.id.youhuiadd_time)
    TextView time;
    @BindView(R.id.youhuiadd_title)
    EditText title;
    @BindView(R.id.youhuiadd_intro)
    EditText intro;
    @BindView(R.id.youhuiadd_price)
    EditText youhuiaddPrice;
    @BindView(R.id.youhuiadd_number)
    EditText number;
    @BindView(R.id.youhuiadd_complate)
    Button youhuiaddComplate;

    private final int RequestCode_type = 22;

    private TakePhotoCrop takePhotoCrop;

    private String imagePath;
    private Coupon oldCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        takePhotoCrop = new TakePhotoCrop(this, this);
        takePhotoCrop.getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_hui_add);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        oldCoupon = intent.getParcelableExtra("Coupon");
        if (oldCoupon != null) {
            initView(oldCoupon);
        }
    }

    private void initView(Coupon coupon) {
        Glide.with(this).load(coupon.getImgFromIndex(0)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        type.setText(coupon.getCategory_name());
        type.setTag(coupon.getCategory_id());
        long aLong = MathUtil.parseLong(coupon.getEnd_time());
        String time = StringUtils.getTime(aLong, "yyyy-MM-dd");
//        long millis = System.currentTimeMillis();
//        String time2 = StringUtils.getTime(millis, "yyyy-MM-dd");
        this.time.setText(time);
        title.setText(coupon.getTitle());
        intro.setText(coupon.getDescription());
        youhuiaddPrice.setText(coupon.getIntegral());
        number.setText(coupon.getStock());
    }

    @Override
    public YouHuiAddPresenter initPresenter() {
        return new YouHuiAddPresenter();
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("新增优惠");
    }

    @OnClick({R.id.youhuiadd_type, R.id.youhuiadd_time, R.id.youhuiadd_complate, R.id.youhuiadd_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.youhuiadd_image:
                takePhotoCrop.photoPicker();
                break;
            case R.id.youhuiadd_type:
                GoToHelp.goResult(this, YouHuiTypeActivity.class, RequestCode_type);
                break;
            case R.id.youhuiadd_time:
                DayDialogFragment dayDialogFragment = DayDialogFragment.newInstance();
                dayDialogFragment.show(getFragmentManager(), getClass().getSimpleName());

                break;
            case R.id.youhuiadd_complate:

                final ArrayMap<String, String> arrayMap = new ArrayMap<>();
                arrayMap.put("sessionid", AppConfig.getAppConfig().get(AppConfig.CONF_KEY));
                arrayMap.put("title", title.getText() + "");
                arrayMap.put("description", intro.getText() + "");
                arrayMap.put("category_id", type.getTag() + "");//分类ID
                arrayMap.put("stock", number.getText() + "");//库存
                arrayMap.put("integral", youhuiaddPrice.getText() + "");//积分
                arrayMap.put("end_time", time.getText() + "");//优惠时间
//                arrayMap.put("file", bitmap);//优惠卷图片
                if (oldCoupon != null) {
                    arrayMap.put("id", oldCoupon.getId());// 	优惠卷ID(传递则为编辑)
                    arrayMap.put("img_path", oldCoupon.getDb_imgFromIndex(0));//回传路径(传递则为编辑)
                    if (imagePath == null) {
                        //imagePath 为空，则没有更换图片，获取原有图片
                        presenter.originalImage(this,oldCoupon.getImgFromIndex(0),arrayMap,imagePath);
                    } else {
                        presenter.addYouHui(arrayMap, imagePath);
                    }

                } else {
                    presenter.addYouHui(arrayMap, imagePath);
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        takePhotoCrop.getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null) {
            return;
        }

        if (requestCode == this.RequestCode_type) {
            //优惠券类型
            YouhuiQuanType mYouhuiQuanType = (YouhuiQuanType) data.getParcelableExtra(YouHuiTypeActivity.RESULT_YouhuiQuanType);
            type.setText(mYouhuiQuanType.getName());
            type.setTag(mYouhuiQuanType.getId());
        } else if (requestCode == TakePhotoCrop.IMAGE) {
            //图片选择
            takePhotoCrop.onCrop(data);
        }

    }

    @Override
    public void choosedBack(long date) {
        String time = StringUtils.getTime(date, "yyyy-MM-dd");
        this.time.setText(time);
    }

    //——————————
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        takePhotoCrop.getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        takePhotoCrop.onRequestPermissionsResult_(requestCode, permissions, grantResults);
    }

    @Override
    public void cropback(TResult result) {

        imagePath = result.getImage().getPath();
        Logger.i("takeSuccess：" + imagePath);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageView.setImageBitmap(bitmap);

//        Bundle bundle = new Bundle();
//        bundle.putString("path", result.getImage().getPath());
//        bundle.putInt("from", ImageAdjustmentActivity.from_main_choosework);
//        GoToHelp.go(this, ImageAdjustmentActivity.class, bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        takePhotoCrop.removeCropCache();
    }

    @Override
    public void callBack() {
        finish();
    }
}
