package com.dw.applebuy.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dw.applebuy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择开始结束时间
 */
public class ChooseStartEndTimeDialogFragment extends DialogFragment {

    @BindView(R.id.timePicker_start)
    TimePicker timePickerstart;
    @BindView(R.id.timePicker_end)
    TimePicker timePickerend;
    @BindView(R.id.startendtime_note)
    TextView note;
    private TimeChoosed mTimeChoosed;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TimeChoosed) {
            mTimeChoosed = ( TimeChoosed) context;
        } else {
            throw new RuntimeException("context not implements TimeChoosed");
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
        //更多样式查看 http://blog.csdn.net/l_lhc/article/details/50495664
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")));
        View view = inflater.inflate(R.layout.dialog_startendtime, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        timePickerstart.setIs24HourView(true);
        timePickerend.setIs24HourView(true);
    }

    /**
     * 隐藏 年
     *
     * @param dp
     */
    private void hideYear(DatePicker dp) {

        if (dp != null) {

            if (Build.VERSION.SDK_INT < 11) {
                ((ViewGroup) dp.getChildAt(0)).getChildAt(0).setVisibility(View.GONE);
//                ((ViewGroup) dp.getChildAt(0)).getChildAt(2).setVisibility( View.GONE);
            } else if (Build.VERSION.SDK_INT > 14) {
                View view1 = ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(0);
                view1.setVisibility(View.GONE);
//                View view2 = ((ViewGroup) ((ViewGroup) dp.getChildAt(0)) .getChildAt(0)).getChildAt(2);
//                view2.setVisibility(View.GONE);
            }
        }

    }

    @OnClick({R.id.dialog_daychoose_canl, R.id.dialog_daychoose_sure})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.dialog_daychoose_sure:
                int startHour = timePickerstart.getCurrentHour();
                Integer cstartMinute = timePickerstart.getCurrentMinute();

                int endHour = timePickerend.getCurrentHour();
                Integer endMinute = timePickerend.getCurrentMinute();
                String  time = startHour+":"+cstartMinute +"-"+endHour+":"+endMinute;
                mTimeChoosed.choosedBack(time);
            case R.id.dialog_daychoose_canl:
                dismiss();
                break;
        }
    }


    public interface TimeChoosed{

        void choosedBack(String time);
    }
}
