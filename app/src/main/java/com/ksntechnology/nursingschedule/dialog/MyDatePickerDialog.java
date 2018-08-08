package com.ksntechnology.nursingschedule.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class MyDatePickerDialog extends DialogFragment {

    public static MyDatePickerDialog newInstance() {
        MyDatePickerDialog fragment = new MyDatePickerDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public interface onFinishDialogListener{
        void onFinishDialog(int[] strDate);
    }

    private onFinishDialogListener mCallBack;

    public void setOnFinishDialogListener(onFinishDialogListener listener) {
        mCallBack = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();
        int dd = cal.get(Calendar.DAY_OF_MONTH);
        int mm = cal.get(Calendar.MONTH);
        int yy = cal.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view,
                                  int year, int month, int dayOfMonth) {
                int[] arrDate = new int[3];
                arrDate[0] = dayOfMonth;
                arrDate[1] = month+1;
                arrDate[2] = year;

                mCallBack.onFinishDialog(arrDate);
            }
        }, yy, mm, dd
        );


        return dialog;
    }
}
