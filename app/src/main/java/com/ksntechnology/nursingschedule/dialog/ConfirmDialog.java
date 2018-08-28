package com.ksntechnology.nursingschedule.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.ksntechnology.nursingschedule.R;

public class ConfirmDialog extends DialogFragment {
    private static final int CONFIRM_DELETE = 1;
    private static final int CONFIRM_EDIT = 2;

    public static ConfirmDialog newInstance(
            String msg, String posText, String negText) {
        ConfirmDialog fragment = new ConfirmDialog();
        Bundle args = new Bundle();
        args.putString("msg", msg);
        args.putString("negText", negText);
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
        String negText = getArguments().getString("negText");
        String posText = getArguments().getString("posText");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("เลือกรายการ")
                .setIcon(R.drawable.ic_confirm)
                .setMessage(msg)
                .setNegativeButton(negText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallBack.onFinishDialog(CONFIRM_EDIT);
                    }
                })
                .setPositiveButton(posText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallBack.onFinishDialog(CONFIRM_DELETE);
                    }
                });

        return builder.create();
    }

}
