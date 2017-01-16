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

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.add.m.YouhuiQuanType;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.add.p.YouHuiAddPresenter;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.add.v.Views;
import com.dw.applebuy.util.DayDialogFragment;
import com.jph.takephoto.model.TResult;
import com.lling.photopicker.PhotoPickerActivity;
import com.lling.photopicker.utils.TakePhotoCrop;
import com.orhanobut.logger.Logger;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.wlj.base.util.AppConfig;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.StringUtils;
import com.wlj.base.util.img.BitmapUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加优惠券
 */
public class YouHuiAddActivity extends BaseMvpActivity<Views.YouHuiAddView, YouHuiAddPresenter> implements Title1Fragment.TitleInterface, DayDialogFragment.TimeChoosed, Views.YouHuiAddView, TakePhotoCrop.CropBack {

    @BindView(R.id.youhuiadd_image)
    ImageView imageView;
    @BindView(R.id.youhuiadd_type)
    TextView type;
    @BindView(R.id.youhuiadd_time)
    TextView youhuiaddTime;
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
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        takePhotoCrop = new TakePhotoCrop(this, this);
        takePhotoCrop.getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_hui_add);
        ButterKnife.bind(this);

    }

    @Override
    public YouHuiAddPresenter initPresenter() {
        return new YouHuiAddPresenter();
    }

    @Override
    public void setTitle(TextView title) {
        title.setText("新增优惠");
    }

    @OnClick({R.id.youhuiadd_type, R.id.youhuiadd_time, R.id.youhuiadd_complate,R.id.youhuiadd_image})
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

                ArrayMap<String, Object> arrayMap = new ArrayMap<>();
                arrayMap.put("sessionid", AppConfig.getAppConfig().get(AppConfig.CONF_KEY));
                arrayMap.put("title", title.getText() + "");
                arrayMap.put("description", intro.getText() + "");
                arrayMap.put("category_id", type.getTag());//分类ID
                arrayMap.put("stock", number.getText());//库存
                arrayMap.put("integral", youhuiaddPrice.getText());//积分
                arrayMap.put("end_time", youhuiaddTime.getText());//优惠时间
                arrayMap.put("file", bitmap);//优惠卷图片

                arrayMap.put("id", number.getText());// 	优惠卷ID(传递则为编辑)
                arrayMap.put("img_path", number.getText());//回传路径(传递则为编辑)

                presenter.addYouHui(arrayMap);
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
//                ArrayList<String> result = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
            ArrayList<String> uristr = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT_URLSTR);

            takePhotoCrop.onCrop(Uri.parse(uristr.get(0)));
        }

    }

    @Override
    public void choosedBack(long date) {
        String time = StringUtils.getTime(date, "yyyy-MM-dd");
        youhuiaddTime.setText(time);
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

        Logger.i("takeSuccess：" + result.getImage().getPath());
          bitmap = BitmapFactory.decodeFile(result.getImage().getPath());
        imageView.setImageBitmap(bitmap);

//        Bundle bundle = new Bundle();
//        bundle.putString("path", result.getImage().getPath());
//        bundle.putInt("from", ImageAdjustmentActivity.from_main_choosework);
//        GoToHelp.go(this, ImageAdjustmentActivity.class, bundle);
    }



}