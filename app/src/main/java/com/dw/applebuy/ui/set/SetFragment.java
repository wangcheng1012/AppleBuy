package com.dw.applebuy.ui.set;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.dw.applebuy.ui.loginreg.ChanagePhoneActivity;
import com.dw.applebuy.ui.loginreg.ForgetPswActivity;
import com.wlj.base.util.AppContext;
import com.wlj.base.util.AppManager;
import com.wlj.base.util.GoToHelp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetFragment extends Fragment  {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set, container, false);
        ButterKnife.bind(this, view);
        initTitle();
        return view;
    }

    private void initTitle() {
        title2Back.setVisibility(View.GONE);
        title2Title.setText("设置");
        title2Right.setText("保存");
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
                GoToHelp.go(getActivity(), ChanagePhoneActivity.class);


                break;
            case R.id.set_about:
                break;
            case R.id.title2_back:
                break;
            case R.id.title2_right:

                break;
            case R.id.set_exit:
                AppContext.getAppContext().loginOut();
                AppManager.getAppManager().AppExit(getContext());
                break;
        }
    }

}
