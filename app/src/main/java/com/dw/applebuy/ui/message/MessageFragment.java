package com.dw.applebuy.ui.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dw.applebuy.R;
import com.wlj.base.ui.BaseFragment;


public class MessageFragment extends BaseFragment {
    Fragment target;

    /**
     * Fragment的布局
     *
     * @return
     */
    @Override
    protected int getlayout() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {

        initTitle();
        if (target == null) {
            target = new MessageFragmentList();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.message_content, target, "MessageFragmentList");
            ft.commitAllowingStateLoss();
        }
    }

    private void initTitle(   ) {
        view.findViewById(R.id.title2_back).setVisibility(View.GONE);
        TextView title = (TextView) view.findViewById(R.id.title2_title);
        title.setText("我的消息");

    }


}
