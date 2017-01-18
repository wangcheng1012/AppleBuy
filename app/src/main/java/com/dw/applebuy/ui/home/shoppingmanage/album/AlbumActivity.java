package com.dw.applebuy.ui.home.shoppingmanage.album;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.shoppingmanage.p.AlbumPresenter;
import com.dw.applebuy.ui.home.shoppingmanage.v.Contract;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.wlj.base.util.GoToHelp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 相册管理
 */
public class AlbumActivity extends BaseMvpActivity<Contract.AlbumView, AlbumPresenter> implements Title1Fragment.TitleInterface, Contract.AlbumView {

    @BindView(R.id.album_firstImage)
    ImageView albumFirstImage;
    @BindView(R.id.album_uploadfirst)
    TextView albumUploadfirst;
    @BindView(R.id.album_images)
    ImageView albumImages;
    @BindView(R.id.album_uploadmore)
    TextView albumUploadmore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);

    }

    @Override
    public AlbumPresenter initPresenter() {
        return new AlbumPresenter();
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("门店相册");
    }

    @OnClick({R.id.album_uploadfirst, R.id.album_uploadmore})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.album_uploadfirst:
                GoToHelp.go(this,UpLoadImageActivity.class);
                break;
            case R.id.album_uploadmore:
                GoToHelp.go(this,UpLoadImageActivity.class);
                break;
        }
    }
}
