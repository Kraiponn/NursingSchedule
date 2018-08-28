package com.ksntechnology.nursingschedule.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksntechnology.nursingschedule.R;

public class ViewNursingListChildItem extends FrameLayout {
    private ImageView imgShiftType;
    private TextView tvLocation;
    private TextView tvShiftType;

    public ViewNursingListChildItem(@NonNull Context context) {
        super(context);
        initInflate();
        initInstance();
    }

    public ViewNursingListChildItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance();
    }

    public ViewNursingListChildItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ViewNursingListChildItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstance();
    }

    private void initInflate() {
        inflate(
                getContext(),
                R.layout.child_item_view_layout,
                this);
    }

    private void initInstance() {
        imgShiftType = findViewById(R.id.imageChild_shiftType);
        tvLocation = findViewById(R.id.textChild_location);
        tvShiftType = findViewById(R.id.textChild_shiftType);
    }


    public void setImageShiftType(int imgId) {
        //TODO:: Set image Here
    }

    public void setTextLocation(String text) {
        tvLocation.setText(text);
    }

    public void setTextShiftType(String text) {
        tvShiftType.setText(text);
    }


}
