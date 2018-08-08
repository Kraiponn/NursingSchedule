package com.ksntechnology.nursingschedule.view;

import android.annotation.TargetApi;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.dialog.MyTimePickerDialog;

import java.util.Calendar;

public class AddItemViewGroup extends FrameLayout {
    private ImageButton btnFromTime;
    private ImageButton btnToTime;
    private ImageButton btnLocation;
    private TextView tvTotalWorkingTime;
    private CheckBox chkShiftType;

    private EditText edtFromTime;
    private EditText edtToTime;
    private EditText edtLocation;
    private EditText edtRemark;

    private final String TOTAL_WORKING_TIME = "จำนวนเวลาทำงาน : ";
    private final String[] arrLocation = {
            "โรงพยาบาลราชวิถี", "โรงพยาบาลพระมงกุฏเกล้า", "โรงพยาบาลการุญเวช",
            "บริษัท NXP", "บริษัท Fabrinet"
    };
    private String total_time;


    public AddItemViewGroup(@NonNull Context context) {
        super(context);
        initInflate();
        initInstance();
    }

    public AddItemViewGroup(@NonNull Context context,
                            @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance();
    }

    public AddItemViewGroup(@NonNull Context context,
                            @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance();
    }

    @TargetApi(21)
    public AddItemViewGroup(@NonNull Context context,
                            @Nullable AttributeSet attrs,
                            int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstance();
    }


    private void toastMessage(String text) {
        Toast.makeText(getContext(),
                text, Toast.LENGTH_SHORT).show();
    }

    private void initInflate() {
        inflate(getContext(),
                R.layout.add_data_view_group_layout, this);
    }

    private void initInstance() {
        chkShiftType = findViewById(R.id.checkbox_shiftType);
        edtFromTime = findViewById(R.id.edit_fromTime);
        edtToTime = findViewById(R.id.edit_toTime);
        edtLocation = findViewById(R.id.edit_location);
        edtRemark = findViewById(R.id.edit_remark);
        btnFromTime = findViewById(R.id.imageButton_fromTime);
        btnToTime = findViewById(R.id.imageButton_toTime);
        btnLocation = findViewById(R.id.imageButton_location);
        tvTotalWorkingTime = findViewById(R.id.text_totalWorkingTime);

        btnFromTime.setOnClickListener(btnFromTimeClickListener);
        btnLocation.setOnClickListener(btnLocationClickListener);
        btnToTime.setOnClickListener(btnToTimeClickListener);
    }




    /*************************************
     *  Custom Method Zone
     */
    public void setShiftText(String text) {
        chkShiftType.setText(text);
    }

    public void setTotalWoring() {
        String[] arrFromTime =
                edtFromTime.getText().toString().trim().split(":");
        String[] arrToTime =
                edtToTime.getText().toString().trim().split(":");

        /*Log.d("Time", arrFromTime[0] + " : " + arrFromTime[1]);
        Log.d("Time", arrToTime[0] + " : " + arrToTime[1]);*/

        int[] fromTime = new int[2];
        fromTime[0] = Integer.parseInt(arrFromTime[0]);
        fromTime[1] = Integer.parseInt(arrFromTime[1]);
        //Log.d("Time", "FromTime "+fromTime[0]+":"+fromTime[1]);

        int[] toTime = new int[2];
        toTime[0] = Integer.parseInt(arrToTime[0]);
        toTime[1] = Integer.parseInt(arrToTime[1]);
        //Log.d("Time", "ToTime "+toTime[0]+":"+toTime[1]);

        int total_hour = toTime[0] - fromTime[0];
        int total_minute = toTime[1] - fromTime[1];
        total_time = "";
        total_time = ((total_hour < 10) ? "0" : "") + total_hour + ":";
        total_time += ((total_minute < 10) ? "0" : "") + total_minute + ":00";
        tvTotalWorkingTime.setText(TOTAL_WORKING_TIME + total_time);

        Log.d("Time", "Total Time " + total_time);
    }

    public void getCurrentTime(final View view) {
        /*MyTimePickerDialog dialog = MyTimePickerDialog.newInstance();
        dialog.show(get, null);

        dialog.setOnFinishDialogListener(
                new MyTimePickerDialog.onFinishDialogListener() {
                    @Override
                    public void onFinishDialog(int[] strTime) {
                        String arrTime = "";
                        arrTime += ((strTime[0]<10) ? "0" : "") + strTime[0] + ":";
                        arrTime += ((strTime[1]<10) ? "0" : "") + strTime[1] + ":00";
                        if (view.getId() == R.id.imageButton_fromTimeMor) {
                            edtFromTime.setText(arrTime);
                        } else if (view.getId() == R.id.imageButton_toTimeMor) {
                            edtToTime.setText(arrTime);
                            setTotalWoring();
                        }
                    }
                });*/
        Calendar cal = Calendar.getInstance();
        int hh = cal.get(Calendar.HOUR_OF_DAY);
        int mm = cal.get(Calendar.MINUTE);
        //int ss = cal.get(Calendar.SECOND);

        TimePickerDialog timeDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view,
                                          int hourOfDay, int minute) {
                        int[] intArrtime = new int[2];
                        intArrtime[0] = hourOfDay;
                        intArrtime[1] = minute;

                        String arrTime = "";
                        arrTime += ((intArrtime[0]<10) ? "0" : "") + intArrtime[0] + ":";
                        arrTime += ((intArrtime[1]<10) ? "0" : "") + intArrtime[1] + ":00";
                        if (view.getId() == R.id.imageButton_fromTimeMor) {
                            edtFromTime.setText(arrTime);
                        } else if (view.getId() == R.id.imageButton_toTimeMor) {
                            edtToTime.setText(arrTime);
                            setTotalWoring();
                        }
                    }
                }, hh, mm, true);
        timeDialog.show();
    }


    /*************************************
     *  Listener Zone
     */
    OnClickListener btnFromTimeClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //getCurrentTime(v);
        }
    };

    OnClickListener btnToTimeClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //getCurrentTime(v);
        }
    };

    OnClickListener btnLocationClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //getLocation(v);
        }
    };


}
