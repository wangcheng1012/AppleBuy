package com.dw.applebuy.util;


import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;

import com.dw.applebuy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 日期选择 Dialog
 */
public class DayDialogFragment extends DialogFragment {

    @BindView(R.id.datePicker)
    DatePicker datePicker;
    private TimeChoosed mTimeChoosed;

    public static DayDialogFragment newInstance() {

        Bundle args = new Bundle();
        DayDialogFragment fragment = new DayDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //    @Override
//    public void onStart() {
//        // TODO Auto-generated method stub
//        super.onStart();
//
//        Window window = getDialog().getWindow();
//        WindowManager.LayoutParams windowParams = window.getAttributes();
//        windowParams.dimAmount = 0.0f;
//
//        window.setAttributes(windowParams);
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        setStyle(DialogFragment.STYLE_NO_FRAME,   android.R.style.Theme_Holo_Light );
        //更多样式查看 http://blog.csdn.net/l_lhc/article/details/50495664
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")));
        View view = inflater.inflate(R.layout.dialog_daychoose, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.dialog_daychoose_canl, R.id.dialog_daychoose_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_daychoose_canl:
                dismiss();
                break;
            case R.id.dialog_daychoose_sure:

                CalendarView calendarView = datePicker.getCalendarView();
                long date = calendarView.getDate();
//                String time = StringUtils.getTime(date, "yyyy-MM-dd");

                mTimeChoosed.choosedBack(date);
//                int dayOfMonth = datePicker.getDayOfMonth();
//                int month = datePicker.getMonth()+1;
//                int year = datePicker.getYear();
//
//                Logger.d(time +"  "+ year +"_"+ month  +"_"+ dayOfMonth );

                dismiss();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof TimeChoosed ){
              mTimeChoosed = (TimeChoosed) context;
        }else{
            throw new RuntimeException("context not implements TimeChoosed");
        }

    }

    public interface TimeChoosed{

        void choosedBack(long time);
    }

}
