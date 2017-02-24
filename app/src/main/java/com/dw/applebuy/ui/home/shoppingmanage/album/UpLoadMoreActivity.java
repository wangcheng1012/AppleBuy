package com.dw.applebuy.ui.home.shoppingmanage.album;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dw.applebuy.R;
import com.dw.applebuy.been.Info;
import com.dw.applebuy.been.ImageBean;
import com.dw.applebuy.ui.home.renzheng.p.InfoUtil;
import com.dw.applebuy.ui.home.shoppingmanage.p.UpLoadMorePresenter;
import com.dw.applebuy.ui.home.shoppingmanage.v.Contract;
import com.jph.takephoto.model.TResult;
import com.lling.photopicker.utils.TakePhotoCrop;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.rxmvp.bean.HttpResult;
import com.wlj.base.decoration.DividerDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpLoadMoreActivity extends BaseMvpActivity<Contract.UpLoadMoreView, UpLoadMorePresenter> implements Contract.UpLoadMoreView, TakePhotoCrop.CropBack {

    @BindView(R.id.uploadmore_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.upload_image_image)
    ImageView uploadImageImage;

    private TakePhotoCrop takePhotoCrop;
    private String path;
    private CommonAdapter<ImageBean> commonAdapter;
    private List<ImageBean> imgs;
    private ImageBean imagebean;
    private Info info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        takePhotoCrop = new TakePhotoCrop(this, this);
        takePhotoCrop.getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_more);
        ButterKnife.bind(this);

        initRecyclerview();
    }

    private void initRecyclerview() {

        info = (Info) getIntent().getSerializableExtra("info");
        imgs = info.getImgs();
        if (imgs == null) {
            imgs = new ArrayList<>();
            info.setImgs(imgs);
        }
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        commonAdapter = new CommonAdapter<ImageBean>(getApplicationContext(), R.layout.item_uploadmore_listitem, imgs) {

            @Override
            protected void convert(ViewHolder holder, ImageBean o, int position) {
                UpLoadMoreActivity.this.imagebean = o;

                ImageView image = holder.getView(R.id.uploadmore_listitem_image);
                Glide.with(UpLoadMoreActivity.this).load(o.getUrl()).into(image);

                holder.getView(R.id.uploadmore_listitem_del)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                presenter.del(UpLoadMoreActivity.this, imagebean,info);
                            }
                        });

            }
        };

        recyclerview.setAdapter(commonAdapter);
        recyclerview.addItemDecoration(new DividerDecoration(getResources().getDrawable(R.drawable.divider_white_f1f1f1_10), DividerDecoration.HORIZONTAL_LIST));
    }

    @Override
    public UpLoadMorePresenter initPresenter() {
        return new UpLoadMorePresenter();
    }

    @Override
    public void delBack(HttpResult httpResult) {
        boolean remove = imgs.remove(imagebean);
        commonAdapter.notifyDataSetChanged();
    }

    @Override
    public void uploadDetailsBack() {
//        imgs.addAll(list); map中已经添加
        commonAdapter.notifyDataSetChanged();
        Glide.with(this).load(R.drawable.upload_image).into(uploadImageImage);

    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        takePhotoCrop.getTakePhoto().onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        takePhotoCrop.getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TakePhotoCrop.IMAGE) {

                data.putExtra("aspectX", 16);
                data.putExtra("aspectY", 9);
                takePhotoCrop.onCrop(data);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        takePhotoCrop.onRequestPermissionsResult_(requestCode, permissions, grantResults);
    }

    /**
     * 裁剪返回
     *
     * @param result
     */
    @Override
    public void cropback(TResult result) {
        path = result.getImage().getPath();
        uploadImageImage.setImageBitmap(BitmapFactory.decodeFile(path));
    }

    @OnClick({R.id.upload_image_image, R.id.upload_image_upbt, R.id.title_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upload_image_image:
                takePhotoCrop.photoPicker();
                break;
            case R.id.upload_image_upbt:
                presenter.uploadDetailsImgs(path, info);
                break;
            case R.id.title_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
