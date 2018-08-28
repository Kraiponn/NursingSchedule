package com.ksntechnology.nursingschedule.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.ksntechnology.nursingschedule.R;

public class CustomViewGroupTemplate extends FrameLayout {
    
    public CustomViewGroupTemplate(@NonNull Context context) {
        super(context);
        initInflate();
        initInstance();
    }

    public CustomViewGroupTemplate(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance();
    }

    public CustomViewGroupTemplate(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomViewGroupTemplate(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
    }


}
