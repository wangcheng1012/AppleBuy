package com.dw.applebuy.ui;

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


public class Title2Fragment extends Fragment {

    private Title1Fragment.TitleInterface mListener;

    public Title2Fragment() {
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
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_title2, container, false);

        if (mListener != null) {
            TextView title = (TextView) inflate.findViewById(R.id.title2_title);
            mListener.setTitle(title);
        }
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @OnClick(R.id.title2_back)
    public void onClick() {
        getActivity().finish();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Title1Fragment.TitleInterface) {
            mListener = (Title1Fragment.TitleInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement Title1Fragment.TitleInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
