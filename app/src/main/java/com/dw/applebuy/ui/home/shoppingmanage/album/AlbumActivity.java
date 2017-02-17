package com.dw.applebuy.ui.home.shoppingmanage.album;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dw.applebuy.R;
import com.dw.applebuy.been.Info;
import com.dw.applebuy.ui.Title1Fragment;
import com.dw.applebuy.ui.home.renzheng.p.InfoUtil;
import com.dw.applebuy.ui.home.shoppingmanage.p.AlbumPresenter;
import com.dw.applebuy.ui.home.shoppingmanage.v.Contract;
import com.rxmvp.basemvp.BaseMvpActivity;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.ListUtils;

import java.util.List;

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
    private Info info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);

        updataInfo();
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
                Bundle bundle2 = new Bundle();
//                bundle2.putString("tip","手持证件人面部无遮挡，五官清晰可见\n 身份证各项信息及头像均清晰可见，无遮挡");
                bundle2.putInt("requestCode", UpLoadImageActivity.album_uploadfirst);
                bundle2.putString("path",info.getCover_img());
                GoToHelp.goResult(this, UpLoadImageActivity.class, UpLoadImageActivity.album_uploadfirst, bundle2);
                break;
            case R.id.album_uploadmore:
                Bundle bundle = new Bundle();
                bundle.putString("tip", "请上传清晰的店铺外内景图，可大大提高曝光率以及用户购买率（可上传15张）");
                bundle.putInt("requestCode", UpLoadImageActivity.album_uploadmore);
                bundle.putSerializable("info",info);
                GoToHelp.goResult(this, UpLoadImageActivity.class, UpLoadImageActivity.album_uploadmore, bundle);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            updataInfo();

        }

    }

    private void updataInfo() {

        InfoUtil.getInstall().getInfo(this, new InfoUtil.InfoBack() {

            @Override
            public void back(Info info) {
                AlbumActivity.this.info = info;

                Glide.with(AlbumActivity.this).load(info.getCover_img()).error(R.drawable.icon_41_renzheng).into(albumFirstImage);
                List<Info.ImgsBean> imgs = info.getImgs();
                if (!ListUtils.isEmpty(imgs)) {
                    Glide.with(AlbumActivity.this).load(imgs.get(0).getUrl()).error(R.drawable.icon_41_renzheng).into(albumImages);
                    albumUploadmore.setText("已上传 " + imgs.size() + " 张环境图、点击继续上传");
                } else {
                    albumUploadmore.setText("已上传 0 张环境图、点击继续上传");
                }

            }
        });
    }
}
