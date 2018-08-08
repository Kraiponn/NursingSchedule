package com.ksntechnology.nursingschedule.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class MyTimePickerDialog extends DialogFragment {

    public static MyTimePickerDialog newInstance() {
        MyTimePickerDialog fragment = new MyTimePickerDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public interface onFinishDialogListener{
        void onFinishDialog(int[] intArrTime);
    }

    private onFinishDialogListener mCallBack;

    public void setOnFinishDialogListener(onFinishDialogListener listener) {
        mCallBack = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();
        int hh = cal.get(Calendar.HOUR_OF_DAY);
        int mm = cal.get(Calendar.MINUTE);
        //int ss = cal.get(Calendar.SECOND);

        TimePickerDialog dialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view,
                                          int hourOfDay, int minute) {
                        /*Calendar tim = Calendar.getInstance();
                        tim.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        tim.set(Calendar.MINUTE, minute);

                        String am_pm = "";
                        if (tim.get(Calendar.AM_PM) == Calendar.AM) {
                            am_pm = "AM";
                        } else {
                            am_pm = "PM";
                        }

                        int[] arrTime = new int[2];
                        arrTime[0] = tim.get(Calendar.HOUR);
                        arrTime[1] = tim.get(Calendar.MINUTE);*/

                        /*arrTime[0] =
                                (tim.get(Calendar.HOUR) == 0) ? 12 : tim.get(Calendar.HOUR);
                        arrTime[1] =
                                (tim.get(Calendar.MINUTE) == 0) ? 12 : tim.get(Calendar.MINUTE);*/

                        int[] arrTime = new int[2];
                        arrTime[0] = hourOfDay;
                        arrTime[1] = minute;
                        mCallBack.onFinishDialog(arrTime);
                    }
                }, hh, mm, true);


        return dialog;
    }
}
