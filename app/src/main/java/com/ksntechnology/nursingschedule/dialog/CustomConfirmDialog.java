package com.ksntechnology.nursingschedule.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.ksntechnology.nursingschedule.R;

public class CustomConfirmDialog extends DialogFragment{
    public enum DataType{
        STRING, NUMBER, PASSWORD
    }

    private onFinishDialogListener mCallBack;

    public interface onFinishDialogListener{
        void onFinishDialog(int itemID);
    }

    public static CustomConfirmDialog newInstance() {
        CustomConfirmDialog fm = new CustomConfirmDialog();
        Bundle args = new Bundle();
        fm.setArguments(args);
        return fm;
    }

    public void setOnFinishDialogListener(onFinishDialogListener listener) {
        mCallBack = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(
                R.layout.fragment_custom_confirm_exit_dialog, null
        );

        Button btnConfirm = view.findViewById(R.id.buttonConfirm);
        Button btnCancel = view.findViewById(R.id.buttonCancel);

        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setView(view);

        return null;
    }


}
