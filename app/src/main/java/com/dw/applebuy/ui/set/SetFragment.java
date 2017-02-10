package com.dw.applebuy.ui.set;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.been.Info;
import com.dw.applebuy.ui.home.renzheng.p.InfoUtil;
import com.dw.applebuy.ui.loginreg.ChangePhoneActivity;
import com.dw.applebuy.ui.loginreg.ForgetPswActivity;
import com.dw.applebuy.ui.loginreg.LoginActivity;
import com.dw.applebuy.ui.set.p.AboutUsPresenter;
import com.dw.applebuy.ui.set.v.Contracts;
import com.rxmvp.basemvp.BaseMvpFragment;
import com.wlj.base.util.AppContext;
import com.wlj.base.util.AppManager;
import com.wlj.base.util.GoToHelp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class SetFragment extends BaseMvpFragment<Contracts.AboutUsView,AboutUsPresenter> implements Contracts.AboutUsView {

    @BindView(R.id.title2_title)
    TextView title2Title;
    @BindView(R.id.title2_right)
    TextView title2Right;
    @BindView(R.id.title2_back)
    ImageView title2Back;

    @BindView(R.id.set_accont)
    TextView setAccont;
    @BindView(R.id.set_aissaPhone)
    TextView setAissaPhone;
    @BindView(R.id.set_modifypsw)
    RelativeLayout setModifypsw;
    @BindView(R.id.set_changephone)
    RelativeLayout setChangephone;
    @BindView(R.id.set_about)
    RelativeLayout setAbout;

    public SetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public AboutUsPresenter initPresenter() {
        return new AboutUsPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set, container, false);
        ButterKnife.bind(this, view);
        initTitle();
        initView();
        return view;
    }

    private void initView() {
        InfoUtil.getInstall().getInfo(getActivity(), new InfoUtil.InfoBack() {
            @Override
            public void back(Info info) {
                String contact_tel = info.getContact_tel();
                setAissaPhone.setText(contact_tel);
                setAccont.setText(info.getName());
            }
        });
    }

    private void initTitle() {
        title2Back.setVisibility(View.GONE);
        title2Title.setText("设置");
//        title2Right.setText("保存");
    }

    @OnClick({R.id.set_modifypsw, R.id.set_changephone, R.id.set_about, R.id.title2_back, R.id.title2_right,R.id.set_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.set_modifypsw:
                Bundle bundle = new Bundle();
                bundle.putString("title", "修改密码");
                GoToHelp.go(getActivity(), ForgetPswActivity.class, bundle);
                break;
            case R.id.set_changephone:
                GoToHelp.go(getActivity(), ChangePhoneActivity.class);
                break;
            case R.id.set_about:
                presenter.getAboutUs();
                break;
            case R.id.title2_back:
                break;
            case R.id.title2_right:
                break;
            case R.id.set_exit:
                 new SweetAlertDialog(getActivity())
                 .setTitleText("提示")
                 .setContentText("确认退出？")
                 .setConfirmText("确认")
                 .setCancelText("取消")
                 .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                     @Override
                     public void onClick(SweetAlertDialog sweetAlertDialog) {
                         AppContext.getAppContext().loginOut();
                         GoToHelp.go(getActivity(), LoginActivity.class);
                         AppManager.getAppManager().finishAllActivity();
                         System.gc();
                     }
                 })
                 .show();

                break;
        }
    }

    @Override
    public void aboutUs(String aboutUsModel) {
         Bundle about = new Bundle();
         about.putString("AboutUs", aboutUsModel);
         GoToHelp.go(getActivity() ,AboutUsActivity.class,about);
    }
}
