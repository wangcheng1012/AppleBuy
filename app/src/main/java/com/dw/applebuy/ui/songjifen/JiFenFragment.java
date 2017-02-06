package com.dw.applebuy.ui.songjifen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.wlj.base.util.GoToHelp;
import com.wlj.base.util.MathUtil;
import com.wlj.base.util.StringUtils;
import com.wlj.base.util.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 送积分
 */
public class JiFenFragment extends Fragment {

    @BindView(R.id.title2_title)
    TextView title2Title;
    @BindView(R.id.jifen_jifen)
    TextView jifenJifen;

    private String mParam2;


    public JiFenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ji_fen, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick({R.id.jifen_saosao, R.id.jifen_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jifen_saosao:
                break;
            case R.id.jifen_phone:
                String text = jifenJifen.getText()+"";
                if(StringUtils.isEmpty(text)){
                    UIHelper.toastMessage(getContext(),"赠送的积分必须大于0");
                    return;
                }
                int jifen = MathUtil.parseInteger(text);
                Bundle bundle = new Bundle();
                bundle.putInt("jifen",jifen);
                GoToHelp.go(getActivity(),InputPhoneActivity.class,bundle);
                break;
        }
    }
}
