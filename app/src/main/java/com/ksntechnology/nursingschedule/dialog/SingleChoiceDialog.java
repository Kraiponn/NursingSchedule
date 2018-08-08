package com.ksntechnology.nursingschedule.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.DatePicker;

import java.util.Calendar;

public class SingleChoiceDialog extends DialogFragment {
    private int mIndex = -1;

    public static SingleChoiceDialog newInstance(
            String msg, String[] items,
            String posText
    ) {
        SingleChoiceDialog fragment = new SingleChoiceDialog();
        Bundle args = new Bundle();
        args.putString("msg", msg);
        args.putStringArray("items", items);
        args.putString("posText", posText);
        fragment.setArguments(args);
        return fragment;
    }

    public interface onFinishDialogListener{
        void onFinishDialog(int selectIndex);
    }

    private onFinishDialogListener mCallBack;

    public void setOnFinishDialogListener(onFinishDialogListener listener) {
        mCallBack = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String msg = getArguments().getString("msg");
        String[] items = getArguments().getStringArray("items");
        String posText = getArguments().getString("posText");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(msg)
                .setSingleChoiceItems(items, -1,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mIndex = which;
                    }
                })
                .setPositiveButton(posText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallBack.onFinishDialog(mIndex);
                    }
                });

        return builder.create();
    }
}
