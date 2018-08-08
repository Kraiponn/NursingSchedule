package com.ksntechnology.nursingschedule.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.ksntechnology.nursingschedule.R;

public class MyAlertDialog extends DialogFragment {
    private final boolean ALERT_TYPE_ERROR = true;
    private final boolean ALERT_TYPE_WARNING = false;

    public static MyAlertDialog newInstance(
            String title, String msg, boolean alertType
    ) {
        MyAlertDialog fragment = new MyAlertDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("msg", msg);
        args.putBoolean("alertType", alertType);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        String msg = getArguments().getString("msg");
        boolean alertTpe =
                getArguments().getBoolean("alertType", false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(msg);

        if (alertTpe == ALERT_TYPE_ERROR) {
            builder.setIcon(R.drawable.ic_error);
        } else if(alertTpe == ALERT_TYPE_WARNING){
            builder.setIcon(R.drawable.ic_warning);
        }

        builder.setCancelable(true);
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
