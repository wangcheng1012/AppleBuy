package com.dw.applebuy.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dw.applebuy.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Title1Fragment extends Fragment {
    private TitleInterface ti;

    public Title1Fragment() {
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
        View inflate = inflater.inflate(R.layout.title1, container, false);
        ButterKnife.bind(this, inflate);

        if(ti != null){
            TextView title = (TextView) inflate.findViewById(R.id.title_);
            ti.setTitle(title);
        }

        return inflate;
    }

    @OnClick(R.id.back_)
    public void onClick() {
        getActivity().finish();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TitleInterface) {
            ti = (TitleInterface) context;
        }
    }

    public interface TitleInterface {
        void setTitle(TextView title);
    }
}
