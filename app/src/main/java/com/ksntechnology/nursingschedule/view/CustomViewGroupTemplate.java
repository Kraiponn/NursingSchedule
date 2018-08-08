package com.ksntechnology.nursingschedule.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ksntechnology.nursingschedule.R;

public class CustomViewGroupTemplate extends FrameLayout {
    private ImageButton btnFromTime;
    private ImageButton btnToTime;
    private ImageButton btnLocation;
    private TextView tvTotalWorkingTime;

    private EditText edtFromTime;
    private EditText edtToTime;
    private EditText edtLocation;
    private EditText edtRemark;


    public CustomViewGroupTemplate(@NonNull Context context) {
        super(context);
        initInflate();
        initInstance();
    }

    public CustomViewGroupTemplate(@NonNull Context context,
                                   @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance();
    }

    public CustomViewGroupTemplate(@NonNull Context context,
                                   @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance();
    }

    @TargetApi(21)
    public CustomViewGroupTemplate(@NonNull Context context,
                                   @Nullable AttributeSet attrs,
                                   int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstance();
    }


    private void initInflate() {
        inflate(getContext(),
                R.layout.add_data_view_group_layout, this);
    }

    private void initInstance() {
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
     *  Listener Zone
     */
    View.OnClickListener btnFromTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //getCurrentTime(v);
        }
    };

    View.OnClickListener btnToTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //getCurrentTime(v);
        }
    };

    View.OnClickListener btnLocationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //getLocation(v);
        }
    };


}
